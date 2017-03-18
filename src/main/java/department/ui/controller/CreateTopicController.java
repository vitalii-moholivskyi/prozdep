package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.ITeacherModel;
import department.model.ITopicModel;
import department.model.form.TopicCreateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.TeacherViewModel;
import department.ui.utils.DefaultStringConverter;
import department.ui.utils.UiConstants;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;

import static department.ui.utils.UiUtils.endDayFactory;
import static department.ui.utils.UiUtils.startDayFactory;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class CreateTopicController {

    @FXML private Parent viewRoot;
    @FXML private TextField titleField;
    @FXML private ComboBox<DepartmentViewModel> departmentComboBox;
    @FXML private TextField clientField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<TeacherViewModel> teacherComboBox;

    private final IDepartmentModel departmentModel;
    private final ITopicModel topicModel;
    private final ITeacherModel teacherModel;

    @Autowired
    public CreateTopicController(IDepartmentModel departmentModel, ITopicModel topicModel, ITeacherModel teacherModel) {
        this.departmentModel = departmentModel;
        this.topicModel = topicModel;
        this.teacherModel = teacherModel;
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
                return object == null ? "" : String.format("%d %s", object.getId(), object.getName());
            }
        });

        teacherComboBox.setConverter(new DefaultStringConverter<TeacherViewModel>() {
            @Override
            public String toString(TeacherViewModel object) {
                return object == null ? "" : object.getFirstName();
            }
        });

        departmentModel.fetchDepartments(0, Integer.MAX_VALUE)
                .subscribe(departments -> departmentComboBox.getItems().addAll(departments)
                        , th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список кафедр").showAndWait();
                            log.log(Level.WARNING, "Failed to fetch departments", th);
                        }
                );

        teacherComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            if (!TextUtils.isEmpty(newValue) && newValue.length() >= 3) {
                teacherModel.fetchTeachers(newValue, 0, UiConstants.HINT_RESULT)
                        .doOnCompleted(teacherComboBox::show)
                        .subscribe(teacherComboBox.getItems()::setAll,
                                th -> {
                                    UiUtils.createErrDialog("Не вдалося завантажити список викладачів").showAndWait();
                                    log.log(Level.WARNING, "Failed to fetch teachers");
                                });
            }
        });
    }

    @FXML
    private void onCreateTopic() {

        val department = departmentComboBox.valueProperty().get();

        if (department == null) {
            UiUtils.createWarnDialog("Для того, аби продовжити, виберіть кафедру зі списку").showAndWait();
            departmentComboBox.requestFocus();
            return;
        }

        val chief = teacherComboBox.valueProperty().get();

        if (chief == null) {
            UiUtils.createWarnDialog("Для того, аби продовжити, виберіть керівника зі списку").showAndWait();
            teacherComboBox.requestFocus();
            return;
        }

        val name = titleField.getText();

        if (TextUtils.isEmpty(name)) {
            UiUtils.createWarnDialog("Тему не вказано").showAndWait();
            titleField.requestFocus();
            return;
        }

        val client = clientField.getText();

        if (TextUtils.isEmpty(client)) {
            UiUtils.createWarnDialog("Компанію не вказано").showAndWait();
            clientField.requestFocus();
            return;
        }

        val start = startDatePicker.getValue();

        if (start == null) {
            UiUtils.createWarnDialog("Дату початку роботи не вказано").showAndWait();
            startDatePicker.requestFocus();
            return;
        }

        val end = endDatePicker.getValue();

        if (end == null) {
            UiUtils.createWarnDialog("Дату кінця роботи не вказано").showAndWait();
            endDatePicker.requestFocus();
            return;
        }

        val form = new TopicCreateForm();

        form.setName(name);
        form.setClient(client);
        form.setDepartment(department.getId());
        form.setStartDate(Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        form.setEndDate(Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        form.setChiefScientist(chief.getId());

        topicModel.create(form).subscribe(master -> {
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
            UiUtils.createErrDialog("Не вдалося створити наукову тему").showAndWait();
        });
    }

}