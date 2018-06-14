package pw.phylame.commons.vdm.zip;

import lombok.RequiredArgsConstructor;
import pw.phylame.commons.vdm.VdmEntry;
import pw.phylame.commons.vdm.VdmWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
@RequiredArgsConstructor
class ZipVdmWriter implements VdmWriter {
    private final ZipOutputStream zipOutput;

    @Override
    public void setComment(String comment) {
        zipOutput.setComment(comment);
    }

    @Override
    public VdmEntry newEntry(String name) {
        return new ZipVdmEntry(new ZipEntry(name), null, this);
    }

    @Override
    public OutputStream putEntry(VdmEntry entry) throws IOException {
        if (!(entry instanceof ZipVdmEntry) || ((ZipVdmEntry) entry).writer != this) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        zipOutput.putNextEntry(((ZipVdmEntry) entry).zipEntry);
        return zipOutput;
    }

    @Override
    public void closeEntry(VdmEntry entry) throws IOException {
        if (!(entry instanceof ZipVdmEntry) || ((ZipVdmEntry) entry).writer != this) {
            throw new IllegalArgumentException("Invalid entry: " + entry);
        }
        zipOutput.closeEntry();
    }

    @Override
    public void close() throws IOException {
        zipOutput.flush();
        zipOutput.close();
    }
}
