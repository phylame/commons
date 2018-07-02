package pw.phylame.commons.text;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.BiFunction;

import static pw.phylame.commons.text.StringUtils.isNotEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/13
 */
@Builder
public final class StringJoiner<E> {
    @NonNull
    private Iterator<E> iterator;

    @NonNull
    @Builder.Default
    private CharSequence separator = ",";

    private CharSequence prefix;

    private BiFunction<Integer, ? super E, ? extends CharSequence> transform;

    private CharSequence suffix;

    public void writeTo(@NonNull Appendable output) throws IOException {
        if (isNotEmpty(prefix)) {
            output.append(prefix);
        }
        int index = 0;
        while (iterator.hasNext()) {
            E next = iterator.next();
            output.append(transform != null ? transform.apply(index, next) : String.valueOf(next));
            if (iterator.hasNext()) {
                output.append(separator);
            }
            ++index;
        }
        if (isNotEmpty(suffix)) {
            output.append(suffix);
        }
    }

    @SneakyThrows(IOException.class)
    public String join() {
        val b = new StringBuilder();
        writeTo(b);
        return b.toString();
    }
}
