package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Master extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;
    private Department department;
    private Teacher teacher;

    public Master() {}
    public Master(String name, String phone, String topic, Date startDate, Date endDate, Department department, Teacher teacher) {
        super(name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.teacher = teacher;
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
