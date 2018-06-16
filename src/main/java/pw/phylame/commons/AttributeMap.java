package pw.phylame.commons;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.io.Disposables;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;

import static pw.phylame.commons.Validate.nonEmpty;
import static pw.phylame.commons.text.StringUtils.isNotEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/13
 */
@RequiredArgsConstructor
public final class AttributeMap implements Iterable<Map.Entry<String, Object>>, Cloneable {
    private final BiFunction<String, Object, Object> validator;

    private HashMap<String, Object> values = new HashMap<>();

    public AttributeMap() {
        this(null);
    }

    public Object set(String name, @NonNull Object value) {
        nonEmpty(name, "`name` cannot be empty");
        if (validator != null) {
            value = validator.apply(name, value);
        }
        val last = values.get(name);
        values.put(name, Disposables.retain(value));
        Disposables.release(last);
        return last;
    }

    public void update(@NonNull Map<String, Object> m) {
        for (val e : m.entrySet()) {
            set(e.getKey(), e.getValue());
        }
    }

    public void update(@NonNull AttributeMap am) {
        update(am.values);
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public boolean contains(String name) {
        return isNotEmpty(name) && values.containsKey(name);
    }

    public Object get(String name) {
        return isNotEmpty(name) ? values.get(name) : null;
    }

    public Object remove(String name) {
        if (isNotEmpty(name)) {
            val last = values.remove(name);
            Disposables.release(last);
            return last;
        }
        return null;
    }

    public void clear() {
        Disposables.release(values.values());
        values.clear();
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + values;
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return Collections.unmodifiableMap(values).entrySet().iterator();
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows(CloneNotSupportedException.class)
    public AttributeMap clone() {
        val am = (AttributeMap) super.clone();
        am.values = (HashMap<String, Object>) values.clone();
        Disposables.retain(am.values.values());
        return am;
    }
}
