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

    private final DisposableWrapper<RandomAccessFile> ref;

    private final long offset;

    private final long length;

    private final DisposableSupport support = new DisposableSupport() {
        @Override
        public void close() {
            Disposables.release(ref);
        }
    };

    public ClipResource(String name, @NonNull DisposableWrapper<RandomAccessFile> ref, long offset, long length, String mime) throws IOException {
        super(mime);
        this.name = name;
        this.offset = offset;
        this.length = length;
        this.ref = Disposables.retain(ref);
        Validate.require(offset + length <= ref.getSource().length(), "Invalid offset or length");
    }

    @Override
    public long size() {
        return length;
    }

    @Override
    public InputStream openStream() throws IOException {
        ref.getSource().seek(offset);
        return new RandomAccessFileInputStream(ref.getSource(), offset, length);
    }

    @Override
    public void transferTo(ByteSink output) throws IOException {
        val raf = ref.getSource();
        raf.seek(offset);
        IOUtils.copy(ByteSource.of(raf), output, length);
    }

    @Override
    public String toString() {
        return "clip://{" + offset + "," + length + "}!" + super.toString();
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
