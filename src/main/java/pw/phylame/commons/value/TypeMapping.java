package pw.phylame.commons.value;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import pw.phylame.commons.NestedException;
import pw.phylame.commons.Validate;
import pw.phylame.commons.io.FilenameUtils;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.io.Resources;
import pw.phylame.commons.text.StringUtils;
import pw.phylame.commons.text.Texts;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/30
 */
@Slf4j
public final class TypeMapping {
    // standard type names
    public static final String REAL = "real";
    public static final String INTEGER = "int";
    public static final String BOOLEAN = "bool";
    public static final String STRING = "str";
    public static final String LOCALE = "locale";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String DATETIME = "datetime";
    public static final String TEXT = "text";
    public static final String RESOURCE = "file";

    private static final Lazy<TypeMapping> INSTANCE = Lazy.of(() -> {
        val tm = new TypeMapping();
        tm.registerBuiltins();
        return tm;
    });

    public static TypeMapping getDefault() {
        return INSTANCE.get();
    }

    private final HashMap<String, Item> items = new HashMap<>();

    private final IdentityHashMap<Class<?>, String> types = new IdentityHashMap<>();

    private final HashMap<String, Item> cache = new HashMap<>();

    public void reset() {
        items.clear();
        types.clear();
        cache.clear();
    }

    public void setAlias(String name, String alias) {
        requiredItem(name).aliases.add(alias);
    }

    public void setAliases(String name, String... aliases) {
        Collections.addAll(requiredItem(name).aliases, aliases);
    }

    public String[] typeNames() {
        val names = new HashSet<String>(items.keySet());
        for (val item : items.values()) {
            names.addAll(item.aliases);
        }
        return names.toArray(new String[0]);
    }

    public void mapType(String name, @NonNull Class<?> type) {
        mapType(name, type, false);
    }

    public void mapType(String name, @NonNull Class<?> type, boolean inheritable) {
        Validate.nonEmpty(name, "type name cannot be empty");

        val item = acquireItem(name);
        item.reset();
        item.type = type;
        item.inheritable = inheritable;

        types.put(type, name);
        cache.remove(name);
    }

    public Class<?> getType(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        val item = lookupItem(name);
        return item != null ? item.type() : null;
    }

    public String getName(@NonNull Object obj) {
        return getName(obj.getClass());
    }

    public String getName(@NonNull Class<?> type) {
        val name = types.get(type);
        if (name != null) {
            return name;
        }
        return items.entrySet()
                .stream()
                .filter(e -> {
                    val item = e.getValue();
                    return item.inheritable ? item.type().isAssignableFrom(type) : item.type() == type;
                })
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void setDefault(String name, Object value) {
        requiredItem(name).initial = value;
    }

    public Object getDefault(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        val item = lookupItem(name);
        return item != null ? Value.get(item.initial) : null;
    }

    public void registerBuiltins() {
        val stream = TypeMapping.class.getResourceAsStream("types.properties");
        if (stream != null) {
            try {
                val prop = new Properties();
                prop.load(stream);
                for (val e : prop.entrySet()) {
                    val name = e.getKey().toString().trim();
                    if (StringUtils.isEmpty(name)) {
                        throw new RuntimeException("Type name cannot be empty");
                    }
                    val path = e.getValue().toString().trim();
                    if (StringUtils.isEmpty(path)) {
                        throw new RuntimeException("Class path cannot be empty for " + name);
                    }
                    val parts = path.split(",");
                    val item = new Item();
                    item.name = name;
                    item.path = parts[0];
                    if (parts.length > 1) {
                        item.inheritable = Boolean.valueOf(parts[1]);
                    }
                    items.put(name, item);
                }
                initDefaults();
            } catch (IOException e) {
                throw new NestedException(e);
            } finally {
                IOUtils.closeQuietly(stream);
            }
        } else {
            log.debug("not found type mapping file");
        }
    }

    private void initDefaults() {
        setDefault(REAL, 0.0);
        setDefault(INTEGER, 0);
        setDefault(STRING, "");
        setDefault(BOOLEAN, false);
        setDefault(DATE, Value.of(LocalDate::now));
        setDefault(TIME, Value.of(LocalTime::now));
        setDefault(DATETIME, Value.of(LocalDateTime::now));
        setDefault(LOCALE, Value.of(Locale::getDefault));
        setDefault(TEXT, Texts.of("", Texts.TYPE_PLAIN));
        setDefault(RESOURCE, Resources.of(new byte[0], "none", FilenameUtils.UNKNOWN_MIME_TYPE));
    }

    private Item acquireItem(String name) {
        var item = lookupItem(name);
        if (item == null) {
            items.put(name, item = new Item());
            item.name = name;
        }
        return item;
    }

    private Item requiredItem(String name) {
        Validate.nonEmpty(name, "type name cannot be empty");
        val item = lookupItem(name);
        if (item == null) {
            throw new NoSuchElementException("No mapping for `" + name + "`");
        }
        return item;
    }

    private Item lookupItem(String name) {
        var item = cache.get(name);
        if (item == null) {
            item = items.get(name);
        }
        if (item != null) {
            return item;
        }
        item = items.values()
                .stream()
                .filter(i -> i.aliases.contains(name))
                .findFirst()
                .orElse(null);
        if (item != null) {
            cache.put(name, item);
        }
        return item;
    }

    private static class Item {
        String name;

        String path;

        Class<?> type;

        HashSet<String> aliases = new HashSet<>();

        boolean inheritable;

        Object initial;

        Class<?> type() {
            if (type != null) {
                return type;
            }
            try {
                type = Class.forName(path);
            } catch (ClassNotFoundException e) {
                throw new NestedException(e);
            }
            return type;
        }

        void reset() {
            path = null;
            aliases.clear();
            inheritable = false;
            initial = null;
        }
    }
}
