package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JejuConnectionMaker implements ConnectionMaker {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/user_db?" +
                        "characterEncoding=utf-8&serverTimezone=UTC"
                , "root", "rootpw"
        );
        //edit configurations 을 통해 environment variables 추가
    }
}
