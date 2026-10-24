package exercises0001;

import java.io.IOException;

@FunctionalInterface
public interface ThrowIOException<T> {
    public void throwIOException(T t) throws IOException;
}
