package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.IPostgraduateModel;
import department.model.ITeacherModel;
import department.model.ITopicModel;
import department.model.form.PostgraduateUpdateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.PostgraduateViewModel;
import department.ui.controller.model.TeacherViewModel;
import department.ui.controller.model.TopicViewModel;
import department.ui.utils.DefaultStringConverter;
import department.ui.utils.UiConstants;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.stereotype.Controller;

import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class EditPostgraduateController extends BasePostrgraduateController {

    private final IPostgraduateModel postgraduateModel;
    private final IDepartmentModel departmentModel;
    private final ITeacherModel teacherModel;
    private final ITopicModel topicModel;

    private PostgraduateViewModel data;

    public EditPostgraduateController(IPostgraduateModel postgraduateModel, IDepartmentModel departmentModel,
                                      ITeacherModel teacherModel, ITopicModel topicModel) {
        this.postgraduateModel = postgraduateModel;
        this.departmentModel = departmentModel;
        this.teacherModel = teacherModel;
        this.topicModel = topicModel;
    }

    public PostgraduateViewModel getData() {
        return data;
    }

    public void setData(PostgraduateViewModel data) {
        this.data = data;


        fullNameField.setText(data.getFirstName());
        if (data.getStartDate() != null) {
            startDatePicker.setValue(data.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        if (data.getEndDate() != null) {
            endDatePicker.setValue(data.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (data.getProtectionDate() != null) {
            defenceDatePicker.setValue(data.getProtectionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        phoneField.setText(data.getPhone());
    }

    @Override
    protected void initialize() {

        departmentComboBox.setConverter(new DefaultStringConverter<DepartmentViewModel>() {
            @Override
            public String toString(DepartmentViewModel object) {
                return object == null ? "Кафедра" : String.format("%d %s", object.getId(), object.getName());
            }

        });

        topicComboBox.setConverter(new DefaultStringConverter<TopicViewModel>() {
            @Override
            public String toString(TopicViewModel object) {
                return object == null ? "Тема" : object.getName();
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
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Помилка");
                            alert.setContentText("Не вдалося завантажити список кафедр");
                            alert.showAndWait();
                            log.log(Level.WARNING, "Failed to fetch departments", th);
                        }
                );

        teacherComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            if (!TextUtils.isEmpty(newValue) && newValue.length() >= 3) {
                teacherModel.fetchTeachers(newValue, 0, UiConstants.HINT_RESULT)
                        .doOnCompleted(teacherComboBox::show)
                        .subscribe(teacherComboBox.getItems()::setAll,
                                th -> log.log(Level.WARNING, "Failed to fetch teachers"));
            }
        });

        topicComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            if (!TextUtils.isEmpty(newValue) && newValue.length() >= 3) {
                topicModel.fetchTopics(newValue, 0, UiConstants.HINT_RESULT)
                        .doOnCompleted(topicComboBox::show)
                        .subscribe(topicComboBox.getItems()::setAll,
                                th -> log.log(Level.WARNING, "Failed to fetch topics"));
            }
        });

    }

    @Override
    protected void onCreatePostgraduate() {

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

        val teacher = teacherComboBox.getValue();

        if (teacher == null) {
            showWarning("Не обрано викладача", "Для того, аби продовжити, виберіть викладача зі списку");
            teacherComboBox.requestFocus();
            return;
        }

        val topic = topicComboBox.getValue();

        if (topic == null) {
            showWarning("Не обрано наукову тему", "Для того, аби продовжити, виберіть тему зі списку");
            topicComboBox.requestFocus();
            return;
        }

        val end = endDatePicker.getValue();
        val defence = defenceDatePicker.getValue();

        val form = new PostgraduateUpdateForm();

        form.setId(data.getId());
        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setTeacher(teacher.getId());
        form.setTopic(topic.getName());
        form.setStartDate(Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        form.setEndDate(end == null ? null : Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        form.setProtectionDate(defence == null ? null : Date.from(defence.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        postgraduateModel.update(form, data, aVoid -> {
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
            alert.setContentText("Не вдалося створити аспіранта");
            alert.showAndWait();
        });
    }

}
