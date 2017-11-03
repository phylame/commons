package pw.phylame.commons.flob;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface Flob {
    String getName();

    String getMimeType();

    InputStream openStream() throws IOException;

    default void writeTo(@NonNull OutputStream output) throws IOException {
        try (val input = openStream()) {
            IOUtils.copy(input, output);
        }
    }

    static Flob of(@NonNull URL url, String mime) {
        return new URLFlob(url, mime);
    }
}
