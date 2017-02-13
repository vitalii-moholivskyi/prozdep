package department.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import department.entity.Department;
import department.entity.Postgraduate;
import department.entity.Teacher;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import util.DateUtil;

@Repository
public class PostgraduateDAO implements IDAOGeneric<Postgraduate>{

    private final static String FIND_ALL = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id;";

    private final static String FIND = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE p.scientist_id=?;";

    private final static String EAGER_FIND = "SELECT " +
            "p.scientist_id AS postgraduate_id, " +
            "ps.name AS postgraduate_name, " +
            "ps.phone AS postgraduate_phone, " +
            "p.topic AS postgraduate_topic, " +
            "p.start_date AS postgraduate_start_date, " +
            "p.end_date AS postgraduate_end_date, " +
            "p.protection_date AS postgraduate_protection_date, " +
            "p.teacher_id AS teacher_id, " +
            "ts.name AS teacher_name, " +
            "ts.phone AS teacher_phone, " +
            "t.position AS teacher_position, " +
            "t.degree AS teacher_degree, " +
            "t.start_date AS teacher_start_date, " +
            "t.department_id AS teacher_department_id, " +
            "p.department_id AS department_id, " +
            "d.name AS department_name, " +
            "d.phone AS department_phone " +
            "FROM postgraduate p " +
            "INNER JOIN scientist ps ON p.scientist_id = ps.id " +
            "INNER JOIN teacher t ON p.teacher_id = t.scientist_id " +
            "INNER JOIN scientist ts ON t.scientist_id = ts.id " +
            "INNER JOIN department d ON p.department_id = d.id " +
            "WHERE p.scientist_id=?;";

    private final static String INSERT = "INSERT INTO postgraduate (" +
            "scientist_id, " +
            "topic, " +
            "start_date, " +
            "end_date, " +
            "protection_date, " +
            "teacher_id, " +
            "department_id " +
            ") VALUES(?,?,?,?,?,?,?);";
    private final static String UPDATE = "UPDATE postgraduate " +
            "SET topic = ?, start_date = ?,  end_date = ?, protection_date = ?, teacher_id = ?, department_id = ? " +
            "WHERE scientist_id = ?;";

    private final static String REMOVE = "DELETE FROM postgraduate WHERE scientist_id = ?;";

    private final PostgraduateMapper postgraduateMapper = new PostgraduateMapper();
    private final EagerPostgraduateMapper eagerPostgraduateMapper = new EagerPostgraduateMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Postgraduate> findAll() {
        return jdbcTemplate.query(FIND_ALL, postgraduateMapper);
    }

    @Override
    public Postgraduate find(int scientistId) {
        List<Postgraduate> postgraduates = jdbcTemplate.query(FIND, new Object[]{ scientistId }, postgraduateMapper);
        return (postgraduates.isEmpty()) ? null : postgraduates.get(0);
    }

    @Override
    public Postgraduate find(int scientistId, boolean isEager) {
        if(isEager){
            List<Postgraduate> postgraduates = jdbcTemplate.query(EAGER_FIND, new Object[]{scientistId}, eagerPostgraduateMapper);
            System.out.println(postgraduates);
            return (postgraduates.isEmpty()) ? null : postgraduates.get(0);
        } else {
            return find(scientistId);
        }
    }

    public void insert(Postgraduate postgraduate) {
        Object [] values = {
                postgraduate.getId(),
                postgraduate.getTopic(),
                DateUtil.convertToSqlDate(postgraduate.getStartDate()),
                DateUtil.convertToSqlDate(postgraduate.getEndDate()),
                DateUtil.convertToSqlDate(postgraduate.getProtectionDate()),
                postgraduate.getTeacher().getId(),
                postgraduate.getDepartment().getId()
        };
        jdbcTemplate.update(INSERT, values);
    }

    public void update(Postgraduate postgraduate) {
        Object [] values = {
                postgraduate.getTopic(),
                DateUtil.convertToSqlDate(postgraduate.getStartDate()),
                DateUtil.convertToSqlDate(postgraduate.getEndDate()),
                DateUtil.convertToSqlDate(postgraduate.getProtectionDate()),
                postgraduate.getTeacher().getId(),
                postgraduate.getDepartment().getId(),
                postgraduate.getId()
        };
        jdbcTemplate.update(UPDATE, values);
    }

    public void remove(Postgraduate postgraduate) {
        jdbcTemplate.update(REMOVE, new Object[]{
                postgraduate.getId()
        });
    }

    private final class PostgraduateMapper implements RowMapper<Postgraduate> {
        @Override
        public Postgraduate mapRow(ResultSet rs, int i) throws SQLException {
            Postgraduate postgraduate = new Postgraduate();
            postgraduate.setId(rs.getInt("scientist_id"));
            postgraduate.setName(rs.getString("name"));
            postgraduate.setPhone(rs.getString("phone"));
            postgraduate.setTopic(rs.getString("topic"));
            postgraduate.setStartDate(rs.getDate("start_date"));
            postgraduate.setEndDate(rs.getDate("end_date"));
            postgraduate.setProtectionDate(rs.getDate("protection_date"));
            postgraduate.setTeacher(new Teacher(rs.getInt("teacher_id")));
            postgraduate.setDepartment(new Department(rs.getInt("department_id")));
            return postgraduate;
        }
    }

    private final class EagerPostgraduateMapper implements RowMapper<Postgraduate> {

        @Override
        public Postgraduate mapRow(ResultSet rs, int i) throws SQLException {
            Postgraduate postgraduate = new Postgraduate();
            postgraduate.setId(rs.getInt("postgraduate_id"));
            postgraduate.setName(rs.getString("postgraduate_name"));
            postgraduate.setPhone(rs.getString("postgraduate_phone"));
            postgraduate.setTopic(rs.getString("postgraduate_topic"));
            postgraduate.setStartDate(rs.getDate("postgraduate_start_date"));
            postgraduate.setEndDate(rs.getDate("postgraduate_end_date"));
            postgraduate.setProtectionDate(rs.getDate("postgraduate_protection_date"));

            postgraduate.setTeacher(new Teacher(
                    rs.getInt("teacher_id"),
                    rs.getString("teacher_name"),
                    rs.getString("teacher_phone"),
                    rs.getString("teacher_position"),
                    rs.getString("teacher_degree"),
                    rs.getDate("teacher_start_date"),
                    new Department(
                            rs.getInt(rs.getInt("teacher_department_id"))
                    )
            ));

            postgraduate.setDepartment(new Department(
                    rs.getInt("department_id"),
                    rs.getString("department_name"),
                    rs.getString("department_phone")));

            return postgraduate;
        }
    }

}