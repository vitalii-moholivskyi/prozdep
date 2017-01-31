package entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "department")
@Inheritance(strategy = InheritanceType.JOINED)
public class Scientist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;

    public Scientist() {}
    public Scientist(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
