package pw.phylame.commons.i18n;

import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.MissingResourceException;

@Value
public class MapTranslator implements Translator {
    @NonNull
    private Map<String, String> map;

    @Override
    public String tr(@NonNull String key) throws MissingResourceException {
        return map.get(key);
    }
}
