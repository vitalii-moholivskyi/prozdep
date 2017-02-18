package department.ui.controller;

import department.model.IMasterModel;
import department.ui.controller.model.MasterViewModel;
import department.ui.utils.UiConstants;
import department.utils.RxUtils;
import javafx.scene.control.TableColumn;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;

import static department.ui.utils.UiUtils.DATE_FLD_MAPPER;
import static department.ui.utils.UiUtils.NULLABLE_FLD_MAPPER;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
@Value
@EqualsAndHashCode(callSuper = false)
@Getter(value = AccessLevel.NONE)
@Controller
public final class MasterTabController extends ListTabController<MasterViewModel> {

    IMasterModel model;
    MainController mainController;

    @Autowired
    public MasterTabController(IMasterModel model, MainController mainController) {
        this.model = model;
        this.mainController = mainController;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initSubclasses() {

        final TableColumn<MasterViewModel, String> firstNameCol = new TableColumn<>("Ім'я"),
                phoneCol = new TableColumn<>("Телефон"), topicCol = new TableColumn<>("Тема"),
                startDateCol = new TableColumn<>("Вступ"), endDateCol = new TableColumn<>("Випуск");

        firstNameCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getFirstNameObs()));
        phoneCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getPhoneObs().map(NULLABLE_FLD_MAPPER)));
        topicCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getTopicObs().map(NULLABLE_FLD_MAPPER)));
        startDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getStartDateObs().map(DATE_FLD_MAPPER)));
        endDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getEndDateObs().map(DATE_FLD_MAPPER)));
        // setup table columns and content
        tableView.getColumns().addAll(firstNameCol, phoneCol, topicCol, startDateCol, endDateCol);

        val size = tableView.getColumns().size();

        for (val column : tableView.getColumns()) {
            column.prefWidthProperty().bind(tableView.widthProperty().divide(size));
        }

        val progress = new DefaultProgressMessage(mainController);
        loadData(new ProgressCallback() {
                     @Override
                     public void onShow() {
                         progress.showProgress("Завантаження списку магістрів...");
                     }

                     @Override
                     public void onHide() {
                         progress.hideProgress();
                     }

                     @Override
                     public void onFailure(Throwable th) {
                         processError(th);
                     }
                 }, model.fetchMasters(0, UiConstants.RESULTS_PER_PAGE), model.count()
        );
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        val toastId = mainController.showProgress("Завантаження списку магістрів...");

        model.fetchMasters(newIndex * UiConstants.RESULTS_PER_PAGE, UiConstants.RESULTS_PER_PAGE)
                .doOnTerminate(() -> mainController.hideProgress(toastId))
                .subscribe(this::setTableContent, this::processError);
    }

    private void processError(Throwable th) {
        log.log(Level.WARNING, "Failed to fetch masters", th);
        mainController.showError("Не вдалося завантажити список магістрів...", UiConstants.DURATION_NORMAL);
    }

}
