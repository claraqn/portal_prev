package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionMaker {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
//        //첫번째 Refactor->Extract->method 를 통해 자주 쓰는 코드를 설정해줌
//        //두번째(더 발전) abstract로 추상화한 다음 main 에 HallauserDao, JejuUserDao 를 만들어 db를 분리시켜줌
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        return DriverManager.getConnection(
//                "jdbc:mysql://localhost/user_db?" +
//                        "characterEncoding=utf-8&serverTimezone=UTC"
//                , "root", "rootpw"
//        );

}