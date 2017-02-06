package department.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Pagination;

/**
 * Created by Максим on 2/1/2017.
 */
public abstract class ListTabController {

    protected static final int RESULTS_PER_PAGE = 30;

    @FXML
    protected Pagination pagination;

    public ListTabController() {
    }

    protected abstract void doInitialize();

    @FXML
    private void initialize() {

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) ->
                onNewPageIndexSelected(oldValue.intValue(), newValue.intValue())
        );

        doInitialize();
    }

    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {}

}
