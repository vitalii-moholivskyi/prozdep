package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.IPostgraduateModel;
import department.model.form.PostgraduateCreateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.logging.Level;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class CreatePostgraduateController {

    private final IPostgraduateModel postgraduateModel;
    private final IDepartmentModel departmentModel;

    @FXML private Parent viewRoot;
    @FXML private ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML private TextField fullNameField;
    @FXML private TextField phoneField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private DatePicker defenceDatePicker;

    public CreatePostgraduateController(IPostgraduateModel postgraduateModel, IDepartmentModel departmentModel) {
        this.postgraduateModel = postgraduateModel;
        this.departmentModel = departmentModel;
    }

    @FXML
    private void initialize() {

        departmentComboBox.setConverter(new StringConverter<DepartmentViewModel>() {
            @Override
            public String toString(DepartmentViewModel object) {
                return String.format("%d %s", object.getId(), object.getName());
            }

            @Override
            public DepartmentViewModel fromString(String string) {
                return null;
            }
        });

        departmentModel.fetchDepartments(0, Integer.MAX_VALUE)
                .subscribe(departments -> departmentComboBox.getItems().addAll(departments)
                        , th -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Помилка");
                            alert.setContentText("Не вдалося завантажити список кафедр");
                            alert.showAndWait();
                            log.log(Level.WARNING, "Failed to fetch departments", th);
                        }
                );
    }

    @FXML
    private void onCreatePostgraduate() {

        val department = departmentComboBox.valueProperty().get();

        if (department == null) {
            showWarning("Не обрано кафедру", "Для того, аби продовжити, виберіть кафедру зі списку");
            departmentComboBox.requestFocus();
            return;
        }

        val name = fullNameField.getText();

        if (TextUtils.isEmpty(name)) {
            showWarning(null, "ФІБ не вказано");
            fullNameField.requestFocus();
            return;
        }

        val start = startDatePicker.getValue();

        if (start == null) {
            showWarning(null, "Дату вступу не вказано");
            startDatePicker.requestFocus();
            return;
        }

        val end = endDatePicker.getValue();
        val defence = defenceDatePicker.getValue();

        val form = new PostgraduateCreateForm();

        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setStartDate(new Date(start.toEpochDay()));
        form.setEndDate(end == null ? null : new Date(end.toEpochDay()));
        form.setProtectionDate(defence == null ? null : new Date(defence.toEpochDay()));

        postgraduateModel.create(form).subscribe(master -> {
            log.log(Level.SEVERE, "Model created");

            if (viewRoot.getScene() == null) {
                RxUtils.fromProperty(viewRoot.sceneProperty())
                        .filter(scene -> scene != null)
                        .flatMap(scene -> RxUtils.fromProperty(scene.windowProperty()))
                        .filter(window -> window != null && window instanceof Stage)
                        .take(1)
                        .map(window -> (Stage) window)
                        .subscribe(Stage::close);
            } else {
                ((Stage) viewRoot.getScene().getWindow()).close();
            }
        }, th -> {
            log.log(Level.SEVERE, "Failed to create model", th);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setContentText("Не вдалося створити магістра");
            alert.showAndWait();
        });
    }

    private void showWarning(String header, String body) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

}
