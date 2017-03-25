package department.ui.controller.edit;

import department.model.*;
import department.model.form.PostgraduateUpdateForm;
import department.ui.controller.model.*;
import department.ui.utils.DefaultStringConverter;
import department.ui.utils.UiConstants;
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
import org.springframework.stereotype.Controller;

import java.util.logging.Level;

import static department.ui.utils.UiUtils.*;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class EditPostgraduateController {

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
    private DatePicker defenceDatePicker;
    @FXML
    private ComboBox<TeacherViewModel> teacherComboBox;
    @FXML
    private ComboBox<TopicViewModel> topicComboBox;
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<PaperViewModel> paperListView;

    private final IPostgraduateModel postgraduateModel;
    private final IDepartmentModel departmentModel;
    private final ITeacherModel teacherModel;
    private final ITopicModel topicModel;
    private final IPaperModel paperModel;

    private PostgraduateViewModel data;

    public EditPostgraduateController(IPostgraduateModel postgraduateModel, IDepartmentModel departmentModel,
                                      ITeacherModel teacherModel, ITopicModel topicModel, IPaperModel paperModel) {
        this.postgraduateModel = postgraduateModel;
        this.departmentModel = departmentModel;
        this.teacherModel = teacherModel;
        this.topicModel = topicModel;
        this.paperModel = paperModel;
    }

    public PostgraduateViewModel getData() {
        return data;
    }

    public void setData(PostgraduateViewModel data) {
        this.data = data;


        fullNameField.setText(data.getFirstName());
        startDatePicker.setValue(DateUtils.tryToLocal(data.getStartDate()));
        endDatePicker.setValue(DateUtils.tryToLocal(data.getEndDate()));
        defenceDatePicker.setValue(DateUtils.tryToLocal(data.getProtectionDate()));
        phoneField.setText(data.getPhone());

        paperModel.fetchByScientist(data.getId())
                .subscribe(paperListView.getItems()::setAll,
                        th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список наукових робіт").showAndWait();
                            errorLabel.setText("Не вдалося завантажити список наукових робіт");
                            log.log(Level.WARNING, "Failed to fetch topics", th);
                        });
    }

    @FXML
    private void initialize() {
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
        defenceDatePicker.setEditable(false);

        startDatePicker.setDayCellFactory(startDayFactory(endDatePicker, defenceDatePicker));
        endDatePicker.setDayCellFactory(endDayFactory(startDatePicker, defenceDatePicker));
        defenceDatePicker.setDayCellFactory(defenceDayFactory(endDatePicker, startDatePicker));

        startDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> {
                    endDatePicker.setDayCellFactory(endDayFactory(startDatePicker, defenceDatePicker));
                    defenceDatePicker.setDayCellFactory(defenceDayFactory(endDatePicker, startDatePicker));
                });
        endDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> {
                    startDatePicker.setDayCellFactory(startDayFactory(endDatePicker, defenceDatePicker));
                    defenceDatePicker.setDayCellFactory(defenceDayFactory(endDatePicker, startDatePicker));
                });

        defenceDatePicker.dayCellFactoryProperty()
                .addListener((observable, oldValue, newValue) -> {
                    startDatePicker.setDayCellFactory(startDayFactory(endDatePicker, defenceDatePicker));
                    endDatePicker.setDayCellFactory(endDayFactory(startDatePicker, defenceDatePicker));
                });

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
                            UiUtils.createErrDialog("Не вдалося завантажити список кафедр").showAndWait();
                            errorLabel.setText("Не вдалося завантажити список кафедр");
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

    @FXML
    private void onCreatePostgraduate() {

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
            errorLabel.setText("Не вдалося створити аспіранта");
            UiUtils.createErrDialog("Не вдалося створити аспіранта").showAndWait();
        });
    }

}
