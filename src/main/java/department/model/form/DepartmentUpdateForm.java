/**
 * 
 */
package department.model.form;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author Nikolay
 *
 */
@Data
@Validated
public class DepartmentUpdateForm {
	private Integer id;
	private String name;
	private String phone;
}
