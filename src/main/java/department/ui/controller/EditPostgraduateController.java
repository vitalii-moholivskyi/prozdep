package department.ui.controller;

import department.model.IDepartmentModel;
import department.model.IPostgraduateModel;
import department.model.ITeacherModel;
import department.model.ITopicModel;
import department.model.form.PostgraduateUpdateForm;
import department.ui.controller.model.PostgraduateViewModel;
import department.ui.utils.UiConstants;
import department.ui.utils.UiUtils;
import department.utils.DateUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.stereotype.Controller;

import java.time.ZoneId;
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
        super.initialize();

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
                                th -> log.log(Level.WARNING, "Failed to fetch teachers"));
            }
        });

        topicComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            if (!TextUtils.isEmpty(newValue) && newValue.length() >= 3) {
                topicModel.fetchTopics(newValue, 0, UiConstants.HINT_RESULT)
                        .doOnCompleted(topicComboBox::show)
                        .subscribe(topicComboBox.getItems()::setAll,
                                th -> {
                                    log.log(Level.WARNING, "Failed to fetch topics");
                                    UiUtils.createErrDialog("Не вдалося завантажити список тем").showAndWait();
                                });
            }
        });

    }

    @Override
    protected void onCreatePostgraduate() {

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

        val teacher = teacherComboBox.getValue();

        if (teacher == null) {
            UiUtils.createWarnDialog("Для того, аби продовжити, виберіть викладача зі списку").showAndWait();
            teacherComboBox.requestFocus();
            return;
        }

        val topic = topicComboBox.getValue();

        if (topic == null) {
            UiUtils.createWarnDialog("Для того, аби продовжити, виберіть тему зі списку").showAndWait();
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
        form.setStartDate(DateUtils.fromLocal(start));
        form.setEndDate(DateUtils.tryFromLocal(end));
        form.setProtectionDate(DateUtils.tryFromLocal(defence));

        postgraduateModel.update(form, data, aVoid -> {
            log.log(Level.INFO, "Model updated");

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
            UiUtils.createErrDialog("Не вдалося створити аспіранта").showAndWait();
        });
    }

}
