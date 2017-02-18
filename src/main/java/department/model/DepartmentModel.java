/**
 * 
 */
package department.model;

import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import department.dao.IDepartmentDAO;
import department.model.bo.Department;
import department.model.form.DepartmentCreateForm;
import department.model.form.DepartmentUpdateForm;
import department.ui.controller.model.DepartmentViewModel;
import department.ui.utils.FxSchedulers;
import lombok.val;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Nikolay
 *
 */
@Repository
public class DepartmentModel implements IDepartmentModel {

	@Autowired
	IDepartmentDAO departmentDao;

	@Override
	public Observable<Collection<? extends DepartmentViewModel>> fetchDepartments(@Min(0) long offset,
			@Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Department>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(departmentDao.findAll(limit, offset));
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<Collection<? extends DepartmentViewModel>> fetchDepartments(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable
				.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Department>>) sub -> {

					sub.onStart();
					try {
						sub.onNext(departmentDao.findAll(limit, offset));
					} finally {
						sub.onCompleted();
					}
				})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<? extends DepartmentViewModel> create(
			@NotNull(message = "form cannot be null") DepartmentCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Department>) sub -> {

			sub.onStart();
			try {
				sub.onNext(
						departmentDao.insert(Department.builder().phone(form.getPhone()).name(form.getName()).build()));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public void update(DepartmentUpdateForm form, DepartmentViewModel model, Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Department>) sub -> {

			sub.onStart();
			try {
				val department = Department.builder().id(form.getId()).phone(form.getPhone()).name(form.getName())
						.build();
				departmentDao.update(department);
				sub.onNext(department);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
			model.setName(result.getName());
			model.setPhone(result.getPhone());
		} , errCallback::call);

	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(departmentDao.count());
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

}
