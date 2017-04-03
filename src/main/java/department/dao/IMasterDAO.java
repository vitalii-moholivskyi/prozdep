package department.dao;

import department.model.bo.Master;

import java.util.Date;
import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IMasterDAO extends IDAOGeneric<Master>{

    List<Master> getMastersByDepartmentId(int departmentId, boolean isEager);
    List<Master> getMastersByTeacherId(int teacherId, boolean isEager);
    List<Master> getMastersByTopicId(int topicId, boolean isEager);
    List<Master> getMastersByPaperId(int paperId, boolean isEager);
    int count(Date startDate, Date endDate);
    List<Master> findAll(Date startDate, Date endDate);
    List<Master> findAll(Date startDate, Date endDate, long limit, long offset);



    List<Master> getMastersByDepartmentId(int departmentId);
    List<Master> getMastersByTeacherId(int teacherId);
    List<Master> getMastersByTopicId(int topicId);
    List<Master> getMastersByPaperId(int paperId);

}
