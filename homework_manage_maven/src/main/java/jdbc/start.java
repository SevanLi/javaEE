package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class start {
    public static void main(String[] args) {
        ConnectionPool cp = new ConnectionPool(3);
        for(int i = 0; i < 100; i++ ){
            new WorkingThread("t"+i,cp).start();

        }
    }
    static class WorkingThread extends Thread {
        private ConnectionPool cp;

        public WorkingThread(String name, ConnectionPool cp) {
            super(name);
            this.cp = cp;
        }

        public void run() {
            Connection c = cp.getConnection();

            System.out.println(this.getName() + ":\t 获取了一根连接，并开始工作");

            try (Statement st = c.createStatement()) {

                //占用连接耗时3s
                Thread.sleep(3000);
                //hero为数据库表
                st.execute("select * from hero");

            } catch (SQLException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cp.returnConnection(c);
        }
    }
}

