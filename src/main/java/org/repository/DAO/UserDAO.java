package org.repository.DAO;

import org.repository.Model.User;
import org.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllUsers() {
        return jdbcTemplate
                .query("SELECT * from user_table", new UserMapper());
    }

    public void save(User user) {
        jdbcTemplate.
                update("INSERT INTO users_table VALUE(?,?,?,?)",
                        user.getId(),user.getEmail(),user.getPassword(),user.getName()
                );
    }

    public void update(String id, User user) {
        jdbcTemplate
                .update("UPDATE users_table SET id=? email=? password=? name=? WHERE id=?",
                user.getId(), user.getEmail(), user.getPassword(), user.getName(),id
        );
    }

    public void delete(String id) {
        jdbcTemplate
                .update("DELETE FROM users_table WHERE id=?", id);
    }

    public User showUser(String id) {
        return jdbcTemplate
                .query("SELECT * FROM users_table WHERE id=?", new Object[]{id}, new UserMapper())
                .stream().findAny().orElse(null);
    }
}
