package department.ui.controller.view;

import department.model.ITeacherModel;
import department.ui.controller.model.PaperViewModel;
import department.utils.Preconditions;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Created by Максим on 4/2/2017.
 */
@Controller
public final class ItemViewPaperController {

    @FXML private Parent root;
    @FXML private Label title;
    @FXML private Label year;
    @FXML private Label supervisor;

    private PaperViewModel paperViewModel;

    @Autowired
    public ItemViewPaperController(ITeacherModel model) {

    }

    @PostConstruct
    public void init() {
    }

    public void setPaperViewModel(PaperViewModel paper) {
        this.paperViewModel = Preconditions.notNull(paper);

        title.setText(paper.getName());
        year.setText(String.valueOf(paper.getYear()));
        supervisor.setText(paper.getType());
    }

    public Parent getRoot() {
        return root;
    }

    @FXML
    private void initialize() {

    }

}
