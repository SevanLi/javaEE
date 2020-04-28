package class12.jdbc;

import class12.model.homework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 22591
 */
@Configuration
public class HomeworkJdbc {
    private static ApplicationContext ct;
    static{
        ct=new AnnotationConfigApplicationContext(homework.class);
    }

//    public static void main(String[] args) {
//
//
//        List<homework> list = selectAll();
//
//        for (homework h : list){
//            System.out.println(h.getContent());
//        }
//    }

    public homework addHomework(homework h){

        String sqlString = "insert into s_homework (title, content, create_time) values(?,?,?)";
        ApplicationContext ac = new ClassPathXmlApplicationContext("app-context.xml");
        DataSource ds = (DataSource)ac.getBean("datasource");

        Connection connection =null;
        try{
            connection = ds.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                ps.setString(1,h.getTitle());
                ps.setString(2,h.getContent());
                ps.setTimestamp(3,new Timestamp(h.getCreateTime().getTime()));
                ps.executeUpdate();
                connection.commit();

            }
        } catch (SQLException e) {
            try {
                if(connection!=null) {
                    connection.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }finally{
            try {
                if(connection!=null) {
                    connection.rollback();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return h;

    }

    public static List<homework> selectAll(){

        String sqlString = "SELECT * FROM s_homework";

        ApplicationContext ac = new ClassPathXmlApplicationContext("app-context.xml");
        DataSource ds = (DataSource)ac.getBean("datasource");

        List<homework> list = new ArrayList<>();
        try(Connection connection =  ds.getConnection()) {
            try(Statement statement = connection.createStatement()){
                try(ResultSet resultSet = statement.executeQuery(sqlString)){
                    while (resultSet.next()){
                        homework sh =(homework)ct.getBean("homework");
                        sh.setId(resultSet.getLong("id"));
                        sh.setTitle(resultSet.getString("title"));
                        sh.setContent(resultSet.getString("content"));
                        sh.setCreateTime(resultSet.getTimestamp("create_time"));
                        list.add(sh);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
