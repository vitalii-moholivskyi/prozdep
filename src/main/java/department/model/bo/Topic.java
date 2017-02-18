package department.model.bo;

import lombok.Value;
import java.util.Date;

@Value
@lombok.Builder(toBuilder = true)
public class Topic {

    private Integer id;
    private String name;
    private String client;
    private Date startDate;
    private Date endDate;
    private Department department;
    private Teacher chiefScientist;

}