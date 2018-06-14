package pw.phylame.commons;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import static pw.phylame.commons.CollectionUtils.firstOf;
import static pw.phylame.commons.CollectionUtils.isNotEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
@RequiredArgsConstructor
public final class MultiMap<K, V> {
    @NonNull
    private final Map<K, Collection<V>> map;

    @NonNull
    private final Supplier<Collection<V>> supplier;

    public void put(K key, V value) {
        getOrCreate(key).add(value);
    }

    public void putAll(K key, Collection<? extends V> c) {
        getOrCreate(key).addAll(c);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean contains(K key) {
        return isNotEmpty(map.get(key));
    }

    public V get(K key) {
        return firstOf(map.get(key)).orElse(null);
    }

    public Collection<V> getAll(K key) {
        return map.get(key);
    }

    public Collection<V> remove(K key) {
        return map.remove(key);
    }

    public boolean remove(K key, V value) {
        val c = map.get(key);
        return isNotEmpty(c) && c.remove(value);
    }

    public void clear() {
        map.clear();
    }

    private Collection<V> getOrCreate(K key) {
        var c = map.get(key);
        if (c == null) {
            c = supplier.get();
            map.put(key, c);
        }
        return c;
    }
}