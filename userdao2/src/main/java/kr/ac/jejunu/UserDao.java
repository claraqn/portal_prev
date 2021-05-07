package kr.ac.jejunu;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public UserDao(JdbcTemplate jdbcTemplate) {
//        //new로 생성하기보단 생성자를 통해 connectionMaker 사용하기 (권장)
//        this.jdbcTemplate = jdbcTemplate;
//        // UserDao 를 호출한 녀석에게 connectionMaker 던짐 ( UserDaoTests 에게)
//    }

    public User findById(Integer id){
        //데이터 어딨어? => mysql
        //템플릿 콜백 패턴 사용
        Object[] params = new Object[]{id};
        String sql = "select * from  userinfo where id = ?";
        return jdbcTemplate.query(sql, params, rs -> {
            User user = null;
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            return user;

        });
    }
    public void insert(User user) {
        Object[] params = new Object[]{user.getName(), user.getPassword()};
        String sql = "insert into userinfo(name, password) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
    }

    public void update(User user){
        Object[] params = new Object[]{user.getName(), user.getPassword(), user.getId()};
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        jdbcTemplate.update(sql, params);
    }

    public void delete(Integer id){
        Object[] params = new Object[]{id};
        String sql = "delete from userinfo where id = ?";
        jdbcTemplate.update(sql, params);
    }

}






