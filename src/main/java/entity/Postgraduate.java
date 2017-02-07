package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class Postgraduate extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;
    private Date protectionDate;
    private Department department;
    private Teacher teacher;

    public Postgraduate() {}
    public Postgraduate(int id, String topic, Date startDate, Date endDate, Date protectionDate , Teacher teacher,  Department department) {
        super(id);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.protectionDate = protectionDate;
        this.department = department;
        this.teacher = teacher;
    }
    public Postgraduate(String name, String phone, String topic, Date startDate, Date endDate, Date protectionDate , Teacher teacher, Department department) {
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
