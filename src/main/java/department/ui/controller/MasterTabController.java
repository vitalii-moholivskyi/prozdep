package department.ui.controller;

import department.model.IMasterModel;
import department.ui.controller.model.MasterViewModel;
import department.ui.utils.UiConstants;
import department.utils.RxUtils;
import department.utils.TextUtils;
import department.utils.Tuple;
import javafx.scene.control.TableColumn;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;

import static department.ui.utils.UiConstants.RESULTS_PER_PAGE;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
@Controller
public final class MasterTabController extends ListTabController<MasterViewModel> {

    private final IMasterModel model;
    private final MainController mainController;

    private static final Func1<? super String, ? extends String> NULLABLE_FLD_MAPPER =
            str -> TextUtils.isEmpty(str) ? "N/a" : str;

    private static final Func1<? super Date, ? extends String> DATE_FLD_MAPPER =
            date -> date == null ? "N/a" : date.toString();

    @Autowired
    public MasterTabController(IMasterModel model, MainController mainController) {
        this.model = model;
        this.mainController = mainController;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doInitialize() {

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
        initialize();
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        val toastId = mainController.showProgress("Loading masters...");

        model.fetchMasters(newIndex * UiConstants.RESULTS_PER_PAGE, UiConstants.RESULTS_PER_PAGE)
                .doOnTerminate(() -> mainController.hideProgress(toastId))
                .subscribe(this::setTableContent, this::processError);
    }

    private void initialize() {
        val toastId = mainController.showProgress("Loading masters...");

        Observable.combineLatest(
                model.fetchMasters(0, UiConstants.RESULTS_PER_PAGE)
                        .doOnTerminate(() -> mainController.hideProgress(toastId)),
                model.count()
                        .doOnSubscribe(() -> pagination.setVisible(false))
                        .doOnCompleted(() -> pagination.setVisible(true)),// will be called only if observers proceeded successfully
                (Func2<Collection<? extends MasterViewModel>, Integer, Tuple<Collection<? extends MasterViewModel>, Integer>>) Tuple::new)
                .subscribe(result -> {
                    val count = result.getV2();
                    val reside = count % RESULTS_PER_PAGE;

                    pagination.setPageCount(count / RESULTS_PER_PAGE + (reside == 0 ? 0 : 1));
                    setTableContent(result.getV1());
                }, this::processError);
    }

    private void processError(Throwable th) {
        log.log(Level.WARNING, "Failed to fetch masters", th);
        mainController.showError("Failed to retrieve master list... Try again later", UiConstants.DURATION_NORMAL);
    }

    private void setTableContent(Collection<? extends MasterViewModel> masters) {
        tableView.getItems().setAll(masters);
    }

}
