package servlet;

import bean.GradesPageBean;
import bean.MatterBean;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/GradesStudentServlet")
public class GradesStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String gp = "gradesPage";
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/GradesStudent.jsp");
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("student") == null || session.getAttribute(gp)== null) {
                response.sendRedirect("index.jsp");
                return;
            }
            GradesPageBean gpb = (GradesPageBean) session.getAttribute(gp);

            String cmd = request.getParameter("cmd");

            if(cmd.equals("materia")) {

                String mat = request.getParameter("materia");
                MatterBean matter = null;
                for (MatterBean m : gpb.getMatter()) {
                    if (m.getMateria().equals(mat))
                        matter = m;
                }
                gpb.setCurrentMatter(matter);
                session.setAttribute(gp,gpb);
                rd.forward(request,response);
            }

        }catch (Exception e){

            rd.forward(request,response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doGet
    }
}
