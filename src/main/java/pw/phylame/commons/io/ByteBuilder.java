package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ByteBuilder extends ByteArrayOutputStream {
    public ByteBuilder() {
        super();
    }

    public ByteBuilder(int size) {
        super(size);
    }

    public ByteBuilder(@NonNull byte[] src) {
        this.buf = src;
    }

    public ByteBuilder append(int b) {
        write(b);
        return this;
    }

    @SneakyThrows(IOException.class)
    public ByteBuilder append(byte[] b) {
        write(b);
        return this;
    }

    public ByteBuilder append(byte[] b, int off, int len) {
        write(b, off, len);
        return this;
    }

    public byte[] getRawArray() {
        return buf;
    }
}
