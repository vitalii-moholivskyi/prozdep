package department.utils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Максим on 3/18/2017.
 */
public final class DateUtils {

    private DateUtils() {
        throw new IllegalStateException();
    }

    public static Date fromLocal(@NotNull LocalDate local) {
        return Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date tryFromLocal(LocalDate local) {
        return local == null ? null : fromLocal(local);
    }

    public static LocalDate toLocal(@NotNull Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate tryToLocal(Date date) {
        return date == null ? null : toLocal(date);
    }

}
