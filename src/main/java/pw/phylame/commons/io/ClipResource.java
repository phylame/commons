package pw.phylame.commons.io;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.NestedException;
import pw.phylame.commons.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class ClipResource extends AbstractResource implements Disposable {
    @Getter
    private final String name;

    private final DisposableWrapper<RandomAccessFile> file;

    private final long offset;

    private final long length;

    private final DisposableSupport support = new DisposableSupport() {
        @Override
        public void close() {
            Disposables.release(file);
        }
    };

    public ClipResource(String name, @NonNull DisposableWrapper<RandomAccessFile> file, long offset, long length, String mime) throws IOException {
        super(mime);
        this.name = name;
        this.offset = offset;
        this.length = length;
        this.file = Disposables.retain(file);
        Validate.require(offset + length <= file.getSource().length(), "Invalid offset or length");
    }

    @Override
    public long size() {
        return length;
    }

    @Override
    public InputStream openStream() throws IOException {
        file.getSource().seek(offset);
        return new RandomAccessFileInputStream(file.getSource(), offset, length);
    }

    @Override
    public void transferTo(ByteSink output) throws IOException {
        val raf = file.getSource();
        raf.seek(offset);
        IOUtils.copy(ByteSource.of(raf), output, -1);
    }

    @Override
    public void retain() throws DisposedException {
        support.retain();
    }

    @Override
    public void release() throws NestedException {
        support.release();
    }

    @Override
    public void close() throws Exception {
        support.close();
    }
}