package pw.phylame.commons.i18n;

import lombok.Getter;
import lombok.NonNull;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/27
 */
public class ResourceBundleTranslator implements Translator {
    @Getter
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

    public ResourceBundleTranslator(@NonNull ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void setResourceBundle(@NonNull ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public String tr(String key) throws MissingResourceException {
        return resourceBundle.getString(key);
    }
}
