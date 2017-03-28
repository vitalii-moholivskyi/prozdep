package department.dao;

import department.model.bo.Postgraduate;

import java.util.Date;
import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IPostgraduateDAO extends IDAOGeneric<Postgraduate>{

    List<Postgraduate> getPostgraduatesByDepartmentId(int departmentId);
    List<Postgraduate> getPostgraduatesByTeacherId(int teacherId);
    List<Postgraduate> getPostgraduatesByTopicId(int topicId);
    List<Postgraduate> getPostgraduatesByPaperId(int paperId);
    List<Postgraduate> getPostgraduatesByProtectionDate(Date protectionDate);
    int count(Date startDate, Date endDate);
    List<Postgraduate> findAll(Date startDate, Date endDate);
    List<Postgraduate> findAll(Date startDate, Date endDate, long limit, long offset);

    List<Postgraduate> getPostgraduatesByDepertmentId(int departmentId, boolean isEager);
    List<Postgraduate> getPostgraduatesByTeacherId(int teacherId, boolean isEager);
    List<Postgraduate> getPostgraduatesByTopicId(int topicId, boolean isEager);
    List<Postgraduate> getPostgraduatesByPaperId(int paperId, boolean isEager);
    List<Postgraduate> getPostgraduatesByProtectionDate(Date protectionDate, boolean isEager);
}
