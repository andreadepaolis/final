package servlet;

import bean.GradesPageBean;
import bean.StudentBean;
import controller.ControllerHomeStudent;

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


@WebServlet("/HomeStudentServlet")
public class HomeStudentServlet extends HttpServlet {

    private static final String STD = "student";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeStudent.jsp");
        ControllerHomeStudent chs = new ControllerHomeStudent();
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute(STD) == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            StudentBean s = (StudentBean) session.getAttribute(STD);
            String cmd = request.getParameter("cmd");

            if (cmd.equals("matter")) {

                String mat = request.getParameter("matt");
                s = chs.changeMatter(s, mat);
                session.setAttribute(STD, s);
                rd.forward(request, response);

            }
            if (cmd.equals("hmw")) {

                String temp = request.getParameter("temp");
                switch (temp) {
                    case "inc": {

                        chs.updateHomework(s, 1);

                        break;
                    }
                    case "dec": {
                        s = chs.updateHomework(s, -1);

                        break;
                    }
                    case "today": {
                        s = chs.updateHomework(s, 0);
                    }
                    default: {
                        break;
                    }
                }
                session.setAttribute(STD, s);
                rd.forward(request, response);
            }

        } catch (ToastException e) {
            Toast t = new Toast(e.getTitle(), e.getMessage(), 1);
            request.setAttribute("toast", t);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute(STD) == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            StudentBean s = (StudentBean) session.getAttribute(STD);
            String cmd = request.getParameter("cmd");


            if (cmd.equals("Grades")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeStudent.jsp");
                ControllerHomeStudent chs = new ControllerHomeStudent();
                GradesPageBean page = chs.fullGradesPage(s);
                session.setAttribute("gradesPage", page);
                response.sendRedirect("GradesStudent.jsp");

            }

        } catch (ToastException e) {
            Toast t = new Toast(e.getTitle(), e.getMessage(), 1);
            request.setAttribute("toast", t);
        }
    }
}
