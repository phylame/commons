package pw.phylame.commons.text;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.flob.Flob;
import pw.phylame.commons.io.DisposableSupport;
import pw.phylame.commons.io.Disposables;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.io.LineIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;

import static pw.phylame.commons.Validate.requireNotEmpty;

class FlobText extends DisposableSupport implements Text {
    private final Flob flob;

    private final Charset charset;

    @Getter
    private final String type;

    FlobText(@NonNull Flob flob, Charset charset, String type) {
        requireNotEmpty(type, "type cannot be null or empty");
        this.charset = charset != null ? charset : Charset.defaultCharset();
        Disposables.retain(flob);
        this.flob = flob;
        this.type = type;
    }

    @Override
    protected void dispose() {
        Disposables.release(flob);
    }

    @Override
    @SneakyThrows(IOException.class)
    public String toString() {
        try (val input = openReader()) {
            return IOUtils.toString(input);
        }
    }

    @Override
    @SneakyThrows(IOException.class)
    public Iterator<String> iterator() {
        return new LineIterator(openReader(), true);
    }

    @Override
    public void writeTo(@NonNull Writer output) throws IOException {
        try (val input = openReader()) {
            IOUtils.copy(input, output);
        }
    }

    private BufferedReader openReader() throws IOException {
        return new BufferedReader(new InputStreamReader(flob.openStream(), charset));
    }
}
