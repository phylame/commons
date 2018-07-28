package pw.phylame.commons.i18n;

import lombok.val;
import pw.phylame.commons.text.StringUtils;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/27
 */
public interface Translator {
    String tr(String key) throws MissingResourceException;

    default String tr(String key, Object... args) throws MissingResourceException {
        return MessageFormat.format(tr(key), args);
    }

    default String optTr(String key, String nullDefault) {
        try {
            return tr(key);
        } catch (MissingResourceException e) {
            return nullDefault;
        }
    }

    default String optTr(String key, String nullDefault, Object... args) {
        val text = optTr(key, nullDefault);
        return StringUtils.isNotEmpty(text)
                ? MessageFormat.format(text, args)
                : text;
    }
}
