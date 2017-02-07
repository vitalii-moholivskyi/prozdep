package department.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Paper {

    private Integer id;
    private String name;
    private String type;
    private int year;

    public Paper() {}
    public Paper(Integer id) { this.id = id; }
    public Paper(String name, String type, int year) {
        this.name = name;
        this.type = type;
        this.year = year;
    }
}