package department.model;

import department.ui.controller.model.PostgraduateViewModel;
import department.model.form.PostgraduateCreateForm;
import department.model.form.PostgraduateUpdateForm;

import org.springframework.validation.annotation.Validated;
import rx.Observable;
import rx.functions.Action1;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author Nikolay
 *
 */
@Validated
public interface IPostgraduateModel {

	/**
	 * Fetches postgraduates
	 *
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 */
	@NotNull(message = "Null is not allowed")
	Observable<Collection<? extends PostgraduateViewModel>> fetchPostgraduates(@Min(0) long offset, @Min(0) long limit);

	/**
	 * Fetches postgraduates by user query asynchronously
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
	Observable<Collection<? extends PostgraduateViewModel>> fetchPostgraduates(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit);

	/**
	 * Updates postgraduate asynchronously
	 *
	 * @param form
	 *            update form
	 * @return rx observable which will return updated postgraduate after completion
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if form is null
	 */
	@NotNull
	Observable<? extends PostgraduateViewModel> create(@NotNull(message = "form cannot be null") PostgraduateCreateForm form);

	/**
	 * @param form
	 * @param model
	 * @param errCallback
	 */
	@NotNull
	void update(@NotNull(message = "form cannot be null") PostgraduateUpdateForm form,
			@NotNull(message = "model cannot be null") PostgraduateViewModel model,
			@NotNull(message = "error callback cannot be null") Action1<? super Throwable> errCallback);
	
	
	Observable<? extends Integer> count();

	// add other methods below...
}