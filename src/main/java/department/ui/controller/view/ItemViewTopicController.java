package department.ui.controller.view;

import department.ui.controller.model.TopicViewModel;
import department.ui.utils.UiUtils;
import department.utils.Preconditions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.val;
import org.springframework.stereotype.Controller;

/**
 * Created by Максим on 4/2/2017.
 */
@Controller
public final class ItemViewTopicController {

    @FXML public Label topic;
    @FXML public Label date;

    public ItemViewTopicController() {
    }

    public void setTopicViewModel(TopicViewModel topicViewModel) {
        Preconditions.notNull(topicViewModel);

        topic.setText(topicViewModel.getName());

        val startDate = topicViewModel.getStartDate();
        val endDate = topicViewModel.getEndDate();

        date.setText(String.format("з %s по %s", UiUtils.DATE_FORMAT.format(startDate),
                UiUtils.DATE_FORMAT.format(endDate)));
    }

}
