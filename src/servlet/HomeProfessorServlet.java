package servlet;

import bean.HomeworkBean;
import bean.ProfessorBean;
import controller.ControllerHomeProfessor;
import model.Argument;
import utils.MonthFactory;
import utils.month;
import model.Homework;
import model.Professor;
import model.Student;
import register.ProfessorRegister;
import utils.InputController;
import utils.Toast;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/HomeProfessorServlet")
public class HomeProfessorServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeProfessor.jsp");
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("professor") == null) {
                throw new Exception("invalid session");
            }
            String cmd = request.getParameter("cmd");
            ProfessorBean p = (ProfessorBean) session.getAttribute("professor");
            ControllerHomeProfessor chp = new ControllerHomeProfessor();


            switch (cmd) {

                case "change_class":{
                    String new_class = request.getParameter("current_class");
                    p.setCurrent_class(new_class);
                    p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrent_class()));
                    p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrent_class()));
                    session.setAttribute("professor",p);
                    rd.forward(request,response);
                    return;
                }



                case "deletehmw": {
                    String descprition = request.getParameter("desc");
                    for (HomeworkBean hmw : p.getHomework()) {

                        if (descprition.equals(hmw.getDescription())) {

                            if (chp.removeHmw(hmw)) {
                                Toast t = new Toast("Removed", "Homework removed correctly", 2);
                                request.setAttribute("toast", t);
                                p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrent_class()));

                                session.setAttribute("professor", p);
                                rd.include(request, response);

                            } else {

                                Toast t = new Toast("Error", "Try again", 1);
                                request.setAttribute("toast", t);
                                rd.include(request, response);
                            }
                        }
                    }

                    break;
                }
                case "newhw": {
                    String classe = request.getParameter("classe");
                    String materia = request.getParameter("materia");
                    String data = request.getParameter("data");
                    String description = request.getParameter("descrizione");
                    HomeworkBean hmwbean = chp.generateHomeworkBean(classe, description, materia, data, p.getMatricola());

                    if (chp.save(hmwbean)) {

                        Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                        request.setAttribute("toast", t);
                        p.setHomework(chp.updateHomeworkList(p.getMatricola(),p.getCurrent_class()));

                        session.setAttribute("professor", p);
                        rd.include(request, response);
                    } else {
                        Toast t = new Toast("Error", "there is an error", 1);
                        request.setAttribute("toast", t);
                        rd.include(request, response);
                    }
                    break;
                }
                case "hmw":{

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(p.getCurrentDate());
                        String temp = request.getParameter("temp");
                        System.out.println(temp);
                        if(temp.equals("inc")){

                            cal.add(Calendar.DATE, +7);
                            p.setCurrentDate(cal.getTime());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(),p.getCurrent_class(),p.getCurrentDate());
                            p.setHomework(h);


                        }else if(temp.equals("dec")){
                            cal.add(Calendar.DATE, -7);
                            p.setCurrentDate(cal.getTime());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(),p.getCurrent_class(),p.getCurrentDate());
                            p.setHomework(h);
                        }
                        else if(temp.equals("today")){
                            p.setCurrentDate(new Date());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(),p.getCurrent_class(),new Date());
                            p.setHomework(h);
                        }
                        session.setAttribute("professor",p);
                        rd.forward(request,response);
                        break;

                }

                case "newArg":{

                    String classe = request.getParameter("classe");
                    String materia = request.getParameter("materia");
                    String description = request.getParameter("description");
                    int index = chp.checkIndex(p.getArguments(),p.getCurrent_class(),materia);
                    Argument arg = new Argument(p.getMatricola(),description,materia,p.getCurrent_class(),index);
                    if(chp.saveArg(arg)) {


                        Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                        request.setAttribute("toast", t);
                        p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrent_class()));
                        session.setAttribute("professor", p);
                    } else{
                        Toast t = new Toast("Error", "Cannot save", 1);
                        request.setAttribute("toast", t);
                    }
                    rd.forward(request,response);
                    break;
                }


                case "Register": {
                    //mont //1 materia //1 classe e il registro

                    Calendar cal = GregorianCalendar.getInstance();
                    MonthFactory f = new MonthFactory();
                    Date d = new Date();
                    month m;
                    cal.setTime(d);
                    m = f.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

                    String materia = p.getMatter().get(0);
                    String classe = p.getClassi().get(0);
                    try {
                        ProfessorRegister register = chp.getFullRegister(classe, m, materia);


                        if (register == null) throw new Exception("erorre registrio");

                        session.setAttribute("register", register);
                        response.sendRedirect("professorRegister.jsp");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    break;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            Toast t = new Toast("Error","there is an error",1);
            request.setAttribute("toast",t);
            rd.include(request,response);

        }





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
