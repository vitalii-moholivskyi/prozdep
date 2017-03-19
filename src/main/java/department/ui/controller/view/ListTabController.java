package department.ui.controller;

import department.utils.Tuple;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import lombok.extern.java.Log;
import lombok.val;
import rx.Observable;
import rx.functions.Func2;

import java.util.Collection;

import static department.ui.utils.UiConstants.RESULTS_PER_PAGE;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
public abstract class ListTabController<T> {

    @FXML protected TableView<T> tableView;
    @FXML protected Pagination pagination;

    protected interface ProgressCallback {
        void onShow();
        void onHide();
        void onFailure(Throwable th);
    }

    public ListTabController() {
    }

    @FXML
    private void initialize() {

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) ->
                onNewPageIndexSelected(oldValue.intValue(), newValue.intValue())
        );

        initSubclasses();
    }

    protected void initSubclasses() {
    }

    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
    }

    @FXML
    protected void onRefresh() {
    }

    protected final void setTableContent(Collection<? extends T> data) {
        tableView.getItems().setAll(data);
    }

    protected final void loadData(ProgressCallback callback, Observable<Collection<? extends T>> dataObs,
                                  Observable<? extends Integer> rowCountObs) {
        callback.onShow();
        Observable.combineLatest(
                dataObs.doOnTerminate(callback::onHide),
                rowCountObs
                        .doOnSubscribe(() -> pagination.setVisible(false))
                        .doOnCompleted(() -> pagination.setVisible(true)),// will be called only if observers proceeded successfully
                (Func2<Collection<? extends T>, Integer, Tuple<Collection<? extends T>, Integer>>) Tuple::new)
                .subscribe(result -> {
                    setTableContent(result.getV1());

                    val count = result.getV2();
                    val reside = count % RESULTS_PER_PAGE;

                    pagination.setPageCount(count / RESULTS_PER_PAGE + (reside == 0 ? 0 : 1));
                }, callback::onFailure);
    }

}
