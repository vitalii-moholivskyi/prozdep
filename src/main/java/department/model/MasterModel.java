package department.model;

import department.model.bo.Master;
import department.model.form.MasterForm;
import department.ui.utils.FxSchedulers;
import org.springframework.stereotype.Repository;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Максим on 2/1/2017.
 */
@Repository
public class MasterModel implements IMasterModel {

    //{
    //    Observable.defer(() -> dao.getAll).subscribeOn(Schedulers.newThread());
    /*Arrays.asList(new Master("Max", "Oliynick"),
                new Master("Kolya", "Nevmer"),
                new Master("Roman", "Nevmer"))*/
    //}

    @Override
    public Observable<Collection<? extends Master>> fetchMasters(@Min(0) long offset, @Min(0) long limit) {
        return Observable.defer(() ->
                Observable.create((Observable.OnSubscribe<Collection<? extends Master>>) sub -> {

                    sub.onStart();
                    try {
                        Thread.sleep(3000L);
                        sub.onNext(Arrays.asList(
                                new Master("Max", "Oliynick"),
                                new Master("Kolya", "Nevmer"),
                                new Master("Roman", "Nevmer")
                        ));
                    } catch (InterruptedException e) {
                        sub.onError(e);
                    } finally {
                        sub.onCompleted();
                    }
                }))
                .observeOn(FxSchedulers.platform())
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<Collection<? extends Master>> fetchMasters(@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
        return null;
    }

    @Override
    public Observable<? extends Master> update(@NotNull(message = "form cannot be null") MasterForm form) {
        return null;
    }
}
