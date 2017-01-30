package entity;

import lombok.Data;

import java.util.Date;

@Data
public class Master extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;

    public Master() {}
    public Master(int id, String name, String phone, String topic, Date startDate, Date endDate) {
        super(id, name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
