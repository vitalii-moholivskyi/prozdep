package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Teacher extends Scientist{

    private String position;
    private String degree;
    private Date startDate;
    private Department department;

    public Teacher() {}
    public Teacher(int id) { super(id); }
    public Teacher(int id, String name, String phone, String position, String degree, Date startDate, Department department) {
        super(id, name, phone);
        this.position = position;
        this.degree = degree;
        this.startDate = startDate;
        this.department = department;
    }
    public Teacher(int id, String position, String degree, Date startDate, Department department) {
        super(id);
        this.position = position;
        this.degree = degree;
        this.startDate = startDate;
        this.department = department;
    }
    public Teacher(String name, String phone, String position, String degree, Date startDate, Department department) {
        super(name, phone);
        this.position = position;
        this.degree = degree;
        this.startDate = startDate;
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() + " & Teacher(" +
                "position='" + position + '\'' +
                ", degree='" + degree + '\'' +
                ", startDate=" + startDate +
                ", department=" + department +
                ')';
    }
}
