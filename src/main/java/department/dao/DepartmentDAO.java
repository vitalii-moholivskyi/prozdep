package department.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import department.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDAO implements IDAOGeneric<Department> {

	private final static String FIND_ALL = "SELECT * FROM department;";
	private final static String FIND = "SELECT * FROM department WHERE id=?;";
	private final static String INSERT = "INSERT INTO department (id, name, phone) VALUES(DEFAULT,?,?);";
	private final static String UPDATE = "UPDATE department SET name=?, phone=? WHERE id = ?;";
	private final static String REMOVE = "DELETE FROM department WHERE id=?;";

	private final DepartmentMapper departmentMapper = new DepartmentMapper();
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Department> findAll() {
		return jdbcTemplate.query(FIND_ALL, departmentMapper);
	}

	@Override
	public Department find(int id) {
		List<Department> departments = jdbcTemplate.query(FIND, new Object[]{id}, departmentMapper);
		return (departments.isEmpty()) ? null : departments.get(0);
	}

	@Override
	public Department find(int id, boolean isEager) {
		return find(id);
	}

	@Override
	public void insert(Department dep) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT, new String[] {"id"});
			ps.setString(1, dep.getName());
			ps.setString(2, dep.getPhone());
			return ps;
		},keyHolder);
		dep.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Department dep) {
		Object [] values = {
				dep.getName(),
				dep.getPhone(),
				dep.getId()
		};
		jdbcTemplate.update(UPDATE, values);
	}

	@Override
	public void remove(Department dep) {
		jdbcTemplate.update(REMOVE, dep.getId());
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