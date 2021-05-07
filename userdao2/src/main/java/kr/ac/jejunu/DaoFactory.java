package kr.ac.jejunu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Configuration
public class DaoFactory { //DaoFactory=스프링 코어 => 스프링 그 자체
    //edit configurations 해서 넣은 db 정보를 받아와서 사용하는 부분
    @Value("${db.classname}")
    private String className;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    // 미루고 미뤄진 남들이 갖기 싫은 정보를 DaoFactory가 받는것!
    // 의존성 주입을 하는곳! (의존성 관리를 전문적으로 하는 곳)
    // UserDao를 new를 통해 만들고 거기에 맞는 의존성을 userDao 에게 주입을 해줘서 클라이언트에게 던져줌
    // 이런게 DI 입니다!
//    @Bean
//    public UserDao userDao() {
//
//        return new UserDao(jdbcContext());
//    }

    @Bean
    public JdbcTemplate jdbcContext() {
        return new JdbcTemplate(dataSource());
    }

    //DataSource = connection에 관련된 다양한 인터페이스 제공
    //SimpleDriverDataSource = 심플하게 사용할 수 있는 스프링에서 데이터소스 implementation 하는 클래스
    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try {
            dataSource.setDriverClass((Class<? extends Driver>) Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
