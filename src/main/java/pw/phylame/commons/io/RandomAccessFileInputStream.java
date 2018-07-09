package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class RandomAccessFileInputStream extends InputStream {
    private final RandomAccessFile file;

    private final long endpos;

    private long curpos;

    public RandomAccessFileInputStream(RandomAccessFile file) throws IOException {
        this(file, file.getFilePointer(), -1);
    }

    public RandomAccessFileInputStream(RandomAccessFile file, long size) throws IOException {
        this(file, file.getFilePointer(), size);
    }

    public RandomAccessFileInputStream(@NonNull RandomAccessFile file, long offset, long size) throws IOException {
        this.file = file;
        val length = file.length();

        curpos = (offset < 0) ? 0 : offset;
        endpos = (size < 0) ? length : curpos + size;

        file.seek(curpos);

        Validate.require(curpos < length, "offset >= length of file");
        Validate.require(endpos <= length, "offset + size > length of file");
    }

    @Override
    public int read() throws IOException {
        if (curpos < endpos) {
            ++curpos;
            return file.read();
        } else {
            return -1;
        }
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        long count = endpos - curpos;
        if (count == 0) {
            return -1;
        }
        count = count < len ? count : len;
        len = file.read(b, off, (int) count);
        curpos += count;
        return len;
    }

    @Override
    public long skip(long n) throws IOException {
        if (n < 0) {
            return 0;
        }
        n = file.skipBytes((int) Math.min(n, endpos - curpos));
        curpos = Math.min(curpos + n, endpos);
        return n;
    }

    @Override
    public int available() throws IOException {
        return (int) (endpos - curpos);
    }
}
