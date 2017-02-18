package department.model;

import department.dao.IMasterDAO;
import department.model.bo.Department;
import department.model.bo.Master;
import department.model.bo.Teacher;
import department.model.form.MasterCreateForm;
import department.model.form.MasterUpdateForm;
import department.model.mapper.MasterMapper;
import department.ui.controller.model.MasterViewModel;
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

@Repository
public class MasterModel implements IMasterModel {

	@Autowired
	IMasterDAO masterDao;

	@Override
	public Observable<Collection<? extends MasterViewModel>> fetchMasters(@Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Master>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(masterDao.findAll(limit, offset));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(MasterMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends MasterViewModel>> fetchMasters(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Master>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(masterDao.findAll(limit, offset));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(MasterMapper::toViewModel);
	}

	@Override
	public Observable<? extends MasterViewModel> create(
			@NotNull(message = "form cannot be null") MasterCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Master>) sub -> {

			sub.onStart();
			try {
				sub.onNext(masterDao.insert(Master.builder().teacher(Teacher.builder().id(form.getTeacher()).build())
						.department(Department.builder().id(form.getDepartment()).build()).endDate(form.getEndDate())
						.startDate(form.getStartDate()).topic(form.getTopic()).name(form.getName())
						.phone(form.getPhone()).build()));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(MasterMapper::toViewModel);
	}

	@Override
	public void update(MasterUpdateForm form, MasterViewModel model, Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Master>) sub -> {

			sub.onStart();
			try {
				val master = Master.builder().id(form.getId()).teacher(Teacher.builder().id(form.getTeacher()).build())
						.department(Department.builder().id(form.getDepartment()).build()).endDate(form.getEndDate())
						.startDate(form.getStartDate()).topic(form.getTopic()).name(form.getName())
						.phone(form.getPhone()).build();
				masterDao.update(master);
				sub.onNext(master);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
			model.setTeacherId(result.getTeacher().getId());
			model.setFirstName(result.getName());
			model.setPhone(result.getPhone());
			model.setTopic(result.getTopic());
			model.setEndDate(result.getEndDate());
			model.setStartDate(result.getStartDate());
		} , errCallback::call);
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(masterDao.count());
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}
}
