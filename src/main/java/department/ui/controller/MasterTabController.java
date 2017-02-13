package department.ui.controller;

import department.model.IMasterModel;
import department.model.bo.MasterViewModel;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.scene.control.TableColumn;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rx.functions.Func1;

import java.util.Collection;
import java.util.Date;
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
                phoneCol = new TableColumn<>("Телефон"), degreeCol = new TableColumn<>("Ступінь"),
                topicCol = new TableColumn<>("Тема"), startDateCol = new TableColumn<>("Вступ"),
                endDateCol = new TableColumn<>("Випуск");

        firstNameCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getFirstNameObs()));
        phoneCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getPhoneObs().map(NULLABLE_FLD_MAPPER)));
        degreeCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getDegreeObs().map(NULLABLE_FLD_MAPPER)));
        topicCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getTopicObs().map(NULLABLE_FLD_MAPPER)));
        startDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getStartDateObs().map(DATE_FLD_MAPPER)));
        endDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getEndDateObs().map(DATE_FLD_MAPPER)));
        // setup table columns and content
        tableView.getColumns().addAll(firstNameCol, degreeCol, phoneCol, topicCol, startDateCol, endDateCol);

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
        model.fetchMasters(offset, limit)
                .doOnSubscribe(mainController::showProgressDialog)
                .doOnTerminate(mainController::hideProgressDialog)
                .subscribe(this::setTableContent,
                        th -> /*todo redo*/log.log(Level.WARNING, "Failed to fetch masters", th));
    }

    private void setTableContent(Collection<? extends MasterViewModel> masters) {
        tableView.getItems().setAll(masters);
    }

}
