package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Master extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;
    private Teacher teacher;
    private Department department;

    public Master() {}
    public Master(int id) { super(id); }
    public Master(int id, String topic, Date startDate, Date endDate, Teacher teacher, Department department) {
        super(id);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.department = department;
    }

    public Master(String name, String phone, String topic, Date startDate, Date endDate, Teacher teacher, Department department) {
        super(name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.department = department;
    }
    public Master(int id, String name, String phone, String topic, Date startDate, Date endDate, Teacher teacher, Department department) {
        super(id, name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + "& Master{" +
                "topic='" + topic + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", department=" + department +
                ", teacher=" + teacher +
                '}';
    }
}
