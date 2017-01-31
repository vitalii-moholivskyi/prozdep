package department.entity;

import lombok.Data;

@Data
public class Scientist {

    private int id;
    private String name;
    private String phone;

    public Scientist() {}
    public Scientist(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
