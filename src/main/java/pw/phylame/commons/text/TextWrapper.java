package pw.phylame.commons.text;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
@RequiredArgsConstructor
public class TextWrapper implements Text {
    @NonNull
    private final Text text;

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public String getType() {
        return text.getType();
    }

    @Override
    public String toString() {
        return text.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return text.iterator();
    }

    @Override
    public void writeTo(Writer output) throws IOException {
        text.writeTo(output);
    }

    @Override
    public boolean equals(Object obj) {
        return text.equals(obj);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
