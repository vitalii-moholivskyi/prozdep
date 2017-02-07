package department.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Topic {

    private Integer id;
    private String name;
    private String cliect;
    private Date startDate;
    private Date endDate;
    private Department department;
    private Teacher chiefScientist;

    public Topic() {}
    public Topic(Integer id) { this.id = id; }
    public Topic(String name, String cliect, Date startDate, Date endDate, Department department, Teacher chiefScientist) {
        this.name = name;
        this.cliect = cliect;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.chiefScientist = chiefScientist;
    }
}