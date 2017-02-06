package department.ui.controller;

import department.model.IMasterModel;
import department.model.bo.Master;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.logging.Level;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
@Controller
public class MasterTabController extends ListTabController {

    private final IMasterModel model;
    private final MainController mainController;

    @FXML
    private TableView<Master> tableView;

    @Autowired
    public MasterTabController(IMasterModel model, MainController mainController) {
        this.model = model;
        this.mainController = mainController;
    }

    @Override
    protected void doInitialize() {
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });

        final TableColumn<Master, String> firstNameCol = new TableColumn<>("First Name"),
                lastNameCol = new TableColumn<>("Last Name");

        firstNameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        // setup table columns and content
        tableView.getColumns().addAll(firstNameCol, lastNameCol);
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


    private void setTableContent(Collection<? extends Master> masters) {
        tableView.getItems().setAll(masters);
    }

}
