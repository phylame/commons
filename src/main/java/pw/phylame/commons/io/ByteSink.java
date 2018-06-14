package pw.phylame.commons.io;

import lombok.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface ByteSink {
    void write(byte[] b, int off, int len) throws IOException;

    static ByteSink of(@NonNull OutputStream output) {
        return output::write;
    }

    static ByteSink of(@NonNull RandomAccessFile file) {
        return file::write;
    }
}
