package department.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Department {

    private Integer id;
    private String name;
    private String phone;

    public Department() {}
    public Department(Integer id) { this.id=id; }
    public Department(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}