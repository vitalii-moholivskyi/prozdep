package dao;

import java.util.List;

import entity.Department;
import entity.Master;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class MasterDAO extends Dao<Master> implements IDAOGeneric<Master> {

    public MasterDAO() { super(Master.class); }

}
