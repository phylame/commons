package pw.phylame.commons.io;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface ByteSource {
    int read(byte[] b, int off, int len) throws IOException;

    static ByteSource of(@NonNull InputStream input) {
        return input::read;
    }

    static ByteSource of(@NonNull RandomAccessFile file) {
        return file::read;
    }
}
