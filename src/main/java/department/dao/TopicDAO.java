package department.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import department.entity.Department;
import department.entity.Teacher;
import department.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import util.DateUtil;

@Repository
public class TopicDAO implements IDAOGeneric<Topic>{

    private final static String FIND_ALL = "SELECT * FROM topic;";
    private final static String FIND = "SELECT * FROM topic WHERE id=?;";
    private final static String FIND_EAGER = "SELECT t.id AS topic_id, " +
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
            "INNER JOIN scientist s ON t.chief_scientist_id = s.id " +
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

    private final TopicMapper paperMapper = new TopicMapper();
    private final EagerTopicMapper eagerTopicMapper = new EagerTopicMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Topic> findAll() {
        return jdbcTemplate.query(FIND_ALL, paperMapper);
    }

    @Override
    public Topic find(int id) {
        List<Topic>  topics = jdbcTemplate.query(FIND, new Object[] { id }, paperMapper);
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
    public void insert(Topic topic) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT,
                    new String[] {"id"});
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getCliect());
            ps.setDate(3, DateUtil.convertToSqlDate(topic.getStartDate()));
            ps.setDate(4, DateUtil.convertToSqlDate(topic.getEndDate()));
            ps.setInt(5, topic.getDepartment().getId());
            ps.setInt(6, topic.getChiefScientist().getId());
            return ps;
        },keyHolder);
        topic.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Topic topic) {
        Object [] values = {
                topic.getName(),
                topic.getCliect(),
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

    private class TopicMapper implements RowMapper<Topic>{
        @Override
        public Topic mapRow(ResultSet rs, int i) throws SQLException {
            Topic topic = new Topic();
            topic.setId(rs.getInt("id"));
            topic.setName(rs.getString("name"));
            topic.setCliect(rs.getString("client"));
            topic.setStartDate(rs.getDate("start_date"));
            topic.setEndDate(rs.getDate("end_date"));
            topic.setDepartment(new Department(rs.getInt("department_id")));
            topic.setChiefScientist(new Teacher(rs.getInt("chief_scientist_id")));
            return topic;
        }
    }

    private class EagerTopicMapper implements RowMapper<Topic>{
        @Override
        public Topic mapRow(ResultSet rs, int i) throws SQLException {
            Topic topic = new Topic();
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
            return topic;
        }
    }
}