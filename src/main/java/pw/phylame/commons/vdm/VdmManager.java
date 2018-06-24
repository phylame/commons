package pw.phylame.commons.vdm;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.NotImplementedException;
import pw.phylame.commons.setting.Settings;
import pw.phylame.commons.spi.ServiceManager;
import pw.phylame.commons.value.Lazy;

import java.io.IOException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class VdmManager extends ServiceManager<VdmFactory> {
    public static final String VDM_TYPE_ZIP = "zip";
    public static final String VDM_TYPE_DIRECTORY = "dir";

    private static final Lazy<VdmManager> INSTANCE = Lazy.of(VdmManager::new);

    public static VdmManager getDefault() {
        return INSTANCE.get();
    }

    public VdmManager() {
        super(VdmFactory.class);
    }

    public VdmReader openReader(@NonNull String name, @NonNull Object input, Settings settings) throws IOException {
        val factory = get(name);
        if (factory != null) {
            val reader = factory.getReader(input, settings);
            if (reader == null) {
                throw new NotImplementedException(factory.getClass().getName() + ".getReader(Object,Settings) returned null");
            }
            return reader;
        }
        return null;
    }

    public VdmWriter openWriter(@NonNull String name, @NonNull Object output, Settings settings) throws IOException {
        val factory = get(name);
        if (factory != null) {
            val writer = factory.getWriter(output, settings);
            if (writer == null) {
                throw new NotImplementedException(factory.getClass().getName() + ".getWriter(Object,Settings) returned null");
            }
            return writer;
        }
        return null;
    }
}
