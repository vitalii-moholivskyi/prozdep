package department.dao;

import department.entity.Department;
import department.entity.Paper;
import department.entity.Scientist;
import department.entity.Topic;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IPaperDAO extends IDAOGeneric<Paper>{

    List<Paper> getPapersByDepartment(Department department);
    List<Paper> getPapersByScientist(Scientist scientist);
    List<Paper> getPapersByTopic(Topic topic);

}
