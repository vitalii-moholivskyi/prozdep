/**
 * 
 */
package department.model;

import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import department.dao.ITopicDAO;
import department.model.bo.Department;
import department.model.bo.Teacher;
import department.model.bo.Topic;
import department.model.form.TopicCreateForm;
import department.model.form.TopicUpdateForm;
import department.ui.controller.model.TopicViewModel;
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
public class TopicModel implements ITopicModel {

	@Autowired
	ITopicDAO topicDao;

	@Override
	public Observable<Collection<? extends TopicViewModel>> fetchTopics(@Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Topic>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.findAll(limit, offset));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<Collection<? extends TopicViewModel>> fetchTopics(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Topic>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.findAll(limit, offset));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public Observable<? extends TopicViewModel> create(@NotNull(message = "form cannot be null") TopicCreateForm form) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Topic>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.insert(Topic.builder().name(form.getName())
						.chiefScientist(Teacher.builder().id(form.getChiefScientist()).build()).client(form.getClient())
						.department(Department.builder().id(form.getDepartment()).build()).endDate(form.getEndDate())
						.startDate(form.getStartDate())

						.build()));
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

	@Override
	public void update(TopicUpdateForm form, TopicViewModel model, Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Topic>) sub -> {

			sub.onStart();
			try {
				val topic = Topic.builder().id(form.getId()).name(form.getName())
						.chiefScientist(Teacher.builder().id(form.getChiefScientist()).build()).client(form.getClient())
						.department(Department.builder().id(form.getDepartment()).build()).endDate(form.getEndDate())
						.startDate(form.getStartDate())

						.build();
				topicDao.update(topic);
				sub.onNext(topic);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).subscribe(result -> {
			model.setChiefScientist(result.getChiefScientist().getId());
			model.setStartDate(result.getStartDate());
			model.setName(result.getName());
			model.setEndDate(result.getEndDate());
			model.setClient(result.getClient());
		} , errCallback::call);
	}

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.count());
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(func -> null);
	}

}
