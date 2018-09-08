package pw.phylame.commons.i18n;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.MissingResourceException;

@RequiredArgsConstructor
public class MapTranslator implements Translator {
    @Getter
    @NonNull
    private final Map<String, String> map;

    @Override
    public String tr(@NonNull String key) throws MissingResourceException {
        return map.get(key);
    }
}
