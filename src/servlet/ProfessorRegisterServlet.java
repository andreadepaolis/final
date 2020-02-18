package servlet;

import bean.ProfessorBean;
import bean.StudentBean;
import controller.ControllerHomeProfessor;
import utils.*;
import database.ProfessorDao;
import model.*;
import register.ProfessorRegister;

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

@WebServlet("/ProfessorRegisterServlet")
public class ProfessorRegisterServlet extends HttpServlet {
    private static final String ERROR = "Error";
    private static final String PR = "professor";
    private static final String REGISTER = "register";
    private static final String TOAST = "toast";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");
 if(session.getAttribute(PR)!= null && session.getAttribute(REGISTER)!= null) {
     String cmd = request.getParameter("cmd");
     ProfessorRegister register = (ProfessorRegister) session.getAttribute(REGISTER);

     ControllerHomeProfessor chp = new ControllerHomeProfessor();

     switch (cmd) {
         case "mat": {

             String materia = request.getParameter("materia");
             register.setCurrentMatter(materia);
             session.setAttribute(REGISTER, register);
            break;
         }
         case "classe": {
             String currentClass = request.getParameter("classe");
             register.setCurrentClass(currentClass);
             session.setAttribute(REGISTER, register);
             break;
         }

         case "delete": {
             try {
                 boolean res;
                 String temp = request.getParameter("type");

                 if (temp.equals("assenza"))
                     res = chp.deleteAbsence(register, request.getParameter("colIndex"), request.getParameter("rowIndex"));
                 else
                     res = chp.deleteGrades(register, request.getParameter("colIndex"), request.getParameter("rowIndex"));
                 if (!res) {
                     Toast t = new Toast(ERROR, "Ops!", 1);
                     request.setAttribute(TOAST
                             , t);
                     rd.forward(request, response);
                     return;
                 }

             } catch (Exception e) {
                 response.sendRedirect("index.jsp");
             }
             break;
         }

         case "today": {

             Calendar cal = Calendar.getInstance();
             MonthFactory mf = new MonthFactory();
             Month m = mf.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
             register.setCurrentMonth(m);
             session.setAttribute(REGISTER, register);
             break;

         }
         case "random": {


             List<StudentBean> list = register.getStudents();
             StudentBean extracted = null;
             try {
                 extracted = chp.extractRandom(list);
             } catch (ToastException e) {
                 Toast t = new Toast(ERROR, e.getMessage(), 1);
                 request.setAttribute(TOAST, t);
             }
             request.setAttribute("random_student", extracted);
             rd.include(request, response);
             return;

         }

         case "newAbsence": {
             try {

                 String tipo = request.getParameter("tipo");

                 int matricola = Integer.parseInt(request.getParameter("matricola"));
                 InputController inpCnt = InputController.getIstance();
                 Date d = inpCnt.converDate(request.getParameter("data"));
                 if (d == null || !inpCnt.checkDate(d)) {

                     Toast t = new Toast(ERROR, "Invalid Date", 1);
                     request.setAttribute(TOAST, t);
                     rd.forward(request, response);
                     return;

                 }

                 chp.saveAbsence(matricola, tipo, d);


             } catch (ToastException e) {

                 Toast t = new Toast(ERROR, e.getMessage(), 1);
                 request.setAttribute(TOAST
                         , t);
                 rd.forward(request, response);
                 return;

             }
             break;
         }

         case "ng": {

             ProfessorBean p = (ProfessorBean) session.getAttribute(PR);
             try {
                 int voto = Integer.parseInt(request.getParameter("voto"));

                 String tipo = request.getParameter("tipo");
                 String materia = request.getParameter("materia");
                 int matricola = Integer.parseInt(request.getParameter("matricola"));
                 int matricolaProfessore = p.getMatricola();
                 String nomeProfessore = p.getLastname();
                 InputController inpCnt = InputController.getIstance();
                 Date d = inpCnt.converDate(request.getParameter("data"));
                 if (d == null || !inpCnt.checkDate(d)) {

                     Toast t = new Toast("Invalid Date", "Date is out from current year", 1);
                     request.setAttribute(TOAST
                             , t);
                     rd.forward(request, response);
                     return;

                 }

                 Grades g = new Grades(matricola, materia, voto, tipo, matricolaProfessore, nomeProfessore, d);
                 if (!inpCnt.checkInRange(voto, 0, 10) || !inpCnt.checkInt(voto)) {
                     Toast t = new Toast(ERROR, "invalid vote", 1);
                     request.setAttribute(TOAST
                             , t);
                     rd.forward(request, response);
                     return;
                 }
                 int result = ProfessorDao.saveGrades(g);
                 if (result > 0) {
                     Toast t = new Toast("ok", "saved correctly", 2);
                     request.setAttribute(TOAST
                             , t);

                 } else {
                     Toast t = new Toast(ERROR, "Saving error", 1);
                     request.setAttribute(TOAST
                             , t);
                     rd.forward(request, response);
                     return;
                 }

             } catch (Exception e) {

                 Toast t = new Toast(ERROR, "check parameter e try again", 1);
                 request.setAttribute(TOAST
                         , t);
                 rd.forward(request, response);
                 return;
             }
             break;
         }

         case "month": {
             String month = request.getParameter("monthIndex");
             String year = request.getParameter("monthYear");
             Month m = chp.getMonth(year, month);
             register.setCurrentMonth(m);
             session.setAttribute(REGISTER, register);
             break;
         }
         default: {
             response.sendRedirect("index.jsp");
         }

        }
            doGet(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");

        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        ProfessorRegister register = (ProfessorRegister)session.getAttribute(REGISTER);
        try {
            register = chp.getFullRegister(register.getCurrentClass(),register.getCurrentMonth(),register.getCurrentMatter());
        } catch (ToastException e) {
            Toast t = new Toast(ERROR, e.getMessage(), 1);
            request.setAttribute(TOAST, t);
        }
        session.setAttribute(REGISTER,register);
        rd.include(request,response);
        }
    }

