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
public class PostgraduateCreateForm {
	private Integer id;
    private String name;
    private String phone;

    private String topic;
    private Date startDate;
    private Date endDate;
    
    private Date protectionDate;
    private Integer department;
    private Integer teacher;
}
