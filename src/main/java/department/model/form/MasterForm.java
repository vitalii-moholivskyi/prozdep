package department.model.form;

import java.util.Date;

import department.model.bo.Department;
import department.model.bo.Teacher;
import department.model.bo.Master;
import department.model.bo.Master.MasterBuilder;
import lombok.Data;
import lombok.Value;

/**
 * Created by Максим on 2/1/2017.
 */
@Data
public class MasterForm {
    private String name;
    private String phone;
	
	private String topic;
    private Date startDate;
    private Date endDate;
    private Teacher teacher;
    private Department department;
}
