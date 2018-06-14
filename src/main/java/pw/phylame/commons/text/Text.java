package pw.phylame.commons.text;

import pw.phylame.commons.Validate;

import java.io.IOException;
import java.io.Writer;
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
}
