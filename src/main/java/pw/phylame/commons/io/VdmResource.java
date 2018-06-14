package pw.phylame.commons.io;

import lombok.Getter;
import lombok.NonNull;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
class VdmResource extends DisposableSupport implements Resource {
    private final VdmReader reader;

    private final VdmEntry entry;

    @Getter
    private final String contentType;

    VdmResource(@NonNull VdmReader reader, @NonNull VdmEntry entry, String mime) {
        this.reader = reader;
        this.entry = entry;
        Disposables.retain(reader);
        contentType = FilenameUtils.detectMime(mime, entry.getName());
    }

    @Override
    public long size() {
        return entry.length();
    }

    @Override
    public String getName() {
        return entry.getName();
    }

    @Override
    public InputStream openStream() throws IOException {
        return reader.openStream(entry);
    }

    @Override
    public void close() {
        Disposables.release(reader);
    }

    @Override
    public String toString() {
        return entry + ";mime=" + contentType;
    }
}