package department.model.bo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.Value;

@Value
@lombok.Builder(toBuilder = true)
public class Paper {
    private Integer id;
    private String name;
    private String type;
    private int year;

}