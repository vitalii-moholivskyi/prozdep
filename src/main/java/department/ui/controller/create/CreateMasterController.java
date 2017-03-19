package department.ui.controller.create;

import department.model.IDepartmentModel;
import department.model.IMasterModel;
import department.model.form.MasterCreateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.utils.DefaultStringConverter;
import department.ui.utils.UiUtils;
import department.utils.DateUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;

import static department.ui.utils.UiUtils.endDayFactory;
import static department.ui.utils.UiUtils.startDayFactory;

/**
 * Created by Максим on 2/7/2017.
 */
@Controller
@Log
public final class CreateMasterController {

    @FXML
    private Parent viewRoot;
    @FXML
    private ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private final IMasterModel model;
    private final IDepartmentModel departmentModel;

    @Autowired
    public CreateMasterController(IMasterModel model, IDepartmentModel departmentModel) {
        this.model = model;
        this.departmentModel = departmentModel;
    }

    @FXML
    private void initialize() {

        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
        startDatePicker.setDayCellFactory(startDayFactory(endDatePicker));
        endDatePicker.setDayCellFactory(endDayFactory(startDatePicker));

        startDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> endDatePicker.setDayCellFactory(endDayFactory(startDatePicker)));
        endDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> startDatePicker.setDayCellFactory(startDayFactory(endDatePicker)));

        departmentComboBox.setConverter(new DefaultStringConverter<DepartmentViewModel>() {
            @Override
            public String toString(DepartmentViewModel object) {
                return String.format("%d %s", object.getId(), object.getName());
            }
        });

        departmentModel.fetchDepartments(0, Integer.MAX_VALUE)
                .subscribe(departments -> departmentComboBox.getItems().addAll(departments)
                        , th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список кафедр").showAndWait();
                            log.log(Level.WARNING, "Failed to fetch departments", th);
                        }
                );
    }

    @FXML
    private void onCreate() {

        val department = departmentComboBox.valueProperty().get();

        if (department == null) {
            UiUtils.createWarnDialog("Для того, аби продовжити, виберіть кафедру зі списку").showAndWait();
            departmentComboBox.requestFocus();
            return;
        }

        val name = fullNameField.getText();

        if (TextUtils.isEmpty(name)) {
            UiUtils.createWarnDialog("ФІБ не вказано").showAndWait();
            fullNameField.requestFocus();
            return;
        }

        val start = startDatePicker.getValue();

        if (start == null) {
            UiUtils.createWarnDialog("Дату вступу не вказано").showAndWait();
            startDatePicker.requestFocus();
            return;
        }

        val end = endDatePicker.getValue();
        val form = new MasterCreateForm();

        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setStartDate(DateUtils.fromLocal(start));
        form.setEndDate(DateUtils.tryFromLocal(end));

        model.create(form).subscribe(master -> {
            log.log(Level.INFO, "Model created");

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
            UiUtils.createErrDialog("Не вдалося створити магістра").showAndWait();
        });
    }

}
