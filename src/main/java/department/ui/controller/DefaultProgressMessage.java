package department.ui.controller;

import department.utils.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotNull;

/**
 * Created by Максим on 2/19/2017.
 */
@Value
@Getter(AccessLevel.NONE)
public class DefaultProgressMessage implements IProgressShower {

    MainController mainController;
    @NonFinal long id;

    public DefaultProgressMessage(MainController mainController) {
        this.mainController = Preconditions.notNull(mainController);
    }

    @Override
    public void showProgress(@NotNull String message) {
        id = mainController.showProgress(message);
    }

    @Override
    public void hideProgress() {
        mainController.hideProgress(id);
    }
}
