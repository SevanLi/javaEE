package class12.controller;

import class12.jdbc.HomeworkJdbc;
import class12.jdbc.StudentJdbc;
import class12.model.student;
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
public class addstudent extends HttpServlet {

    @RequestMapping(value = "/addstudent",method = RequestMethod.POST)
    protected String addstudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        student st = new student();
        st.setId(req.getParameter("id"));
        st.setName(req.getParameter("name"));
        Date date = new Date();
        st.setCreateTime(date);
        StudentJdbc.addstudent(st);

        return "allstudent.jsp";
    }
}