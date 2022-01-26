package com.bodycodi.test.repository;

import com.bodycodi.test.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int insert(UserDto user) {
        int re = this.jdbcTemplate.update("insert into users(user_name, password) values(?,?)", user.getUsername(), user.getPassword());
        if (re == 1) {
            int userId = this.jdbcTemplate.queryForObject("select id from users where user_name = ?", Integer.class, user.getUsername());   // 저장한 사용자의 아이디 가지고 오기
            return userId;
        } else {
            throw new RuntimeException("insert error");
        }
    }


    public UserDto findUser(String username) {
        try{
            return this.jdbcTemplate.queryForObject("select id, user_name, password from users where user_name = ?", new String[]{username}, new RowMapper<UserDto>() {
            
                @Override
                public UserDto mapRow(ResultSet resultSet, int i) throws SQLException {
                    int id = Integer.parseInt(resultSet.getString("id"));
                    String user_name = resultSet.getString("user_name");
                    String password = resultSet.getString("password");
                    UserDto user = new UserDto(id, user_name, password);
                    //가져온 사용자 정보 매핑
    
                    return user;
                }
            });
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
