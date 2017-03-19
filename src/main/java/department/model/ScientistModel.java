/**
 * 
 */
package department.model;

import department.dao.IScientistDAO;
import department.model.bo.Scientist;
import department.model.form.ScientistCreateForm;
import department.model.form.ScientistUpdateForm;
import department.model.mapper.ScientistMapper;
import department.ui.controller.model.ScientistViewModel;
import department.ui.utils.FxSchedulers;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author Nikolay
 *
 */
@Repository
public class ScientistModel implements IScientistModel {

	@Autowired
	IScientistDAO scientistDao;

	@Override
	public Observable<? extends ScientistViewModel> fetch(
			@NotNull(message = "id cannot be null") int id) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Scientist>) sub -> {

			sub.onStart();
			try {
				sub.onNext(scientistDao.find(id));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(ScientistMapper::toViewModel);
	}
	
	@Override
	public Observable<Collection<? extends ScientistViewModel>> fetchScientists(@Min(0) long offset,
			@Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Scientist>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(scientistDao.findAll(limit, offset));
					} catch (Exception e) {
						sub.onError(e);
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(ScientistMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends ScientistViewModel>> fetchScientists(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Scientist>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(scientistDao.findAll(limit, offset));
					} catch (Exception e) {
						sub.onError(e);
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(ScientistMapper::toViewModel);
	}

	@Override
	public Observable<? extends ScientistViewModel> create(
			@NotNull(message = "form cannot be null") ScientistCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Scientist>) sub -> {

			sub.onStart();
			try {
				sub.onNext(
						scientistDao.insert(Scientist.builder().phone(form.getPhone()).name(form.getName()).build()));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(ScientistMapper::toViewModel);
	}

	@Override
	public void update(ScientistUpdateForm form, ScientistViewModel model, Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Scientist>) sub -> {

			sub.onStart();
			try {
				val scientist = Scientist.builder().id(form.getId()).phone(form.getPhone()).name(form.getName())
						.build();
				scientistDao.update(scientist);
				sub.onNext(scientist);
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
			model.setFirstName(result.getName());
			model.setPhone(result.getPhone());
		} , errCallback::call);
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(scientistDao.count());
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}

}
