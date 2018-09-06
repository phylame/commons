package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import pw.phylame.commons.CollectionUtils;
import pw.phylame.commons.log.Log;
import pw.phylame.commons.text.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

public final class HttpUtils {
    public static String randomAgent() {
        val stream = HttpUtils.class.getResourceAsStream("agents.lst");
        if (stream != null) {
            try {
                return CollectionUtils.anyOf(IOUtils.toLines(stream)).orElse(null);
            } finally {
                IOUtils.closeQuietly(stream);
            }
        } else {
            Log.d(HttpUtils.class.getName(), "not found agents file");
        }
        return null;
    }

    @SneakyThrows(UnsupportedEncodingException.class)
    public static String joinQuery(@NonNull Map<String, String> params, @NonNull String encoding) {
        val b = new StringBuilder();
        val it = params.entrySet().iterator();
        while (it.hasNext()) {
            val e = it.next();
            b.append(URLEncoder.encode(e.getKey(), encoding))
                    .append('=')
                    .append(URLEncoder.encode(e.getValue(), encoding));
            if (it.hasNext()) {
                b.append('&');
            }
        }
        return b.toString();
    }

    public static InputStream actualStream(@NonNull URLConnection connection) throws IOException {
        var encoding = connection.getContentEncoding();
        if (StringUtils.isEmpty(encoding)) {
            return connection.getInputStream();
        }
        encoding = encoding.toLowerCase();
        if (encoding.contains("gzip")) {
            return new GZIPInputStream(connection.getInputStream());
        } else if (encoding.contains("deflate")) {
            return new DeflaterInputStream(connection.getInputStream());
        } else {
            return connection.getInputStream();
        }
    }

    public static String trimSpace(String str) {
        return StringUtils.isNotEmpty(str) ? StringUtils.trim(str).replace("\\u00A0", "") : str;
    }
}
