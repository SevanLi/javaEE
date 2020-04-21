package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {


    List<Connection> cs = new ArrayList<Connection>();

    int size;

    public ConnectionPool(int size) {
        this.size = size;
        init();
    }

    public void init() {
        //这里恰恰不能使用try-with-resource的方式，因为这些连接都需要是"活"的，不要被自动关闭了
        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0; i < size; i++) {
                Connection c = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1/user?characterEncoding=UTF-8",
                        "root","admin");
                System.out.println("完成第"+ ( i + 1 ) +"个连接");
                cs.add(c);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //synchronized 线程安全性
    public synchronized Connection getConnection() {
        while (cs.isEmpty()) {
            try {
                //如果连接池中没有连接可用则等待
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //移除第一个连接池
        Connection c = cs.remove(0);
        return c;
    }

    public synchronized void returnConnection(Connection c) {
        //使用完给回连接池中
        cs.add(c);
        //唤醒wait告诉它有新的连接池可用
        this.notifyAll();
    }

}


