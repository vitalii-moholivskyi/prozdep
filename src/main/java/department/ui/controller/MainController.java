package department.ui.controller;

import department.utils.RxUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * Controller which services main.xml view
 * </p>
 * Created by Максим on 1/31/2017.
 */
@Controller
public final class MainController {

    @FXML
    private Parent viewRoot;

    public MainController() {
    }

    @FXML
    private void initialize() {
        // once stage's window becomes initialized,
        // set window title
        RxUtils.fromProperty(viewRoot.sceneProperty())
                .filter(scene -> scene != null)
                .take(1)
                .flatMap(scene -> RxUtils.fromProperty(scene.windowProperty()))
                .filter(window -> window != null)
                .take(1)
                .map(window -> (Stage) window)
                .subscribe(stage -> stage.setTitle("Department"));
    }

}
