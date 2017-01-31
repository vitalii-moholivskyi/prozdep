package entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "master")
@PrimaryKeyJoinColumn(name = "scientist_id")
public class Master extends Scientist{

    private String topic;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "department_id")
    private Department department;

    public Master() {}
    public Master(String name, String phone, String topic, Date startDate, Date endDate, Department department) {
        super(name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
    }
}
