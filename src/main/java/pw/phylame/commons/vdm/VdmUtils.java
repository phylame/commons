package pw.phylame.commons.vdm;

import lombok.val;
import pw.phylame.commons.io.IOConsumer;
import pw.phylame.commons.io.IOFunction;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.setting.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public final class VdmUtils {
    public static void useStream(VdmWriter writer, String name, IOConsumer<? super OutputStream> block) throws IOException {
        val entry = writer.newEntry(name);
        try {
            block.accept(writer.putEntry(entry));
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static <R> R useStream(VdmWriter writer, String name, IOFunction<? super OutputStream, R> block) throws IOException {
        val entry = writer.newEntry(name);
        try {
            return block.apply(writer.putEntry(entry));
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static void writeData(VdmWriter writer, String name, byte[] data) throws IOException {
        val entry = writer.newEntry(name);
        try {
            writer.putEntry(entry).write(data);
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static void writeData(VdmWriter writer, String name, byte[] data, int off, int len) throws IOException {
        val entry = writer.newEntry(name);
        try {
            writer.putEntry(entry).write(data, off, len);
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static void writeString(VdmWriter writer, String name, String text) throws IOException {
        writeString(writer, name, text, IOUtils.defaultCharset());
    }

    public static void writeString(VdmWriter writer, String name, String text, Charset charset) throws IOException {
        writeData(writer, name, text.getBytes(charset));
    }

    public static void useStream(VdmReader reader, String name, IOConsumer<? super InputStream> block) throws IOException {
        val entry = reader.getEntry(name);
        if (entry == null) {
            throw new NoSuchFileException(name);
        }
        try (val stream = reader.openStream(entry)) {
            block.accept(stream);
        }
    }

    public static <R> R useStream(VdmReader reader, String name, IOFunction<? super InputStream, R> block) throws IOException {
        val entry = reader.getEntry(name);
        if (entry == null) {
            throw new NoSuchFileException(name);
        }
        try (val stream = reader.openStream(entry)) {
            return block.apply(stream);
        }
    }

    public static byte[] readData(VdmReader reader, String name) throws IOException {
        val stream = reader.openStream(name);
        if (stream == null) {
            throw new NoSuchFileException(name);
        }
        try {
            return IOUtils.toBytes(stream);
        } finally {
            stream.close();
        }
    }

    public static String readString(VdmReader reader, String name) throws IOException {
        return readString(reader, name, IOUtils.defaultCharset());
    }

    public static String readString(VdmReader reader, String name, Charset charset) throws IOException {
        return new String(readData(reader, name), charset);
    }

    public static VdmReader detectReader(Path path, Settings settings) throws IOException {
        val vdmManager = VdmManager.getDefault();
        return Files.isDirectory(path)
                ? vdmManager.openReader(VdmManager.VDM_TYPE_DIRECTORY, path, settings)
                : vdmManager.openReader(VdmManager.VDM_TYPE_ZIP, path, settings);
    }
}
