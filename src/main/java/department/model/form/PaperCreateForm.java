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
public class PaperCreateForm {
    private String name;
    private String type;
    private Integer year;
    private Integer supervisor;
    private Integer executor;
}
