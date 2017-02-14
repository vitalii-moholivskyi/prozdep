package department.model.bo;

import java.util.Date;
import lombok.Value;

@Value
@lombok.Builder(toBuilder = true)
public class Teacher {

	private Integer id;
	private String name;
	private String phone;

	private String position;
	private String degree;
	private Date startDate;
	private Department department;

}
