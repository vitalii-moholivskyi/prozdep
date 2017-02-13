package department.dao;

import department.entity.Department;
import department.entity.Paper;
import department.entity.Master;
import department.entity.Teacher;
import department.entity.Topic;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IMasterDAO extends IDAOGeneric<Master>{

    List<Master> getMastersByDepertment(Department department);
    List<Master> getMastersByTeacher(Teacher teacher);
    List<Master> getMastersByTopic(Topic topic);
    List<Master> getMastersByPaper(Paper paper);

}
