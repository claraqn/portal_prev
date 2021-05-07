package kr.ac.jejunu;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    String name="clara";
    String password="1234";

    private static UserDao userDao;

    @BeforeAll
    public static void setup() throws ClassNotFoundException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("kr.ac.jejunu");
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("kr.ac.jejunu.user");
//        StaticApplicationContext applicationContext = new StaticApplicationContext();
//
//        BeanDefinition dataSourceBeanDefinition = new RootBeanDefinition(SimpleDriverDataSource.class);
//        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("driverClass",
//                Class.forName(System.getenv("DB_CLASSNAME")));
//        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("url",
//                System.getenv("DB_URL"));
//        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("username",
//                System.getenv("DB_USERNAME"));
//        dataSourceBeanDefinition.getPropertyValues().addPropertyValue("password",
//                System.getenv("DB_PASSWORD"));
//        applicationContext.registerBeanDefinition("dataSource", dataSourceBeanDefinition);
//
//        BeanDefinition jdbcContextBeanDeifinition = new RootBeanDefinition(JdbcTemplate.class);
//        jdbcContextBeanDeifinition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference("dataSource"));
//        applicationContext.registerBeanDefinition("jdbcContext", jdbcContextBeanDeifinition);
//
//        BeanDefinition beanDefinition = new RootBeanDefinition(UserDao.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference("jdbcContext"));
//        applicationContext.registerBeanDefinition("userDao", beanDefinition);
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("daoFactory.xml");
//        ApplicationContext applicationContext = new GenericGroovyApplicationContext("daoFactory.groovy");
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Integer id = 1;

        // UserDao 에서 받은 것 DaoFactory를 만들어 거기로 던져줌
        User user = userDao.findById(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName(name);
        user.setPassword((password));
        //여기서 new 를 통해 connectionMaker를 사용하는 것
        userDao.insert(user);
        assertThat(user.getId(), greaterThan(0));
        User insertedUser = userDao.findById(user.getId());
        assertThat(insertedUser.getName(), is(name));
        assertThat(insertedUser.getPassword(), is(password));
    }

    @Test
    public void update() throws SQLException {
        User user = new User();
        user.setName(name);
        user.setPassword((password));
        userDao.insert(user);

        String updatedName = "rinnie";
        String updatedPassword = "1111";
        user.setName(updatedName);

        user.setPassword(updatedPassword);

        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());
        assertThat(updatedUser.getName(),is(updatedName));
        assertThat(updatedUser.getPassword(),is(updatedPassword));
    }

    @Test
    public void delete() throws SQLException {
        User user = new User();
        user.setName(name);
        user.setPassword((password));
        userDao.insert(user);

        userDao.delete(user.getId());

        User deletedUser = userDao.findById(user.getId());

        assertThat(deletedUser, IsNull.nullValue());
    }

    //hanlla
//    @Test
//    public void getHalla() throws SQLException, ClassNotFoundException {
//        Integer id = 1;
//
//        UserDao userDao = new UserDao(connectionMaker);
//        User user = userDao.findById(id);
//        assertThat(user.getId(), is(id));
//        assertThat(user.getName(), is(name));
//        assertThat(user.getPassword(), is(password));
//    }
//
//    @Test
//    public void insertHalla() throws SQLException, ClassNotFoundException {
//        User user = new User();
//        user.setName(name);
//        user.setPassword((password));
//        UserDao userDao = new UserDao(connectionMaker);
//        userDao.insert(user);
//        assertThat(user.getId(), greaterThan(0));
//
//        User insertedUser = userDao.findById(user.getId());
//        assertThat(insertedUser.getName(), is(name));
//        assertThat(insertedUser.getPassword(), is(password));
//    }
}
