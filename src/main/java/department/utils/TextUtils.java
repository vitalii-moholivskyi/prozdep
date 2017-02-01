package department.utils;

/**
 * Created by Максим on 2/1/2017.
 */
public final class TextUtils {

    private TextUtils() {
        throw new RuntimeException();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}
