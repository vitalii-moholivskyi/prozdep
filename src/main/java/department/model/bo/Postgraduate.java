package department.model.bo;

import lombok.Value;
import java.util.Date;

@Value
@lombok.ToString(callSuper = true)
public class Postgraduate extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;
    private Date protectionDate;
    private Teacher teacher;
    private Department department;

    @lombok.Builder(toBuilder = true)
    private Postgraduate(int id, String name, String phone, String topic, Date startDate, Date endDate, Date protectionDate, Teacher teacher, Department department){
        super(id, name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.protectionDate = protectionDate;
        this.teacher = teacher;
        this.department = department;
    }
    /*workaround to make lombok builder work for inheritance*/
    public static class PostgraduateBuilder extends ScientistBuilder {
        PostgraduateBuilder() {
            super();
        }
    }
}