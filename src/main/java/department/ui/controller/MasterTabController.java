package department.ui.controller;

import department.model.IMasterModel;
import department.ui.controller.model.MasterViewModel;
import department.ui.utils.FxSchedulers;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.scene.control.TableColumn;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

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
        fetchMasters(0, ListTabController.RESULTS_PER_PAGE);
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        fetchMasters(newIndex * ListTabController.RESULTS_PER_PAGE, ListTabController.RESULTS_PER_PAGE);
    }

    private void fetchMasters(long offset, long limit) {
        val toastId = mainController.showProgress("Loading masters...");

        model.fetchMasters(offset, limit)
                .doOnTerminate(() -> mainController.hideProgress(toastId))
                .subscribe(this::setTableContent,
                        th -> {
                            val errId = mainController.showError("Failed to retrieve master list... Try again later");

                            log.log(Level.WARNING, "Failed to fetch masters", th);
                            Observable.defer(() -> Observable.just(null))
                                    .delay(2, TimeUnit.SECONDS)
                                    .observeOn(FxSchedulers.platform())
                                    .doOnNext(obj -> mainController.hideError(errId))
                                    .subscribe();
                        }
                );
    }

    private void setTableContent(Collection<? extends MasterViewModel> masters) {
        tableView.getItems().setAll(masters);
    }

}
