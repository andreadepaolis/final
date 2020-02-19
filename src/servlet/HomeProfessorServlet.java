package servlet;

import bean.HomeworkBean;
import bean.ProfessorBean;
import controller.ControllerHomeProfessor;
import model.Argument;
import utils.*;
import register.ProfessorRegister;
import utils.Toast;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet("/HomeProfessorServlet")
public class HomeProfessorServlet extends HttpServlet {


   private static final String PROF = "professor";
   private static final String TST = "toast";
   private static final String ERR = "Error";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeProfessor.jsp");
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute(PROF) == null) {
                throw new BasicExcpetion("invalid session");
            }
            String cmd = request.getParameter("cmd");
            ProfessorBean p = (ProfessorBean) session.getAttribute(PROF);
            ControllerHomeProfessor chp = new ControllerHomeProfessor();


            switch (cmd) {

                case "deletehmw": {
                    String descprition = request.getParameter("desc");
                    chp.findAndRemove(p.getHomework(), descprition);

                    Toast t = new Toast("Success", "Homework removed correctly", 2);
                    request.setAttribute(TST, t);
                    p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(), p.getCurrentMatter()));
                    session.setAttribute(PROF, p);
                    rd.include(request, response);


                     break;

                }
                case "newhw": {
                    String classe = request.getParameter("classe");
                    String materia = request.getParameter("materia");
                    String data = request.getParameter("data");
                    String description = request.getParameter("descrizione");
                    HomeworkBean hmwbean = chp.generateHomeworkBean(classe, description, materia, data, p.getMatricola());

                    chp.save(hmwbean);

                        Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                        request.setAttribute(TST, t);
                        p.setHomework(chp.updateHomeworkList(p.getMatricola(),p.getCurrentClass(),p.getCurrentMatter()));

                        session.setAttribute(PROF, p);
                        rd.include(request, response);

                    break;
                }
                case "hmw":{

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(p.getCurrentDate());
                        String temp = request.getParameter("temp");
                    switch (temp) {
                        case "inc": {

                            cal.add(Calendar.DATE, +7);
                            p.setCurrentDate(cal.getTime());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(), p.getCurrentClass(), p.getCurrentDate());
                            p.setHomework(h);


                            break;
                        }
                        case "dec": {
                            cal.add(Calendar.DATE, -7);
                            p.setCurrentDate(cal.getTime());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(), p.getCurrentClass(), p.getCurrentDate());
                            p.setHomework(h);
                            break;
                        }
                        case "today": {
                            p.setCurrentDate(new Date());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(), p.getCurrentClass(), new Date());
                            p.setHomework(h);
                            break;
                        } default:{
                            throw new ToastException(ERR,"invalid request");
                        }
                    }
                        session.setAttribute(PROF,p);
                        rd.forward(request,response);
                        break;

                }

                case "newArg":{


                    String materia = p.getCurrentMatter();
                    String description = request.getParameter("description");
                    int index = chp.checkIndex(p.getArguments());
                    Argument arg = new Argument(p.getMatricola(),description,materia,p.getCurrentClass(),index+1);
                    chp.saveArg(arg);


                        Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                        request.setAttribute(TST, t);
                        p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrentClass(),p.getCurrentMatter()));
                        session.setAttribute(PROF, p);
                    rd.forward(request,response);
                    break;
                }


                default:{
                    throw  new ToastException(ERR,"invalid request");
                }
            }

        }catch(Exception e){
            Toast t = new Toast(ERR,e.getMessage(),1);
            request.setAttribute(TST,t);
            rd.include(request,response);
        }
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeProfessor.jsp");
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute(PROF) == null) {
                response.sendRedirect("index.jsp");
            }
            String cmd = request.getParameter("cmd");
            ProfessorBean p = (ProfessorBean) session.getAttribute(PROF);
            ControllerHomeProfessor chp = new ControllerHomeProfessor();

            switch (cmd) {

                case "change_class":{
                    String newClass = request.getParameter("current_class");
                    p.setCurrentClass(newClass);
                    p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(),p.getCurrentMatter()));
                    p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrentClass(),p.getMatter().get(0)));
                    session.setAttribute(PROF,p);
                    rd.forward(request,response);
                    return;


                }

                case "change_matter":{

                    String newMatter = request.getParameter("current_matter");
                    p.setCurrentMatter(newMatter);
                    p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(),newMatter));
                    p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrentClass(),newMatter));
                    session.setAttribute(PROF,p);
                    rd.forward(request,response);
                    return;

                }
                case "Register": {
                    //mont //1 materia //1 classe e il registro

                    Calendar cal = Calendar.getInstance();
                    MonthFactory f = new MonthFactory();
                    Date d = new Date();
                    cal.setTime(d);
                    Month m = f.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

                    String materia = p.getMatter().get(0);
                    String classe = p.getClassi().get(0);
                        ProfessorRegister register = chp.getFullRegister(classe, m, materia);


                        if (register == null) throw new ToastException(ERR,"Critical Error");

                        session.setAttribute("register", register);
                        response.sendRedirect("professorRegister.jsp");

                    break;
                }
                default:{
                    throw  new ToastException(ERR,"Invalid request");
                }
            }


        } catch (ToastException te){

            Toast t = new Toast(te.getTitle(), te.getMessage(), 1);
            request.setAttribute(TST, t);
            rd.forward(request,response);

        }
    }
}
