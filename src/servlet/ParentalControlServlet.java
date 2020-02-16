package servlet;

import bean.StudentBean;
import controller.ControllerHomeStudent;
import controller.ControllerStudent;
import model.Absences;
import utils.Toast;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ParentalControlServlet")
public class ParentalControlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        RequestDispatcher rd = getServletContext().getRequestDispatcher("/ParentalControl.jsp");
        ControllerStudent cs = new ControllerStudent();
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("student") == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            StudentBean s = (StudentBean)session.getAttribute("student");
            String cmd = request.getParameter("cmd");


            if(cmd.equals("Absences")){

                s.setAbsences(cs.loadAbsences(s.getMatricola()));



                rd.forward(request,response);
            }
            if(cmd.equals("giust")){


                    String pin = request.getParameter("pinvalue");
                    if(cs.verifyPin(pin,s.getMatricola())) {

                        int index = Integer.parseInt(request.getParameter("index"));
                        Absences a = s.getAbsences().get(index);
                        if (cs.manageAbsence(a) > 0) {

                            Toast t = new Toast("Justified!", "Absence has been correctly justified", 2);
                            request.setAttribute("toast", t);
                            rd.include(request, response);
                        } else {
                            Toast t = new Toast("Error","Something gone wrong",1);
                            request.setAttribute("toast",t);
                            rd.include(request,response);
                        }

                    } else {
                        Toast t = new Toast("Error","Pin incorrect",1);
                        request.setAttribute("toast",t);
                        rd.include(request,response);
                    }

            }



         } catch(Exception e){
                e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
