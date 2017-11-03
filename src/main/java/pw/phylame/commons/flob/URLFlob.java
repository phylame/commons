package pw.phylame.commons.flob;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.io.PathUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static pw.phylame.commons.text.StringUtils.isEmpty;

class URLFlob implements Flob {
    private final URL url;

    @Getter
    private final String name;

    @Getter
    private final String mimeType;

    @SneakyThrows(IOException.class)
    URLFlob(@NonNull URL url, String mime) {
        this.url = url;
        name = PathUtils.fullName(url.getPath());
        if (isEmpty(mime)) {
            mime = PathUtils.mimeType(name);
        }
        if (isEmpty(mime)) {
            mime = IOUtils.UNKNOWN_MIME;
        }
        mimeType = mime;
    }

    @Override
    public InputStream openStream() throws IOException {
        return url.openStream();
    }

    @Override
    public String toString() {
        return url.toString() + ";mime=" + mimeType;
    }
}
