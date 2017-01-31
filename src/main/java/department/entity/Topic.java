package department.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Topic {

    private int id;
    private String name;
    private String cliect;
    private Date startDate;
    private Date endDate;

    public Topic() {}
    public Topic(int id, String name, String cliect, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.cliect = cliect;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
