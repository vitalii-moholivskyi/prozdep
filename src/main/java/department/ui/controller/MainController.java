package department.ui.controller;

import department.model.IMasterModel;
import department.ui.utils.FxSchedulers;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rx.Observable;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
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
    private static final String TEACHERS_VIEW_TAB_ID = "id_view_teachers";
    private static final String POSTGRADUATES_VIEW_TAB_ID = "id_view_postgraduates";
    private static final String TOPICS_VIEW_TAB_ID = "id_view_topics";
    private static final String PAPERS_VIEW_TAB_ID = "id_view_papers";
    private static final String MASTERS_CREATE_TAB_ID = "id_create_masters";

    private static long idGenerator;

    @FXML private Parent viewRoot;
    @FXML private TabPane contentTabPane;

    @FXML private ProgressBar progressBar;
    @FXML private Label progressMessageLabel;
    @FXML private Label errorMessageLabel;

    private final TreeMap<Long, String> idToProgressMsg;
    private final TreeMap<Long, String> idToErrMsg;

    @Autowired
    public MainController(IMasterModel model) {
        idToProgressMsg = new TreeMap<>();
        idToErrMsg = new TreeMap<>();
    }

    public long showProgress(String message) {
        progressMessageLabel.setVisible(true);
        progressMessageLabel.setText(message);
        progressBar.setVisible(true);
        idToProgressMsg.put(idGenerator, message);
        return idGenerator++;
    }

    public void hideProgress() {
        progressBar.setVisible(false);
        progressMessageLabel.setVisible(false);
        idToProgressMsg.clear();
    }

    public void hideProgress(long id) {

        if(idToProgressMsg.remove(id) != null) {
            if(idToProgressMsg.isEmpty()) {
                hideProgress();
            } else {
                progressMessageLabel.setText(idToProgressMsg.get(idToProgressMsg.keySet().iterator().next()));
            }
        }
    }

    public long showError(String message) {
        errorMessageLabel.setVisible(true);
        errorMessageLabel.setText(message);
        idToErrMsg.put(idGenerator, message);
        return idGenerator++;
    }

    public void showError(String message, long millis) {
        val errId = showError(message);

        Observable.defer(() -> Observable.just(null))
                .delay(millis, TimeUnit.MILLISECONDS)
                .observeOn(FxSchedulers.platform())
                .doOnNext(obj -> hideError(errId))
                .subscribe();
    }

    public void hideError() {
        errorMessageLabel.setVisible(false);
        idToErrMsg.clear();
    }

    public void hideError(long id) {

        if(idToErrMsg.remove(id) != null) {
            if(idToErrMsg.isEmpty()) {
                hideError();
            } else {
                errorMessageLabel.setText(idToErrMsg.get(idToErrMsg.keySet().iterator().next()));
            }
        }
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
        loadCreationView("/view/partials/_formMaster.fxml", CreateMasterController.class);
    }

    @FXML
    private void onCreateTeacher() {
        loadCreationView("/view/partials/_formTeacher.fxml");
    }

    @FXML
    private void onCreatePostgraduate() {
        loadCreationView("/view/partials/_formPostgraduate.fxml", CreatePostgraduateController.class);
    }

    @FXML
    private void onCreateTopic() {
        loadCreationView("/view/partials/_formTopic.fxml");
    }

    @FXML
    private void onCreatePaper() {
        loadCreationView("/view/partials/_formPaper.fxml");
    }

    @FXML
    private void onViewMaster() {
        loadTab(MainController.MASTERS_VIEW_TAB_ID, "Магістри",
                "/view/partials/_listTemplate.fxml", MasterTabController.class);
    }

    @FXML
    private void onViewTeacher() {
        loadTab(MainController.TEACHERS_VIEW_TAB_ID, "Викладачі",
                "/view/partials/_listTemplate.fxml", TeacherTabController.class);
    }

    @FXML
    private void onViewPostgraduate() {
        loadTab(MainController.POSTGRADUATES_VIEW_TAB_ID, "Аспіранти",
                "/view/partials/_listTemplate.fxml", PostgraduateTabController.class);
    }

    @FXML
    private void onViewTopic() {
        loadTab(MainController.TOPICS_VIEW_TAB_ID, "Наукові теми",
                "/view/partials/_listTemplate.fxml", TopicController.class);
    }

    @FXML
    private void onViewPapers() {
        loadTab(MainController.PAPERS_VIEW_TAB_ID, "Наукові роботи",
                "/view/partials/_listTemplate.fxml", PaperController.class);
    }

    private void loadCreationView(String filepath, Class<?> controller) {

        val stage = new Stage();
        final FXMLLoader loader;

        if(controller == null) {
            loader = UiUtils.newLoader(filepath);
        } else {
            loader = UiUtils.newLoader(filepath, controller);
        }

        try {
            stage.setScene(new Scene(loader.load()));

            stage.centerOnScreen();
            stage.show();
            stage.sizeToScene();
        } catch (final IOException e) {
            log.log(Level.SEVERE, "Failed to open form", e);
        }
    }

    private void loadCreationView(String filepath) {
        loadCreationView(filepath, null);
    }

    private void loadTab(String tabId, String title, String filePath, Class<?> controller) {

        if (hasTabWithId(tabId)) return;

        val loader = UiUtils.newLoader(filePath, controller);

        try {
            val tab = new Tab(title, loader.load());

            tab.setId(tabId);
            contentTabPane.getTabs().add(tab);
            contentTabPane.getSelectionModel().selectLast();
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
