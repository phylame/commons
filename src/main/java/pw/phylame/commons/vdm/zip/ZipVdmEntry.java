package pw.phylame.commons.vdm.zip;

import lombok.RequiredArgsConstructor;
import pw.phylame.commons.vdm.VdmEntry;

import java.util.zip.ZipEntry;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
@RequiredArgsConstructor
class ZipVdmEntry implements VdmEntry {
    final ZipEntry zipEntry;

    final ZipVdmReader reader;

    final ZipVdmWriter writer;

    @Override
    public long length() {
        return zipEntry.getSize();
    }

    @Override
    public String getName() {
        return zipEntry.getName();
    }

    @Override
    public String getComment() {
        return zipEntry.getComment();
    }

    @Override
    public long lastModified() {
        return zipEntry.getTime();
    }

    @Override
    public boolean isDirectory() {
        return zipEntry.isDirectory();
    }
}
