package department.ui.controller.edit;

import department.model.*;
import department.model.form.PaperUpdateForm;
import department.ui.controller.model.PaperViewModel;
import department.ui.utils.UiUtils;
import department.utils.Preconditions;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Calendar;
import java.util.logging.Level;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class EditPaperController {

    @FXML
    private Parent viewRoot;
    @FXML
    private TextField titleField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField yearField;
    @FXML
    private Label supervisorLabel;
    @FXML
    private Label executorLabel;
    @FXML
    private Label errorLabel;

    private final IPaperModel paperModel;
    private final ITeacherModel teacherModel;
    private final IScientistModel scientistModel;

    private PaperViewModel paper;

    @Autowired
    public EditPaperController(IScientistModel scientistModel, IPaperModel paperModel, ITeacherModel teacherModel) {
        this.paperModel = paperModel;
        this.teacherModel = teacherModel;
        this.scientistModel = scientistModel;
    }

    public PaperViewModel getPaper() {
        return paper;
    }

    public void setPaper(PaperViewModel paper) {
        this.paper = Preconditions.notNull(paper);

        typeField.setText(paper.getType());
        titleField.setText(paper.getName());
        yearField.setText(String.valueOf(paper.getYear()));

        scientistModel.fetchScientistsByPaperId(paper.getId(), 0, 1)
                .subscribe(models -> {

                    if (!models.isEmpty()) {
                        val m = models.iterator().next();
                        executorLabel.setText(m.getFirstName());
                    }
                }, th -> {
                    UiUtils.createErrDialog("Не вдалося завантажити виконавця").showAndWait();
                    log.log(Level.WARNING, "Failed to fetch scientists");
                });

        teacherModel.fetchChiefTeacherByPaperId(paper.getId())
                .subscribe(v -> supervisorLabel.setText(v.getFirstName()), th -> {
                    UiUtils.createErrDialog("Не вдалося завантажити виконавця").showAndWait();
                    log.log(Level.WARNING, "Failed to fetch scientists");
                });
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void onCreatePaper() {

        val year = tryGetYear(yearField.getText());

        if (year < 0) {
            UiUtils.createWarnDialog("Неправильно вказаний рік").showAndWait();
            yearField.requestFocus();
            return;
        }

        val name = titleField.getText();

        if (TextUtils.isEmpty(name)) {
            UiUtils.createWarnDialog("Назву не вказано").showAndWait();
            titleField.requestFocus();
            return;
        }

        val type = typeField.getText();

        if (TextUtils.isEmpty(type)) {
            UiUtils.createWarnDialog("Тип не вказано").showAndWait();
            typeField.requestFocus();
            return;
        }

        val form = new PaperUpdateForm();

        form.setId(paper.getId());
        form.setType(type);
        form.setName(name);
        form.setYear(year);

        paperModel.update(form, paper, () -> {
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
            UiUtils.createErrDialog("Не вдалося створити наукову роботу").showAndWait();
        });
    }

    private int tryGetYear(String str) {

        try {
            val year = Integer.parseInt(str);
            return year >= 1900 && year <= Calendar.getInstance().get(Calendar.YEAR) ? year : -1;
        } catch (Exception e) {
            return -1;
        }
    }

}
