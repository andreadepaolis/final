package servlet;

import bean.ProfessorBean;
import bean.StudentBean;
import bean.UserLoginBean;
import controller.ControllerHomeProfessor;
import controller.ControllerHomeStudent;
import controller.ControllerLogin;
import utils.Toast;
import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        ControllerLogin cl = new ControllerLogin();
        String matricola = request.getParameter("matricola");
        String password = request.getParameter("password");
        String cmd = request.getParameter("type");
        UserLoginBean u = cl.generateBean(matricola,password);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        if(cmd.equals("user")){
            StudentBean s = cl.validateStudent(u);
            if(s != null){
                ControllerHomeStudent cntl = new ControllerHomeStudent();

                s = cntl.full(s);
                s.setCurrentDate(new Date());
                HttpSession session = request.getSession();
                session.setAttribute("student",s);
                session.setMaxInactiveInterval(30 * 60);
                response.sendRedirect("HomeStudent.jsp");

            } else{
                Toast t = new Toast("Error","invalid password or matricola",1);
                request.setAttribute("toast",t);
                rd.include(request, response);
            }


        } else if(cmd.equals("professor")){
            try {
                ProfessorBean p = cl.validateProfessor(u);
                if (p != null) {
                    HttpSession session = request.getSession();
                    ControllerHomeProfessor cntl = new ControllerHomeProfessor();

                    p = cntl.full(p);
                    session.setAttribute("professor", p);
                    session.setMaxInactiveInterval(30 * 60);

                    response.sendRedirect("HomeProfessor.jsp");

                } else {
                    throw new Exception("Faild load data");

                }
            }catch (Exception e ){
                Toast t = new Toast("Error", "invalid password or matricola", 1);
                request.setAttribute("toast", t);
                rd.include(request, response);
            }

        }
    }

}