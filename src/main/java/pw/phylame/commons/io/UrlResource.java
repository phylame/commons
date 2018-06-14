package pw.phylame.commons.io;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.Validate;
import pw.phylame.commons.value.Lazy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
class UrlResource implements Resource {
    private final URL url;

    @Getter
    private final String name;

    private volatile String contentType;

    private final Lazy<Long> contentLength = Lazy.of(() -> {
        try {
            return openConnection().getContentLengthLong();
        } catch (IOException e) {
            return -1L;
        }
    });

    UrlResource(@NonNull URL url, String name, String contentType) {
        this.url = url;
        this.name = Validate.nonEmpty(name);
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        if (contentType == null) {
            synchronized (this) {
                if (contentType == null) {
                    try {
                        contentType = openConnection().getContentType();
                    } catch (IOException e) {
                        contentType = FilenameUtils.UNKNOWN_MIME_TYPE;
                    }
                }
            }
        }
        return contentType;
    }

    @Override
    public long size() {
        return contentLength.get();
    }

    @Override
    public InputStream openStream() throws IOException {
        return openConnection().getInputStream();
    }

    @Override
    public String toString() {
        return url + ";mime=" + getContentType();
    }

    private URLConnection openConnection() throws IOException {
        val conn = url.openConnection();
        conn.setUseCaches(false);
        conn.connect();
        return conn;
    }
}
