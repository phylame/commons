package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.Reflections;
import pw.phylame.commons.Validate;
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

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Resources {
    public static final String PREFIX = "!";

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

    public static InputStream open(String uri, ClassLoader loader, boolean useCache) throws IOException {
        val url = locate(uri, loader);
        if (url != null) {
            val conn = url.openConnection();
            conn.setUseCaches(useCache);
            return conn.getInputStream();
        }
        return null;
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
}
