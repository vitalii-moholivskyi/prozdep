package department.dao;

import department.model.bo.Scientist;

import java.util.Collection;
import java.util.List;

/**
 * Created by mogo on 2/15/17.
 */
public interface IScientistDAO extends IDAOGeneric<Scientist> {

    List<Scientist> getScientistsByPaperId(int paperId);

	/**
	 * @param paperId
	 * @param limit
	 * @param offset
	 * @return
	 */
	Collection<? extends Scientist> getScientistsByPaperId(int paperId, int limit, int offset);
}
