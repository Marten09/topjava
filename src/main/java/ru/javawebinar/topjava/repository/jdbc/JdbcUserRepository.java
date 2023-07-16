package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            saveUserRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        } else {
            jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.getId());
            saveUserRoles(user);
        }
        return user;
    }

    private void saveUserRoles(User user) {
        Set<Role> roles = user.getRoles();
        List<Object[]> batchParams = roles.stream()
                .map(role -> new Object[]{user.id(), role.name()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO user_role (user_id, role) VALUES (?,?)", batchParams);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return queryRolesForUser(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return queryRolesForUser(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_role ur on u.id = ur.user_id ORDER BY name, email", (ResultSetExtractor<List<User>>) rs -> {
            Map<Integer, User> userMap = new LinkedHashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                User user = userMap.computeIfAbsent(id, var -> {
                    try {
                        return Objects.requireNonNull(ROW_MAPPER.mapRow(rs, 0));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                if(user.getRoles() == null) {
                    user.setRoles(new HashSet<>());
                }
                String role = rs.getString("role");
                if (role != null && !role.isEmpty()) {
                    user.getRoles().add(Role.valueOf(role));
                }
            }
            return new ArrayList<>(userMap.values());
        });
    }

    private User queryRolesForUser(User user) {
        if (user == null) {
            return null;
        }
        List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_role WHERE user_id=?", Role.class, user.id());
        user.setRoles(roles);
        return user;
    }
}
