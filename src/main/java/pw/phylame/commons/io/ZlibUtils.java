package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.val;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.*;

public final class ZlibUtils {
    /**
     * Compresses specified byte data with default compression level.
     *
     * @param data the input byte data to be compressed
     * @return compressed data
     */
    public static byte[] compress(byte[] data) {
        return compress(data, Deflater.DEFAULT_COMPRESSION);
    }

    /**
     * Compresses specified input data with specified compression level.
     *
     * @param data  the input byte data to be compressed
     * @param level zlib compression level
     * @return compressed data
     */
    public static byte[] compress(byte[] data, int level) {
        return compress(data, 0, data.length, level);
    }

    /**
     * Compresses a specified area of input byte data with default compression level.
     *
     * @param data   the input byte data
     * @param offset start index of compressing area
     * @param length length of compressing area
     * @return compressed data
     */
    public static byte[] compress(byte[] data, int offset, int length) {
        return compress(data, offset, length, Deflater.DEFAULT_COMPRESSION);
    }

    /**
     * Compresses a specified area of input byte data with specified compression level.
     *
     * @param data   the input byte data
     * @param offset start index of compressing area
     * @param length length of compressing area
     * @param level  zlib compression level
     * @return compressed data
     */
    public static byte[] compress(@NonNull byte[] data, int offset, int length, int level) {
        if (data.length == 0) {
            return data;
        }

        val deflater = new Deflater(level);
        deflater.setInput(data, offset, length);

        val output = new ByteBuilder(length);
        val buffer = new byte[IOUtils.DEFAULT_BUFFER_SIZE];
        while (!deflater.finished()) {
            output.write(buffer, 0, deflater.deflate(buffer));
        }
        deflater.end();

        return output.getRawArray();
    }

    /**
     * Compresses specified input data with default compression level
     * and writes to output.
     *
     * @param data   the input byte data to be compressed
     * @param output the output stream
     * @throws IOException if occur IO errors
     */
    public static void compress(byte[] data, OutputStream output) throws IOException {
        compress(data, 0, data.length, output);
    }

    /**
     * Compresses a specified area of input data with default compression level
     * and writes to output.
     *
     * @param data   the input byte data
     * @param offset start index of compressing area
     * @param length length of compression area
     * @param output the output stream
     * @throws IOException if occur IO errors
     */
    public static void compress(byte[] data, int offset, int length, OutputStream output) throws IOException {
        val dos = new DeflaterOutputStream(output);
        dos.write(data, offset, length);
        dos.finish();
        dos.flush();
    }

    /**
     * Decompresses specified input byte data.
     *
     * @param data the input byte data to be decompressed
     * @return decompressed data
     * @throws DataFormatException if the compressed data format is invalid
     */
    public static byte[] decompress(byte[] data) throws DataFormatException {
        return decompress(data, 0, data.length);
    }

    /**
     * Decompresses a specified area of input data.
     *
     * @param data   the input byte data
     * @param offset start index of decompressing area
     * @param length length of decompression area
     * @return decompressed data
     * @throws DataFormatException if the compressed data format is invalid
     */
    public static byte[] decompress(@NonNull byte[] data, int offset, int length) throws DataFormatException {
        if (data.length == 0) {
            return data;
        }

        val inflater = new Inflater();
        inflater.setInput(data, offset, length);

        val output = new ByteBuilder(length);
        val buffer = new byte[IOUtils.DEFAULT_BUFFER_SIZE];
        while (!inflater.finished()) {
            output.write(buffer, 0, inflater.inflate(buffer));
        }
        inflater.end();

        return output.getRawArray();
    }

    /**
     * Decompresses byte data from specified input stream.
     *
     * @param input the input stream
     * @return decompressed data
     * @throws IOException if an I/O error occurs
     */
    public static byte[] decompress(InputStream input) throws IOException {
        val iis = new InflaterInputStream(input);

        val output = new ByteBuilder();
        val buffer = new byte[IOUtils.DEFAULT_BUFFER_SIZE];

        int bytes;
        while ((bytes = iis.read(buffer)) > 0) {
            output.write(buffer, 0, bytes);
        }

        return output.getRawArray();
    }
}
