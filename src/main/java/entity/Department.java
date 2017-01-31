package entity;

import lombok.Data;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.Entity;

@Data
@Entity(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;

    public Department() {}
    public Department(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
