package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import pw.phylame.commons.value.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class PathUtils {
    private PathUtils() {
    }

    public static Pair<Integer, Integer> splitPath(@NonNull String path) {
        int end = path.length(), begin;
        boolean found = false;
        for (begin = end - 1; begin >= 0; --begin) {
            val c = path.charAt(begin);
            if (c == '.' && !found) {
                end = begin;
                found = true;
            } else if (c == '/' || c == '\\') {
                break;
            }
        }
        return new Pair<>(begin, end);
    }

    public static String dirName(@NonNull String path) {
        val index = splitPath(path).getFirst();
        return index != -1 ? path.substring(0, index) : "";
    }

    public static String fullName(@NonNull String path) {
        int index = splitPath(path).getFirst();
        return path.substring(index != 0 ? index + 1 : index);
    }

    public static String baseName(@NonNull String path) {
        val pair = splitPath(path);
        return path.substring(pair.getFirst() + 1, pair.getSecond());
    }

    public static String extName(@NonNull String path) {
        int index = splitPath(path).getSecond();
        return index != path.length() ? path.substring(index + 1) : "";
    }

    @SneakyThrows(IOException.class)
    public static String mimeType(@NonNull String name) {
        return Files.probeContentType(Paths.get(name));
    }
}
