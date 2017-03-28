package department.dao;

import department.model.bo.Department;
import department.model.bo.Postgraduate;
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
public class PostgraduateDAO implements IPostgraduateDAO{

    private final static String FIND_ALL = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id;";

    private final static String COUNT = "SELECT COUNT(*) FROM postgraduate;";
    private final static String FIND_ALL_WITH_PAGINATION = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_WIHT_NAME_LIKE = "SELECT COUNT(*) " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE s.name LIKE CONCAT('%', ? , '%')  COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE= "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE s.name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE s.name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_FROM_TO_DATE = "SELECT COUNT(*) " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE p.start_date >= ? AND p.end_date <= ?;";
    private final static String FIND_ALL_FROM_TO_DATE = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE p.start_date >= ? AND p.end_date <= ?;";
    private final static String FIND_ALL_FROM_TO_DATE_WITH_PAGINATION = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id " +
            "WHERE p.start_date >= ? AND p.end_date <= ? " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";

    private final static String FIND_SELECT = "SELECT * " +
            "FROM postgraduate p " +
            "INNER JOIN scientist s ON p.scientist_id = s.id ";
    private final static String FIND = FIND_SELECT +
            "WHERE p.scientist_id=?;";

    private final static String EAGER_FIND_SELECT = "SELECT " +
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
            "INNER JOIN department d ON p.department_id = d.id ";
    private final static String EAGER_FIND = EAGER_FIND_SELECT +
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

    private final static String FIND_BY_DEPARTMENT = FIND_SELECT + "WHERE p.department_id = ?;";
    private final static String EAGER_FIND_BY_DEPARTMENT = EAGER_FIND_SELECT + "WHERE p.department_id = ?;";

    private final static String FIND_BY_TEACHER = FIND_SELECT + "WHERE p.teacher_id = ?;";
    private final static String EAGER_FIND_BY_TEACHER = EAGER_FIND_SELECT + "WHERE p.teacher_id = ?;";

    private final static String FIND_BY_PROTECTION_DATE = FIND_SELECT + "WHERE p.protection_date <= ?;";
    private final static String EAGER_FIND_BY_PROTECTION_DATE = EAGER_FIND_SELECT + "WHERE p.protection_date <= ?;";

    private final static String FIND_BY_TOPIC = FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON p.scientist_id = s_t.topic_id " +
            "WHERE s_t.topic_id = ?;";
    private final static String EAGER_FIND_BY_TOPIC = EAGER_FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON p.scientist_id = s_t.topic_id " +
            "WHERE s_t.topic_id = ?;";

    private final static String FIND_BY_PAPER = FIND_SELECT +
            "INNER JOIN scientist_paper s_p ON p.scientist_id = s_p.paper_id " +
            "WHERE s_p.paper_id = ?;";
    private final static String EAGER_FIND_BY_PAPER = EAGER_FIND_SELECT +
            "INNER JOIN scientist_paper s_p ON p.scientist_id = s_p.paper_id " +
            "WHERE s_p.paper_id = ?;";


    private final PostgraduateMapper postgraduateMapper = new PostgraduateMapper();
    private final EagerPostgraduateMapper eagerPostgraduateMapper = new EagerPostgraduateMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IScientistDAO scientistDAO;

    @Override
    public List<Postgraduate> findAll() {
        return jdbcTemplate.query(FIND_ALL, postgraduateMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }


    @Override
    public List<Postgraduate> findAll(long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_PAGINATION, new Object[] { limit, offset }, postgraduateMapper);
    }

    @Override
    public int count(String name) {
        return jdbcTemplate.queryForObject(COUNT_WIHT_NAME_LIKE, new Object[] {name}, Integer.class);
    }

    @Override
    public List<Postgraduate> findAll(String name) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE, new Object[] {name}, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> findAll(String name, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION, new Object[] {name, limit, offset }, postgraduateMapper);
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

    public Postgraduate insert(Postgraduate postgraduate) {
        if(postgraduate.getId() == 0){
            Scientist scientist = scientistDAO.insert(postgraduate);
            postgraduate = postgraduate.toBuilder().id(scientist.getId()).build();
        }
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
        return postgraduate;
    }

