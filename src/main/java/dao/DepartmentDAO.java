package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DepartmentDAO extends Dao<Department> implements IDAOGeneric<Department> {

	public DepartmentDAO() { super(Department.class); }

}
