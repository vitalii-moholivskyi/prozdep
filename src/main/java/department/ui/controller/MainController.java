package department.ui.controller;

import department.di.Injector;
import department.model.IMasterModel;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.logging.Level;

/**
 * <p>
 * Controller which services main.xml view
 * </p>
 * Created by Максим on 1/31/2017.
 */
@Controller
@Log
public final class MainController {

    private static final String MASTERS_TAB_ID = "id_masters";

    @FXML
    private Parent viewRoot;

    @FXML
    private TabPane contentTabPane;

    @FXML
    private Button viewMasterButton;

    @FXML
    private ProgressBar progressBar;

    @Autowired
    public MainController(IMasterModel model) {
        //   model.fetchMasters(null, -1, -1);
    }

    public void showProgressDialog() {
        progressBar.setVisible(true);
    }

    public void hideProgressDialog() {
        progressBar.setVisible(false);
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

        progressBar.setVisible(false);
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        contentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    }

    @FXML
    private void onViewMaster() {

        for (val tab : contentTabPane.getTabs()) {
            if (!TextUtils.isEmpty(tab.getId())
                    && tab.getId().equals(MainController.MASTERS_TAB_ID)) {
                // trying to open a new tab again, skip
                return;
            }
        }

        val loader = new FXMLLoader(UiUtils.class.getResource("/view/partials/_listTab.fxml"));

        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return Injector.getInstance().getContext().getBean(MasterTabController.class);
            }
        });

        //val loader = UiUtils.newLoader("/view/partials/_listTab.fxml");

        try {

            final Tab tab = loader.load();

            tab.setId(MainController.MASTERS_TAB_ID);
            tab.setText("Masters");
            contentTabPane.getTabs().add(tab);
        } catch (final IOException e) {
            log.log(Level.SEVERE, "Failed to open master tab", e);
        }
    }

}
