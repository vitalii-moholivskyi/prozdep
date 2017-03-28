package department.dao;

import department.model.bo.Department;
import department.model.bo.Master;
import department.model.bo.Scientist;
import department.model.bo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import util.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MasterDAO implements IMasterDAO{

    private final static String FIND_ALL = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id;";
    private final static String COUNT = "SELECT COUNT(*) FROM master;";
    private final static String FIND_ALL_WITH_PAGINATION = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_WIHT_NAME_LIKE = "SELECT COUNT(*) " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE s.name LIKE CONCAT('%', ? , '%')  COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE= "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE s.name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE s.name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";
    private final static String COUNT_FROM_TO_DATE = "SELECT COUNT(*) " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE m.start_date >= ? AND m.end_date <= ?;";
    private final static String FIND_ALL_FROM_TO_DATE = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE m.start_date >= ? AND m.end_date <= ?;";
    private final static String FIND_ALL_FROM_TO_DATE_WITH_PAGINATION = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id " +
            "WHERE m.start_date >= ? AND m.end_date <= ? " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";

    private final static String FIND_SELECT = "SELECT * " +
            "FROM master m " +
            "INNER JOIN scientist s ON m.scientist_id = s.id ";
    private final static String FIND = FIND_SELECT +
            "WHERE m.scientist_id=?;";

    private final static String EAGER_FIND_SELECT = "SELECT " +
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
            "INNER JOIN department d ON m.department_id = d.id ";

    private final static String EAGER_FIND = EAGER_FIND_SELECT +
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

    private final static String FIND_BY_DEPARTMENT = FIND_SELECT + "WHERE m.department_id = ?;";
    private final static String EAGER_FIND_BY_DEPARTMENT = EAGER_FIND_SELECT + "WHERE m.department_id = ?;";

    private final static String FIND_BY_TEACHER = FIND_SELECT + "WHERE m.teacher_id = ?;";
    private final static String EAGER_FIND_BY_TEACHER = EAGER_FIND_SELECT + "WHERE m.teacher_id = ?;";

    private final static String FIND_BY_TOPIC = FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON m.scientist_id = s_t.topic_id " +
            "WHERE s_t.topic_id = ?;";
    private final static String EAGER_FIND_BY_TOPIC = EAGER_FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON m.scientist_id = s_t.topic_id " +
            "WHERE s_t.topic_id = ?;";

    private final static String FIND_BY_PAPER = FIND_SELECT +
            "INNER JOIN scientist_paper s_p ON m.scientist_id = s_p.paper_id " +
            "WHERE s_p.paper_id = ?;";
    private final static String EAGER_FIND_BY_PAPER = EAGER_FIND_SELECT +
            "INNER JOIN scientist_paper s_p ON m.scientist_id = s_p.paper_id " +
            "WHERE s_p.paper_id = ?;";

    private final MasterMapper masterMapper = new MasterMapper();
    private final EagerMasterMapper eagerMasterMapper = new EagerMasterMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IScientistDAO scientistDAO;

    @Override
    public List<Master> findAll() {
        return jdbcTemplate.query(FIND_ALL, masterMapper);
    }


    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public List<Master> findAll(long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_PAGINATION, new Object[] { limit, offset }, masterMapper);
    }

    @Override
    public int count(String name) {
        return jdbcTemplate.queryForObject(COUNT_WIHT_NAME_LIKE, new Object[] {name}, Integer.class);
    }

    @Override
    public List<Master> findAll(String name) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE, new Object[] {name}, masterMapper);
    }

    @Override
    public List<Master> findAll(String name, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION, new Object[] {name, limit, offset }, masterMapper);
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
        if(master.getId() == 0){
            Scientist scientist = scientistDAO.insert(master);
            master = master.toBuilder().id(scientist.getId()).build();
        }
        Object [] values = {
                master.getId(),
                master.getTopic(),
                DateUtil.convertToSqlDate(master.getStartDate()),
                DateUtil.convertToSqlDate(master.getEndDate()),
                master.getTeacher() == null ? null : master.getTeacher().getId(),
                master.getDepartment().getId()
        };
        jdbcTemplate.update(INSERT, values);
        return master;
    }

    public void update(Master master) {
        scientistDAO.update(master);
        Object [] values = {
                master.getTopic(),
                DateUtil.convertToSqlDate(master.getStartDate()),
                DateUtil.convertToSqlDate(master.getEndDate()),
                master.getTeacher() == null ? null : master.getTeacher().getId(),
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
    public List<Master> getMastersByDepartmentId(int departmentId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_DEPARTMENT, new Object[]{ departmentId }, eagerMasterMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{ departmentId }, masterMapper);
        }
    }

    @Override
    public List<Master> getMastersByTeacherId(int teacherId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_TEACHER, new Object[]{ teacherId }, eagerMasterMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_TEACHER, new Object[]{ teacherId }, masterMapper);
        }
    }

    @Override
    public List<Master> getMastersByTopicId(int topicId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_TOPIC, new Object[]{ topicId }, eagerMasterMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_TOPIC, new Object[]{ topicId }, masterMapper);
        }
    }

    @Override
    public List<Master> getMastersByPaperId(int paperId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_PAPER, new Object[]{ paperId }, eagerMasterMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_PAPER, new Object[]{ paperId }, masterMapper);
        }
    }

    @Override
    public int count(Date startDate, Date endDate) {
        return jdbcTemplate.queryForObject(COUNT_FROM_TO_DATE, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate)}, Integer.class);
    }

    @Override
    public List<Master> findAll(Date startDate, Date endDate) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_DATE, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate) }, masterMapper);
    }

    @Override
    public List<Master> findAll(Date startDate, Date endDate, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_DATE_WITH_PAGINATION, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate), limit, offset }, masterMapper);
    }

    @Override
    public List<Master> getMastersByDepartmentId(int departmentId) {
        return jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{ departmentId }, masterMapper);
    }

    @Override
    public List<Master> getMastersByTeacherId(int teacherId) {
        return jdbcTemplate.query(FIND_BY_TEACHER, new Object[]{ teacherId }, masterMapper);
    }

    @Override
    public List<Master> getMastersByTopicId(int topicId) {
        return jdbcTemplate.query(FIND_BY_TOPIC, new Object[]{ topicId }, masterMapper);
    }

    @Override
    public List<Master> getMastersByPaperId(int paperId) {
        return jdbcTemplate.query(FIND_BY_PAPER, new Object[]{ paperId }, masterMapper);
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
                        .startDate(rs.getTimestamp("start_date"))
                        .endDate(rs.getTimestamp("end_date"))
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
					    .startDate(rs.getTimestamp("master_start_date"))
                        .endDate(rs.getTimestamp("master_end_date"))
					    .teacher(Teacher
                                .builder()
                                    .id(rs.getInt("teacher_id"))
                                    .name(rs.getString("teacher_name"))
							        .phone(rs.getString("teacher_phone"))
                                    .position(rs.getString("teacher_position"))
							        .degree(rs.getString("teacher_degree"))
                                    .startDate(rs.getTimestamp("teacher_start_date"))
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

}