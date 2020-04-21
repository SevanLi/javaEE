package class12.controller;

import class12.jdbc.HomeworkJdbc;
import class12.model.homework;
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
public class postHomework extends HttpServlet {
    @RequestMapping(value = "/postHomework",method = RequestMethod.POST)
    protected String post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码

        homework hk = new homework();

        hk.setTitle(req.getParameter("title"));
        hk.setContent(req.getParameter("content"));
        Date date = new Date();
        hk.setCreateTime(date);

        HomeworkJdbc.addHomework(hk);

        return "allhomework.jsp";
    }
}