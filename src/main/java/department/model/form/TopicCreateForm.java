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
public class TopicCreateForm {
    private String name;
    private String client;
    private Date startDate;
    private Date endDate;
    private Integer department;
    private Integer chiefScientist;
}
