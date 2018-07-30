package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.Validate;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface Resource {
    long size();

    String getName();

    String getContentType();

    InputStream openStream() throws IOException;

    default void transferTo(ByteSink output) throws IOException {
        Validate.nonNull(output);
        try (val input = openStream()) {
            IOUtils.copy(ByteSource.of(input), output, -1);
        }
    }

    static Resource of(byte[] data, String name) {
        return of(data, name, null);
    }

    static Resource of(@NonNull byte[] data, String name, String mime) {
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

    static Resource of(Path path) {
        return of(path, null);
    }

    static Resource of(@NonNull Path path, String mime) {
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

    static Resource of(URL url) {
        return new UrlResource(url, url.getPath(), null);
    }

    static Resource of(URL url, String name) {
        return new UrlResource(url, name, null);
    }

    static Resource of(URL url, String name, String mime) {
        return new UrlResource(url, name, mime);
    }

    static Resource of(VdmReader reader, VdmEntry entry) {
        return new VdmResource(reader, entry, null);
    }

    static Resource of(VdmReader reader, VdmEntry entry, String mime) {
        return new VdmResource(reader, entry, mime);
    }
}
