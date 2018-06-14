package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.value.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class FilenameUtils {
    public static final String UNKNOWN_MIME_TYPE = "application/octet-stream";

    @SneakyThrows(IOException.class)
    public static String normalized(String path) {
        return new File(path).getCanonicalPath();
    }

    public static String slashified(@NonNull String path) {
        return path.replace('\\', '/');
    }

    public static Pair<Integer, Integer> splitPath(@NonNull String path) {
        int begin = path.length(), end;
        boolean found = false;

        char ch;
        for (end = begin - 1; end >= 0; --end) {
            ch = path.charAt(end);
            if (ch == '.' && !found) {
                begin = end;
                found = true;
            } else if (ch == '/' || ch == '\\') {
                break;
            }
        }

        return Pair.of(end, begin);
    }

    public static String dirName(String path) {
        val index = splitPath(path).getFirst();
        return index != -1 ? path.substring(0, index) : "";
    }

    public static String fullName(String path) {
        int index = splitPath(path).getFirst();
        return path.substring(index != 0 ? index + 1 : index);
    }

    public static String baseName(String path) {
        val pair = splitPath(path);
        return path.substring(pair.getFirst() + 1, pair.getSecond());
    }

    public static String extName(String path) {
        int index = splitPath(path).getSecond();
        return index != path.length() ? path.substring(index + 1) : "";
    }

    @SneakyThrows(IOException.class)
    public static String mimeType(String path) {
        return Files.probeContentType(Paths.get(path));
    }
}
