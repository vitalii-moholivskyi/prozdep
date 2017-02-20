package department.ui.controller;

import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.TeacherViewModel;
import department.ui.controller.model.TopicViewModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    }

    @FXML
    protected void onCreatePostgraduate() {
    }

    protected void showWarning(String header, String body) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

}
