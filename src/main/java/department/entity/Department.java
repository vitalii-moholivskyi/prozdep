package department.entity;

import lombok.Data;

@Data
public class Department {

    private int id;
    private String name;
    private String phone;

    public Department() {}
    public Department(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
