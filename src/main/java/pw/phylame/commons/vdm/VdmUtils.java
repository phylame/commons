package pw.phylame.commons.vdm;

import lombok.val;
import pw.phylame.commons.io.IOConsumer;
import pw.phylame.commons.io.IOFunction;
import pw.phylame.commons.setting.Settings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VdmUtils {
    public static void useStream(VdmWriter writer, String name, IOConsumer<? super OutputStream> block) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        try {
            block.accept(stream);
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static <T> T useStream(VdmWriter writer, String name, IOFunction<? super OutputStream, T> block) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        T result;
        try {
            result = block.apply(stream);
        } finally {
            writer.closeEntry(entry);
        }
        return result;
    }

    public static void writeData(VdmWriter writer, String name, byte[] data) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        try {
            stream.write(data);
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static void writeData(VdmWriter writer, String name, byte[] data, int off, int len) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        try {
            stream.write(data, off, len);
        } finally {
            writer.closeEntry(entry);
        }
    }

    public static VdmReader detectReader(Path path, Settings settings) throws IOException {
        val vdmManager = VdmManager.getDefault();
        return Files.isDirectory(path)
                ? vdmManager.openReader(VdmManager.VDM_TYPE_DIRECTORY, path, settings)
                : vdmManager.openReader(VdmManager.VDM_TYPE_ZIP, path, settings);
    }
}
