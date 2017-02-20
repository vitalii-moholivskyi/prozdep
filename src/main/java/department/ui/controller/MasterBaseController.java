package department.ui.controller;

import department.ui.controller.model.DepartmentViewModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;

/**
 * Created by Максим on 2/20/2017.
 */
@Controller
@Log
public class MasterBaseController {

    @FXML protected Parent viewRoot;
    @FXML protected ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML protected TextField fullNameField;
    @FXML protected TextField phoneField;
    @FXML protected DatePicker startDatePicker;
    @FXML protected DatePicker endDatePicker;
    @FXML protected Button actionButton;
    @FXML protected Label titleLabel;

    @FXML
    protected void initialize() {
    }

    @FXML
    protected void onCreate() {
    }

    protected void showWarning(String header, String body) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

}
