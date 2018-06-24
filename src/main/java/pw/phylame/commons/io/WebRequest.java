package pw.phylame.commons.io;

import lombok.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Consumer;

@Builder
@ToString
public class WebRequest {
    @NonNull
    private String url;

    @Builder.Default
    private String method = "get";

    @Builder.Default
    private boolean allowUserInteraction = false;

    @Builder.Default
    private int connectTimeout = 0;

    @Builder.Default
    private boolean doInput = true;

    @Builder.Default
    private boolean doOutput = false;

    @Builder.Default
    private long ifModifiedSince = 0;

    @Builder.Default
    private int readTimeout = 0;

    @Builder.Default
    private boolean useCaches = true;

    private byte[] payload;

    private Consumer<OutputStream> payloadWriter;

    @Singular
    private Map<String, String> parameters;

    @Builder.Default
    private String parameterEncoding = "UTF-8";

    @Singular
    private Map<String, String> properties;

    public WebResponse open() throws IOException {
        var path = this.url;
        if (path.startsWith("http") && "get".equalsIgnoreCase(method) && !parameters.isEmpty()) {
            path = path + "?" + HttpUtils.joinQuery(parameters, parameterEncoding);
        }
        val url = new URL(path);
        val conn = url.openConnection();
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).setRequestMethod(method.toUpperCase());
        }
        conn.setAllowUserInteraction(allowUserInteraction);
        conn.setConnectTimeout(connectTimeout);
        conn.setDoInput(doInput);
        conn.setDoOutput(doOutput);
        conn.setIfModifiedSince(ifModifiedSince);
        conn.setReadTimeout(readTimeout);
        conn.setUseCaches(useCaches);
        for (val e : properties.entrySet()) {
            conn.setRequestProperty(e.getKey(), e.getValue());
        }
        conn.connect();
        if (doOutput && (payload != null || payloadWriter != null)) {
            try (val output = conn.getOutputStream()) {
                if (payloadWriter != null) {
                    payloadWriter.accept(output);
                } else {
                    output.write(payload);
                }
            }
        } else if ("post".equalsIgnoreCase(method) && !parameters.isEmpty()) {
            try (val output = conn.getOutputStream()) {
                output.write(HttpUtils.joinQuery(parameters, parameterEncoding).getBytes(StandardCharsets.ISO_8859_1));
            }
        }
        return new WebResponse(conn);
    }
}
