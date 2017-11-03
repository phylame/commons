package pw.phylame.commons.value;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.io.Disposables;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static pw.phylame.commons.Validate.requireNotEmpty;
import static pw.phylame.commons.Validate.requireNotNull;
import static pw.phylame.commons.text.StringUtils.isEmpty;

@RequiredArgsConstructor
public final class ValueMap implements Iterable<Map.Entry<String, Object>>, Cloneable {
    private HashMap<String, Object> map = new HashMap<>();

    private final Validator validator;

    public ValueMap() {
        this(null);
    }

    public Object set(String key, Object value) {
        requireNotEmpty(key, "key cannot be null or empty");
        requireNotNull(value, "value cannot be null");
        if (validator != null) {
            validator.validate(key, value);
        }
        val old = map.put(key, value);
        Disposables.retain(value);
        Disposables.release(old);
        return old;
    }

    public void update(@NonNull ValueMap other) {
        update(other.map);
    }

    public void update(@NonNull Map<String, Object> map) {
        for (val e : map.entrySet()) {
            set(e.getKey(), e.getValue());
        }
    }

    public void update(@NonNull Iterable<? extends Map.Entry<String, Object>> entries) {
        for (val e : entries) {
            set(e.getKey(), e.getValue());
        }
    }

    public boolean contains(String name) {
        return !isEmpty(name) && map.containsKey(name);
    }

    public Set<String> names() {
        return map.keySet();
    }

    public Object get(String name) {
        return isEmpty(name) ? null : map.get(name);
    }

    public int size() {
        return map.size();
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return map.entrySet().iterator();
    }

    public Object remove(String name) {
        if (isEmpty(name)) {
            return null;
        }
        val old = map.remove(name);
        Disposables.release(old);
        return old;
    }

    public void clear() {
        Disposables.releaseAll(map.values());
        map.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows(CloneNotSupportedException.class)
    public ValueMap clone() {
        val copy = (ValueMap) super.clone();
        copy.map = (HashMap<String, Object>) map.clone();
        Disposables.retainAll(map.values());
        return copy;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @FunctionalInterface
    public interface Validator {
        void validate(String key, Object value) throws RuntimeException;
    }
}
