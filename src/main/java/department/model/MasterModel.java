package department.model;

import department.model.bo.Master;
import department.model.form.MasterForm;
import org.springframework.stereotype.Repository;
import rx.Observable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by Максим on 2/1/2017.
 */
@Repository
public class MasterModel implements IMasterModel {

    //{
    //    Observable.defer(() -> dao.getAll).subscribeOn(Schedulers.newThread());
    //}

    @Override
    public Observable<Collection<? extends Master>> fetchMasters(@Min(0) long offset, @Min(0) long limit) {
        return null;
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
