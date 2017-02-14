package department.ui.controller;

import department.model.IMasterModel;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    private static final String MASTERS_VIEW_TAB_ID = "id_view_masters";
    private static final String MASTERS_CREATE_TAB_ID = "id_create_masters";

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
    private void onCreateMaster() {
        loadTab(MainController.MASTERS_CREATE_TAB_ID, "Create master",
                "/view/partials/_formMaster.fxml", CreateMasterTabController.class);
    }

    @FXML
    private void onViewMaster() {
        loadTab(MainController.MASTERS_VIEW_TAB_ID, "Masters",
                "/view/partials/_listTemplate.fxml", MasterTabController.class);
    }

    private void loadTab(String tabId, String title, String filePath, Class<?> controller) {

        if (hasTabWithId(tabId)) return;

        val loader = UiUtils.newLoader(filePath, controller);

        try {

            final Tab tab = loader.load();

            tab.setId(tabId);
            tab.setText(title);
            contentTabPane.getTabs().add(tab);
        } catch (final IOException e) {
            log.log(Level.SEVERE, String.format("Failed to open %s tab", title), e);
        }
    }

    private boolean hasTabWithId(String tabId) {

        for (val tab : contentTabPane.getTabs()) {
            if (!TextUtils.isEmpty(tab.getId())
                    && tab.getId().equalsIgnoreCase(tabId)) {
                // trying to open a new tab again, skip
                return true;
            }
        }

        return false;
    }

}
