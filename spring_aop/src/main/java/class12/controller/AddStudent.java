package class12.controller;

import class12.jdbc.StudentJdbc;
import class12.model.student;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * @author 22591
 */
@Aspect
@Controller
public class AddStudent extends HttpServlet {
    private static ApplicationContext ct;
    static{
        ct=new AnnotationConfigApplicationContext(student.class);
    }

    @Before(value = "execution(* class12.jdbc.StudentJdbc.*(..)) && args(st)")
    public void before(student st){
        System.out.println("执行添加学生："+st.getName() + "前");
    }

    @After(value = "execution(* class12.jdbc.StudentJdbc.*(..)) && args(st)")
    public void after(student st){
        System.out.println("执行添加学生："+st.getName() + "后");
    }


    @RequestMapping(value = "/addstudent",method = RequestMethod.POST)
    protected String addstudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        student st = (student)ct.getBean("student");
        st.setId(req.getParameter("id"));
        st.setName(req.getParameter("name"));
        Date date = new Date();
        st.setCreateTime(date);

        ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext("app-servlet.xml");
        StudentJdbc sj=ac.getBean(StudentJdbc.class);
        sj.addstudent(st);

        return "allstudent.jsp";
    }
}