package pw.phylame.commons;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.io.Disposables;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static pw.phylame.commons.Validate.nonEmpty;
import static pw.phylame.commons.text.StringUtils.isNotEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/13
 */
@RequiredArgsConstructor
public final class AttributeMap implements Iterable<Map.Entry<String, Object>>, Cloneable {
    private final BiFunction<String, Object, Object> filter;

    private HashMap<String, Object> map = new HashMap<>();

    public AttributeMap() {
        this(null);
    }

    public Object set(String name, @NonNull Object value) {
        nonEmpty(name, "`name` cannot be empty");
        if (filter != null) {
            value = filter.apply(name, value);
        }
        val last = map.get(name);
        map.put(name, Disposables.retain(value));
        Disposables.release(last);
        return last;
    }

    public void update(@NonNull Map<String, Object> m) {
        for (val e : m.entrySet()) {
            set(e.getKey(), e.getValue());
        }
    }

    public void update(@NonNull AttributeMap am) {
        update(am.map);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<String> names() {
        return map.keySet();
    }

    public boolean contains(String name) {
        return isNotEmpty(name) && map.containsKey(name);
    }

    public Object get(String name) {
        return isNotEmpty(name) ? map.get(name) : null;
    }

    public Object remove(String name) {
        if (isNotEmpty(name)) {
            val last = map.remove(name);
            Disposables.release(last);
            return last;
        }
        return null;
    }

    public void clear() {
        Disposables.releaseAll(map.values());
        map.clear();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return Collections.unmodifiableMap(map).entrySet().iterator();
    }

    public Stream<Map.Entry<String, Object>> stream() {
        return map.entrySet().stream();
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows(CloneNotSupportedException.class)
    public AttributeMap clone() {
        val am = (AttributeMap) super.clone();
        am.map = (HashMap<String, Object>) map.clone();
        Disposables.retainAll(am.map.values());
        return am;
    }
}
