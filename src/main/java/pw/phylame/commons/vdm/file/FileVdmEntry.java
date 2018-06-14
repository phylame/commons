package pw.phylame.commons.vdm.file;

import lombok.RequiredArgsConstructor;
import pw.phylame.commons.vdm.VdmEntry;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/14
 */
@RequiredArgsConstructor
class FileVdmEntry implements VdmEntry {
    final Path path;

    private final String name;

    final FileVdmReader reader;

    final FileVdmWriter writer;

    OutputStream stream;

    @Override
    public String getName() {
        return name + (Files.isDirectory(path) ? "/" : "");
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public long lastModified() {
        try {
            return Files.getLastModifiedTime(path).toMillis();
        } catch (IOException e) {
            return -1L;
        }
    }

    @Override
    public boolean isDirectory() {
        return Files.isDirectory(path);
    }

    @Override
    public String toString() {
        return path.toUri().toString();
    }
}
