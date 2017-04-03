package department.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import department.model.bo.Paper;
import department.model.bo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PaperDAO implements IPaperDAO{

    private final static String FIND_ALL = "SELECT * FROM paper;";
    private final static String COUNT = "SELECT COUNT(*) FROM paper;";
    private final static String FIND_ALL_WITH_PAGINATION = "SELECT * " +
            "FROM paper " +
            "ORDER BY id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_WIHT_NAME_LIKE = "SELECT COUNT(*) " +
            "FROM paper " +
            "WHERE name LIKE CONCAT('%', ? , '%')  COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE= "SELECT * " +
            "FROM paper " +
            "WHERE name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci;";
    private final static String FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION = "SELECT * " +
            "FROM paper " +
            "WHERE name LIKE CONCAT('%', ? , '%') COLLATE utf8_general_ci " +
            "ORDER BY id " +
            "LIMIT ? OFFSET ?;";

    private final static String COUNT_FROM_TO_YEAR = "SELECT COUNT(*) " +
            "FROM paper " +
            "WHERE year >= ? AND year <= ?;";
    private final static String FIND_ALL_FROM_TO_YEAR = "SELECT * " +
            "FROM paper " +
            "WHERE year >= ? AND year <= ?;";
    private final static String FIND_ALL_FROM_TO_YEAR_WITH_PAGINATION = "SELECT * " +
            "FROM paper " +
            "WHERE year >= ? AND year <= ? " +
            "ORDER BY s.id " +
            "LIMIT ? OFFSET ?;";

    private final static String FIND = "SELECT * FROM paper WHERE id=?;";
    private final static String INSERT = "INSERT INTO paper (id, name, type, year) VALUES(DEFAULT,?,?,?);";
    private final static String UPDATE = "UPDATE paper SET name=?, type=?, year=? WHERE id = ?;";
    private final static String REMOVE = "DELETE FROM paper WHERE id=?;";

    private final static String FIND_BY_DEPARTMENT = "SELECT * " +
            "FROM paper p " +
            "INNER JOIN paper_topic p_t ON p.id = p_t.paper_id " +
            "INNER JOIN topic t ON p_t.topic_id = t.id " +
            "WHERE t.department_id = ?;";
    private final static String EAGER_FIND_BY_DEPARTMENT = FIND_BY_DEPARTMENT;

    private final static String FIND_BY_TOPIC = "SELECT * " +
            "FROM paper p INNER JOIN paper_topic p_t ON p.id = p_t.paper_id " +
            "WHERE p_t.topic_id = ?;";
    private final static String EAGER_FIND_BY_TOPIC = FIND_BY_TOPIC;

    private final static String FIND_BY_SCIENTIST = "SELECT * " +
            "FROM paper p INNER JOIN scientist_paper s_p ON p.id = s_p.paper_id " +
            "WHERE s_p.scientist_id = ?;";
    private final static String EAGER_FIND_BY_SCIENTIST = FIND_BY_SCIENTIST;

    private final static String FIND_BY_YEAR = "SELECT * FROM paper WHERE year = ?;";
    private final static String EAGER_FIND_BY_YEAR = FIND_BY_YEAR;

    private final PaperMapper paperMapper = new PaperMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Paper> findAll() {
        return jdbcTemplate.query(FIND_ALL, paperMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public List<Paper> findAll(long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_PAGINATION, new Object[] { limit, offset }, paperMapper);
    }

    @Override
    public int count(String name) {
        return jdbcTemplate.queryForObject(COUNT_WIHT_NAME_LIKE, new Object[] {name}, Integer.class);
    }
    @Override
    public List<Paper> findAll(String name) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE, new Object[] {name}, paperMapper);
    }

    @Override
    public List<Paper> findAll(String name, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION, new Object[] {name, limit, offset }, paperMapper);
    }

    @Override
    public List<Paper> findAll(String name, long limit, long offset, boolean isEager) {
        return jdbcTemplate.query(FIND_ALL_WITH_NAME_LIKE_WITH_PAGINATION, new Object[] {name, limit, offset }, paperMapper);
    }

    @Override
    public Paper find(int id) {
        List<Paper> papers = jdbcTemplate.query(FIND, new Object[]{id}, paperMapper);
        return (papers.isEmpty()) ? null : papers.get(0);
    }

    @Override
    public Paper find(int id, boolean isEager) {
        return find(id);
    }

    @Override
    public Paper insert(Paper paper) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT,
                    new String[] {"id"});
            ps.setString(1, paper.getName());
            ps.setString(2, paper.getType());
            ps.setInt(3, paper.getYear());
            return ps;
        },keyHolder);
        return paper.toBuilder().id(keyHolder.getKey().intValue()).build();
    }

    @Override
    public void update(Paper paper) {
        Object [] values = {
                paper.getName(),
                paper.getType(),
                paper.getYear(),
                paper.getId()
        };
        jdbcTemplate.update(UPDATE, values);
    }

    @Override
    public void remove(Paper paper) {
        jdbcTemplate.update(REMOVE, paper.getId());
    }

    @Override
    public List<Paper> getPapersByDepartmentId(int departmentId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_DEPARTMENT, new Object[]{departmentId}, paperMapper);
        } else {
            return getPapersByDepartmentId(departmentId);
        }
    }

    @Override
    public List<Paper> getPapersByScientistId(int scientistId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_SCIENTIST, new Object[]{scientistId}, paperMapper);
        } else {
            return getPapersByScientistId(scientistId);
        }
    }

    @Override
    public List<Paper> getPapersByTopicId(int topicId, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_TOPIC, new Object[]{topicId}, paperMapper);
        } else {
            return getPapersByTopicId(topicId);
        }
    }

    @Override
    public List<Paper> getPapersByYear(int year, boolean isEager) {
        if(isEager){
            return jdbcTemplate.query(EAGER_FIND_BY_YEAR, new Object[]{year}, paperMapper);
        } else {
            return getPapersByYear(year);
        }
    }

    @Override
    public int count(int startYear, int endYear) {
        return jdbcTemplate.queryForObject(COUNT_FROM_TO_YEAR, new Object[] { startYear, endYear}, Integer.class);
    }

    @Override
    public List<Paper> findAll(int startYear, int endYear) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_YEAR, new Object[] { startYear, endYear }, paperMapper);
    }

    @Override
    public List<Paper> findAll(int startYear, int endYear, long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_FROM_TO_YEAR_WITH_PAGINATION, new Object[] { startYear, endYear, limit, offset }, paperMapper);
    }

    @Override
    public List<Paper> getPapersByDepartmentId(int departmentId) {
        return jdbcTemplate.query(FIND_BY_DEPARTMENT, new Object[]{ departmentId }, paperMapper);
    }

    @Override
    public List<Paper> getPapersByScientistId(int scientistId) {
        return jdbcTemplate.query(FIND_BY_SCIENTIST, new Object[]{ scientistId }, paperMapper);
    }

    @Override
    public List<Paper> getPapersByTopicId(int topicId) {
        return jdbcTemplate.query(FIND_BY_TOPIC, new Object[]{ topicId }, paperMapper);
    }

    @Override
    public List<Paper> getPapersByYear(int year) {
        return jdbcTemplate.query(FIND_BY_YEAR, new Object[]{ year }, paperMapper);
    }

    private class PaperMapper implements RowMapper<Paper> {
        @Override
        public Paper mapRow(ResultSet rs, int rn) throws SQLException {
            /*Paper paper = new Paper();
            paper.setId(rs.getInt("id"));
            paper.setName(rs.getString("name"));
            paper.setType(rs.getString("type"));
            paper.setYear(rs.getInt("year"));*/
            return Paper
                    .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .year(rs.getInt("year"))
                    .build();
        }
    }

}