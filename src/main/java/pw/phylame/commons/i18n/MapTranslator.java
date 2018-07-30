package pw.phylame.commons.i18n;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;
import java.util.MissingResourceException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/27
 */
@Getter
@Setter
@AllArgsConstructor
public class MapTranslator implements Translator {
    @NonNull
    private Map<String, String> map;

    @Override
    public String tr(@NonNull String key) throws MissingResourceException {
        return map.get(key);
    }
}
