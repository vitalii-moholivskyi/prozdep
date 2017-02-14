package department.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import department.model.bo.Paper;
import department.model.bo.Topic;
import department.model.bo.Department;
import department.model.bo.Master;
import department.model.bo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import util.DateUtil;

@Repository
public class MasterDAO implements IMasterDAO{

    private final static String FIND_ALL = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id;";

    private final static String FIND = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE m.scientist_id=?;";

    private final static String EAGER_FIND_SELECT_PART = "SELECT " +
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
            "d.phone AS department_phone ";

    private final static String EAGER_FIND = EAGER_FIND_SELECT_PART +
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

    private final static String FIND_BY_DEPARTMENT = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE m.department_id=?;";

    private final static String EAGER_FIND_BY_DEPARTMENT = EAGER_FIND_SELECT_PART +
            "FROM master m " +
            "INNER JOIN scientist ms ON m.scientist_id = ms.id " +
            "INNER JOIN teacher t ON m.teacher_id = t.scientist_id " +
            "INNER JOIN scientist ts ON t.scientist_id = ts.id " +
            "INNER JOIN department d ON m.department_id = d.id " +
            "WHERE m.scientist_id=?;";

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
        return find(scientistId, false);
    }

    @Override
    public Master find(int scientistId, boolean isEager) {
        List<Master> masters;
        if(isEager){
            masters = jdbcTemplate.query(EAGER_FIND, new Object[]{ scientistId }, eagerMasterMapper);
        } else {
            masters = jdbcTemplate.query(FIND, new Object[]{ scientistId }, masterMapper);
        }
        return (masters.isEmpty()) ? null : masters.get(0);
    }

    public Master insert(Master master) {
        Object [] values = {
                master.getId(),
                master.getTopic(),
                DateUtil.convertToSqlDate(master.getStartDate()),
                DateUtil.convertToSqlDate(master.getEndDate()),
                master.getTeacher().getId(),
                master.getDepartment().getId()
        };
        jdbcTemplate.update(INSERT, values);
        return master;
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

    @Override
    public List<Master> findMastersByDepartmentId(int departmentId, boolean isEager) {
        List<Master> masters;
        if(isEager){
            masters = jdbcTemplate.query(EAGER_FIND_BY_DEPARTMENT, new Object[]{ departmentId }, eagerMasterMapper);
        } else {
            masters = jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{ departmentId }, masterMapper);
        }
        return masters;
    }

    @Override
    public List<Master> findMastersByTeacherId(int teacherId, boolean isEager) {
        return null;
    }

    @Override
    public List<Master> findMastersByTopicId(int topicId, boolean isEager) {
        return null;
    }

    @Override
    public List<Master> findMastersByPaperId(int paperId, boolean isEager) {
        return null;
    }

    @Override
    public List<Master> findMastersByDepartmentId(int departmentId) {
        return null;
    }

    @Override
    public List<Master> findMastersByTeacherId(int teacherId) {
        return null;
    }

    @Override
    public List<Master> findMastersByTopicId(int topicId) {
        return null;
    }

    @Override
    public List<Master> findMastersByPaperId(int paperId) {
        return null;
    }


    private final class MasterMapper implements RowMapper<Master> {
        @Override
        public Master mapRow(ResultSet rs, int i) throws SQLException {
            return Master
                    .builder()
                        .id(rs.getInt("scientist_id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .topic(rs.getString("topic"))
                        .startDate(rs.getDate("start_date"))
                        .endDate(rs.getDate("end_date"))
                        .teacher(Teacher
                                .builder()
                                    .id(rs.getInt("teacher_id"))
                                .build())
                        .department(Department
                            .builder()
                                .id(rs.getInt("department_id"))
                            .build())
                    .build();
        }
    }

    private final class EagerMasterMapper implements RowMapper<Master> {
		@Override
		public Master mapRow(ResultSet rs, int i) throws SQLException {
			return Master
                    .builder()
                        .id(rs.getInt("master_id"))
                        .name(rs.getString("master_name"))
					    .phone(rs.getString("master_phone"))
                        .topic(rs.getString("master_topic"))
					    .startDate(rs.getDate("master_start_date"))
                        .endDate(rs.getDate("master_end_date"))
					    .teacher(Teacher
                                .builder()
                                    .id(rs.getInt("teacher_id"))
                                    .name(rs.getString("teacher_name"))
							        .phone(rs.getString("teacher_phone"))
                                    .position(rs.getString("teacher_position"))
							        .degree(rs.getString("teacher_degree"))
                                    .startDate(rs.getDate("teacher_start_date"))
							        .department(Department
                                            .builder()
                                                .id(rs.getInt("teacher_department_id"))
                                            .build())
                                .build())
					    .department(Department
                                .builder()
                                    .id(rs.getInt("department_id"))
							        .name(rs.getString("department_name"))
                                    .phone(rs.getString("department_phone"))
                                .build())
					.build();

		}
	}

	@Override
	public List<Master> findAll(long limit, long offset) {
		// TODO Auto-generated method stub
		return Arrays.asList(Master.builder().build(), Master.builder().build());
	}

}