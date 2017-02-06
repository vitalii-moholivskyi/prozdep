package department.ui.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
@Controller
public final class TeacherTabController extends ListTabController<Object> {

    private final MainController mainController;

    @Autowired
    public TeacherTabController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    protected void doInitialize() {
        //todo implement
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        //todo implement
    }

}
