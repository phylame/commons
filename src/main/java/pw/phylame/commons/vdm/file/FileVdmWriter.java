package pw.phylame.commons.vdm.file;

import lombok.RequiredArgsConstructor;
import lombok.val;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/14
 */
@RequiredArgsConstructor
class FileVdmWriter implements VdmWriter {
    private final Path root;

    private final LinkedList<OutputStream> streams = new LinkedList<>();

    @Override
    public void setComment(String comment) {
    }

    @Override
    public VdmEntry newEntry(String name) {
        return new FileVdmEntry(root.resolve(name), name, null, this);
    }

    @Override
    public OutputStream putEntry(VdmEntry entry) throws IOException {
        if (!(entry instanceof FileVdmEntry)) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        val fileEntry = (FileVdmEntry) entry;
        if (fileEntry.writer != this) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        if (fileEntry.stream != null) {
            throw new IllegalArgumentException("Entry already opened: " + entry);
        }
        val path = fileEntry.path;
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        val stream = Files.newOutputStream(path);
        fileEntry.stream = stream;
        streams.add(stream);
        return stream;
    }

    @Override
    public void closeEntry(VdmEntry entry) throws IOException {
        if (!(entry instanceof FileVdmEntry)) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        val fileEntry = (FileVdmEntry) entry;
        if (fileEntry.writer != this) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        val stream = fileEntry.stream;
        if (stream == null) {
            throw new IllegalArgumentException("Entry not opened: " + entry);
        }
        streams.remove(stream);
        stream.flush();
        stream.close();
    }

    @Override
    public void close() throws IOException {
        for (val stream : streams) {
            stream.flush();
            stream.close();
        }
        streams.clear();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode() + "{root=" + root + "}";
    }
}
