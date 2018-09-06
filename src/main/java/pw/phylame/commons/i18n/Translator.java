package pw.phylame.commons.i18n;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.text.StringUtils;

import java.text.MessageFormat;
import java.util.MissingResourceException;

public interface Translator {
    String tr(String key) throws MissingResourceException;

    default String tr(@NonNull String key, Object... args) throws MissingResourceException {
        return MessageFormat.format(tr(key), args);
    }

    default String optTr(@NonNull String key, String nullDefault) {
        try {
            return tr(key);
        } catch (MissingResourceException e) {
            return nullDefault;
        }
    }

    default String optTr(@NonNull String key, String nullDefault, Object... args) {
        val text = optTr(key, nullDefault);
        return StringUtils.isNotEmpty(text) ? MessageFormat.format(text, args) : text;
    }
}
