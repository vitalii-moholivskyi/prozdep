package department.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import department.entity.Department;
import department.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import util.DateUtil;

@Repository
public class TeacherDAO implements IDAOGeneric<Teacher>{

    private final static String FIND_ALL = "SELECT * " +
            "FROM teacher t " +
            "INNER JOIN scientist s ON t.scientist_id = s.id;";

    private final static String FIND = "SELECT * " +
            "FROM teacher t " +
            "INNER JOIN scientist s ON t.scientist_id = s.id " +
            "WHERE t.scientist_id=?;";

    private final static String EAGER_FIND = "SELECT " +
            "t.scientist_id AS teacher_id, " +
            "s.name AS teacher_name, " +
            "s.phone AS teacher_phone, " +
            "t.position AS teacher_position, " +
            "t.degree AS teacher_degree, " +
            "t.start_date AS teacher_start_date, " +
            "t.department_id AS department_id, " +
            "d.name AS department_name, " +
            "d.phone AS department_phone " +
            "FROM teacher t " +
            "INNER JOIN scientist s ON t.scientist_id = s.id " +
            "INNER JOIN department d ON t.department_id = d.id " +
            "WHERE t.scientist_id=?;";

    private final static String INSERT = "INSERT INTO teacher (" +
            "scientist_id, " +
            "position, " +
            "degree, " +
            "start_date, " +
            "department_id " +
            ") VALUES(?,?,?,?,?);";
    private final static String UPDATE = "UPDATE teacher " +
            "SET position = ?, degree = ?,  start_date = ?, department_id = ? " +
            "WHERE scientist_id = ?;";

    private final static String REMOVE = "DELETE FROM teacher WHERE scientist_id = ?;";

    private final TeacherMapper teacherMapper = new TeacherMapper();
    private final EagerTeacherMapper eagerTeacherMapper = new EagerTeacherMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(FIND_ALL, teacherMapper);
    }

    @Override
    public Teacher find(int scientistId) {
        List<Teacher> teachers = jdbcTemplate.query(FIND, new Object[]{ scientistId }, teacherMapper);
        return (teachers.isEmpty()) ? null : teachers.get(0);
    }

    @Override
    public Teacher find(int scientistId, boolean isEager) {
        if(isEager){
            List<Teacher> teachers = jdbcTemplate.query(EAGER_FIND, new Object[]{scientistId}, eagerTeacherMapper);
            return (teachers.isEmpty()) ? null : teachers.get(0);
        } else {
            return find(scientistId);
        }
    }

    public void insert(Teacher teacher) {
        Object [] values = {
                teacher.getId(),
                teacher.getPosition(),
                teacher.getDegree(),
                DateUtil.convertToSqlDate(teacher.getStartDate()),
                teacher.getDepartment().getId()
        };
        jdbcTemplate.update(INSERT, values);
    }

    public void update(Teacher teacher) {
        Object [] values = {
                teacher.getPosition(),
                teacher.getDegree(),
                DateUtil.convertToSqlDate(teacher.getStartDate()),
                teacher.getDepartment().getId(),
                teacher.getId()
        };
        jdbcTemplate.update(UPDATE, values);
    }

    public void remove(Teacher teacher) {
        jdbcTemplate.update(REMOVE, new Object[]{
                teacher.getId()
        });
    }

    private class TeacherMapper implements RowMapper<Teacher>{
        @Override
        public Teacher mapRow(ResultSet rs, int i) throws SQLException {
            Teacher teacher = new Teacher();
            teacher.setId(rs.getInt("scientist_id"));
            teacher.setName(rs.getString("name"));
            teacher.setPhone(rs.getString("phone"));
            teacher.setDepartment(new Department(rs.getInt("department_id")));
            teacher.setPosition(rs.getString("position"));
            teacher.setDegree(rs.getString("degree"));
            teacher.setStartDate(rs.getDate("start_date"));
            return teacher;
        }
    }

    private class EagerTeacherMapper implements RowMapper<Teacher>{
        @Override
        public Teacher mapRow(ResultSet rs, int i) throws SQLException {
            Teacher teacher = new Teacher();
            teacher.setId(rs.getInt("teacher_id"));
            teacher.setName(rs.getString("teacher_name"));
            teacher.setPhone(rs.getString("teacher_phone"));
            teacher.setDepartment(new Department(
                    rs.getInt("department_id"),
                    rs.getString("department_name"),
                    rs.getString("department_phone")));
            teacher.setPosition(rs.getString("teacher_position"));
            teacher.setDegree(rs.getString("teacher_degree"));
            teacher.setStartDate(rs.getDate("teacher_start_date"));
            return teacher;
        }
    }

	@Override
	public List<Teacher> findAll(long limit, long offset) {
		// TODO Auto-generated method stub
		return null;
	}
}