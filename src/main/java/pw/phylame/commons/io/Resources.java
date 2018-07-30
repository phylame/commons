package pw.phylame.commons.io;

import lombok.val;
import pw.phylame.commons.Reflections;
import pw.phylame.commons.Validate;
import pw.phylame.commons.value.Lazy;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Resources {
    public static final String CLASSPATH_PREFIX = "!";

    public static URL locate(String uri) {
        return locate(uri, null);
    }

    public static URL locate(String uri, ClassLoader loader) {
        Validate.nonEmpty(uri, "`uri` cannot be empty");
        if (uri.startsWith(CLASSPATH_PREFIX)) {
            return (loader != null ? loader : Reflections.currentClassLoader()).getResource(uri.substring(CLASSPATH_PREFIX.length()));
        }
        try {
            return Paths.get(uri).toUri().toURL();
        } catch (InvalidPathException | MalformedURLException ignored) {
        }
        try {
            return new URL(uri);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static InputStream open(String uri) throws IOException {
        return open(uri, null, true);
    }

    public static InputStream open(String uri, ClassLoader loader) throws IOException {
        return open(uri, loader, true);
    }

    public static InputStream open(String uri, ClassLoader loader, boolean useCache) throws IOException {
        val url = locate(uri, loader);
        if (url != null) {
            val conn = url.openConnection();
            conn.setUseCaches(useCache);
            return conn.getInputStream();
        }
        return null;
    }

    public static String resourcePath(Class<?> clazz, String name) {
        return CLASSPATH_PREFIX + Reflections.resolvePath(clazz, name);
    }

    public static Lazy<Properties> lazyProperties(String uri) {
        return lazyProperties(uri, null, true);
    }

    public static Lazy<Properties> lazyProperties(String uri, ClassLoader loader) {
        return lazyProperties(uri, loader, true);
    }

    public static Lazy<Properties> lazyProperties(String uri, ClassLoader loader, boolean useCache) {
        return Lazy.of(() -> {
            val props = getProperties(uri, loader, useCache);
            if (props == null) {
                throw new RuntimeException("No such properties: " + uri);
            }
            return props;
        });
    }

    public static Lazy<Properties> lazyProperties(Class<?> clazz, String name) {
        return lazyProperties(resourcePath(clazz, name), clazz.getClassLoader(), true);
    }

    public static Properties getProperties(String uri) {
        return getProperties(uri, null, true);
    }

    public static Properties getProperties(String uri, ClassLoader loader) {
        return getProperties(uri, loader, true);
    }

    public static Properties getProperties(String uri, ClassLoader loader, boolean useCache) {
        try {
            val input = open(uri, loader, useCache);
            if (input != null) {
                val props = new Properties();
                props.load(input);
                return props;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return null;
    }

    public static Properties getProperties(Class<?> clazz, String name) {
        return getProperties(resourcePath(clazz, name), clazz.getClassLoader(), true);
    }

    public static ResourceBundle getResourceBundle(String name) {
        return ResourceBundle.getBundle(name, ResourceControl.INSTANCE);
    }

    public static ResourceBundle getResourceBundle(String name, Locale locale) {
        return ResourceBundle.getBundle(name, locale, ResourceControl.INSTANCE);
    }

    public static ResourceBundle getResourceBundle(String name, Locale locale, ClassLoader loader) {
        return ResourceBundle.getBundle(name, locale, loader, ResourceControl.INSTANCE);
    }

    private static class ResourceControl extends ResourceBundle.Control {
        private static final ResourceControl INSTANCE = new ResourceControl();

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            if (format.equals("java.properties")) {
                val stream = open(toBundleName(baseName, locale), loader, !reload);
                if (stream != null) {
                    try {
                        return new PropertyResourceBundle(stream);
                    } finally {
                        stream.close();
                    }
                }
                return null;
            } else {
                return super.newBundle(baseName, locale, format, loader, reload);
            }
        }
    }
}
