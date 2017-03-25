package department.utils;

import com.sun.istack.internal.Nullable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.val;
import rx.Observable;
import rx.Subscriber;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by Максим on 2/1/2017.
 */
public final class RxUtils {

    private RxUtils() {
        throw new IllegalStateException("shouldn't be called");
    }

    public static <T> ObservableValue<T> fromRx(@NotNull Observable<T> obs, T initial) {
        Preconditions.notNull(obs);
        val property = new SimpleObjectProperty<T>(initial);
        obs.subscribe(new Subscriber<T>() {

            @Override
            public void onCompleted() {
                property.unbind();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(T t) {
                property.set(t);
            }
        });

        return property;
    }

    public static <T> ObservableValue<T> fromRx(@NotNull Observable<T> obs) {
        return RxUtils.fromRx(obs, null);
    }

    public static <T> Observable<T> fromProperty(@NotNull ReadOnlyObjectProperty<T> property) {
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
