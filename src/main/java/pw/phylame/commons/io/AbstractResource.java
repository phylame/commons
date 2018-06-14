package pw.phylame.commons.io;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pw.phylame.commons.text.StringUtils;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
@RequiredArgsConstructor
public abstract class AbstractResource implements Resource {
    private final String mime;

    @Getter(lazy = true)
    private final String contentType = detectContentType();

    @Override
    public String toString() {
        return getName() + ";mime=" + getContentType();
    }

    private String detectContentType() {
        if (StringUtils.isNotEmpty(mime)) {
            return mime;
        }
        val mimeType = FilenameUtils.mimeType(getName());
        if (mimeType != null) {
            return mimeType;
        }
        return FilenameUtils.UNKNOWN_MIME_TYPE;
    }
}
