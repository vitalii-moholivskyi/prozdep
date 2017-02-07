package department.utils;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import rx.Observable;

import java.util.Objects;

/**
 * Created by Максим on 2/1/2017.
 */
public final class RxUtils {

    private RxUtils() {
        throw new IllegalStateException("shouldn't be called");
    }

    public static <T> Observable<T> fromProperty(ReadOnlyObjectProperty<T> property) {
        Objects.requireNonNull(property, "property == null");

        return Observable.create(subscriber -> {
            subscriber.onStart();
            property.addListener((new ChangeListener<T>() {

                @Override
                public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                    if (subscriber.isUnsubscribed()) {
                        property.removeListener(this);
                    } else {
                        subscriber.onNext(newValue);
                    }
                }
            }));
        });
    }

}
