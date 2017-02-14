package department.dao;

import department.model.bo.Master;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IMasterDAO extends IDAOGeneric<Master>{

    List<Master> findMastersByDepartmentId(int departmentId, boolean isEager);
    List<Master> findMastersByTeacherId(int teacherId, boolean isEager);
    List<Master> findMastersByTopicId(int topicId, boolean isEager);
    List<Master> findMastersByPaperId(int paperId, boolean isEager);

    List<Master> findMastersByDepartmentId(int departmentId);
    List<Master> findMastersByTeacherId(int teacherId);
    List<Master> findMastersByTopicId(int topicId);
    List<Master> findMastersByPaperId(int paperId);

}
