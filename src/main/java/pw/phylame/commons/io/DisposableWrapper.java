package pw.phylame.commons.io;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DisposableWrapper<C extends AutoCloseable> extends DisposableSupport {
    @Getter
    @NonNull
    private final C source;

    @Override
    public void close() throws Exception {
        source.close();
    }
}
