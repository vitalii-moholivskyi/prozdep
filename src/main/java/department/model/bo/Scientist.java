package department.model.bo;

import lombok.Value;

/**
 * Created by mogo on 2/14/17.
 */
@Value
@lombok.Builder(toBuilder = true)
public class Scientist {

    private Integer id;
    private String name;
    private String phone;
}
