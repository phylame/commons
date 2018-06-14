package pw.phylame.commons.value;

import lombok.NonNull;
import lombok.Value;

import java.util.Map;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
@Value(staticConstructor = "of")
public class Pair<A, B> implements Map.Entry<A, B>, Keyed<A> {
    private final A first;

    private final B second;

    public static <A, B> Pair<A, B> of(@NonNull Map.Entry<? extends A, ? extends B> entry) {
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    @Override
    public A getKey() {
        return first;
    }

    @Override
    public B getValue() {
        return second;
    }

    @Override
    public B setValue(B value) {
        throw new UnsupportedOperationException();
    }
}
