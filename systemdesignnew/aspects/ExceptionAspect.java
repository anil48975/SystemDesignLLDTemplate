package systemdesignnew.aspects;

import java.util.function.Function;

public class ExceptionAspect {
    public <T, R> Function<T, R> handleException(Function<T, R> fun) {
        System.out.println("Exception handled.");
        return (t) -> {
            return fun.apply(t);
        };
    }
}
