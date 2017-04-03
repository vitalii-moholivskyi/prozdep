package department.ui.utils;

import department.utils.Preconditions;
import javafx.scene.Parent;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Created by Максим on 4/2/2017.
 */
@Value
public class ViewHolder<C, V extends Parent> {

    C controller;
    V view;

    public ViewHolder(@NotNull C controller, @NotNull V view) {
        this.controller = Preconditions.notNull(controller);
        this.view = Preconditions.notNull(view);
    }
}
