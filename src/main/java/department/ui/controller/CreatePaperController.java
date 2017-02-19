package department.ui.controller;

import department.model.IPaperModel;
import department.model.bo.Scientist;
import department.model.form.PaperCreateForm;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;

/**
 * Created by Максим on 2/19/2017.
 */
@Log
@Controller
public final class CreatePaperController {

    @FXML private Parent viewRoot;
    @FXML private TextField titleField;
    @FXML private TextField typeField;
    @FXML private TextField yearField;
    @FXML private ComboBox<? extends Scientist> supervisorBox;
    @FXML private ComboBox<? extends Scientist> executorBox;

    private final IPaperModel paperModel;

    @Autowired
    public CreatePaperController(IPaperModel paperModel) {
        this.paperModel = paperModel;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void onCreatePaper() {

        val year = tryGetYear(yearField.getText());

        if(year < 0) {
            showWarning(null, "Неправильно вказаний рік");
            yearField.requestFocus();
            return;
        }

        val name = titleField.getText();

        if (TextUtils.isEmpty(name)) {
            showWarning(null, "Назву не вказано");
            titleField.requestFocus();
            return;
        }

        val type = typeField.getText();

        if (TextUtils.isEmpty(type)) {
            showWarning(null, "Тип не вказано");
            typeField.requestFocus();
            return;
        }

        val form = new PaperCreateForm();

        form.setType(type);
        form.setName(name);
        form.setYear(year);
        paperModel.create(form).subscribe(master -> {
            log.log(Level.SEVERE, "Model created");

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
            alert.setContentText("Не вдалося створити наукову роботу");
            alert.showAndWait();
        });
    }

    private int tryGetYear(String str) {

        try {
            val year = Integer.parseInt(str);
            return year >= 1900 ? year : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    private void showWarning(String header, String body) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(body);

        alert.showAndWait();
    }

}
