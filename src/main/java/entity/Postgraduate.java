package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class Postgraduate extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;
    private Date protectionDate;
    private Department department;
    private Teacher teacher;

    public Postgraduate() {}
    public Postgraduate(String name, String phone, int departmentId, String topic, Date startDate, Date endDate, Date protectionDate , Department department, Teacher teacher) {
        super(name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.protectionDate = protectionDate;
        this.department = department;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return super.toString() + " & Postgraduate{" +
                "topic='" + topic + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", protectionDate=" + protectionDate +
                ", department=" + department +
                ", teacher=" + teacher +
                '}';
    }
}
