package pw.phylame.commons.vdm.zip;

import lombok.NonNull;
import lombok.val;
import lombok.var;
import pw.phylame.commons.io.IOUtils;
import pw.phylame.commons.setting.Settings;
import pw.phylame.commons.setting.SettingsUtils;
import pw.phylame.commons.vdm.VdmFactory;
import pw.phylame.commons.vdm.VdmManager;
import pw.phylame.commons.vdm.VdmReader;
import pw.phylame.commons.vdm.VdmWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class ZipVdmFactory implements VdmFactory {
    public static final String CHARSET_KEY = "vdm.zip.charset";
    public static final String METHOD_KEY = "vdm.zip.method";
    public static final String LEVEL_KEY = "vdm.zip.level";

    @Override
    public VdmReader getReader(@NonNull Object input, Settings settings) throws IOException {
        var charset = SettingsUtils.get(settings, CHARSET_KEY, Charset.class);
        if (charset == null) {
            charset = IOUtils.defaultCharset();
        }
        ZipFile zipFile;
        if (input instanceof CharSequence) {
            zipFile = new ZipFile(input.toString(), charset);
        } else if (input instanceof File) {
            zipFile = new ZipFile((File) input, charset);
        } else if (input instanceof Path) {
            zipFile = new ZipFile(((Path) input).toFile(), charset);
        } else if (input instanceof ZipFile) {
            zipFile = (ZipFile) input;
        } else {
            throw new IllegalArgumentException(input.toString());
        }
        return new ZipVdmReader(zipFile);
    }

    @Override
    public VdmWriter getWriter(@NonNull Object output, Settings settings) throws IOException {
        var charset = SettingsUtils.get(settings, CHARSET_KEY, Charset.class);
        if (charset == null) {
            charset = IOUtils.defaultCharset();
        }
        ZipOutputStream zipOutput;
        if (output instanceof CharSequence) {
            zipOutput = new ZipOutputStream(new FileOutputStream(output.toString()), charset);
        } else if (output instanceof File) {
            zipOutput = new ZipOutputStream(new FileOutputStream((File) output), charset);
        } else if (output instanceof Path) {
            zipOutput = new ZipOutputStream(Files.newOutputStream((Path) output), charset);
        } else if (output instanceof ZipOutputStream) {
            zipOutput = (ZipOutputStream) output;
        } else if (output instanceof OutputStream) {
            zipOutput = new ZipOutputStream((OutputStream) output, charset);
        } else {
            throw new IllegalArgumentException(output.toString());
        }
        val method = SettingsUtils.getInt(settings, METHOD_KEY);
        zipOutput.setMethod(method != null ? method : ZipOutputStream.DEFLATED);
        val level = SettingsUtils.getInt(settings, LEVEL_KEY);
        zipOutput.setLevel(level != null ? level : Deflater.DEFAULT_COMPRESSION);
        return new ZipVdmWriter(zipOutput);
    }

    @Override
    public Set<String> getKeys() {
        return Collections.singleton(VdmManager.VDM_TYPE_ZIP);
    }
}
