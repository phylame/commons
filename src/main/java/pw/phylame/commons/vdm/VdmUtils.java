package pw.phylame.commons.vdm;

import lombok.val;
import pw.phylame.commons.setting.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VdmUtils {
    public static VdmReader detectReader(Path path, Settings settings) throws IOException {
        val vdmManager = VdmManager.getDefault();
        return Files.isDirectory(path)
                ? vdmManager.openReader(VdmManager.VDM_TYPE_DIRECTORY, path, settings)
                : vdmManager.openReader(VdmManager.VDM_TYPE_ZIP, path, settings);
    }
}
