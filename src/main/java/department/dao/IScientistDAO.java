package department.dao;

import department.entity.Department;
import department.entity.Paper;
import department.entity.Scientist;
import department.entity.Topic;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IScientistDAO extends IDAOGeneric<Scientist>{

    List<Scientist> getScientistsByDepertment(Department department);
    List<Scientist> getScientistsByTopic(Topic topic);
    List<Scientist> getScientistsByPaper(Paper paper);

}
