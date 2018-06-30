package pw.phylame.commons.vdm;

import lombok.val;
import pw.phylame.commons.setting.Settings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;

public final class VdmUtils {
    public static void useStream(VdmWriter writer, String name, Consumer<? super OutputStream> block) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        block.accept(stream);
        writer.closeEntry(entry);
    }

    public static <T> T useStream(VdmWriter writer, String name, Function<? super OutputStream, T> block) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        val result = block.apply(stream);
        writer.closeEntry(entry);
        return result;
    }

    public static void writeData(VdmWriter writer, String name, byte[] data) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        stream.write(data);
        writer.closeEntry(entry);
    }

    public static void writeData(VdmWriter writer, String name, byte[] data, int off, int len) throws IOException {
        val entry = writer.newEntry(name);
        val stream = writer.putEntry(entry);
        stream.write(data, off, len);
        writer.closeEntry(entry);
    }

    public static VdmReader detectReader(Path path, Settings settings) throws IOException {
        val vdmManager = VdmManager.getDefault();
        return Files.isDirectory(path)
                ? vdmManager.openReader(VdmManager.VDM_TYPE_DIRECTORY, path, settings)
                : vdmManager.openReader(VdmManager.VDM_TYPE_ZIP, path, settings);
    }
}
