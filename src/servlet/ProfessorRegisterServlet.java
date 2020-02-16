package servlet;

import bean.ProfessorBean;
import bean.StudentBean;
import controller.ControllerHomeProfessor;
import utils.InputController;
import database.ProfessorDao;
import model.*;
import register.ProfessorRegister;
import utils.MonthFactory;
import utils.Toast;
import utils.month;

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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");
 if(session.getAttribute("professor")!= null && session.getAttribute("register")!= null) {
     String cmd = request.getParameter("cmd");
     ProfessorRegister register = (ProfessorRegister) session.getAttribute("register");

     ControllerHomeProfessor chp = new ControllerHomeProfessor();


     if (cmd.equals("mat")) {

         String materia = request.getParameter("materia");
         register.setCurrent_matter(materia);
         session.setAttribute("register", register);


     }
     if (cmd.equals("classe")) {
         String currentClass = request.getParameter("classe");
         register.setCurrent_class(currentClass);
         session.setAttribute("register", register);
     }

     if (cmd.equals("delete")) {
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

     if (cmd.equals("today")) {

         Calendar cal = Calendar.getInstance();
         MonthFactory mf = new MonthFactory();
         month m = mf.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
         register.setCurrent_month(m);
         session.setAttribute("register", register);

     }
     if (cmd.equals("random")) {


         List<StudentBean> list = register.getStudents();
         StudentBean extracted = null;
         try {
             extracted = chp.extract_random(list);
         } catch (NoSuchAlgorithmException e) {
                return;
          }
         request.setAttribute("random_student", extracted);
         rd.include(request, response);
         return;

     }

     if (cmd.equals("newAbsence")) {
         try {

             String tipo = request.getParameter("tipo");

             int matricola = Integer.parseInt(request.getParameter("matricola"));
             InputController inpCnt = InputController.getIstance();
             Date d = inpCnt.converDate(request.getParameter("data"));
             if (d == null || !inp_cnt.checkDate(d)) {

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


     }

     if (cmd.equals("ng")) {

         ProfessorBean p = (ProfessorBean) session.getAttribute("professor");
         try {
             int voto = Integer.parseInt(request.getParameter("voto"));

             String tipo = request.getParameter("tipo");
             String materia = request.getParameter("materia");
             int matricola = Integer.parseInt(request.getParameter("matricola"));
             int matricolaProfessore = p.getMatricola();
             String nomeProfessore = p.getLastname();
             InputController inp_cnt = InputController.getIstance();
             Date d = inp_cnt.converDate(request.getParameter("data"));
             if (d == null || !inp_cnt.checkDate(d)) {

                 Toast t = new Toast("Invalid Date", "Date is out from current year", 1);
                 request.setAttribute("toast", t);
                 rd.forward(request, response);
                 return;

             }

             Grades g = new Grades(matricola, materia, voto, tipo, matricolaProfessore, nomeProfessore, d);
             if (!inp_cnt.checkInRange(voto, 0, 10) || !inp_cnt.checkInt(voto)) {
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

             Toast t = new Toast(error, "check parameter e try again", 1);
             request.setAttribute("toast", t);
             rd.forward(request, response);
             return;
         }
     }

     if (cmd.equals("m")) {

         String month = request.getParameter("monthIndex");
         String year = request.getParameter("monthYear");
         month m = chp.getMonth(year, month);
         register.setCurrent_month(m);
         session.setAttribute("register", register);
     }

     doGet(request, response);
    } else{

             response.sendRedirect("index.jsp");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/professorRegister.jsp");

        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        ProfessorRegister register = (ProfessorRegister)session.getAttribute("register");
        register = chp.getFullRegister(register.getCurrent_class(),register.getCurrent_month(),register.getCurrent_matter());
        session.setAttribute("register",register);
        rd.include(request,response);
        }
    }

