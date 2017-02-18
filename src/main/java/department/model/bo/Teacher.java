package department.model.bo;

import java.util.Date;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@lombok.ToString(callSuper = true)
public class Teacher extends Scientist{

	private String position;
	private String degree;
	private Date startDate;
	private Department department;

	@lombok.Builder(toBuilder = true)
	private Teacher(int id, String name, String phone, String position, String degree, Date startDate, Department department){
		super(id, name, phone);
		this.position = position;
		this.degree = degree;
		this.startDate = startDate;
		this.department = department;
	}
	/*workaround to make lombok builder work for inheritance*/
	public static class TeacherBuilder extends ScientistBuilder{
		TeacherBuilder() {
			super();
		}
	}

}