    public void update(Postgraduate postgraduate) {
        scientistDAO.update(postgraduate);
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

    @Override
    public int count(Date startDate, Date endDate) {
        return jdbcTemplate.queryForObject(COUNT_FROM_TO_DATE, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate)}, Integer.class);
    }

    @Override
    public List<Postgraduate> findAll(Date startDate, Date endDate) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_DATE, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate) }, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> findAll(Date startDate, Date endDate, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_DATE_WITH_PAGINATION, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate), limit, offset }, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> getPostgraduatesByDepertmentId(int departmentId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_DEPARTMENT, new Object[]{ departmentId }, eagerPostgraduateMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{ departmentId }, postgraduateMapper);
        }
    }

    @Override
    public List<Postgraduate> getPostgraduatesByTeacherId(int teacherId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_TEACHER, new Object[]{ teacherId }, eagerPostgraduateMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_TEACHER, new Object[]{ teacherId }, postgraduateMapper);
        }
    }

    @Override
    public List<Postgraduate> getPostgraduatesByTopicId(int topicId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_TOPIC, new Object[]{ topicId }, eagerPostgraduateMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_TOPIC, new Object[]{ topicId }, postgraduateMapper);
        }
    }

    @Override
    public List<Postgraduate> getPostgraduatesByPaperId(int paperId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_PAPER, new Object[]{ paperId }, eagerPostgraduateMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_PAPER, new Object[]{ paperId }, postgraduateMapper);
        }
    }

    @Override
    public List<Postgraduate> getPostgraduatesByProtectionDate(Date protectionDate, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_PROTECTION_DATE, new Object[]{ DateUtil.convertToSqlDate(protectionDate) }, eagerPostgraduateMapper);
        } else {
            return jdbcTemplate.query(FIND_BY_PROTECTION_DATE, new Object[]{ DateUtil.convertToSqlDate(protectionDate) }, postgraduateMapper);
        }
    }

    @Override
    public List<Postgraduate> getPostgraduatesByDepartmentId(int departmentId) {
        return jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{ departmentId }, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> getPostgraduatesByTeacherId(int teacherId) {
        return jdbcTemplate.query(FIND_BY_TEACHER, new Object[]{ teacherId }, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> getPostgraduatesByTopicId(int topicId) {
        return jdbcTemplate.query(FIND_BY_TOPIC, new Object[]{ topicId }, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> getPostgraduatesByPaperId(int paperId) {
        return jdbcTemplate.query(FIND_BY_PAPER, new Object[]{ paperId }, postgraduateMapper);
    }

    @Override
    public List<Postgraduate> getPostgraduatesByProtectionDate(Date protectionDate) {
        return jdbcTemplate.query(FIND_BY_PROTECTION_DATE, new Object[]{ DateUtil.convertToSqlDate(protectionDate) }, postgraduateMapper);
    }

    private final class PostgraduateMapper implements RowMapper<Postgraduate> {
        @Override
        public Postgraduate mapRow(ResultSet rs, int i) throws SQLException {
            /*Postgraduate postgraduate = new Postgraduate();
            postgraduate.setId(rs.getInt("scientist_id"));
            postgraduate.setName(rs.getString("name"));
            postgraduate.setPhone(rs.getString("phone"));
            postgraduate.setTopic(rs.getString("topic"));
            postgraduate.setStartDate(rs.getDate("start_date"));
            postgraduate.setEndDate(rs.getDate("end_date"));
            postgraduate.setProtectionDate(rs.getDate("protection_date"));
            postgraduate.setTeacher(new Teacher(rs.getInt("teacher_id")));
            postgraduate.setDepartment(new Department(rs.getInt("department_id")));
            return postgraduate;*/
            return Postgraduate
                    .builder()
                    .id(rs.getInt("scientist_id"))
                    .name(rs.getString("name"))
                    .phone(rs.getString("phone"))
                    .topic(rs.getString("topic"))
                    .startDate(rs.getTimestamp("start_date"))
                    .endDate(rs.getTimestamp("end_date"))
                    .protectionDate(rs.getTimestamp("protection_date"))
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

    private final class EagerPostgraduateMapper implements RowMapper<Postgraduate> {

        @Override
        public Postgraduate mapRow(ResultSet rs, int i) throws SQLException {
            /*Postgraduate postgraduate = new Postgraduate();
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

            return postgraduate;*/
            return Postgraduate
                    .builder()
                    .id(rs.getInt("postgraduate_id"))
                    .name(rs.getString("postgraduate_name"))
                    .phone(rs.getString("postgraduate_phone"))
                    .topic(rs.getString("postgraduate_topic"))
                    .startDate(rs.getTimestamp("postgraduate_start_date"))
                    .endDate(rs.getTimestamp("postgraduate_end_date"))
                    .protectionDate(rs.getTimestamp("postgraduate_protection_date"))
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