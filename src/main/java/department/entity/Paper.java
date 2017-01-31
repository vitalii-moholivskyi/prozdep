package department.entity;

import lombok.Data;

@Data
public class Paper {

    private int id;
    private String name;
    private String type;
    private int year;

    public Paper() {}
    public Paper(int id, String name, String type, int year) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.year = year;
    }
}
