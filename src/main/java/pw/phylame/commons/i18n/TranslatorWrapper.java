package pw.phylame.commons.i18n;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/27
 */
public abstract class TranslatorWrapper implements Translator {
    @Getter
    @Setter
    private Translator translator;

    @Override
    public String tr(String key) throws MissingResourceException {
        if (translator == null) {
            throw new IllegalStateException("No translator specified");
        }
        return translator.tr(key);
    }

    @Override
    public String tr(String key, Object... args) throws MissingResourceException {
        if (translator == null) {
            throw new IllegalStateException("No translator specified");
        }
        return translator.tr(key, args);
    }

    @Override
    public String optTr(String key, String nullDefault) {
        return translator != null
                ? translator.optTr(key, nullDefault)
                : nullDefault;
    }

    @Override
    public String optTr(String key, String nullDefault, Object... args) {
        return translator != null
                ? translator.optTr(key, nullDefault, args)
                : MessageFormat.format(nullDefault, args);
    }
}
