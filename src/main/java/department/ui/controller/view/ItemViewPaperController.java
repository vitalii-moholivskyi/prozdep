package department.ui.controller.view;

import department.ui.controller.model.PaperViewModel;
import department.utils.Preconditions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Controller;

/**
 * Created by Максим on 4/2/2017.
 */
@Controller
public final class ItemViewPaperController {

    @FXML private Label title;
    @FXML private Label year;
    @FXML private Label supervisor;

    public ItemViewPaperController() {}

    public void setPaperViewModel(PaperViewModel paper) {
        Preconditions.notNull(paper);

        title.setText(paper.getName());
        year.setText(String.valueOf(paper.getYear()));
        supervisor.setText(paper.getType());
    }

}
