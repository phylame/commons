package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.val;

import java.io.*;

public final class IOUtils {
    private IOUtils() {
    }

    public static final int BUFFER_SIZE = 8192;

    public static final String UNKNOWN_MIME = "application/octet-stream";

    public static void copy(@NonNull InputStream in, @NonNull OutputStream out) throws IOException {
        int bytes;
        val buffer = new byte[BUFFER_SIZE];
        while ((bytes = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytes);
        }
    }

    public static void copy(@NonNull Reader in, @NonNull Writer out) throws IOException {
        int chars;
        val buffer = new char[BUFFER_SIZE];
        while ((chars = in.read(buffer)) != -1) {
            out.write(buffer, 0, chars);
        }
    }

    public static String toString(@NonNull Reader reader) throws IOException {
        val writer = new CharArrayWriter(BUFFER_SIZE << 2);
        copy(reader, writer);
        return writer.toString();
    }
}
