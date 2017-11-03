package pw.phylame.commons.value;

import lombok.Value;

import java.util.Map;

@Value
public final class Pair<A, B> implements Map.Entry<A, B>, Keyed<A> {
    private final A first;
    private final B second;

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
