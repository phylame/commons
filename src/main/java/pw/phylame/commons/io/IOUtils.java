package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.NestedException;
import pw.phylame.commons.text.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilities for byte and char stream.
 *
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class IOUtils {
    private static final int EOF = -1;

    public static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void closeQuietly(AutoCloseable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                throw new NestedException(e);
            }
        }
    }

    public static Charset defaultCharset() {
        return StandardCharsets.UTF_8;
    }

    public static Charset charsetFor(String name) {
        return StringUtils.isNotEmpty(name) ? Charset.forName(name) : defaultCharset();
    }

    public static BufferedInputStream buffered(InputStream input) {
        return !(input instanceof BufferedInputStream)
                ? new BufferedInputStream(input, DEFAULT_BUFFER_SIZE)
                : (BufferedInputStream) input;
    }

    public static BufferedOutputStream buffered(OutputStream output) {
        return !(output instanceof BufferedOutputStream)
                ? new BufferedOutputStream(output, DEFAULT_BUFFER_SIZE)
                : (BufferedOutputStream) output;
    }

    public static long copy(InputStream input, OutputStream output, long size) throws IOException {
        return copy(input, output, size, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(InputStream input, OutputStream output, long size, int bufferSize) throws IOException {
        return copy(ByteSource.of(input), ByteSink.of(output), size, bufferSize);
    }

    public static long copy(ByteSource input, ByteSink output, long size) throws IOException {
        return copy(input, output, size, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(@NonNull ByteSource input, @NonNull ByteSink output, long size, int bufferSize) throws IOException {
        val buf = new byte[bufferSize];

        int n;
        long copied = 0L;
        while ((n = input.read(buf, 0, bufferSize)) != EOF) {
            copied += n;
            if (size < 0 || copied < size) {
                output.write(buf, 0, n);
            } else {
                output.write(buf, 0, n - (int) (copied - size));
                copied = size;
                break;
            }
        }

        return copied;
    }

    public static BufferedReader buffered(Reader reader) {
        return !(reader instanceof BufferedReader)
                ? new BufferedReader(reader, DEFAULT_BUFFER_SIZE)
                : (BufferedReader) reader;
    }

    public static BufferedWriter buffered(Writer writer) {
        return !(writer instanceof BufferedWriter)
                ? new BufferedWriter(writer, DEFAULT_BUFFER_SIZE)
                : (BufferedWriter) writer;
    }

    public static long copy(@NonNull Reader input, @NonNull Writer output, long size) throws IOException {
        return copy(input, output, size, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(@NonNull Reader input, @NonNull Writer output, long size, int bufferSize) throws IOException {
        val buf = new char[bufferSize];

        int n;
        long copied = 0L;
        while ((n = input.read(buf)) != EOF) {
            copied += n;
            if (size < 0 || copied < size) {
                output.write(buf, 0, n);
            } else {
                output.write(buf, 0, n - (int) (copied - size));
                copied = size;
                break;
            }
        }

        return copied;
    }

    public static String toString(InputStream input) throws IOException {
        return toString(new InputStreamReader(input, defaultCharset()));
    }

    public static String toString(InputStream input, Charset charset) throws IOException {
        return toString(new InputStreamReader(input, charset));
    }

    public static String toString(Reader reader) throws IOException {
        val out = new CharArrayWriter();
        copy(reader, out, -1);
        return out.toString();
    }

    public static List<String> toLines(InputStream input) {
        return toLines(input, defaultCharset());
    }

    public static List<String> toLines(InputStream input, Charset charset) {
        return toLines(new InputStreamReader(input, charset));
    }

    public static List<String> toLines(Reader reader) {
        return buffered(reader).lines().collect(Collectors.toList());
    }
}
