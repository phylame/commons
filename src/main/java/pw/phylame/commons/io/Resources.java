package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.Reflections;
import pw.phylame.commons.Validate;
import pw.phylame.commons.value.Lazy;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public static final String PREFIX = "!";

    public static String resourcePath(Class<?> clazz, String name) {
        return PREFIX + Reflections.resolvePath(clazz, name);
    }

    public static URL locate(String uri) {
        return locate(uri, null);
    }

    public static URL locate(String uri, ClassLoader loader) {
        Validate.nonEmpty(uri, "`uri` cannot be empty");
        if (uri.startsWith(PREFIX)) {
            return (loader != null ? loader : Reflections.currentClassLoader()).getResource(uri.substring(PREFIX.length()));
        }
        val path = Paths.get(uri);
        if (Files.exists(path)) {
            try {
                return path.toUri().toURL();
            } catch (MalformedURLException e) {
                throw new InternalError();
            }
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

    @SneakyThrows(IOException.class)
    public static Properties getProperties(String uri, ClassLoader loader, boolean useCache) {
        val input = open(uri, loader, useCache);
        if (input != null) {
            val props = new Properties();
            props.load(input);
            return props;
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

    public static Resource of(byte[] data, String name) {
        return of(data, name, null);
    }

    public static Resource of(@NonNull byte[] data, String name, String mime) {
        Validate.nonEmpty(name, "`name` cannot be empty");
        return new AbstractResource(mime) {
            @Override
            public long size() {
                return data.length;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public InputStream openStream() {
                return new ByteArrayInputStream(data);
            }

            @Override
            public String toString() {
                return "bytes://" + super.toString();
            }
        };
    }

    public static Resource of(Path path) {
        return of(path, null);
    }

    public static Resource of(@NonNull Path path, String mime) {
        return new AbstractResource(mime) {
            @Override
            public long size() {
                try {
                    return Files.size(path);
                } catch (IOException e) {
                    return -1L;
                }
            }

            @Override
            public String getName() {
                return path.toString();
            }

            @Override
            public InputStream openStream() throws IOException {
                return Files.newInputStream(path);
            }
        };
    }

    public static Resource of(URL url) {
        return new UrlResource(url, url.getPath(), null);
    }

    public static Resource of(URL url, String name) {
        return new UrlResource(url, name, null);
    }

    public static Resource of(URL url, String name, String mime) {
        return new UrlResource(url, name, mime);
    }

    public static Resource of(VdmReader reader, VdmEntry entry) {
        return new VdmResource(reader, entry, null);
    }

    public static Resource of(VdmReader reader, VdmEntry entry, String mime) {
        return new VdmResource(reader, entry, mime);
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
