package pw.phylame.commons.vdm.file;

import lombok.RequiredArgsConstructor;
import lombok.val;
import pw.phylame.commons.NestedException;
import pw.phylame.commons.io.DisposableSupport;
import pw.phylame.commons.io.FilenameUtils;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;

import static pw.phylame.commons.function.Functions.isNotSame;
import static pw.phylame.commons.Validate.nonEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/14
 */
@RequiredArgsConstructor
class FileVdmReader extends DisposableSupport implements VdmReader {
    private final Path root;

    private final LinkedList<InputStream> streams = new LinkedList<>();

    @Override
    public int size() {
        try {
            return (int) Files.walk(root).filter(isNotSame(root)).count();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public String getName() {
        return root.toString();
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public VdmEntry getEntry(String name) {
        nonEmpty(name, "`name` for entry cannot be empty");
        val path = root.resolve(name);
        return Files.exists(path)
                ? newEntry(path, name)
                : null;
    }

    @Override
    public InputStream openStream(VdmEntry entry) throws IOException {
        if (!(entry instanceof FileVdmEntry) || ((FileVdmEntry) entry).reader != this) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        val stream = Files.newInputStream(((FileVdmEntry) entry).path);
        streams.add(stream);
        return stream;
    }

    @Override
    public void close() throws IOException {
        for (val stream : streams) {
            stream.close();
        }
        streams.clear();
    }

    @Override
    public Iterator<VdmEntry> iterator() {
        try {
            val begin = root.toString().length() + 1;
            return Files.walk(root)
                    .filter(isNotSame(root))
                    .map(p -> newEntry(p, p.toString().substring(begin)))
                    .iterator();
        } catch (IOException e) {
            throw new NestedException(e);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode() + "{root=" + root + "}";
    }

    private VdmEntry newEntry(Path path, String name) {
        return new FileVdmEntry(path, FilenameUtils.slashified(name), this, null);
    }
}
