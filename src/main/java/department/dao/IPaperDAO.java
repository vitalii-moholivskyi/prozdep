package department.dao;

import department.model.bo.Department;
import department.model.bo.Paper;
import department.model.bo.Topic;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IPaperDAO extends IDAOGeneric<Paper>{

    List<Paper> getPapersByDepartmentId(int departmentId, boolean isEager);
    List<Paper> getPapersByScientistId(int scientistId, boolean isEager);
    List<Paper> getPapersByTopicId(int topicId, boolean isEager);

    List<Paper> getPapersByDepartmentId(int departmentId);
    List<Paper> getPapersByScientistId(int scientistId);
    List<Paper> getPapersByTopicId(int topicId);

}
