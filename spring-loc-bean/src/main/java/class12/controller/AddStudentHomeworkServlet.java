package class12.controller;

import class12.jdbc.StudentHomeworkJdbc;
import class12.model.StudentHomework;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class AddStudentHomeworkServlet extends HttpServlet {

    @RequestMapping(value = "/AddStudentHomeworkServlet",method = RequestMethod.POST)
    protected String post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        StudentHomework st = new StudentHomework();

        st.setStudentId(req.getParameter("id"));
        st.setHomeworkId(req.getParameter("hwid"));
        st.setHomeworkTitle(req.getParameter("hwname"));
        st.setHomeworkContent(req.getParameter("content"));
        Date date = new Date();
        st.setCreateTime(date);
        System.out.println(req.getParameter("content"));

        StudentHomeworkJdbc.addStudentHomework(st);
        return "index.jsp";
    }
}
