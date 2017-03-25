package department.ui.utils;

import java.time.LocalDate;

/**
 * Created by Максим on 2/18/2017.
 */
public final class UiConstants {

    private UiConstants() {
        throw new RuntimeException();
    }

    public static final long DURATION_SHORT = 1000L;
    public static final long DURATION_NORMAL = 2000L;
    public static final long DURATION_LONG = 3000L;

    public static final int RESULTS_PER_PAGE = 30;

    public static final int HINT_RESULT = 15;

    public static final LocalDate MIN_DATE_ALLOWED = LocalDate.of(1900, 1, 1);

}
