package department.model;

import department.dao.ITeacherDAO;
import department.model.bo.Department;
import department.model.bo.Teacher;
import department.model.form.TeacherCreateForm;
import department.model.form.TeacherUpdateForm;
import department.model.mapper.TeacherMapper;
import department.ui.controller.model.TeacherViewModel;
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
public class TeacherModel implements ITeacherModel {

	@Autowired
	ITeacherDAO teacherDao;

	@Override
	public Observable<Collection<? extends TeacherViewModel>> fetchTeachers(@Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Teacher>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(teacherDao.findAll(limit, offset));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TeacherMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends TeacherViewModel>> fetchTeachers(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Teacher>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(teacherDao.findAll(limit, offset));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TeacherMapper::toViewModel);
	}

	@Override
	public Observable<? extends TeacherViewModel> create(
			@NotNull(message = "form cannot be null") TeacherCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Teacher>) sub -> {

			sub.onStart();
			try {
				sub.onNext(teacherDao.insert(Teacher.builder().degree(form.getDegree())
						.department(Department.builder().id(form.getDepartment()).build())
						.startDate(form.getStartDate()).name(form.getName()).phone(form.getPhone())
						.position(form.getPosition()).build()));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TeacherMapper::toViewModel);
	}

	@Override
	public void update(TeacherUpdateForm form, TeacherViewModel model, Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Teacher>) sub -> {

			sub.onStart();
			try {
				val teacher = Teacher.builder().id(form.getId()).degree(form.getDegree())
						.department(Department.builder().id(form.getDepartment()).build())
						.startDate(form.getStartDate()).name(form.getName()).phone(form.getPhone())
						.position(form.getPosition()).build();
				teacherDao.update(teacher);
				sub.onNext(teacher);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
			model.setFirstName(result.getName());
			model.setStartDate(result.getStartDate());
			model.setPhone(result.getPhone());
			model.setDegree(result.getDegree());
			model.setDepartment(result.getDepartment().getId());
			model.setPosition(result.getPosition());
		} , errCallback::call);
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(teacherDao.count());
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}
}
