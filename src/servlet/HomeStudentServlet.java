package servlet;

import bean.GradesPageBean;

import bean.MatterBean;
import bean.StudentBean;
import controller.ControllerHomeStudent;
import model.Homework;

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
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("student") == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            StudentBean s = (StudentBean)session.getAttribute("student");
            String cmd = request.getParameter("cmd");


            if(cmd.equals("Grades")){

                GradesPageBean page = new GradesPageBean();
                page.setStudent(s);
                List<MatterBean> list = chs.getMatterBean(s.getMatricola(),s.getClasse());
                page.setMatter(list);
                page.setCurrentMatter(list.get(0));
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
                }
              session.setAttribute("student",s);
              rd.forward(request,response);
            }

        }catch (Exception e){
                    rd.forward(request,response);
            }
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
