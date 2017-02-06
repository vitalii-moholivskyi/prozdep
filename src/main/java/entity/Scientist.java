package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Scientist {

    private Integer id;
    private String name;
    private String phone;

    public Scientist() {}
    public Scientist(Integer id) { this.id = id; }
    public Scientist(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
