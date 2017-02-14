package department.model.bo;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder(toBuilder = true)
public class Master {
    Integer id;
    String name;
    String phone;

    String topic;
    Date startDate;
    Date endDate;
    Teacher teacher;
    Department department;
}
