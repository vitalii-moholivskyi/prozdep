/**
 * 
 */
package department.model;

import department.dao.IPostgraduateDAO;
import department.model.bo.Department;
import department.model.bo.Postgraduate;
import department.model.bo.Teacher;
import department.model.form.PostgraduateCreateForm;
import department.model.form.PostgraduateUpdateForm;
import department.model.mapper.PostgraduateMapper;
import department.ui.controller.model.PostgraduateViewModel;
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
public class PostgraduateModel implements IPostgraduateModel {

	@Autowired
	IPostgraduateDAO postgraduateDao;
	
	@Override
	public Observable<? extends PostgraduateViewModel> fetch(
			@NotNull(message = "id cannot be null") int id) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Postgraduate>) sub -> {

			sub.onStart();
			try {
				sub.onNext(postgraduateDao
						.find(id,true));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PostgraduateMapper::toViewModel);
	}
	@Override
	public Observable<Collection<? extends PostgraduateViewModel>> fetchPostgraduates(@Min(0) long offset,
			@Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Postgraduate>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(postgraduateDao.findAll("",limit, offset,true));
					} catch (Exception e) {
						sub.onError(e);
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PostgraduateMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends PostgraduateViewModel>> fetchPostgraduates(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Postgraduate>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(postgraduateDao.findAll(query,limit, offset,true));
					} catch (Exception e) {
						sub.onError(e);
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PostgraduateMapper::toViewModel);
	}

	@Override
	public Observable<? extends PostgraduateViewModel> create(
			@NotNull(message = "form cannot be null") PostgraduateCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Postgraduate>) sub -> {

			sub.onStart();
			try {
				sub.onNext(postgraduateDao
						.insert(Postgraduate.builder().teacher(Teacher.builder().id(form.getTeacher()).build())
								.department(Department.builder().id(form.getDepartment()).build())
								.endDate(form.getEndDate()).startDate(form.getStartDate()).topic(form.getTopic())
								.name(form.getName()).phone(form.getPhone()).endDate(form.getEndDate()).build()));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(PostgraduateMapper::toViewModel);
	}

	@Override
	public void update(@NotNull(message = "form cannot be null") PostgraduateUpdateForm form, @NotNull(message = "model cannot be null") PostgraduateViewModel model, @NotNull(message = "error callback cannot be null") Action1<? super Void> callback, @NotNull(message = "error callback cannot be null") Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Postgraduate>) sub -> {

			sub.onStart();
			try {
				val postgraduate = Postgraduate.builder().id(form.getId())
						.teacher(Teacher.builder().id(form.getTeacher()).build())
						.department(Department.builder().id(form.getDepartment()).build()).endDate(form.getEndDate())
						.startDate(form.getStartDate()).topic(form.getTopic()).name(form.getName())
						.phone(form.getPhone()).endDate(form.getEndDate()).build();
				postgraduateDao.update(postgraduate);
				sub.onNext(postgraduate);
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
			model.setFirstName(result.getName());
			model.setEndDate(result.getEndDate());
			model.setStartDate(result.getStartDate());
			model.setPhone(result.getPhone());
			model.setProtectionDate(result.getProtectionDate());
			model.setTopic(result.getTopic());
			model.setTeacherId(result.getTeacher().getId());
			callback.call(null);
		} , errCallback::call);
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(postgraduateDao.count());
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}

}
