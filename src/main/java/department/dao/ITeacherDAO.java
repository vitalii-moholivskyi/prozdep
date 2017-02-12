package department.dao;

import department.entity.Department;
import department.entity.Paper;
import department.entity.Teacher;
import department.entity.Topic;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface ITeacherDAO extends IDAOGeneric<Teacher>{

    List<Teacher> getTeachersByDepertment(Department department);
    List<Teacher> getTeachersByTopic(Topic topic);
    List<Teacher> getTeachersByPaper(Paper paper);

}
