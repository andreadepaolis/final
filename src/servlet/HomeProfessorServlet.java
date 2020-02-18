package servlet;

import bean.HomeworkBean;
import bean.ProfessorBean;
import controller.ControllerHomeProfessor;
import model.Argument;
import utils.BasicExcpetion;
import utils.Month;
import utils.MonthFactory;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String prof = "professor";
    String tst = "toast";
    String errr = "Error";
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomeProfessor.jsp");
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute(prof) == null) {
                throw new Exception("invalid session");
            }
            String cmd = request.getParameter("cmd");
            ProfessorBean p = (ProfessorBean) session.getAttribute(prof);
            ControllerHomeProfessor chp = new ControllerHomeProfessor();


            switch (cmd) {

                case "change_class":{
                    String newClass = request.getParameter("current_class");
                    p.setCurrentClass(newClass);
                    p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(),p.getCurrentMatter()));
                    p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrentClass(),p.getMatter().get(0)));
                    session.setAttribute(prof,p);
                    rd.forward(request,response);
                    return;

                }

                case "change_matter":{

                    String newMatter = request.getParameter("current_matter");
                    p.setCurrentMatter(newMatter);
                    p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(),newMatter));
                    p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrentClass(),newMatter));
                    session.setAttribute(prof,p);
                    rd.forward(request,response);
                    return;

                }



                case "deletehmw": {
                    String descprition = request.getParameter("desc");
                    for (HomeworkBean hmw : p.getHomework()) {

                        if (descprition.equals(hmw.getDescription())) {

                            if (chp.removeHmw(hmw)) {
                                Toast t = new Toast("Removed", "Homework removed correctly", 2);
                                request.setAttribute(tst, t);
                                p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(),p.getCurrentMatter()));

                                session.setAttribute(prof, p);
                                rd.include(request, response);

                            } else {

                                Toast t = new Toast(errr, "Try again", 1);
                                request.setAttribute(tst, t);
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
                        request.setAttribute(tst, t);
                        p.setHomework(chp.updateHomeworkList(p.getMatricola(),p.getCurrentClass(),p.getCurrentMatter()));

                        session.setAttribute(prof, p);
                        rd.include(request, response);
                    } else {
                        Toast t = new Toast(errr, "there is an error", 1);
                        request.setAttribute(tst, t);
                        rd.include(request, response);
                    }
                    break;
                }
                case "hmw":{

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(p.getCurrentDate());
                        String temp = request.getParameter("temp");
                        if(temp.equals("inc")){

                            cal.add(Calendar.DATE, +7);
                            p.setCurrentDate(cal.getTime());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(),p.getCurrentClass(),p.getCurrentDate());
                            p.setHomework(h);


                        }else if(temp.equals("dec")){
                            cal.add(Calendar.DATE, -7);
                            p.setCurrentDate(cal.getTime());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(),p.getCurrentClass(),p.getCurrentDate());
                            p.setHomework(h);
                        }
                        else if(temp.equals("today")){
                            p.setCurrentDate(new Date());
                            List<HomeworkBean> h = chp.scrollHomework(p.getMatricola(),p.getCurrentClass(),new Date());
                            p.setHomework(h);
                        }
                        session.setAttribute(prof,p);
                        rd.forward(request,response);
                        break;

                }

                case "newArg":{


                    String materia = p.getCurrentMatter();
                    String description = request.getParameter("description");
                    int index = chp.checkIndex(p.getArguments());
                    Argument arg = new Argument(p.getMatricola(),description,materia,p.getCurrentClass(),index+1);
                    if(chp.saveArg(arg)) {


                        Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                        request.setAttribute(tst, t);
                        p.setArguments(chp.reloadArgument(p.getMatricola(),p.getCurrentClass(),p.getCurrentMatter()));
                        session.setAttribute(prof, p);
                    } else{
                        Toast t = new Toast(errr, "Cannot save", 1);
                        request.setAttribute(tst, t);
                    }
                    rd.forward(request,response);
                    break;
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
                    try {
                        ProfessorRegister register = chp.getFullRegister(classe, m, materia);


                        if (register == null) throw new BasicExcpetion("Critical Error");

                        session.setAttribute("register", register);
                        response.sendRedirect("professorRegister.jsp");
                    } catch (Exception e) {
                          response.sendRedirect("index.jsp");
                    }

                    break;
                }
            }

        }catch(Exception e){
            Toast t = new Toast(errr,e.getMessage(),1);
            request.setAttribute(tst,t);
            rd.include(request,response);

        }
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //doGet

    }
}
