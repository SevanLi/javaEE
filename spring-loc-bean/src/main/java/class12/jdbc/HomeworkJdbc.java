package class12.jdbc;

import class12.model.homework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HomeworkJdbc {

    public static void main(String[] args) {


        List<homework> list = selectAll();

        for (homework h : list){
            System.out.println(h.getContent());
        }
    }

    public static boolean addHomework(homework h){

        String sqlString = "insert into s_homework (title,content,create_time) values(?,?,?)";
        ApplicationContext ac = new ClassPathXmlApplicationContext("app-context.xml");
        DataSource ds = (DataSource)ac.getBean("datasource");

        int resultSet = 0;
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                ps.setString(1,h.getTitle());
                ps.setString(2,h.getContent());
                ps.setTimestamp(3,new Timestamp(h.getCreateTime().getTime()));
                resultSet = ps.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet > 0;

    }

    public static List<homework> selectAll(){

        String sqlString = "SELECT * FROM s_homework";

        ApplicationContext ac = new ClassPathXmlApplicationContext("app-context.xml");
        DataSource ds = (DataSource)ac.getBean("datasource");

        List<homework> list = new ArrayList<>();
        try(Connection connection =  ds.getConnection()) {
            try(Statement statement = connection.createStatement()){
                try(ResultSet resultSet = statement.executeQuery(sqlString)){
                    // 获取执行结果
                    while (resultSet.next()){
                        homework sh = new homework();
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
