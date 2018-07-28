package pw.phylame.commons.io;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.text.StringUtils;
import pw.phylame.commons.value.Lazy;
import pw.phylame.commons.value.Value;

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

    private final Value<String> contentType;

    private final Lazy<Long> contentLength = Lazy.of(() -> {
        try {
            return openConnection().getContentLengthLong();
        } catch (IOException e) {
            return -1L;
        }
    });

    UrlResource(@NonNull URL url, String name, String contentType) {
        this.url = url;
        this.name = StringUtils.isNotEmpty(name) ? name : StringUtils.partition(url.toString(), "?").getFirst();
        if (contentType != null) {
            this.contentType = Value.of(contentType);
        } else {
            this.contentType = Lazy.of(() -> {
                try {
                    return openConnection().getContentType();
                } catch (IOException e) {
                    return FilenameUtils.UNKNOWN_MIME_TYPE;
                }
            });
        }
    }

    @Override
    public long size() {
        return contentLength.get();
    }

    @Override
    public String getContentType() {
        return contentType.get();
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
