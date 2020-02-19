package servlet;

import bean.GradesPageBean;
import bean.StudentBean;
import controller.ControllerHomeStudent;
import model.Argument;
import model.Homework;
import utils.Toast;
import utils.ToastException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/HomeStudentServlet")
public class HomeStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeStudent.jsp");
        ControllerHomeStudent chs = new ControllerHomeStudent();
        String std = "student";
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute(std) == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            StudentBean s = (StudentBean)session.getAttribute(std);
            String cmd = request.getParameter("cmd");

            if(cmd.equals("matter")){

                try {
                    String mat = request.getParameter("matt");
                    s.setCurrentMatter(mat);
                    List<Argument> list = chs.reload(s.getCurrentMatter(), s.getClasse());
                    s.setArg(list);
                    session.setAttribute(std, s);
                    rd.forward(request, response);
                }catch (ToastException e){
                    Toast t = new Toast("Error",e.getMessage(),1);
                    request.setAttribute("toast",t);
                    rd.forward(request, response);
                }
            }

            if(cmd.equals("Grades")){

                GradesPageBean page = chs.fullGradesPage(s);
                session.setAttribute("gradesPage",page);
                response.sendRedirect("GradesStudent.jsp");

            }

            if(cmd.equals("hmw")){
                Calendar cal = Calendar.getInstance();
                cal.setTime(s.getCurrentDate());
                String temp = request.getParameter("temp");
                switch (temp) {
                    case "inc": {

                        cal.add(Calendar.DATE, +1);
                        s.setCurrentDate(cal.getTime());
                        List<Homework> h = chs.scrollHomework(s.getClasse(), s.getCurrentDate());
                        s.setHomework(h);


                        break;
                    }
                    case "dec": {
                        cal.add(Calendar.DATE, -1);
                        s.setCurrentDate(cal.getTime());
                        List<Homework> h = chs.scrollHomework(s.getClasse(), s.getCurrentDate());
                        s.setHomework(h);
                        break;
                    }
                    case "today": {
                        s.setCurrentDate(new Date());
                        List<Homework> h = chs.scrollHomework(s.getClasse(), new Date());
                        s.setHomework(h);
                        break;
                    }
                    default:{
                        break;
                    }
                }
              session.setAttribute(std,s);
              rd.forward(request,response);
            }

        }catch (ToastException e){
            Toast t = new Toast("Error",e.getMessage(),1);
            request.setAttribute("toast",t);
            }
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //doGet
    }
}
