package pw.phylame.commons.text;

import lombok.Builder;
import lombok.NonNull;
import lombok.val;

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

    @Builder.Default
    private CharSequence separator = ",";

    private CharSequence prefix;

    private BiFunction<Integer, ? super E, ? extends CharSequence> transform;

    private CharSequence suffix;

    public String join() {
        val b = new StringBuilder();
        if (isNotEmpty(prefix)) {
            b.append(prefix);
        }
        int index = 0;
        while (iterator.hasNext()) {
            E next = iterator.next();
            b.append(transform != null ? transform.apply(index, next) : String.valueOf(next));
            if (iterator.hasNext()) {
                b.append(separator);
            }
            ++index;
        }
        if (isNotEmpty(suffix)) {
            b.append(suffix);
        }
        return b.toString();
    }
}
