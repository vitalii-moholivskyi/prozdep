package department.ui.controller.view;

import department.model.IPostgraduateModel;
import department.ui.controller.DefaultProgressMessage;
import department.ui.controller.MainController;
import department.ui.controller.model.PostgraduateViewModel;
import department.ui.controller.edit.EditPostgraduateController;
import department.ui.utils.UiConstants;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.logging.Level;

import static department.ui.utils.UiConstants.RESULTS_PER_PAGE;
import static department.ui.utils.UiUtils.DATE_FLD_MAPPER;
import static department.ui.utils.UiUtils.NULLABLE_FLD_MAPPER;

@Log
@Value
@EqualsAndHashCode(callSuper = false)
@Getter(value = AccessLevel.NONE)
@Controller
public final class PostgraduateTabController extends ListTabController<PostgraduateViewModel> {

    MainController mainController;
    IPostgraduateModel model;

    @Autowired
    @SuppressWarnings("unchecked")
    public PostgraduateTabController(IPostgraduateModel model, MainController mainController) {
        this.mainController = mainController;
        this.model = model;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initSubclasses() {

        final TableColumn<PostgraduateViewModel, String> firstNameCol = new TableColumn<>("Ім'я"),
                phoneCol = new TableColumn<>("Телефон"), topicCol = new TableColumn<>("Тема"),
                startDateCol = new TableColumn<>("Вступ"), endDateCol = new TableColumn<>("Випуск"),
                protectionDateCol = new TableColumn<>("Дата захисту");

        firstNameCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getFirstNameObs()));
        phoneCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getPhoneObs().map(NULLABLE_FLD_MAPPER)));
        topicCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getTopicObs().map(NULLABLE_FLD_MAPPER)));
        startDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getStartDateObs().map(DATE_FLD_MAPPER)));
        endDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getEndDateObs().map(DATE_FLD_MAPPER)));
        protectionDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getProtectionDateObs()
                .map(DATE_FLD_MAPPER)));
        // setup table columns and content
        tableView.getColumns().addAll(firstNameCol, phoneCol, topicCol, startDateCol, endDateCol, protectionDateCol);

        tableView.setRowFactory(tv -> {
            TableRow<PostgraduateViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {

                    val stage = new Stage();
                    val loader = UiUtils.newLoader("/view/partials/_formEditPostgraduate.fxml", EditPostgraduateController.class);

                    try {
                        stage.setScene(new Scene(loader.load()));

                        EditPostgraduateController controller = loader.getController();

                        controller.setData(row.getItem());
                        stage.centerOnScreen();
                        stage.show();
                        stage.sizeToScene();
                    } catch (final IOException e) {
                        log.log(Level.SEVERE, "Failed to open form", e);
                    }
                }
            });
            return row;
        });

        val size = tableView.getColumns().size();

        val progress = new DefaultProgressMessage(mainController);
        loadData(new ProgressCallback() {
                     @Override
                     public void onShow() {
                         progress.showProgress("Завантаження списку аспірантів...");
                     }

                     @Override
                     public void onHide() {
                         progress.hideProgress();
                     }

                     @Override
                     public void onFailure(Throwable th) {
                         processError(th);
                     }
                 }, model.fetchPostgraduates(0, UiConstants.RESULTS_PER_PAGE), model.count()
        );
    }

    @Override
    protected void onRefresh() {
        val indx = pagination.currentPageIndexProperty().get();

        if (indx >= 0) {
            doLoad(indx);
        }
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        doLoad(newIndex);
    }

    private void doLoad(int indx) {
        val toastId = mainController.showProgress("Завантаження списку аспірантів...");

        model.fetchPostgraduates(indx * RESULTS_PER_PAGE, RESULTS_PER_PAGE)
                .doOnTerminate(() -> mainController.hideProgress(toastId))
                .subscribe(this::setTableContent, this::processError);
    }

    private void processError(Throwable th) {
        log.log(Level.WARNING, "Failed to fetch postgraduates", th);
        mainController.showError("Не вдалося завантажити список аспірантів...", UiConstants.DURATION_NORMAL);
    }

}
