/**
 * 
 */
package department.model;

import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import department.dao.IPostgraduateDAO;
import department.model.bo.Department;
import department.model.bo.Postgraduate;
import department.model.bo.Teacher;
import department.model.form.PostgraduateCreateForm;
import department.model.form.PostgraduateUpdateForm;
import department.ui.controller.model.PostgraduateViewModel;
import department.ui.utils.FxSchedulers;
import lombok.val;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Nikolay
 *
 */
@Repository
public class PostgraduateModel implements IPostgraduateModel {

	@Autowired
	IPostgraduateDAO postgraduateDao;

	@Override
	public Observable<Collection<? extends PostgraduateViewModel>> fetchPostgraduates(@Min(0) long offset,
			@Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Postgraduate>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(postgraduateDao.findAll(limit, offset));
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<Collection<? extends PostgraduateViewModel>> fetchPostgraduates(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Postgraduate>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(postgraduateDao.findAll(limit, offset));
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<? extends PostgraduateViewModel> create(
			@NotNull(message = "form cannot be null") PostgraduateCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Postgraduate>) sub -> {

			sub.onStart();
			try {
				sub.onNext(postgraduateDao
						.insert(Postgraduate
								.builder()
								.teacher(Teacher.builder()
										.id(form.getTeacher())
										.build())
								.department(Department.builder()
										.id(form.getDepartment())
										.build())
								.endDate(form.getEndDate())
								.startDate(form.getStartDate())
								.topic(form.getTopic())
								.name(form.getName())
								.phone(form.getPhone())
								.endDate(form.getEndDate())
								.build()));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<? extends PostgraduateViewModel> update(PostgraduateUpdateForm form) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Postgraduate>) sub -> {

			sub.onStart();
			try {
				val postgraduate = Postgraduate
						.builder()
						.id(form.getId())
						.teacher(Teacher.builder()
								.id(form.getTeacher())
								.build())
						.department(Department.builder()
								.id(form.getDepartment())
								.build())
						.endDate(form.getEndDate())
						.startDate(form.getStartDate())
						.topic(form.getTopic())
						.name(form.getName())
						.phone(form.getPhone())
						.endDate(form.getEndDate())
						.build();
				postgraduateDao.update(postgraduate);
				sub.onNext(postgraduate);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(postgraduateDao.count());
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

}
