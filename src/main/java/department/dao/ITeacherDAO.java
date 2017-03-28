package department.dao;

import department.model.bo.Teacher;

import java.util.Date;
import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface ITeacherDAO extends IDAOGeneric<Teacher>{

    List<Teacher> getTeachersByDepartmentId(int departmentId);
    List<Teacher> getTeachersByTopicId(int topicId);
    List<Teacher> getTeachersByPaperId(int paperId);
    int count(Date startDate);
    List<Teacher> findAll(Date startDate);
    List<Teacher> findAll(Date startDate, long limit, long offset);

    List<Teacher> getTeachersByDepartmentId(int departmentId, boolean isEager);
    List<Teacher> getTeachersByTopicId(int topicId, boolean isEager);
    List<Teacher> getTeachersByPaperId(int paperId, boolean isEager);

}
