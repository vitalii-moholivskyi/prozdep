package department.ui.controller.update;

import department.model.IDepartmentModel;
import department.model.IMasterModel;
import department.model.form.MasterUpdateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.MasterViewModel;
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

import java.time.ZoneId;
import java.util.logging.Level;

import static department.ui.utils.UiUtils.endDayFactory;
import static department.ui.utils.UiUtils.startDayFactory;

/**
 * Created by Максим on 2/7/2017.
 */
@Controller
@Log
public final class EditMasterController {

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
    @FXML
    private Button actionButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Label errorLabel;

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

    protected void initialize() {
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
        startDatePicker.setDayCellFactory(startDayFactory(endDatePicker));
        endDatePicker.setDayCellFactory(endDayFactory(startDatePicker));

        startDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> endDatePicker.setDayCellFactory(endDayFactory(startDatePicker)));
        endDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> startDatePicker.setDayCellFactory(startDayFactory(endDatePicker)));

        titleLabel.setText("Редагувати магістра");
        actionButton.setText("Зберегти");
        departmentComboBox.setConverter(new DefaultStringConverter<DepartmentViewModel>() {
            @Override
            public String toString(DepartmentViewModel object) {
                return String.format("%d %s", object.getId(), object.getName());
            }
        });

        departmentModel.fetchDepartments(0, Integer.MAX_VALUE)
                .subscribe(departments -> {

                            for (val department : departments) {
                                departmentComboBox.getItems().add(department);
                                if (dataModel.getDepartment() != null && department.getId() == dataModel.getDepartment()) {
                                    departmentComboBox.getSelectionModel().select(department);
                                }
                            }
                        }, th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список кафедр").showAndWait();
                            errorLabel.setText("Не вдалося завантажити список кафедр");
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

        val form = new MasterUpdateForm();

        form.setId(dataModel.getId());
        form.setTeacher(dataModel.getTeacherId());
        form.setTopic(dataModel.getTopic());
        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setStartDate(DateUtils.fromLocal(start));
        form.setEndDate(DateUtils.tryFromLocal(end));

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
            errorLabel.setText("Не вдалося оновити дані про магістра");
            UiUtils.createErrDialog("Не вдалося оновити дані про магістра").showAndWait();
        });
    }

}
