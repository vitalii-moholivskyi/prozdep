package department.dao;

import department.model.bo.Topic;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface ITopicDAO extends IDAOGeneric<Topic> {

    List<Topic> getTopicsByChiefScientistId(int scientistId);
    List<Topic> getTopicsByDepartmentId(int departmentId);
    List<Topic> getTopicsByScientistId(int scientistId);
    List<Topic> getTopicsByPapperId(int paperId);

    List<Topic> getTopicsByChiefScientistId(int scientistId, boolean isEager);
    List<Topic> getTopicsByDepartmentId(int departmentId, boolean isEager);
    List<Topic> getTopicsByScientistId(int scientistId, boolean isEager);
    List<Topic> getTopicsByPapperId(int paperId, boolean isEager);
}
