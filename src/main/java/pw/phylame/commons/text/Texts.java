package pw.phylame.commons.text;

import lombok.*;
import pw.phylame.commons.Validate;
import pw.phylame.commons.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Texts {
    public static final String TYPE_HTML = "html";

    public static final String TYPE_PLAIN = "plain";

    public static Text of(@NonNull CharSequence cs, String type) {
        Validate.nonEmpty(type, "`type` cannot be null or empty");
        return new StringText(cs, type);
    }

    public static Text of(@NonNull Resource resource, Charset charset, String type) {
        Validate.nonEmpty(type, "`type` cannot be null or empty");
        return new ResourceText(resource, charset != null ? charset : StandardCharsets.UTF_8, type);
    }

    @RequiredArgsConstructor
    private static class StringText implements Text {
        private final CharSequence cs;

        @Getter
        private final String type;

        @Override
        public int length() {
            return cs.length();
        }

        @Override
        public String toString() {
            return cs.toString();
        }
    }

    private static class ResourceText extends DisposableSupport implements Text {
        private final Resource resource;

        private final Charset charset;

        @Getter
        private final String type;

        ResourceText(Resource resource, Charset charset, String type) {
            this.resource = Disposables.retain(resource);
            this.charset = charset;
            this.type = type;
        }

        @Override
        public int length() {
            return toString().length();
        }

        @Override
        @SneakyThrows(IOException.class)
        public String toString() {
            try (val reader = openReader()) {
                return IOUtils.toString(reader);
            }
        }

        @Override
        @SneakyThrows(IOException.class)
        public Iterator<String> iterator() {
            try (val reader = openReader()) {
                return new LineIterator(reader, true);
            }
        }

        @Override
        public void writeTo(Writer output) throws IOException {
            try (val reader = openReader()) {
                IOUtils.copy(reader, output, -1);
            }
        }

        @Override
        public void close() {
            Disposables.release(resource);
        }

        private BufferedReader openReader() throws IOException {
            return new BufferedReader(new InputStreamReader(resource.openStream(), charset));
        }
    }
}
