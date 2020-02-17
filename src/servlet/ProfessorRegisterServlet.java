package servlet;

import bean.ProfessorBean;
import bean.StudentBean;
import controller.ControllerHomeProfessor;
import utils.InputController;
import database.ProfessorDao;
import model.*;
import register.ProfessorRegister;
import utils.Month;
import utils.MonthFactory;
import utils.Toast;
import utils.Month;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/ProfessorRegisterServlet")
public class ProfessorRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String error = "Error";
    String r = "register";
    String pr = "professor";
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");
 if(session.getAttribute(pr)!= null && session.getAttribute(r)!= null) {
     String cmd = request.getParameter("cmd");
     ProfessorRegister register = (ProfessorRegister) session.getAttribute(r);

     ControllerHomeProfessor chp = new ControllerHomeProfessor();

     switch (cmd) {
         case "mat": {

             String materia = request.getParameter("materia");
             register.setCurrentMatter(materia);
             session.setAttribute(r, register);
            break;
         }
         case "classe": {
             String currentClass = request.getParameter("classe");
             register.setCurrentClass(currentClass);
             session.setAttribute(r, register);
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
                     Toast t = new Toast(error, "Ops!", 1);
                     request.setAttribute("toast", t);
                     rd.forward(request, response);
                     return;
                 }

             } catch (Exception e) {
                 response.sendRedirect("index.jsp");
             }
         }

         case "today": {

             Calendar cal = Calendar.getInstance();
             MonthFactory mf = new MonthFactory();
             Month m = mf.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
             register.setCurrentMonth(m);
             session.setAttribute(r, register);
             break;

         }
         case "random": {


             List<StudentBean> list = register.getStudents();
             StudentBean extracted = null;
             try {
                 extracted = chp.extractRandom(list);
             } catch (NoSuchAlgorithmException e) {
                 return;
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

                     Toast t = new Toast(error, "Invalid Date", 1);
                     request.setAttribute("toast", t);
                     rd.forward(request, response);
                     return;

                 }

                 Absences a = new Absences(matricola, tipo, d, 1);

                 int result = ProfessorDao.saveAbsence(a);
                 if (result > 0) {
                     Toast t = new Toast("ok", "saved correctly", 2);
                     request.setAttribute("toast", t);

                 } else {
                     Toast t = new Toast(error, "Something gone wrong", 1);
                     request.setAttribute("toast", t);
                     rd.forward(request, response);
                     return;

                 }

             } catch (Exception e) {

                 Toast t = new Toast(error, "check parameter e try again", 1);
                 request.setAttribute("toast", t);
                 rd.forward(request, response);
                 return;

             }
             break;
         }

         case "ng": {

             ProfessorBean p = (ProfessorBean) session.getAttribute(pr);
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
                     request.setAttribute("toast", t);
                     rd.forward(request, response);
                     return;

                 }

                 Grades g = new Grades(matricola, materia, voto, tipo, matricolaProfessore, nomeProfessore, d);
                 if (!inpCnt.checkInRange(voto, 0, 10) || !inpCnt.checkInt(voto)) {
                     Toast t = new Toast(error, "invalid vote", 1);
                     request.setAttribute("toast", t);
                     rd.forward(request, response);
                     return;
                 }
                 int result = ProfessorDao.saveGrades(g);
                 if (result > 0) {
                     Toast t = new Toast("ok", "saved correctly", 2);
                     request.setAttribute("toast", t);

                 } else {
                     Toast t = new Toast(error, "Saving error", 1);
                     request.setAttribute("toast", t);
                     rd.forward(request, response);
                     return;
                 }

             } catch (Exception e) {
                e.printStackTrace();
                 Toast t = new Toast(error, "check parameter e try again", 1);
                 request.setAttribute("toast", t);
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
             session.setAttribute(r, register);
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
        ProfessorRegister register = (ProfessorRegister)session.getAttribute("register");
        register = chp.getFullRegister(register.getCurrentClass(),register.getCurrentMonth(),register.getCurrentMatter());
        session.setAttribute("register",register);
        rd.include(request,response);
        }
    }

