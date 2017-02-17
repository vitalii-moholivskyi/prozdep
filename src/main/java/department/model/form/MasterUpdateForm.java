package department.model.form;

import java.util.Date;

import department.model.bo.Department;
import department.model.bo.Teacher;
import department.model.bo.Master;
import department.model.bo.Master.MasterBuilder;
import lombok.Data;
import lombok.Value;

@Data
public class MasterUpdateForm {
	private Integer id;
    private String name;
    private String phone;
	
	private String topic;
    private Date startDate;
    private Date endDate;
    private Integer teacher;
    private Integer department;
}
