package department.model;

import java.util.Collection;
import department.model.form.ScientistCreateForm;
import department.model.form.ScientistUpdateForm;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import department.ui.controller.model.ScientistViewModel;
import rx.Observable;

/**
 * @author Nikolay
 *
 */
@Validated
public interface IScientistModel {

	/**
	 * Fetches scientists
	 *
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 */
	@NotNull(message = "Null is not allowed")
	Observable<Collection<? extends ScientistViewModel>> fetchScientists(@Min(0) long offset, @Min(0) long limit);

	/**
	 * Fetches scientists by user query asynchronously
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
	Observable<Collection<? extends ScientistViewModel>> fetchScientists(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit);

	/**
	 * Updates scientist asynchronously
	 *
	 * @param form
	 *            update form
	 * @return rx observable which will return updated scientist after completion
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if form is null
	 */
	@NotNull
	Observable<? extends ScientistViewModel> create(@NotNull(message = "form cannot be null") ScientistCreateForm form);

	@NotNull
	Observable<? extends ScientistViewModel> update(@NotNull(message = "form cannot be null") ScientistUpdateForm scientist);

	Observable<? extends Integer> count();

	// add other methods below...
}