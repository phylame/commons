package pw.phylame.commons.setting;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.format.FormatterService;
import pw.phylame.commons.text.StringJoiner;
import pw.phylame.commons.value.Pair;

import java.util.HashMap;
import java.util.Map;

import static pw.phylame.commons.CollectionUtils.isEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public abstract class AbstractSettings implements Settings {
    @Getter
    private Map<String, Definition> definitions = new HashMap<>();

    protected abstract Object handleGet(String key);

    protected abstract Object handleSet(String key, Object value);

    protected <T> T handleConvert(Object value, Class<T> type) {
        if (value instanceof CharSequence) {
            return FormatterService.getDefault().parse(value.toString(), type);
        }
        throw new UnsupportedOperationException("`value` is not a CharSequence");
    }

    public final Definition getDefinition(String key) {
        return definitions.get(key);
    }

    @Override
    public final Object set(String key, Object value) {
        val definition = getDefinition(key);
        if (definition != null) {
            val type = definition.getType();
            if (!type.isInstance(value)) {
                throw new IllegalArgumentException("`" + key + "` require type: " + type);
            }
        }
        return handleSet(key, value);
    }

    @Override
    public final boolean isEnable(String key) {
        val definition = getDefinition(key);
        if (definition == null) {
            return true;
        }
        val dependencies = definition.getDependencies();
        if (isEmpty(dependencies)) {
            return true;
        }
        for (val dependency : dependencies) {
            if (!isEnable(dependency.getKey())) {
                return false;
            } else if (!dependency.getCondition().test(get(definition.getKey()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final Object get(String key) {
        val value = handleGet(key);
        if (value != null) {
            return value;
        }
        val definition = getDefinition(key);
        if (definition != null) {
            return definition.getInitial();
        }
        return null;
    }

    public final <T> T get(String key, @NonNull Class<T> type) {
        val value = get(key);
        if (value == null) {
            return null;
        } else if (type.isInstance(value)) {
            return type.cast(value);
        } else {
            T converted;
            try {
                converted = handleConvert(value, type);
            } catch (RuntimeException e) {
                throw new IllegalArgumentException("`" + key + "` is not " + type, e);
            }
            if (converted == null) {
                throw new IllegalArgumentException("`" + key + "` is not " + type);
            }
            return converted;
        }
    }

    @Override
    public String toString() {
        val str = StringJoiner.<Pair<String, Object>>builder()
                .iterator(iterator())
                .prefix("{")
                .transform((i, p) -> "'" + p.getFirst() + "'=" + p.getSecond())
                .separator(", ")
                .suffix("}")
                .build()
                .join();
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + str;
    }
}
