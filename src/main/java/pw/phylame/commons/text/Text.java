package pw.phylame.commons.text;

import lombok.NonNull;
import pw.phylame.commons.Validate;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.io.Resource;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface Text extends Iterable<String> {
    int length();

    String getType();

    @Override
    String toString();

    @Override
    default Iterator<String> iterator() {
        return new LineSplitter(toString());
    }

    default void writeTo(Writer output) throws IOException {
        Validate.nonNull(output);
        output.append(toString());
    }

    String TYPE_HTML = "html";

    String TYPE_PLAIN = "plain";

    static Text of(@NonNull CharSequence text, String type) {
        Validate.nonEmpty(type, "`type` cannot be null or empty");
        return new Text() {
            @Override
            public int length() {
                return text.length();
            }

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

    static Text of(@NonNull Resource resource, Charset charset, String type) {
        Validate.nonEmpty(type, "`type` cannot be null or empty");
        return new ResourceText(resource, charset != null ? charset : IOUtils.defaultCharset(), type);
    }
}
