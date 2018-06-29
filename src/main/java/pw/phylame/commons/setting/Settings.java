package pw.phylame.commons.setting;

import lombok.val;
import pw.phylame.commons.value.Pair;

import java.util.Map;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/13
 */
public interface Settings extends Iterable<Pair<String, Object>> {
    boolean isEnable(String key);

    Object set(String key, Object value);

    default void update(Map<String, Object> map) {
        for (val e : map.entrySet()) {
            set(e.getKey(), e.getValue());
        }
    }

    default void update(Settings settings) {
        for (val e : settings) {
            set(e.getKey(), e.getValue());
        }
    }

    Object get(String key);

    default boolean contains(String key) {
        return get(key) != null;
    }

    Object remove(String key);

    void clear();
}
