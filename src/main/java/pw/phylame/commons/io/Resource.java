package pw.phylame.commons.io;

import lombok.val;
import pw.phylame.commons.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface Resource {
    long size();

    String getName();

    String getContentType();

    InputStream openStream() throws IOException;

    default void transferTo(OutputStream output) throws IOException {
        Validate.nonNull(output);
        try (val input = openStream()) {
            IOUtils.copy(input, output, -1);
        }
    }
}
