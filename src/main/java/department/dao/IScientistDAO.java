package department.dao;

import department.model.bo.Scientist;

import java.util.List;

/**
 * Created by mogo on 2/15/17.
 */
public interface IScientistDAO extends IDAOGeneric<Scientist> {

    List<Scientist> getScientistsByPaperId(int paperId);
}
