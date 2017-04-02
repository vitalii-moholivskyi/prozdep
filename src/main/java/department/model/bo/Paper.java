package department.model.bo;

import lombok.Value;

@Value
@lombok.Builder(toBuilder = true)
public class Paper {
    private Integer id;
    private String name;
    private String type;
    private int year;
    
}