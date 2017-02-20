package department.ui.utils;

import javafx.util.StringConverter;

/**
 * Created by Максим on 2/20/2017.
 */
public abstract class DefaultStringConverter <T> extends StringConverter<T> {

    @Override
    public T fromString(String string) {
        return null;
    }
}
