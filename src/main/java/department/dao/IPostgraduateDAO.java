package department.dao;

import department.model.bo.*;
import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IPostgraduateDAO extends IDAOGeneric<Postgraduate>{

    List<Postgraduate> getPostgraduatesByDepertmentId(int departmentId);
    List<Postgraduate> getPostgraduatesByTeacherId(int teacherId);
    List<Postgraduate> getPostgraduatesByTopicId(int topicId);
    List<Postgraduate> getPostgraduatesByPaperId(int paperId);

    List<Postgraduate> getPostgraduatesByDepertment(int departmentId, boolean isEager);
    List<Postgraduate> getPostgraduatesByTeacher(int teacherId, boolean isEager);
    List<Postgraduate> getPostgraduatesByTopic(int topicId, boolean isEager);
    List<Postgraduate> getPostgraduatesByPaper(int paperId, boolean isEager);
}
