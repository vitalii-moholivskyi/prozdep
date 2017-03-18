package department.ui.utils;

import java.time.LocalDate;

/**
 * Created by Максим on 3/18/2017.
 */
public class DateDisableRange {

    private final LocalDate initialDate;
    private final LocalDate endDate;

    public DateDisableRange(LocalDate initialDate, LocalDate endDate) {
        this.initialDate = initialDate;
        this.endDate = endDate;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
