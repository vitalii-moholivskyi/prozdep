package entity;

import lombok.Data;

import java.util.Date;

@Data
public class Teacher extends Scientist{

    private String position;
    private String degree;
    private Date startDate;

    public Teacher() {}
    public Teacher(int id, String name, String phone, String position, String degree, Date startDate) {
        super(id, name, phone);
        this.position = position;
        this.degree = degree;
        this.startDate = startDate;
    }
}
