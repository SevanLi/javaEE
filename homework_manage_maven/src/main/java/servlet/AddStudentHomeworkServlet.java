package servlet;


import jdbc.StudentHomeworkJdbc;
import model.StudentHomework;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class AddStudentHomeworkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        StudentHomework st = new StudentHomework();

        st.setStudentId(req.getParameter("id"));
        st.setHomeworkId(req.getParameter("hwid"));
        st.setHomeworkTitle(req.getParameter("hwname"));
        st.setHomeworkContent(req.getParameter("content"));
        Date date = new Date();
        st.setCreateTime(date);
        System.out.println("到这里没问题1");

        boolean result = StudentHomeworkJdbc.addStudentHomework(st);

        System.out.println("到这里没问题2");
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
