package pw.phylame.commons.io;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import pw.phylame.commons.value.Lazy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Properties;

@Slf4j
public class LocalMimeDetector extends FileTypeDetector {
    private final Lazy<Properties> mimes = new Lazy<>(() -> {
        val props = new Properties();
        try (val in = getClass().getResourceAsStream("mime.properties")) {
            props.load(in);
        } catch (IOException e) {
            log.error("cannot load 'mime.properties'", e);
        }
        return props;
    });

    @Override
    public String probeContentType(Path path) throws IOException {
        return mimes.get().getProperty(PathUtils.extName(path.toString()));
    }
}
