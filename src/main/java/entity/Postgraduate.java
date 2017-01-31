package entity;

import lombok.Data;

import java.util.Date;

@Data
public class Postgraduate extends Scientist{

    private String topic;
    private Date startDate;
    private Date endDate;
    private Date protectionDate;

    public Postgraduate() {}
    public Postgraduate(String name, String phone, int departmentId, String topic, Date startDate, Date endDate, Date protectionDate) {
        super(name, phone);
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.protectionDate = protectionDate;
    }
}
