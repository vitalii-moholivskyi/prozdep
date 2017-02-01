package department.model;

import department.model.bo.Master;
import department.model.form.MasterForm;
import org.springframework.validation.annotation.Validated;
import rx.Observable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * <p>
 * Model which provides operations for corresponding controller(s)
 * </p>
 * Created by Максим on 2/1/2017.
 */
@Validated
public interface IMasterModel {

    /**
     * Fetches masters
     *
     * @param offset number of elements to skip, inclusive
     * @param limit  max number to fetch, inclusive
     * @throws IllegalArgumentException if offset < 0 or limit < 0
     */
    @NotNull(message = "Null is not allowed")
    Observable<Collection<? extends Master>> fetchMasters(@Min(0) long offset, @Min(0) long limit);

    /**
     * Fetches masters by user query asynchronously
     *
     * @param query  query to search by
     * @param offset number of elements to skip, inclusive
     * @param limit  max number to fetch, inclusive
     * @throws IllegalArgumentException if offset < 0 or limit < 0
     * @throws NullPointerException     if query is null
     */
    @NotNull
    Observable<Collection<? extends Master>> fetchMasters(@NotNull(message = "query cannot be null") String query,
                                                @Min(0) long offset, @Min(0) long limit);

    /**
     * Updates master asynchronously
     *
     * @param form update form
     * @return rx observable which will return updated master after completion
     * @throws IllegalArgumentException if offset < 0 or limit < 0
     * @throws NullPointerException     if form is null
     */
    @NotNull
    Observable<? extends Master> update(@NotNull(message = "form cannot be null") MasterForm form);

    // add other methods below...
}
