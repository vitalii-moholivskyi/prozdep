package department.ui.controller.view;

import department.model.IPaperModel;
import department.ui.controller.DefaultProgressMessage;
import department.ui.controller.MainController;
import department.ui.controller.model.PaperViewModel;
import department.ui.utils.Controllers;
import department.ui.utils.UiConstants;
import department.utils.RxUtils;
import department.utils.TextUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rx.Observable;

import java.util.Collection;
import java.util.logging.Level;

import static department.ui.utils.UiUtils.NULLABLE_FLD_MAPPER;

/**
 * Created by Максим on 2/20/2017.
 */
@Log
@Value
@EqualsAndHashCode(callSuper = false)
@Getter(value = AccessLevel.NONE)
@Controller
public class PaperController extends ListTabController<PaperViewModel> {

    IPaperModel paperModel;
    MainController mainController;

    @Autowired
    public PaperController(IPaperModel paperModel, MainController mainController) {
        this.paperModel = paperModel;
        this.mainController = mainController;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initSubclasses() {
        searchField.setPromptText("Пошук наукових робіт");

        final TableColumn<PaperViewModel, String> titleCol = new TableColumn<>("Назва"),
                typeCol = new TableColumn<>("Тип"), yearCol = new TableColumn<>("Рік написання");

        titleCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getNameObs()));
        typeCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getTypeObs().map(NULLABLE_FLD_MAPPER)));
        yearCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getYearObs()
                .map(year -> year == null ? "N/a" : year.toString())));
        // setup table columns and content
        tableView.getColumns().addAll(titleCol, typeCol, yearCol);

        tableView.setRowFactory(tv -> {
            TableRow<PaperViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Controllers.createPaperEditViewAndShow(row.getItem());
                }
            });
            return row;
        });

        val size = tableView.getColumns().size();

        val progress = new DefaultProgressMessage(mainController);
        loadData(new ProgressCallback() {
                     @Override
                     public void onShow() {
                         progress.showProgress("Завантаження списку наукових робіт...");
                     }

                     @Override
                     public void onHide() {
                         progress.hideProgress();
                     }

                     @Override
                     public void onFailure(Throwable th) {
                         processError(th);
                     }
                 }, paperModel.fetchPapers(0, UiConstants.RESULTS_PER_PAGE), paperModel.count()
        );
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        doLoad(searchField.getText(), newIndex);
    }

    @Override
    protected void onRefresh() {
        val indx = pagination.currentPageIndexProperty().get();

        if (indx >= 0) {
            doLoad(searchField.getText(), indx);
        }
    }

    @Override
    protected void onSearch(String query) {
        doLoad(searchField.getText(), 0);
    }

    private void doLoad(String query, int indx) {
        val toastId = mainController.showProgress("Завантаження списку наукових робіт...");
        final Observable<Collection<? extends PaperViewModel>> observable;

        if (TextUtils.isEmpty(query)) {
            observable = paperModel.fetchPapers(indx * UiConstants.RESULTS_PER_PAGE, UiConstants.RESULTS_PER_PAGE);
        } else {
            observable = paperModel.fetchPapers(query, indx * UiConstants.RESULTS_PER_PAGE, UiConstants.RESULTS_PER_PAGE);
        }

        observable.doOnTerminate(() -> mainController.hideProgress(toastId))
                .subscribe(this::setTableContent, this::processError);
    }

    private void processError(Throwable th) {
        log.log(Level.WARNING, "Failed to fetch papers", th);
        mainController.showError("Не вдалося завантажити список наукових робіт...", UiConstants.DURATION_NORMAL);
    }
}
