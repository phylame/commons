package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public final class LineIterator implements Iterator<String> {
    @NonNull
    private final BufferedReader reader;

    private final boolean autoClose;

    private String nextLine;

    private boolean isDone;

    @Override
    @SneakyThrows(IOException.class)
    public boolean hasNext() {
        if (nextLine == null && !isDone) {
            nextLine = reader.readLine();
            if (nextLine == null) {
                isDone = true;
                if (autoClose) {
                    reader.close();
                }
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
