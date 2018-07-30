package pw.phylame.commons.io;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import pw.phylame.commons.CollectionUtils;
import pw.phylame.commons.Reflections;
import pw.phylame.commons.text.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
@Slf4j
public final class MapFileTypeDetector extends FileTypeDetector {
    private static final String MIME_TYPE_FILE_NAME = "mime.properties";

    private final HashMap<String, String> mimeMap = new HashMap<>();

    public MapFileTypeDetector() {
        val path = Reflections.resolvePath(getClass(), MIME_TYPE_FILE_NAME);
        try {
            val urls = getClass().getClassLoader().getResources(path);
            while (urls.hasMoreElements()) {
                try (val input = urls.nextElement().openStream()) {
                    val props = new Properties();
                    props.load(input);
                    CollectionUtils.copy(props, mimeMap);
                }
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("cannot load mime types from " + path, e);
            }
        }
    }

    @Override
    public String probeContentType(Path path) {
        val ext = FilenameUtils.extName(path.toString());
        return StringUtils.isNotEmpty(ext) ? mimeMap.get(ext) : null;
    }
}
