package pw.phylame.commons.text;

import lombok.NonNull;
import lombok.val;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
public final class LineSplitter implements Iterator<String> {
    private final String text;
    private final int length;

    private boolean found;

    private int from = 0;
    private int begin = 0;
    private int end;

    public LineSplitter(@NonNull String text) {
        this.text = text;
        end = length = text.length();
    }

    @Override
    public boolean hasNext() {
        if (!found) {
            begin = from;
            if (begin != end) {
                found = true;
            }
            int i = from, j = from;
            for (; i < length; ++i) {
                char c = text.charAt(i);
                if (c == '\n') {
                    j = i++;
                    break;
                } else if (c == '\r') {
                    j = i;
                    if (i < length - 1 && text.charAt(i + 1) == '\n') {
                        i += 2;
                    } else {
                        i++;
                    }
                    break;
                } else {
                    j = i + 1;
                }
            }
            from = i;
            end = j;
        }
        return found;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        val line = text.substring(begin, end);
        found = false;
        return line;
    }
}