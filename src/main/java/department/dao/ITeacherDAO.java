package department.dao;

import department.model.bo.Teacher;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface ITeacherDAO extends IDAOGeneric<Teacher>{

    List<Teacher> getTeachersByDepertmentId(int department);
    List<Teacher> getTeachersByTopicId(int topic);
    List<Teacher> getTeachersByPaperId(int paper);

    List<Teacher> getTeachersByDepertmentId(int department, boolean isEager);
    List<Teacher> getTeachersByTopicId(int topic, boolean isEager);
    List<Teacher> getTeachersByPaperId(int paper, boolean isEager);

}
