package department.ui.utils;

import javax.validation.constraints.NotNull;

/**
 * Created by Максим on 2/19/2017.
 */
public interface IProgressShower {

    void showProgress(@NotNull String message);

    void hideProgress();

}
