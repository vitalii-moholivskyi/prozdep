package department.model.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * Created by mogo on 2/14/17.
 */

@lombok.ToString
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@lombok.Builder(toBuilder = true)
public class Scientist {

    protected int id;
    protected String name;
    protected String phone;

}
