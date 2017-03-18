package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.ITeacherModel;
import department.model.form.TeacherCreateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;

import static department.ui.utils.UiConstants.MIN_DATE_ALLOWED;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class CreateTeacherController {

    private final ITeacherModel teacherModel;
    private final IDepartmentModel departmentModel;

    @FXML
    private Parent viewRoot;
    @FXML
    private ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField degreeField;
    @FXML
    private DatePicker startDatePicker;

    public CreateTeacherController(ITeacherModel teacherModel, IDepartmentModel departmentModel) {
        this.teacherModel = teacherModel;
        this.departmentModel = departmentModel;
    }

    @FXML
    private void initialize() {

        startDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(MIN_DATE_ALLOWED) || item.isAfter(LocalDate.now()));
                    }
                };
            }
        });

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
                .doOnCompleted(() -> departmentComboBox.getSelectionModel().selectFirst())
                .subscribe(departments -> departmentComboBox.getItems().addAll(departments)
                        , th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список кафедр").showAndWait();
                            log.log(Level.WARNING, "Failed to fetch departments", th);
                        }
                );
    }

    @FXML
    private void onCreateTeacher() {

        val department = departmentComboBox.valueProperty().get();

        if (department == null) {
            UiUtils.createWarnDialog("Не обрано кафедру, для того, " +
                    "аби продовжити, виберіть кафедру зі списку").showAndWait();
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
            UiUtils.createWarnDialog("Дату початку роботи не вказано").showAndWait();
            startDatePicker.requestFocus();
            return;
        }

        val degree = degreeField.getText();

        if (TextUtils.isEmpty(degree)) {
            UiUtils.createWarnDialog("Ступінь не вказано").showAndWait();
            degreeField.requestFocus();
            return;
        }

        val position = positionField.getText();

        if (TextUtils.isEmpty(position)) {
            UiUtils.createWarnDialog("Посаду не вказано").showAndWait();
            positionField.requestFocus();
            return;
        }

        val form = new TeacherCreateForm();

        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setStartDate(Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        form.setDegree(degree);
        form.setPosition(position);

        teacherModel.create(form).subscribe(master -> {
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
            UiUtils.createErrDialog("Не вдалося створити викладача").showAndWait();
        });
    }

}
