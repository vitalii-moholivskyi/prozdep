package department.dao;

import department.model.bo.Topic;

import java.util.Date;
import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface ITopicDAO extends IDAOGeneric<Topic> {

    List<Topic> getTopicsByChiefScientistId(int scientistId);
    List<Topic> getTopicsByDepartmentId(int departmentId);
    List<Topic> getTopicsByScientistId(int scientistId);
    List<Topic> getTopicsByPaperId(int paperId);
    int count(Date startDate, Date endDate);
    List<Topic> findAll(Date startDate, Date endDate);
    List<Topic> findAll(Date startDate, Date endDate, long limit, long offset);

    List<Topic> getTopicsByChiefScientistId(int scientistId, boolean isEager);
    List<Topic> getTopicsByDepartmentId(int departmentId, boolean isEager);
    List<Topic> getTopicsByScientistId(int scientistId, boolean isEager);
    List<Topic> getTopicsByPaperId(int paperId, boolean isEager);

}
