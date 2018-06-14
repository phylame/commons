package pw.phylame.commons.vdm.zip;

import lombok.RequiredArgsConstructor;
import lombok.val;
import pw.phylame.commons.CollectionUtils;
import pw.phylame.commons.io.DisposableSupport;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import java.util.zip.ZipFile;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
@RequiredArgsConstructor
class ZipVdmReader extends DisposableSupport implements VdmReader {
    private final ZipFile zipFile;

    @Override
    public int size() {
        return zipFile.size();
    }

    @Override
    public String getName() {
        return zipFile.getName();
    }

    @Override
    public String getComment() {
        return zipFile.getComment();
    }

    @Override
    public VdmEntry getEntry(String name) {
        val entry = zipFile.getEntry(name);
        return entry != null ? new ZipVdmEntry(entry, this, null) : null;
    }

    @Override
    public InputStream openStream(VdmEntry entry) throws IOException {
        if (!(entry instanceof ZipVdmEntry) || ((ZipVdmEntry) entry).reader != this) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        return zipFile.getInputStream(((ZipVdmEntry) entry).zipEntry);
    }

    @Override
    public void close() throws IOException {
        zipFile.close();
    }

    @Override
    public Iterator<VdmEntry> iterator() {
        val it = CollectionUtils.iterable(CollectionUtils.iterator(zipFile.entries()));
        return StreamSupport.stream(it.spliterator(), false)
                .map(e -> (VdmEntry) new ZipVdmEntry(e, this, null))
                .iterator();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode() + "{zip=" + zipFile.getName() + "}";
    }
}
