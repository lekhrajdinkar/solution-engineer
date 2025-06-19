package java8.streamProcessing;

import java.util.stream.Stream;

// Producer
@FunctionalInterface
public interface FnIStreamGen<T>{
    public Stream<T> generate();
}