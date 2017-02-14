package department.model.bo;

import java.util.Date;

import department.model.bo.Department;
import department.model.bo.Teacher;
import lombok.Value;

@Value
@lombok.Builder(toBuilder=true)
public class Master {

	private Integer id;
    private String name;
    private String phone;
    
	private String topic;
    private Date startDate;
    private Date endDate;
    private Teacher teacher;
    private Department department;

}
