package department.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import department.entity.Department;
import department.entity.Master;
import department.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import util.DateUtil;

@Repository
public class MasterDAO implements IDAOGeneric<Master>{

    private final static String FIND_ALL = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id;";

    private final static String FIND = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE m.scientist_id=?;";

    private final static String EAGER_FIND = "SELECT " +
            "m.scientist_id AS master_id, " +
            "ms.name AS master_name, " +
            "ms.phone AS master_phone, " +
            "m.topic AS master_topic, " +
            "m.start_date AS master_start_date, " +
            "m.end_date AS master_end_date, " +
            "m.teacher_id AS teacher_id, " +
            "ts.name AS teacher_name, " +
            "ts.phone AS teacher_phone, " +
            "t.position AS teacher_position, " +
            "t.degree AS teacher_degree, " +
            "t.start_date AS teacher_start_date, " +
            "t.department_id AS teacher_department_id, " +
            "m.department_id AS department_id, " +
            "d.name AS department_name, " +
            "d.phone AS department_phone " +
            "FROM master m " +
            "INNER JOIN scientist ms ON m.scientist_id = ms.id " +
            "INNER JOIN teacher t ON m.teacher_id = t.scientist_id " +
            "INNER JOIN scientist ts ON t.scientist_id = ts.id " +
            "INNER JOIN department d ON m.department_id = d.id " +
            "WHERE m.scientist_id=?;";

    private final static String INSERT = "INSERT INTO master (" +
            "scientist_id, " +
            "topic, " +
            "start_date, " +
            "end_date, " +
            "teacher_id, " +
            "department_id " +
            ") VALUES(?,?,?,?,?,?);";
    private final static String UPDATE = "UPDATE master " +
            "SET topic = ?, start_date = ?,  end_date = ?, teacher_id = ?, department_id = ? " +
            "WHERE scientist_id = ?;";

    private final static String REMOVE = "DELETE FROM master WHERE scientist_id = ?;";

    private final MasterMapper masterMapper = new MasterMapper();
    private final EagerMasterMapper eagerMasterMapper = new EagerMasterMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Master> findAll() {
        return jdbcTemplate.query(FIND_ALL, masterMapper);
    }

    @Override
    public Master find(int scientistId) {
        List<Master> masters = jdbcTemplate.query(FIND, new Object[]{ scientistId }, masterMapper);
        return (masters.isEmpty()) ? null : masters.get(0);
    }

    @Override
    public Master find(int scientistId, boolean isEager) {
        if(isEager){
            List<Master> masters = jdbcTemplate.query(EAGER_FIND, new Object[]{scientistId}, eagerMasterMapper);
            System.out.println(masters);
            return (masters.isEmpty()) ? null : masters.get(0);
        } else {
            return find(scientistId);
        }
    }

    public void insert(Master master) {
        Object [] values = {
                master.getId(),
                master.getTopic(),
                DateUtil.convertToSqlDate(master.getStartDate()),
                DateUtil.convertToSqlDate(master.getEndDate()),
                master.getTeacher().getId(),
                master.getDepartment().getId()
        };
        jdbcTemplate.update(INSERT, values);
    }

    public void update(Master master) {
        Object [] values = {
                master.getTopic(),
                DateUtil.convertToSqlDate(master.getStartDate()),
                DateUtil.convertToSqlDate(master.getEndDate()),
                master.getTeacher().getId(),
                master.getDepartment().getId(),
                master.getId()
        };
        jdbcTemplate.update(UPDATE, values);
    }

    public void remove(Master master) {
        jdbcTemplate.update(REMOVE, new Object[]{
                master.getId()
        });
    }

    private final class MasterMapper implements RowMapper<Master>{
        @Override
        public Master mapRow(ResultSet rs, int i) throws SQLException {
            Master master = new Master();
            master.setId(rs.getInt("scientist_id"));
            master.setName(rs.getString("name"));
            master.setPhone(rs.getString("phone"));
            master.setTopic(rs.getString("topic"));
            master.setStartDate(rs.getDate("start_date"));
            master.setEndDate(rs.getDate("end_date"));
            master.setTeacher(new Teacher(rs.getInt("teacher_id")));
            master.setDepartment(new Department(rs.getInt("department_id")));
            return master;
        }
    }

    private final class EagerMasterMapper implements RowMapper<Master>{
        @Override
        public Master mapRow(ResultSet rs, int i) throws SQLException {
            Master master = new Master();
            master.setId(rs.getInt("master_id"));
            master.setName(rs.getString("master_name"));
            master.setPhone(rs.getString("master_phone"));
            master.setTopic(rs.getString("master_topic"));
            master.setStartDate(rs.getDate("master_start_date"));
            master.setEndDate(rs.getDate("master_end_date"));

            master.setTeacher(new Teacher(
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

            master.setDepartment(new Department(
                    rs.getInt("department_id"),
                    rs.getString("department_name"),
                    rs.getString("department_phone")));

            return master;
        }
    }



}