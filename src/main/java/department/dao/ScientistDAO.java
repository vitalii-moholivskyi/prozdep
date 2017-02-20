package department. dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import department.model.bo.Scientist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ScientistDAO implements IScientistDAO{

    private final static String FIND_ALL = "SELECT * FROM scientist;";
    private final static String COUNT = "SELECT COUNT(*) FROM scientist;";
    private final static String FIND_ALL_WITH_PAGINATION = "SELECT * " +
            "FROM scientist " +
            "ORDER BY id " +
            "LIMIT ? OFFSET ?;";
    private final static String FIND = "SELECT * FROM scientist WHERE id=?;";
    private final static String INSERT = "INSERT INTO scientist (id, name, phone) VALUES(DEFAULT,?,?);";
    private final static String UPDATE = "UPDATE scientist SET name=?, phone=? WHERE id = ?;";
    private final static String REMOVE = "DELETE FROM scientist WHERE id=?;";

    private final ScientistMapper scientistMapper = new ScientistMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Scientist> findAll() {
        return jdbcTemplate.query(FIND_ALL, scientistMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public List<Scientist> findAll(long limit, long offset) {
        return jdbcTemplate.query(FIND_ALL_WITH_PAGINATION, new Object[] { limit, offset }, scientistMapper);
    }

    @Override
    public int count(String name) {
        // TODO
        return count();
    }

    @Override
    public List<Scientist> findAll(String name) {
        // TODO
        return findAll();
    }

    @Override
    public List<Scientist> findAll(String name, long limit, long offset) {
        // TODO
        return findAll(limit, offset);
    }

    @Override
    public Scientist find(int id) {
        List<Scientist> scientists = jdbcTemplate.query(FIND, new Object[]{id}, scientistMapper);
        return (scientists.isEmpty()) ? null : scientists.get(0);
    }

    @Override
    public Scientist find(int id, boolean isEager) {
        return find(id);
    }

    @Override
    public Scientist insert(Scientist scientist) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, new String[] {"id"});
            ps.setString(1, scientist.getName());
            ps.setString(2, scientist.getPhone());
            return ps;
        },keyHolder);
        return scientist.toBuilder().id(keyHolder.getKey().intValue()).build();
    }

    @Override
    public void update(Scientist scientist) {
        Object [] values = {
                scientist.getName(),
                scientist.getPhone(),
                scientist.getId()
        };
        jdbcTemplate.update(UPDATE, values);
    }

    @Override
    public void remove(Scientist scientist) {
        jdbcTemplate.update(REMOVE, scientist.getId());
    }

    private class ScientistMapper implements RowMapper<Scientist>{
        @Override
        public Scientist mapRow(ResultSet rs, int rn) throws SQLException {
            /*Scientist scientist = new Scientist();
            scientist.setId(rs.getInt("id"));
            scientist.setName(rs.getString("name"));
            scientist.setPhone(rs.getString("phone"));
            return scientist;*/
            return Scientist
                    .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                    .build();
        }
    }

}