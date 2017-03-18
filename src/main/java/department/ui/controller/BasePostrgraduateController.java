package department.ui.controller;

import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.TeacherViewModel;
import department.ui.controller.model.TopicViewModel;
import department.ui.utils.DefaultStringConverter;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import lombok.val;

import java.time.LocalDate;

import static department.ui.utils.UiConstants.MIN_DATE_ALLOWED;

/**
 * Created by Максим on 2/21/2017.
 */
public class BasePostrgraduateController {

    @FXML protected Parent viewRoot;
    @FXML protected ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML protected TextField fullNameField;
    @FXML protected TextField phoneField;
    @FXML protected DatePicker startDatePicker;
    @FXML protected DatePicker endDatePicker;
    @FXML protected DatePicker defenceDatePicker;
    @FXML protected ComboBox<TeacherViewModel> teacherComboBox;
    @FXML protected ComboBox<TopicViewModel> topicComboBox;

    @FXML
    protected void initialize() {
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
        defenceDatePicker.setEditable(false);

        startDatePicker.setDayCellFactory(startDayFactory());
        endDatePicker.setDayCellFactory(endDayFactory());
        defenceDatePicker.setDayCellFactory(defenceDayFactory());

        startDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> {
                    endDatePicker.setDayCellFactory(endDayFactory());
                    defenceDatePicker.setDayCellFactory(defenceDayFactory());
                });
        endDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> {
                    startDatePicker.setDayCellFactory(startDayFactory());
                    defenceDatePicker.setDayCellFactory(defenceDayFactory());
                });

        defenceDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> {
                    startDatePicker.setDayCellFactory(startDayFactory());
                    endDatePicker.setDayCellFactory(endDayFactory());
                });

        departmentComboBox.setConverter(new DefaultStringConverter<DepartmentViewModel>() {
            @Override
            public String toString(DepartmentViewModel object) {
                return object == null ? "Кафедра" : String.format("%d %s", object.getId(), object.getName());
            }

        });

        topicComboBox.setConverter(new DefaultStringConverter<TopicViewModel>() {
            @Override
            public String toString(TopicViewModel object) {
                return object == null ? "Тема" : object.getName();
            }
        });

        teacherComboBox.setConverter(new DefaultStringConverter<TeacherViewModel>() {
            @Override
            public String toString(TeacherViewModel object) {
                return object == null ? "" : object.getFirstName();
            }
        });
    }

    @FXML
    protected void onCreatePostgraduate() {
    }

    private Callback<DatePicker, DateCell> defenceDayFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        val endDate = endDatePicker.getValue();
                        val startDate = startDatePicker.getValue();

                        val isBeforeStart = startDate != null && (item.isBefore(startDate) || item.isEqual(startDate));
                        val isAfterEnd = endDate != null && (item.isAfter(endDate) || item.isEqual(endDate));

                        setDisable(item.isBefore(MIN_DATE_ALLOWED) || item.isAfter(LocalDate.now())
                                || isBeforeStart || isAfterEnd);
                    }
                };
            }
        };
    }

    private Callback<DatePicker, DateCell> startDayFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        val endDate = endDatePicker.getValue();
                        val defenceDate = defenceDatePicker.getValue();

                        val isAfterEnd = endDate != null && (item.isAfter(endDate) || item.isEqual(endDate));
                        val isAfterDefence = defenceDate != null && (item.isAfter(defenceDate) || item.isEqual(defenceDate));

                        setDisable(item.isBefore(MIN_DATE_ALLOWED) || item.isAfter(LocalDate.now())
                                || isAfterEnd || isAfterDefence);
                    }
                };
            }
        };
    }

    private Callback<DatePicker, DateCell> endDayFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        val startDate = startDatePicker.getValue();
                        val defenceDate = defenceDatePicker.getValue();

                        val isBeforeStart = startDate != null && (item.isBefore(startDate) || item.isEqual(startDate));
                        val isBeforeDefence = defenceDate != null && (item.isBefore(defenceDate) || item.isEqual(defenceDate));

                        setDisable(item.isBefore(MIN_DATE_ALLOWED) || item.isAfter(LocalDate.now())
                                || isBeforeStart || isBeforeDefence);
                    }
                };
            }
        };
    }

}
