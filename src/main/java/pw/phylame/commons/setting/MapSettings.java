package pw.phylame.commons.setting;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.CollectionUtils;
import pw.phylame.commons.format.FormatterService;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.io.Persistable;
import pw.phylame.commons.io.Resources;
import pw.phylame.commons.value.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public class MapSettings extends AbstractSettings implements Persistable {
    protected final Map<String, Object> values;

    public MapSettings() {
        this(new HashMap<>());
    }

    public MapSettings(Map<String, Object> values) {
        this.values = values;
    }

    public MapSettings(String path) throws IOException {
        this();
        val input = Resources.open(path);
        if (input != null) {
            try {
                load(input);
            } catch (IOException e) {
                input.close();
                throw e;
            }
        }
    }

    @Override
    protected Object handleGet(String key) {
        return values.get(key);
    }

    @Override
    protected Object handleSet(String key, Object value) {
        return values.put(key, value);
    }

    @Override
    public Object remove(String key) {
        return values.remove(key);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public Iterator<Pair<String, Object>> iterator() {
        return values.entrySet().stream().map(Pair::of).iterator();
    }

    public void load(@NonNull Reader reader) throws IOException {
        val props = new Properties();
        props.load(reader);
        CollectionUtils.copy(props, values);
    }

    public void sync(@NonNull Writer writer, String comment) throws IOException {
        val props = new Properties();
        for (val e : values.entrySet()) {
            Object value = e.getValue();
            if (value instanceof CharSequence) {
                props.setProperty(e.getKey(), value.toString());
            } else {
                value = FormatterService.getDefault().render(e.getValue());
                if (value == null) {
                    throw new IllegalStateException("`" + e.getKey() + "` cannot be converted to string");
                }
                props.setProperty(e.getKey(), (String) value);
            }
        }
        props.store(writer, comment);
    }

    @Override
    public void load(@NonNull InputStream input) throws IOException {
        load(new InputStreamReader(input, IOUtils.defaultCharset()));
    }

    @Override
    public void sync(@NonNull OutputStream output) throws IOException {
        sync(new OutputStreamWriter(output, IOUtils.defaultCharset()), null);
    }
}
