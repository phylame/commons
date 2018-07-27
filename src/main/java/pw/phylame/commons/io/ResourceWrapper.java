package pw.phylame.commons.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
@RequiredArgsConstructor
public abstract class ResourceWrapper implements Resource {
    @NonNull
    private final Resource resource;

    @Override
    public long size() {
        return resource.size();
    }

    @Override
    public String getName() {
        return resource.getName();
    }

    @Override
    public String getContentType() {
        return resource.getContentType();
    }

    @Override
    public InputStream openStream() throws IOException {
        return resource.openStream();
    }

    @Override
    public void transferTo(ByteSink output) throws IOException {
        resource.transferTo(output);
    }

    @Override
    public int hashCode() {
        return resource.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return resource.equals(o);
    }

    @Override
    public String toString() {
        return resource.toString();
    }
}
