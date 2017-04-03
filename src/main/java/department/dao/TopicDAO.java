package department.dao;

import department.model.bo.Department;
import department.model.bo.Teacher;
import department.model.bo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import util.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class TopicDAO implements ITopicDAO{

    private final static String FIND_ALL = "SELECT * FROM topic;";
    private final static String COUNT = "SELECT COUNT(*) FROM topic;";
    private final static String FIND_ALL_WITH_PAGINATION = "SELECT * " +
            "FROM topic " +
            "ORDER BY id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_WIHT_NAME_LIKE = "SELECT COUNT(*) " +
            "FROM topic " +
            "WHERE name LIKE CONCAT('%', ? , '%')  COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE= "SELECT * " +
            "FROM topic " +
            "WHERE name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION = "SELECT * " +
            "FROM topic " +
            "WHERE name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci " +
            "ORDER BY id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_FROM_TO_DATE = "SELECT COUNT(*) " +
            "FROM topic " +
            "WHERE start_date >= ? AND end_date <= ?;";
    private final static String FIND_ALL_FROM_TO_DATE = "SELECT * " +
            "FROM topic " +
            "WHERE start_date >= ? AND end_date <= ?;";
    private final static String FIND_ALL_FROM_TO_DATE_WITH_PAGINATION = "SELECT * " +
            "FROM topic " +
            "WHERE start_date >= ? AND end_date <= ? " +
            "ORDER BY id " +
            "LIMIT ? OFFSET ?;";

    private final static String FIND_SELECT = "SELECT * FROM topic ";
    private final static String FIND = FIND_SELECT + "WHERE id=?;";

    private final static String EAGER_FIND_SELECT = "SELECT t.id AS topic_id, " +
            "t.name AS topic_name," +
            "t.client AS topic_client, " +
            "t.start_date AS topic_start_date, " +
            "t.end_date AS topic_end_date, " +
            "t.department_id AS department_id, " +
            "d.name AS department_name, " +
            "d.phone AS department_phone, " +
            "t.chief_scientist_id as teacher_id, " +
            "s.name AS teacher_name, " +
            "s.phone AS teacher_phone, " +
            "teach.position AS teacher_position, " +
            "teach.degree AS teacher_degree, " +
            "teach.start_date AS teacher_start_date " +
            "FROM topic t " +
            "INNER JOIN department d ON t.department_id = d.id " +
            "INNER JOIN teacher teach ON t.chief_scientist_id = teach.scientist_id " +
            "INNER JOIN scientist s ON t.chief_scientist_id = s.id ";

    private final static String EAGER_FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION = EAGER_FIND_SELECT +
            "WHERE t.name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci " +
            "ORDER BY t.id " +
            "LIMIT ? OFFSET ?;";


    private final static String FIND_EAGER = EAGER_FIND_SELECT +
            "WHERE t.id=?;";
    private final static String INSERT = "INSERT INTO topic (id," +
            "name, " +
            "client, " +
            "start_date, " +
            "end_date, " +
            "department_id, " +
            "chief_scientist_id) VALUES(DEFAULT,?,?,?,?,?,?);";
    private final static String UPDATE = "UPDATE topic SET " +
            "name=?, " +
            "client = ?, " +
            "start_date = ?, " +
            "end_date = ?, " +
            "department_id = ?, " +
            "chief_scientist_id = ? " +
            "WHERE id = ?;";
    private final static String REMOVE = "DELETE FROM topic WHERE id=?;";

    private final static String FIND_BY_CHIEF_SCIENTIST = FIND_SELECT +
            "WHERE chief_scientist_id = ?;";
    private final static String EAGER_FIND_BY_CHIEF_SCIENTIST = EAGER_FIND_SELECT +
            "WHERE t.chief_scientist_id = ?;";

    private final static String FIND_BY_DEPARTMENT = FIND_SELECT +
            "WHERE department_id = ?;";
    private final static String EAGER_FIND_BY_DEPARTMENT = EAGER_FIND_SELECT +
            "WHERE t.department_id = ?;";

    private final static String FIND_BY_SCIENTIST = FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON topic.id = s_t.scientist_id " +
            "WHERE s_t.scientist_id = ?;";
    private final static String EAGER_FIND_BY_SCIENTIST = EAGER_FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON t.id = s_t.scientist_id " +
            "WHERE s_t.scientist_id = ?;";
    private final static String EAGER_FIND_BY_SCIENTIST_WITH_LIMIT = EAGER_FIND_SELECT +
            "INNER JOIN scientist_topic s_t ON t.id = s_t.scientist_id " +
            "WHERE s_t.scientist_id = ? LIMIT ? OFFSET ?;";

    private final static String FIND_BY_PAPER = FIND_SELECT +
            "INNER JOIN paper_topic p_t ON topic.id = p_t.topic_id " +
            "WHERE p_t.paper_id = ?;";
    private final static String EAGER_FIND_BY_PAPER = EAGER_FIND_SELECT +
            "INNER JOIN paper_topic p_t ON t.id = p_t.topic_id " +
            "WHERE p_t.paper_id = ?;";

    private final TopicMapper topicMapper = new TopicMapper();
    private final EagerTopicMapper eagerTopicMapper = new EagerTopicMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Topic> findAll() {
        return jdbcTemplate.query(FIND_ALL, topicMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public List<Topic> findAll(long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_PAGINATION, new Object[] { limit, offset }, topicMapper);
    }

    @Override
    public int count(String name) {
        return jdbcTemplate.queryForObject(COUNT_WIHT_NAME_LIKE, new Object[] {name}, Integer.class);
    }

    @Override
    public List<Topic> findAll(String name) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE, new Object[] {name}, topicMapper);
    }

    @Override
    public List<Topic> findAll(String name, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION, new Object[] {name, limit, offset }, topicMapper);
    }

    @Override
    public List<Topic> findAll(String name, long limit, long offset, boolean isEager) {
        if(isEager) return jdbcTemplate.query(EAGER_FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION, new Object[] {name, limit, offset }, eagerTopicMapper);
        else return findAll(name, limit, offset);
    }

    @Override
    public Topic find(int id) {
        List<Topic>  topics = jdbcTemplate.query(FIND, new Object[] { id }, topicMapper);
        return topics.isEmpty() ? null : topics.get(0);
    }

    @Override
    public Topic find(int id, boolean isEager) {
        if (isEager) {
            List<Topic>  topics = jdbcTemplate.query(FIND_EAGER, new Object[] { id }, eagerTopicMapper);
            return topics.isEmpty() ? null : topics.get(0);
        } else {
            return find(id);
        }
    }

    @Override
    public Topic insert(Topic topic) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT,
                    new String[] {"id"});
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getClient());
            ps.setDate(3, DateUtil.convertToSqlDate(topic.getStartDate()));
            ps.setDate(4, DateUtil.convertToSqlDate(topic.getEndDate()));
            ps.setInt(5, topic.getDepartment().getId());
            ps.setInt(6, topic.getChiefScientist().getId());
            return ps;
        },keyHolder);
        return topic.toBuilder().id(keyHolder.getKey().intValue()).build();
    }

    @Override
    public void update(Topic topic) {
        Object [] values = {
                topic.getName(),
                topic.getClient(),
                topic.getStartDate(),
                topic.getEndDate(),
                topic.getDepartment().getId(),
                topic.getChiefScientist().getId(),
                topic.getId()
        };
        jdbcTemplate.update(UPDATE, values);
    }

    @Override
    public void remove(Topic topic) {
        jdbcTemplate.update(REMOVE, topic.getId());
    }

    @Override
    public int count(Date startDate, Date endDate) {
        return jdbcTemplate.queryForObject(COUNT_FROM_TO_DATE, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate)}, Integer.class);
    }

    @Override
    public List<Topic> findAll(Date startDate, Date endDate) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_DATE, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate) }, topicMapper);
    }

    @Override
    public List<Topic> findAll(Date startDate, Date endDate, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_DATE_WITH_PAGINATION, new Object[] { DateUtil.convertToSqlDate(startDate), DateUtil.convertToSqlDate(endDate), limit, offset }, topicMapper);
    }

    @Override
    public List<Topic> getTopicsByChiefScientistId(int scientistId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_CHIEF_SCIENTIST, new Object[]{scientistId}, eagerTopicMapper);
        } else {
            return getTopicsByChiefScientistId(scientistId);
        }
    }

    @Override
    public List<Topic> getTopicsByDepartmentId(int departmentId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_DEPARTMENT, new Object[]{departmentId}, eagerTopicMapper);
        } else {
            return getTopicsByDepartmentId(departmentId);
        }
    }

    @Override
    public List<Topic> getTopicsByScientistId(int scientistId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_SCIENTIST, new Object[]{ scientistId }, eagerTopicMapper);
        } else {
            return getTopicsByScientistId(scientistId);
        }
    }

    @Override
    public List<Topic> getTopicsByPaperId(int paperId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_PAPER, new Object[]{ paperId }, eagerTopicMapper);
        } else {
            return getTopicsByPaperId(paperId);
        }
    }

    @Override
    public List<Topic> getTopicsByChiefScientistId(int scientistId) {
        return jdbcTemplate.query(FIND_BY_CHIEF_SCIENTIST, new Object[]{scientistId}, topicMapper);
    }

    @Override
    public List<Topic> getTopicsByDepartmentId(int departmentId) {
        return jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{departmentId}, topicMapper);
    }

    @Override
    public List<Topic> getTopicsByScientistId(int scientistId) {
        return jdbcTemplate.query(FIND_BY_SCIENTIST, new Object[]{ scientistId }, topicMapper);
    }

    @Override
    public List<Topic> getTopicsByPaperId(int paperId) {
        return jdbcTemplate.query(FIND_BY_PAPER, new Object[]{ paperId }, topicMapper);
    }

    private class TopicMapper implements RowMapper<Topic>{
        @Override
        public Topic mapRow(ResultSet rs, int i) throws SQLException {
            /*Topic topic = new Topic();
            topic.setId(rs.getInt("id"));
            topic.setName(rs.getString("name"));
            topic.setCliect(rs.getString("client"));
            topic.setStartDate(rs.getDate("start_date"));
            topic.setEndDate(rs.getDate("end_date"));
            topic.setDepartment(new Department(rs.getInt("department_id")));
            topic.setChiefScientist(new Teacher(rs.getInt("chief_scientist_id")));
            return topic;*/
            return Topic
                    .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .client(rs.getString("client"))
                        .startDate(rs.getTimestamp("start_date"))
                        .endDate(rs.getTimestamp("end_date"))
                        .department(Department.builder().id(rs.getInt("department_id")).build())
                        .chiefScientist(Teacher.builder().id(rs.getInt("chief_scientist_id")).build())
                    .build();
        }
    }

    private class EagerTopicMapper implements RowMapper<Topic>{
        @Override
        public Topic mapRow(ResultSet rs, int i) throws SQLException {
           /*Topic topic = new Topic();
            topic.setId(rs.getInt("topic_id"));
            topic.setName(rs.getString("topic_name"));
            topic.setCliect(rs.getString("topic_start_date"));
            topic.setStartDate(rs.getDate("topic_end_date"));
            topic.setEndDate(rs.getDate("topic_end_date"));

            Department department = new Department(
                    rs.getInt("department_id"),
                    rs.getString("department_name"),
                    rs.getString("department_phone")
            );
            topic.setDepartment(department);
            topic.setChiefScientist(new Teacher(
                    rs.getInt("teacher_id"),
                    rs.getString("teacher_name"),
                    rs.getString("teacher_phone"),
                    rs.getString("teacher_position"),
                    rs.getString("teacher_degree"),
                    rs.getDate("teacher_start_date"),
                    department
                    ));
            return topic;*/
            return Topic
                    .builder()
                        .id(rs.getInt("topic_id"))
                        .name(rs.getString("topic_name"))
                        .client(rs.getString("topic_client"))
                        .startDate(rs.getTimestamp("topic_start_date"))
                        .endDate(rs.getTimestamp("topic_end_date"))
                        .department(Department
                                .builder()
                                    .id(rs.getInt("department_id"))
                                    .name(rs.getString("department_name"))
                                    .phone(rs.getString("department_phone"))
                                .build())
                        .chiefScientist(Teacher
                                .builder()
                                    .id(rs.getInt("teacher_id"))
                                    .name(rs.getString("teacher_name"))
                                    .phone(rs.getString("teacher_phone"))
                                    .position(rs.getString("teacher_position"))
                                    .degree(rs.getString("teacher_degree"))
                                    .startDate(rs.getTimestamp("teacher_start_date"))
                                .build())
                    .build();
        }
    }

	/* (non-Javadoc)
	 * @see department.dao.ITopicDAO#getTopicsByScientistId(int, int, int)
	 */
	@Override
	public List<Topic> getTopicsByScientistId(int scientistId, int limit, int offset) {
		return jdbcTemplate.query(EAGER_FIND_BY_SCIENTIST_WITH_LIMIT, new Object[]{ scientistId,limit,offset }, eagerTopicMapper);
	}

}