package pw.phylame.commons.vdm;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.setting.Settings;
import pw.phylame.commons.spi.ServiceManager;
import pw.phylame.commons.value.Lazy;

import java.io.IOException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class VdmManager extends ServiceManager<VdmFactory> {
    private static final Lazy<VdmManager> INSTANCE = Lazy.of(VdmManager::new);

    public static VdmManager getDefault() {
        return INSTANCE.get();
    }

    public VdmManager() {
        super(VdmFactory.class);
    }

    public VdmReader openReader(@NonNull String name, @NonNull Object input, Settings settings) throws IOException {
        val factory = get(name);
        return factory != null ? factory.getReader(input, settings) : null;
    }

    public VdmWriter openWriter(@NonNull String name, @NonNull Object output, Settings settings) throws IOException {
        val factory = get(name);
        return factory != null ? factory.getWriter(output, settings) : null;
    }
}
