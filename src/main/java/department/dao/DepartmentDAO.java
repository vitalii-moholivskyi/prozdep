package department.dao;

import department.entity.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentDAO implements IDAOGeneric<Department> {

	private final static String SELECT_ALL = "SELECT * FROM department";

	private final DepartmentMapper departmentMapper = new DepartmentMapper();
	//@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Department> findAll() {
		return jdbcTemplate.query(SELECT_ALL, departmentMapper);
	}

	@Override
	public Department find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department insert(Department dep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department update(Department dep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Department dep) {
		// TODO Auto-generated method stub
		
	}

	private class DepartmentMapper implements RowMapper<Department>{

		@Override
		public Department mapRow(ResultSet rs, int i) throws SQLException {
			Department department = new Department();
			department.setId(rs.getInt("id"));
			department.setName(rs.getString("name"));
			department.setPhone(rs.getString("phone"));
			return department;
		}
	}
	

}
