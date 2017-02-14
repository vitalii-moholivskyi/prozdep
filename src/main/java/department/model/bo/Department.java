package department.model.bo;

import lombok.Value;

@Value
@lombok.Builder(toBuilder = true)
public class Department {
	private Integer id;
	private String name;
	private String phone;
}
