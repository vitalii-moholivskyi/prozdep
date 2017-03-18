package department.ui.controller;

import department.ui.controller.model.DepartmentViewModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;

import static department.ui.utils.UiUtils.endDayFactory;
import static department.ui.utils.UiUtils.startDayFactory;

/**
 * Created by Максим on 2/20/2017.
 */
@Controller
@Log
public class MasterBaseController {

    @FXML
    protected Parent viewRoot;
    @FXML
    protected ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML
    protected TextField fullNameField;
    @FXML
    protected TextField phoneField;
    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DatePicker endDatePicker;
    @FXML
    protected Button actionButton;
    @FXML
    protected Label titleLabel;

    @FXML
    protected void initialize() {

        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
        startDatePicker.setDayCellFactory(startDayFactory(endDatePicker));
        endDatePicker.setDayCellFactory(endDayFactory(startDatePicker));

        startDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> endDatePicker.setDayCellFactory(endDayFactory(startDatePicker)));
        endDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> startDatePicker.setDayCellFactory(startDayFactory(endDatePicker)));
    }

    @FXML
    protected void onCreate() {
    }

}
