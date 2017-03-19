/**
 * 
 */
package department.model;

import department.dao.IPaperDAO;
import department.model.bo.Paper;
import department.model.form.PaperCreateForm;
import department.model.form.PaperUpdateForm;
import department.model.mapper.PaperMapper;
import department.ui.controller.model.PaperViewModel;
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
public class PaperModel implements IPaperModel {

	@Autowired
	IPaperDAO paperDao;

	@Override
	public Observable<? extends PaperViewModel> fetch(@NotNull(message = "id cannot be null") int id) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Paper>) sub -> {

			sub.onStart();
			try {
				sub.onNext(paperDao.find(id));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PaperMapper::toViewModel);
	}
	
	@Override
	public Observable<Collection<? extends PaperViewModel>> fetchPapers(@Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Paper>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(paperDao.findAll(limit, offset));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PaperMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends PaperViewModel>> fetchPapers(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Paper>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(paperDao.findAll(limit, offset));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PaperMapper::toViewModel);
	}

	@Override
	public Observable<? extends PaperViewModel> create(@NotNull(message = "form cannot be null") PaperCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Paper>) sub -> {

			sub.onStart();
			try {
				sub.onNext(paperDao.insert(
						Paper.builder().type(form.getType()).name(form.getName()).year(form.getYear()).build()));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PaperMapper::toViewModel);
	}

	@Override
	public void update(PaperUpdateForm form, PaperViewModel model, Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Paper>) sub -> {

			sub.onStart();
			try {
				val paper = Paper.builder().id(form.getId()).type(form.getType()).name(form.getName())
						.year(form.getYear()).build();
				paperDao.update(paper);
				sub.onNext(paper);
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
           model.setName(result.getName());
           model.setYear(result.getYear());
           model.setType(result.getType());
        }, errCallback::call);
		
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(paperDao.count());
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}

}
