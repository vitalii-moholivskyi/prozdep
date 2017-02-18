package department.model.bo;

import lombok.Builder;
import lombok.Value;
import java.util.Date;

@Value
@lombok.ToString(callSuper = true)
public class Master extends Scientist{

    String topic;
    Date startDate;
    Date endDate;
    Teacher teacher;
    Department department;

    @lombok.Builder(toBuilder = true)
    private Master(int id, String name, String phone, String topic, Date startDate, Date endDate, Teacher teacher, Department department){
        super(id, name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.department = department;
    }
    /*workaround to make lombok builder work for inheritance*/
    public static class MasterBuilder extends ScientistBuilder {
        MasterBuilder() {
            super();
        }
    }
}
