package department.model.bo;

import lombok.Value;
import java.util.Date;

@Value
@lombok.Builder(toBuilder = true)
public class Postgraduate{

    private Integer id;
    private String name;
    private String phone;

    private String topic;
    private Date startDate;
    private Date endDate;
    private Date protectionDate;
    private Department department;
    private Teacher teacher;

}