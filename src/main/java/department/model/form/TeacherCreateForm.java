/**
 * 
 */
package department.model.form;

import java.util.Date;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author Nikolay
 *
 */
@Data
@Validated
public class TeacherCreateForm {
	
	private String name;
	private String phone;

	private String position;
	private String degree;
	private Date startDate;
	private Integer department;
}
