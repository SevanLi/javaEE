package class12.jdbc;

import class12.model.StudentHomework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 22591
 */
@Configuration
public class StudentHomeworkJdbc {
    private static AnnotationConfigApplicationContext ct;
    static{
        ct = new AnnotationConfigApplicationContext();
        ct.register(StudentHomework.class);
        ct.refresh();
    }
//    public static void main(String[] args) {
//        List<StudentHomework> list = selectAll();
//
//        for (StudentHomework sh : list){
//            System.out.println(sh.getHomeworkContent());
//        }
//    }

    public StudentHomework addStudentHomework(StudentHomework sh){

        String sqlString = "insert into s_student_homework (student_id,homework_id,homework_title,homework_content,create_time) values(?,?,?,?,?)";
        ApplicationContext ac = new ClassPathXmlApplicationContext("app-context.xml");
        DataSource ds = (DataSource)ac.getBean("datasource");

        Connection connection =null;
        try{
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                ps.setString(1,sh.getStudentId());
                ps.setString(2,sh.getHomeworkId());
                ps.setString(3,sh.getHomeworkTitle());
                ps.setString(4,sh.getHomeworkContent());
                ps.setTimestamp(5,new Timestamp(sh.getCreateTime().getTime()));
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

        return sh;
    }

    public static List<StudentHomework> selectAll(){


        String sqlString = "SELECT * FROM s_student_homework";
        ApplicationContext ac = new ClassPathXmlApplicationContext("app-context.xml");
        DataSource ds = (DataSource)ac.getBean("datasource");

        List<StudentHomework> list = new ArrayList<>();
        try(Connection connection =  ds.getConnection()) {
            try(Statement statement = connection.createStatement()){
                try(ResultSet resultSet = statement.executeQuery(sqlString)){
                    while (resultSet.next()){
                        StudentHomework sh =(StudentHomework) ct.getBean(StudentHomework.class);
                        sh.setId(resultSet.getLong("id"));
                        sh.setStudentId(resultSet.getString("student_id"));
                        sh.setHomeworkId(resultSet.getString("homework_id"));
                        sh.setHomeworkTitle(resultSet.getString("homework_title"));
                        sh.setHomeworkContent(resultSet.getString("homework_content"));
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
