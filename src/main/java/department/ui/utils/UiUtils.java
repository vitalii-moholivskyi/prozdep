package department.ui.utils;

import department.di.Injector;
import department.utils.TextUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.val;
import rx.functions.Func1;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static department.ui.utils.UiConstants.MIN_DATE_ALLOWED;

/**
 * Created by Максим on 1/31/2017.
 */
@Value
@Getter(value = AccessLevel.NONE)
public final class UiUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static final Func1<? super String, ? extends String> NULLABLE_FLD_MAPPER =
            str -> TextUtils.isEmpty(str) ? "" : str;

    public static final Func1<? super Date, ? extends String> DATE_FLD_MAPPER =
            date -> date == null ? "" : DATE_FORMAT.format(date);

    /**
     * default callback which takes controllers from app context as beans
     */
    static Callback<Class<?>, Object> DEFAULT_CALLBACK = cl ->
            Objects.requireNonNull(Injector.getInstance().getContext().getBean(cl),
                    String.format("Controller %s wasn't found. Are you missing component registering?", cl));


    private UiUtils() {
        throw new IllegalStateException("shouldn't be called");
    }

    /**
     * creates loader which loads ui and controller by
     * class which is defined in corresponding fxml file
     */
    public static FXMLLoader newLoader(String filePath) {
        return UiUtils.newLoader(filePath, (ResourceBundle) null);
    }

    /**
     * creates loader which loads ui and controller by class which is defined
     * in corresponding fxml file
     */
    public static FXMLLoader newLoader(String filePath, ResourceBundle bundle) {
        Objects.requireNonNull(filePath, "path to a file wasn't specified!");

        val loader = new FXMLLoader(UiUtils.class.getResource(filePath), bundle);

        loader.setControllerFactory(DEFAULT_CALLBACK);
        return loader;
    }

    /**
     * creates loader which loads ui and controller; This method will load and attach specified controller
     */
    public static FXMLLoader newLoader(String filePath, Class<?> controller) {
        return UiUtils.newLoader(filePath, controller, null);
    }

    /**
     * creates loader which loads ui and controller; This method will load and attach specified controller
     */
    public static FXMLLoader newLoader(String filePath, Class<?> controller, ResourceBundle bundle) {
        Objects.requireNonNull(filePath, "path to a file wasn't specified!");

        val loader = new FXMLLoader(UiUtils.class.getResource(filePath), bundle);

        loader.setControllerFactory(cl -> Injector.getInstance().getContext().getBean(controller));
        return loader;
    }

    public static Alert createErrDialog(String title, String body) {
        return createDialog(Alert.AlertType.ERROR, title, body);
    }

    public static Alert createErrDialog(String body) {
        return createErrDialog("Помилка", body);
    }

    public static Alert createWarnDialog(String title, String body) {
        return createDialog(Alert.AlertType.WARNING, title, body);
    }

    public static Alert createWarnDialog(String body) {
        return createDialog(Alert.AlertType.WARNING, "Попередження", body);
    }

    public static Alert createInfoDialog(String title, String body) {
        return createDialog(Alert.AlertType.INFORMATION, title, body);
    }

    public static Alert createDialog(Alert.AlertType type, String title, String body) {
        val alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(body);

        return alert;
    }

    public static Callback<DatePicker, DateCell> startDayFactory(DatePicker endDatePicker) {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        val endDate = endDatePicker.getValue();
                        setDisable(item.isBefore(MIN_DATE_ALLOWED) || item.isAfter(LocalDate.now())
                                || (endDate != null && (item.isAfter(endDate) || item.isEqual(endDate))));
                    }
                };
            }
        };
    }

    public static Callback<DatePicker, DateCell> endDayFactory(DatePicker startDatePicker) {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        val startDate = startDatePicker.getValue();
                        setDisable(item.isBefore(MIN_DATE_ALLOWED) || item.isAfter(LocalDate.now())
                                || (startDate != null && (item.isBefore(startDate) || item.isEqual(startDate))));
                    }
                };
            }
        };
    }

}
