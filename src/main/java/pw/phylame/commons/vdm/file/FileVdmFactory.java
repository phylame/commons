package pw.phylame.commons.vdm.file;

import lombok.NonNull;
import pw.phylame.commons.setting.Settings;
import pw.phylame.commons.vdm.VdmFactory;
import pw.phylame.commons.vdm.VdmReader;
import pw.phylame.commons.vdm.VdmWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/14
 */
public final class FileVdmFactory implements VdmFactory {
    @Override
    public VdmReader getReader(@NonNull Object input, Settings settings) throws IOException {
        return new FileVdmReader(getPath(input, true));
    }

    @Override
    public VdmWriter getWriter(@NonNull Object output, Settings settings) throws IOException {
        return new FileVdmWriter(getPath(output, false));
    }

    private Path getPath(Object obj, boolean readMode) throws IOException {
        Path path;
        if (obj instanceof Path) {
            path = (Path) obj;
        } else if (obj instanceof CharSequence) {
            path = Paths.get(obj.toString());
        } else if (obj instanceof File) {
            path = ((File) obj).toPath();
        } else {
            throw new IllegalArgumentException(obj.toString());
        }
        if (readMode) {
            if (Files.notExists(path)) {
                throw new NoSuchFileException(path.toString());
            } else if (!Files.isDirectory(path)) {
                throw new NotDirectoryException(path.toString());
            }
        }
        return path;
    }

    @Override
    public Set<String> getKeys() {
        return Collections.singleton("dir");
    }
}
