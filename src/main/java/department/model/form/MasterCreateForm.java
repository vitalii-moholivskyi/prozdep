package department.model.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class MasterCreateForm {
	@NotNull
    private String name;
	@NotNull
    private String phone;
	@NotNull
	private String topic;
	@NotNull
    private Date startDate;
	
	private Date endDate;
	
	@NotNull
	private Integer teacher;
	
	@NotNull
    private Integer department;
}
