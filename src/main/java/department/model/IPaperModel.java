package department.model;

import java.util.Collection;
import department.model.form.PaperCreateForm;
import department.model.form.PaperUpdateForm;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import department.ui.controller.model.PaperViewModel;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Nikolay
 *
 */
@Validated
public interface IPaperModel {

	/**
	 * Fetches papers
	 *
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 */
	@NotNull(message = "Null is not allowed")
	Observable<Collection<? extends PaperViewModel>> fetchPapers(@Min(0) long offset, @Min(0) long limit);

	/**
	 * Fetches papers by user query asynchronously
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
	Observable<Collection<? extends PaperViewModel>> fetchPapers(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit);

	/**
	 * Updates paper asynchronously
	 *
	 * @param form
	 *            update form
	 * @return rx observable which will return updated paper after completion
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if form is null
	 */
	@NotNull
	Observable<? extends PaperViewModel> create(@NotNull(message = "form cannot be null") PaperCreateForm form);

	Observable<? extends Integer> count();

	/**
	 * @param form
	 * @param model
	 * @param errCallback
	 */
	@NotNull
	void update(@NotNull(message = "form cannot be null") PaperUpdateForm form,
			@NotNull(message = "model cannot be null") PaperViewModel model,
			@NotNull(message = "error callback cannot be null") Action1<? super Throwable> errCallback);

	/**
	 * @param id
	 * @return
	 */
	Observable<? extends PaperViewModel> fetch(int id);

	// add other methods below...
}