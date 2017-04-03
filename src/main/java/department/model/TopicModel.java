/**
 *
 */
package department.model;

import department.dao.ITopicDAO;
import department.model.bo.Department;
import department.model.bo.Teacher;
import department.model.bo.Topic;
import department.model.form.TopicCreateForm;
import department.model.form.TopicUpdateForm;
import department.model.mapper.TopicMapper;
import department.ui.controller.model.TopicViewModel;
import department.ui.utils.FxSchedulers;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rx.Observable;
import rx.functions.Action0;
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
public class TopicModel implements ITopicModel {

	@Autowired
	ITopicDAO topicDao;

	@Override
	public Observable<? extends TopicViewModel> fetch(@NotNull(message = "id cannot be null") int id) {

		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Topic>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.find(id,true));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TopicMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends TopicViewModel>> fetchTopics(@Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Topic>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.findAll("",limit, offset,true));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TopicMapper::toViewModel);
	}

	@Override
	public Observable<Collection<? extends TopicViewModel>> fetchTopics(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Topic>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.findAll(query,limit, offset,true));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TopicMapper::toViewModel);
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
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TopicMapper::toViewModel);
	}

	@Override
	public void update(@NotNull(message = "form cannot be null") TopicUpdateForm form, @NotNull(message = "model cannot be null") TopicViewModel model, @NotNull(message = "model cannot be null") Action0 resultCallback, @NotNull(message = "error callback cannot be null") Action1<? super Throwable> errCallback) {
		Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Topic>) sub -> {

			sub.onStart();
			try {
				val topic = Topic.builder().id(form.getId()).name(form.getName())
						.chiefScientist(Teacher.builder().id(form.getChiefScientist()).build()).client(form.getClient())
						.department(Department.builder().id(form.getDepartment()).build()).endDate(form.getEndDate())
						.startDate(form.getStartDate())

						.build();
				topicDao.update(topic);
				sub.onNext(topicDao.find(topic.getId(),true));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
            }
        })).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread())
                .doOnCompleted(resultCallback)
                .subscribe(result -> {
                    model.setChiefScientist(result.getChiefScientist()!=null?result.getChiefScientist().getId():null);
                    model.setStartDate(result.getStartDate());
                    model.setName(result.getName());
                    model.setEndDate(result.getEndDate());
                    model.setChiefScientistName(result.getChiefScientist()!=null?result.getChiefScientist().getName():null);
                    model.setDepartmentTitle(result.getDepartment()!=null?result.getDepartment().getName():null);
                }, errCallback::call);
    }

	@Override
	public Observable<? extends Integer> count() {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<? extends Integer>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.count());
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread());
	}

	/* (non-Javadoc)
	 * @see department.model.ITopicModel#fetchByScientist(int, long, long)
	 */
	@Override
	public Observable<Collection<? extends TopicViewModel>> fetchByScientist(int id, int offset, int limit) {
		return Observable.defer(() -> Observable.create((Observable.OnSubscribe<Collection<? extends Topic>>) sub -> {

			sub.onStart();
			try {
				sub.onNext(topicDao.getTopicsByScientistId(id,limit,offset));
			} catch (Exception e) {
				sub.onError(e);
			} finally {
				sub.onCompleted();
			}
		})).observeOn(FxSchedulers.platform()).subscribeOn(Schedulers.newThread()).map(TopicMapper::toViewModel);
	}

}
