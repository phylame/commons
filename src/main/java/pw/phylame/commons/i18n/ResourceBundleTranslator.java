package pw.phylame.commons.i18n;

import lombok.NonNull;
import lombok.Value;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Value
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
    public String tr(@NonNull String key) throws MissingResourceException {
        return resourceBundle.getString(key);
    }
}
