package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.IMasterModel;
import department.model.form.MasterUpdateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.MasterViewModel;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created by Максим on 2/7/2017.
 */
@Controller
@Log
public final class EditMasterController extends MasterBaseController {

    private final IMasterModel model;
    private final IDepartmentModel departmentModel;

    private MasterViewModel dataModel;

    @Autowired
    public EditMasterController(IMasterModel model, IDepartmentModel departmentModel) {
        this.model = model;
        this.departmentModel = departmentModel;
    }

    public MasterViewModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(MasterViewModel dataModel) {
        this.dataModel = dataModel;

        fullNameField.setText(dataModel.getFirstName());

        if (dataModel.getStartDate() != null) {
            startDatePicker.setValue(dataModel.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        if (dataModel.getEndDate() != null) {
            endDatePicker.setValue(dataModel.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        phoneField.setText(dataModel.getPhone());
    }

    @Override
    protected void initialize() {

        titleLabel.setText("Редагувати магістра");
        actionButton.setText("Зберегти");
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
                .subscribe(departments -> {

                            if (dataModel.getDepartment() != null) {
                                for (val department : departments) {
                                    departmentComboBox.getItems().add(department);
                                    if (department.getId() == dataModel.getDepartment()) {
                                        departmentComboBox.getSelectionModel().select(department);
                                    }
                                }
                            }
                        }
                        , th -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Не вдалося завантажити список кафедр");
                            alert.showAndWait();
                            log.log(Level.WARNING, "Failed to fetch departments", th);
                        }
                );
    }

    @Override
    protected void onCreate() {

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

        val form = new MasterUpdateForm();

        form.setId(dataModel.getId());
        form.setTeacher(dataModel.getTeacherId());
        form.setTopic(dataModel.getTopic());
        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setStartDate(Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        form.setEndDate(end == null ? null : Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        model.update(form, dataModel, a -> {
            log.log(Level.SEVERE, "Model updated");

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
            alert.setContentText("Не вдалося оновити дані про магістра");
            alert.showAndWait();
        });
    }

}
