package department.ui.controller;

import department.model.IMasterModel;
import department.utils.RxUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public MainController(IMasterModel model) {
        //   model.fetchMasters(null, -1, -1);
    }

    @FXML
    private void initialize() {
        // once stage's window becomes initialized,
        // set window title
        RxUtils.fromProperty(viewRoot.sceneProperty())
                .filter(scene -> scene != null)
                .flatMap(scene -> RxUtils.fromProperty(scene.windowProperty()))
                .filter(window -> window != null && window instanceof Stage)
                .take(1)
                .map(window -> (Stage) window)
                .subscribe(stage -> stage.setTitle("Department"));
    }

}
