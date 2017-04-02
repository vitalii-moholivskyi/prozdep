package department.ui.utils;

import department.ui.controller.edit.EditPaperController;
import department.ui.controller.model.PaperViewModel;
import department.ui.controller.view.ItemViewPaperController;
import department.utils.Preconditions;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.val;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created by Максим on 4/2/2017.
 */
public final class Controllers {

    private Controllers() {
        throw new IllegalStateException("shouldn't be invoked");
    }

    public static ViewHolder<? extends EditPaperController, ? extends Parent> createPaperEditView(@NotNull PaperViewModel paper) {
        Preconditions.notNull(paper);

        val editLoader = UiUtils.newLoader("/view/partials/_formEditPaper.fxml", EditPaperController.class);

        try {
            val root = (Parent) editLoader.load();
            val editController = (EditPaperController) editLoader.getController();

            editController.setPaper(paper);
            return new ViewHolder<>(editController, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ViewHolder<ItemViewPaperController, ? extends Parent> createPaperItemView(PaperViewModel viewModel) {

        try {

            val loader = UiUtils.newLoader("/view/partials/_itemViewPaper.fxml");
            val root = (Parent) loader.load();
            val controller = (ItemViewPaperController) loader.getController();

            controller.setPaperViewModel(viewModel);
            return new ViewHolder<>(controller, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createPaperEditViewAndShow(@NotNull PaperViewModel paper) {

        val holder = createPaperEditView(paper);
        val stage = new Stage();

        stage.setScene(new Scene(holder.getView()));
        stage.centerOnScreen();
        stage.show();
        stage.sizeToScene();
    }

}
