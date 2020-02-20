package servlet;

import bean.ProfessorBean;
import bean.StudentBean;
import controller.ControllerHomeProfessor;
import register.ProfessorRegister;
import utils.Month;
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
import java.util.List;

@WebServlet("/ProfessorRegisterServlet")
public class ProfessorRegisterServlet extends HttpServlet {
    private static final String ERROR = "Error";
    private static final String PR = "professor";
    private static final String REGISTER = "register";
    private static final String TOAST = "toast";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");

        try {
            HttpSession session = request.getSession(false);

            if (session.getAttribute(PR) != null && session.getAttribute(REGISTER) != null) {
                String cmd = request.getParameter("cmd");
                ProfessorRegister register = (ProfessorRegister) session.getAttribute(REGISTER);

                ControllerHomeProfessor chp = new ControllerHomeProfessor();

                if ("mat".equals(cmd)) {
                    String materia = request.getParameter("materia");
                    register.setCurrentMatter(materia);
                    session.setAttribute(REGISTER, register);
                } else if ("classe".equals(cmd)) {
                    String currentClass = request.getParameter("classe");
                    register.setCurrentClass(currentClass);
                    session.setAttribute(REGISTER, register);
                } else if ("delete".equals(cmd)) {

                    String temp = request.getParameter("type");

                    if (temp.equals("assenza"))
                        chp.deleteAbsence(register, request.getParameter("colIndex"), request.getParameter("rowIndex"));
                    else
                        chp.deleteGrades(register, request.getParameter("colIndex"), request.getParameter("rowIndex"));
                    Toast t = new Toast("Succes", "delete correctly", 2);
                    request.setAttribute(TOAST, t);

                } else if ("newAbsence".equals(cmd)) {

                    String tipo = request.getParameter("tipo");

                    String matricola = request.getParameter("matricola");
                    String d = request.getParameter("data");
                    chp.updateAbsence(tipo,matricola,d);
                    Toast t = new Toast("OK!", "Absence saved correctly", 2);
                    request.setAttribute(TOAST, t);



                } else if ("ng".equals(cmd)) {
                    ProfessorBean p = (ProfessorBean) session.getAttribute(PR);

                    String stvoto = request.getParameter("voto");
                    String tipo = request.getParameter("tipo");
                    String materia = request.getParameter("materia");
                    String matricola = request.getParameter("matricola");
                    String d = request.getParameter("data");
                    chp.generateGradesAndSave(stvoto,tipo,materia,matricola,p.getMatricola(),p.getLastname(),d);

                    Toast t = new Toast("Ok!", " Grades saved correctly", 2);
                    request.setAttribute(TOAST, t);


                } else {
                    response.sendRedirect("index.jsp");
                }
                doGet(request, response);

            }
        } catch (ToastException ts) {
            Toast t = new Toast(ts.getTitle(), ts.getMessage(), 1);
            request.setAttribute(TOAST, t);
            rd.forward(request, response);

        } catch (Exception e){
            Toast t = new Toast(ERROR, e.getMessage(), 1);
            request.setAttribute(TOAST, t);
            rd.forward(request, response);


        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");

        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        ProfessorRegister register = (ProfessorRegister) session.getAttribute(REGISTER);
        String cmd = request.getParameter("cmd");
        try {

            if (cmd.equals("month")) {
                String month = request.getParameter("monthIndex");
                String year = request.getParameter("monthYear");
                Month m = chp.getMonth(year, month);
                register.setCurrentMonth(m);
                session.setAttribute(REGISTER, register);
            } else if ("random".equals(cmd)) {
                List<StudentBean> list = register.getStudents();
                StudentBean extracted;

                extracted = chp.extractRandom(list);

                request.setAttribute("random_student", extracted);
                rd.include(request, response);
                return;

            } else if ("today".equals(cmd)) {
                register = chp.updateView(register);
                session.setAttribute(REGISTER, register);
            }
            register = chp.getFullRegister(register.getCurrentClass(), register.getCurrentMonth(), register.getCurrentMatter());
            session.setAttribute(REGISTER, register);
            rd.include(request, response);
        } catch (ToastException e) {
            Toast t = new Toast(ERROR, e.getMessage(), 1);
            request.setAttribute(TOAST, t);
        }catch (Exception e){
            Toast t = new Toast(ERROR, e.getMessage(), 1);
            request.setAttribute(TOAST, t);
            rd.forward(request, response);


        }
    }

}

