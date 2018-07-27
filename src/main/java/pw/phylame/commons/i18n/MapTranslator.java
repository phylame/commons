package pw.phylame.commons.i18n;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.MissingResourceException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/27
 */
public class MapTranslator implements Translator {
    @Getter
    private Map<String, String> map;

    public MapTranslator(@NonNull Map<String, String> map) {
        this.map = map;
    }

    public void setMap(@NonNull Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String tr(String key) throws MissingResourceException {
        return map.get(key);
    }
}
