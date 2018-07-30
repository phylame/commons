package pw.phylame.commons.text;

import lombok.Getter;
import lombok.val;
import pw.phylame.commons.io.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/30
 */
class ResourceText extends DisposableSupport implements Text {
    private final Resource resource;

    private final Charset charset;

    @Getter
    private final String type;

    ResourceText(Resource resource, Charset charset, String type) {
        this.resource = Disposables.retain(resource);
        this.charset = charset;
        this.type = type;
    }

    @Override
    public int length() {
        return toString().length();
    }

    @Override
    public String toString() {
        try (val reader = openReader()) {
            return IOUtils.toString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Iterator<String> iterator() {
        try (val reader = openReader()) {
            return new LineIterator(reader, true);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void writeTo(Writer output) throws IOException {
        try (val reader = openReader()) {
            IOUtils.copy(reader, output, -1);
        }
    }

    @Override
    public void close() {
        Disposables.release(resource);
    }

    private BufferedReader openReader() throws IOException {
        return new BufferedReader(new InputStreamReader(resource.openStream(), charset));
    }
}
