package department.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import department.model.bo.Paper;
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
    private final static String FIND = "SELECT * FROM paper WHERE id=?;";
    private final static String INSERT = "INSERT INTO paper (id, name, type, year) VALUES(DEFAULT,?,?,?);";
    private final static String UPDATE = "UPDATE paper SET name=?, type=?, year=? WHERE id = ?;";
    private final static String REMOVE = "DELETE FROM paper WHERE id=?;";

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
        // TODO
        return count();
    }

    @Override
    public List<Paper> findAll(String name, long limit, long offset) {
        // TODO
        return findAll(limit, offset);
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
        return null;
    }

    @Override
    public List<Paper> getPapersByScientistId(int scientistId, boolean isEager) {
        return null;
    }

    @Override
    public List<Paper> getPapersByTopicId(int topicId, boolean isEager) {
        return null;
    }

    @Override
    public List<Paper> getPapersByDepartmentId(int departmentId) {
        return null;
    }

    @Override
    public List<Paper> getPapersByScientistId(int scientistId) {
        return null;
    }

    @Override
    public List<Paper> getPapersByTopicId(int topicId) {
        return null;
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