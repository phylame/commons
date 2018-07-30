package pw.phylame.commons.i18n;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/27
 */
@Getter
@Setter
@AllArgsConstructor
public class ResourceBundleTranslator implements Translator {
    @NonNull
    private ResourceBundle resourceBundle;

    public ResourceBundleTranslator(String baseName) {
        resourceBundle = ResourceBundle.getBundle(baseName);
    }

    public ResourceBundleTranslator(String baseName, Locale locale) {
        resourceBundle = ResourceBundle.getBundle(baseName, locale);
    }

    public ResourceBundleTranslator(String baseName, Locale locale, ClassLoader loader) {
        resourceBundle = ResourceBundle.getBundle(baseName, locale, loader);
    }

    @Override
    public String tr(String key) throws MissingResourceException {
        return resourceBundle.getString(key);
    }
}