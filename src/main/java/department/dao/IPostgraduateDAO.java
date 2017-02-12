package department.dao;

import department.entity.*;

import java.util.List;

/**
 * Created by mogo on 2/12/17.
 */
public interface IPostgraduateDAO extends IDAOGeneric<Postgraduate>{

    List<Postgraduate> getPostgraduatesByDepertment(Department department);
    List<Postgraduate> getPostgraduatesByTeacher(Teacher teacher);
    List<Postgraduate> getPostgraduatesByTopic(Topic topic);
    List<Postgraduate> getPostgraduatesByPaper(Paper paper);

}
