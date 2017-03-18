package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.IMasterModel;
import department.model.form.MasterCreateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.utils.DefaultStringConverter;
import department.ui.utils.UiUtils;
import department.utils.DateUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;

/**
 * Created by Максим on 2/7/2017.
 */
@Controller
@Log
public final class CreateMasterController extends MasterBaseController {

    private final IMasterModel model;
    private final IDepartmentModel departmentModel;

    @Autowired
    public CreateMasterController(IMasterModel model, IDepartmentModel departmentModel) {
        this.model = model;
        this.departmentModel = departmentModel;
    }

    @Override
    protected void initialize() {
        super.initialize();

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

    @Override
    protected void onCreate() {

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
