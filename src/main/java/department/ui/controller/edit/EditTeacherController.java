package department.ui.controller.edit;

import department.model.IDepartmentModel;
import department.model.IPaperModel;
import department.model.ITeacherModel;
import department.model.ITopicModel;
import department.model.form.TeacherUpdateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.controller.model.PaperViewModel;
import department.ui.controller.model.TeacherViewModel;
import department.ui.controller.model.TopicViewModel;
import department.ui.utils.UiUtils;
import department.utils.DateUtils;
import department.utils.Preconditions;
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
import java.util.logging.Level;

import static department.ui.utils.UiConstants.MIN_DATE_ALLOWED;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class EditTeacherController {

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
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<TopicViewModel> topicsListView;
    @FXML
    private ListView<PaperViewModel> paperListView;

    private final ITeacherModel teacherModel;
    private final IDepartmentModel departmentModel;
    private final ITopicModel topicModel;
    private final IPaperModel paperModel;

    private TeacherViewModel model;

    public EditTeacherController(ITeacherModel teacherModel, IDepartmentModel departmentModel,
                                 ITopicModel topicModel, IPaperModel paperModel) {
        this.teacherModel = teacherModel;
        this.departmentModel = departmentModel;
        this.topicModel = topicModel;
        this.paperModel = paperModel;
    }

    public TeacherViewModel getModel() {
        return model;
    }

    public void setModel(TeacherViewModel model) {
        this.model = Preconditions.notNull(model);

        fullNameField.setText(model.getFirstName());
        phoneField.setText(model.getPhone());
        positionField.setText(model.getPosition());
        degreeField.setText(model.getDegree());
        startDatePicker.setValue(DateUtils.tryToLocal(model.getStartDate()));

        topicModel.fetchByScientist(model.getId())
                .subscribe(topicsListView.getItems()::setAll,
                        th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список наукових тем").showAndWait();
                            errorLabel.setText("Не вдалося завантажити список наукових тем");
                            log.log(Level.WARNING, "Failed to fetch papers", th);
                        });

        paperModel.fetchByScientist(model.getId())
                .subscribe(paperListView.getItems()::setAll,
                        th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список наукових робіт").showAndWait();
                            errorLabel.setText("Не вдалося завантажити список наукових робіт");
                            log.log(Level.WARNING, "Failed to fetch topics", th);
                        });
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
                .subscribe(departments -> {

                            for (val d : departments) {
                                departmentComboBox.getItems().add(d);
                                if (model.getDepartment() == d.getId()) {
                                    departmentComboBox.getSelectionModel().select(d);
                                }
                            }
                        }
                        , th -> {
                            UiUtils.createErrDialog("Не вдалося завантажити список кафедр").showAndWait();
                            errorLabel.setText("Не вдалося завантажити список кафедр");
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

        val form = new TeacherUpdateForm();

        form.setId(model.getId());
        form.setDepartment(department.getId());
        form.setName(name);
        form.setPhone(phoneField.getText());
        form.setStartDate(DateUtils.fromLocal(start));
        form.setDegree(degree);
        form.setPosition(position);

        teacherModel.update(form, model, () -> {
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
            errorLabel.setText("Не вдалося оновити дані про викладача");
            UiUtils.createErrDialog("Не вдалося оновити дані про викладача").showAndWait();
        });
    }

}
