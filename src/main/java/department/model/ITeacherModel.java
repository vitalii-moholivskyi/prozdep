package department.model;

import department.ui.controller.model.TeacherViewModel;
import department.model.form.TeacherCreateForm;
import department.model.form.TeacherUpdateForm;

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
public interface ITeacherModel {

	/**
	 * Fetches teachers
	 *
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 */
	@NotNull(message = "Null is not allowed")
	Observable<Collection<? extends TeacherViewModel>> fetchTeachers(@Min(0) long offset, @Min(0) long limit);

	/**
	 * Fetches teachers by user query asynchronously
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
	Observable<Collection<? extends TeacherViewModel>> fetchTeachers(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit);

	/**
	 * Updates teacher asynchronously
	 *
	 * @param form
	 *            update form
	 * @return rx observable which will return updated teacher after completion
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if form is null
	 */
	@NotNull
	Observable<? extends TeacherViewModel> create(@NotNull(message = "form cannot be null") TeacherCreateForm form);

	/**
	 * @param form
	 * @param model
	 * @param errCallback
	 */
	@NotNull
	void update(@NotNull(message = "form cannot be null") TeacherUpdateForm form,
			@NotNull(message = "model cannot be null") TeacherViewModel model,
				Action0 resultcallback,
			@NotNull(message = "error callback cannot be null") Action1<? super Throwable> errCallback);
	Observable<? extends Integer> count();

	/**
	 * @param id
	 * @return
	 */
	Observable<? extends TeacherViewModel> fetch(int id);

	/**
	 * @param paperId
	 * @return
	 */
	Observable<? extends TeacherViewModel> fetchChiefTeacherByPaperId(int paperId);

	/**
	 * @param topicId
	 * @return
	 */
	Observable<Collection<? extends TeacherViewModel>> fetchTeachersByTopicId(int topicId);

	// add other methods below...
}