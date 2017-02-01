package department.ui.controller;

import department.model.IMasterModel;
import department.model.bo.Master;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
@Controller
public class MasterTabController extends ListTabController {

    private final IMasterModel model;

    @FXML
    private TableView<Master> tableView;

    @Autowired
    public MasterTabController(IMasterModel model) {
        this.model = model;
    }

    @FXML
    private void initialize() {

        val firstNameCol = new TableColumn<Master, String>("First Name");
        val lastNameCol = new TableColumn<Master, String>("Last Name");

        firstNameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));

        tableView.getColumns().addAll(firstNameCol, lastNameCol);

        model.fetchMasters(0, ListTabController.RESULTS_PER_PAGE)
                .subscribe(masters -> tableView.getItems().addAll(masters),
                        th -> /*todo redo*/log.log(Level.WARNING, "Failed to fetch masters", th));
    }

}
