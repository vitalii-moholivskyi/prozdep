package department.model;

import department.dao.IDAOGeneric;
import department.model.bo.Master;
import department.model.form.MasterForm;
import department.ui.controller.model.MasterViewModel;
import department.ui.utils.FxSchedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by Максим on 2/1/2017.
 */
@Repository
public class MasterModel implements IMasterModel {

	@Autowired
	IDAOGeneric<Master> masterDao;
	// {
	// Observable.defer(() -> dao.getAll).subscribeOn(Schedulers.newThread());
	/*
	 * Arrays.asList(new Master("Max", "Oliynick"), new Master("Kolya",
	 * "Nevmer"), new Master("Roman", "Nevmer"))
	 */
	// }

    @Override
    public Observable<Collection<? extends MasterViewModel>> fetchMasters(@Min(0) long offset, @Min(0) long limit) {
        return Observable.defer(() ->
                Observable.create((Observable.OnSubscribe<Collection<? extends MasterViewModel>>) sub -> {

                    sub.onStart();
                    try {
                        Thread.sleep(3000L);
                        /*sub.onNext(Arrays.asList(
                                new MasterViewModel("Max", "Oliynick"),
                                new MasterViewModel("Kolya", "Nevmer"),
                                new MasterViewModel("Roman", "Nevmer")
                        ));*/
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
	public Observable<Collection<? extends MasterViewModel>> fetchMasters(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends MasterViewModel>>) sub -> {

			sub.onStart();
			try {
				Thread.sleep(3000L);
				//sub.onNext(masterDao.findAll(limit, offset)); map here
			} catch (InterruptedException e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
		// return null;
	}

	@Override
	public Observable<? extends MasterViewModel> create(@NotNull(message = "form cannot be null") MasterForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends MasterViewModel>) sub -> {

			sub.onStart();
			try {
				Master master = Master.builder().teacher(form.getTeacher()).department(form.getDepartment())
						.endDate(form.getEndDate()).startDate(form.getStartDate()).topic(form.getTopic())
						.name(form.getName()).phone(form.getPhone()).build();
				masterDao.update(master);
				Thread.sleep(3000L);
				//sub.onNext(master); map here
			} catch (InterruptedException e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}

	@Override
	public Observable<? extends MasterViewModel> update(@NotNull(message = "form cannot be null") Master master) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends MasterViewModel>) sub -> {

			sub.onStart();
			try {
				masterDao.update(master);
				Thread.sleep(3000L);
				//sub.onNext(master); map here
			} catch (InterruptedException e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}
}
