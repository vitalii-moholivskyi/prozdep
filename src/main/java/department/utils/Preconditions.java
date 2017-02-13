package department.utils;

/**
 * Created by Максим on 2/14/2017.
 */
public final class Preconditions {

    private Preconditions() {
        throw new IllegalStateException("shouldn't be called");
    }

    public static void checkArgument(boolean condition, String message) {
        if (!condition)
            throw new IllegalArgumentException(message);
    }

    public static void checkArgument(boolean condition) {
        if (!condition)
            throw new IllegalArgumentException();
    }

    public static <T> T notNull(T t, String message) {
        if (t == null)
            throw new NullPointerException(message);

        return t;
    }

    public static <T> T notNull(T t) {
        if (t == null)
            throw new NullPointerException();

        return t;
    }

}
