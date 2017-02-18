package department.model;

import java.util.Collection;
import department.model.form.DepartmentCreateForm;
import department.model.form.DepartmentUpdateForm;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import department.ui.controller.model.DepartmentViewModel;
import rx.Observable;

/**
 * @author Nikolay
 *
 */
@Validated
public interface IDepartmentModel {

	/**
	 * Fetches departments
	 *
	 * @param offset
	 *            number of elements to skip, inclusive
	 * @param limit
	 *            max number to fetch, inclusive
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 */
	@NotNull(message = "Null is not allowed")
	Observable<Collection<? extends DepartmentViewModel>> fetchDepartments(@Min(0) long offset, @Min(0) long limit);

	/**
	 * Fetches departments by user query asynchronously
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
	Observable<Collection<? extends DepartmentViewModel>> fetchDepartments(
			@NotNull(message = "query cannot be null") String query, @Min(0) long offset, @Min(0) long limit);

	/**
	 * Updates department asynchronously
	 *
	 * @param form
	 *            update form
	 * @return rx observable which will return updated department after completion
	 * @throws IllegalArgumentException
	 *             if offset < 0 or limit < 0
	 * @throws NullPointerException
	 *             if form is null
	 */
	@NotNull
	Observable<? extends DepartmentViewModel> create(@NotNull(message = "form cannot be null") DepartmentCreateForm form);

	@NotNull
	Observable<? extends DepartmentViewModel> update(@NotNull(message = "form cannot be null") DepartmentUpdateForm department);

	Observable<? extends Integer> count();

	// add other methods below...
}