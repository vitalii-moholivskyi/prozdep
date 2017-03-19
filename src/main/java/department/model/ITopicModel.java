package department.model;

import department.ui.controller.model.TopicViewModel;
import department.model.form.TopicCreateForm;
import department.model.form.TopicUpdateForm;

import org.springframework.validation.annotation.Validated;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author Nikolay
 *
 */
@Validated
public interface ITopicModel {

	/**
	 * Fetches topics
	 *
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 */
	@NotNull(message = "Null is not allowed")
	Observable<Collection<? extends TopicViewModel>> fetchTopics(@Min(0) long offset, @Min(0) long limit);

	/**
	 * Fetches topics by user query asynchronously
	 *
	 * @param query
	 *            query to search by
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if query is null
	 */
	@NotNull
	Observable<Collection<? extends TopicViewModel>> fetchTopics(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit);

	/**
	 * Updates topic asynchronously
	 *
	 * @param form
	 *            update form
	 * @return rx observable which will return updated topic after completion
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if form is null
	 */
	@NotNull
	Observable<? extends TopicViewModel> create(@NotNull(message = "form cannot be null") TopicCreateForm form);

	/**
	 * @param form
	 * @param model
	 * @param errCallback
	 */
	@NotNull
	void update(@NotNull(message = "form cannot be null") TopicUpdateForm form,
			@NotNull(message = "model cannot be null") TopicViewModel model,
				@NotNull(message = "model cannot be null") Action0 resultCallback,
			@NotNull(message = "error callback cannot be null") Action1<? super Throwable> errCallback);

	Observable<? extends Integer> count();

	/**
	 * @param id
	 * @return
	 */
	Observable<? extends TopicViewModel> fetch(int id);

	// add other methods below...
}