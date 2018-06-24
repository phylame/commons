package pw.phylame.commons.io;

import lombok.RequiredArgsConstructor;
import lombok.val;
import pw.phylame.commons.text.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class WebResponse {
    private final URLConnection connection;

    public URL getURL() {
        return connection.getURL();
    }

    public long getContentLength() {
        return connection.getContentLengthLong();
    }

    public String getContentType() {
        return connection.getContentType();
    }

    public String getContentCharset() {
        val contentType = getContentType();
        return StringUtils.isNotEmpty(contentType)
                ? StringUtils.getValue(contentType, "charset", ";")
                : null;
    }

    public String getContentEncoding() {
        return connection.getContentEncoding();
    }

    public long getDate() {
        return connection.getDate();
    }

    public long getLastModified() {
        return connection.getLastModified();
    }

    public long getExpiration() {
        return connection.getExpiration();
    }

    public String getHeaderField(String name) {
        return connection.getHeaderField(name);
    }

    public Map<String, List<String>> getHeaderFields() {
        return connection.getHeaderFields();
    }

    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    public InputStream getActualStream() throws IOException {
        return HttpUtils.actualStream(connection);
    }

    public Reader getReader() throws IOException {
        val charset = getContentCharset();
        return charset != null
                ? new InputStreamReader(getInputStream(), charset)
                : new InputStreamReader(getInputStream(), StandardCharsets.UTF_8);
    }

    public Object getContent() throws IOException {
        return connection.getContent();
    }

    @Override
    public String toString() {
        return "Response for " + connection.toString();
    }
}
