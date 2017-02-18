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
public class ScientistUpdateForm {
	private Integer id;
    private String name;
    private String phone;
}
