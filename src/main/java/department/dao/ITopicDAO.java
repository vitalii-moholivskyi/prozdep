package department.dao;
import department.entity.Department;
import department.entity.Paper;
import department.entity.Scientist;
import department.entity.Topic;
import javafx.scene.Scene;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface ITopicDAO extends IDAOGeneric<Topic> {

    List<Topic> getTopicsByChiefScientist(Scientist scientist);
    List<Topic> getTopicsByDepartment(Department department);
    List<Topic> getTopicsByScientist(Scientist scientist);
    List<Topic> getTopicsByPapper(Paper paper);
}
