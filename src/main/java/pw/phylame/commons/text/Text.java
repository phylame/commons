package pw.phylame.commons.text;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.flob.Flob;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;

import static pw.phylame.commons.Validate.requireNotEmpty;

public interface Text extends Iterable<String> {
    String HTML = "html";

    String PLAIN = "plain";

    String getType();

    @Override
    String toString();

    @Override
    default Iterator<String> iterator() {
        return new LineSplitter(toString());
    }

    default void writeTo(@NonNull Writer output) throws IOException {
        output.write(toString());
    }

    static Text of(@NonNull CharSequence text) {
        return of(text, PLAIN);
    }

    static Text of(@NonNull CharSequence text, String type) {
        requireNotEmpty(type, "type cannot be null or empty");
        return new Text() {
            @Override
            public String getType() {
                return type;
            }

            @Override
            public String toString() {
                return text.toString();
            }
        };
    }

    static Text of(@NonNull Flob flob, Charset charset) {
        val mimeType = flob.getMimeType();
        return new FlobText(flob, charset, mimeType.startsWith("text/") ? mimeType.substring(5) : PLAIN);
    }

    static Text of(@NonNull Flob flob, Charset charset, String type) {
        return new FlobText(flob, charset, type);
    }
}
