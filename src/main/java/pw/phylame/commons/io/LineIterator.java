package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
@RequiredArgsConstructor
public final class LineIterator implements Iterator<String> {
    @NonNull
    private final BufferedReader reader;

    private final boolean autoClose;

    private String nextLine;

    private boolean isDone;

    @Override
    public boolean hasNext() {
        if (nextLine == null && !isDone) {
            try {
                nextLine = reader.readLine();
                if (nextLine == null) {
                    isDone = true;
                    if (autoClose) {
                        reader.close();
                    }
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return nextLine != null;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        val line = nextLine;
        nextLine = null;
        return line;
    }
}
