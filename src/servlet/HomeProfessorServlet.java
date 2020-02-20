package servlet;

import bean.HomeworkBean;
import bean.ProfessorBean;
import controller.ControllerHomeProfessor;
import model.Argument;
import register.ProfessorRegister;
import utils.BasicExcpetion;
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


            if ("deletehmw".equals(cmd)) {
                String descprition = request.getParameter("desc");
                chp.findAndRemove(p.getHomework(), descprition);

                Toast t = new Toast("Success", "Homework removed correctly", 2);
                request.setAttribute(TST, t);
                p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(), p.getCurrentMatter()));
                session.setAttribute(PROF, p);
                rd.include(request, response);


            } else if ("newhw".equals(cmd)) {
                String classe = request.getParameter("classe");
                String materia = request.getParameter("materia");
                String data = request.getParameter("data");
                String description = request.getParameter("descrizione");
                HomeworkBean hmwbean = chp.generateHomeworkBean(classe, description, materia, data, p.getMatricola());

                chp.save(hmwbean);

                Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                request.setAttribute(TST, t);
                p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(), p.getCurrentMatter()));

                session.setAttribute(PROF, p);
                rd.include(request, response);
            } else if ("hmw".equals(cmd)) {

                String temp = request.getParameter("temp");
                switch (temp) {
                    case "inc":
                        p = chp.updateHomeworkView(p,+7);
                        break;

                    case "dec":
                        p = chp.updateHomeworkView(p,-7);

                        break;

                    case "today":
                        p = chp.updateHomeworkView(p,0);
                        break;

                    default:
                        throw new ToastException(ERR, "invalid request");

                }
                session.setAttribute(PROF, p);
                rd.forward(request, response);
            } else if ("newArg".equals(cmd)) {
                String materia = p.getCurrentMatter();
                String description = request.getParameter("description");
                int index = chp.checkIndex(p.getArguments());
                Argument arg = new Argument(p.getMatricola(), description, materia, p.getCurrentClass(), index + 1);
                chp.saveArg(arg);


                Toast t = new Toast("Saved", "Homeword saved correctly", 2);
                request.setAttribute(TST, t);
                p.setArguments(chp.reloadArgument(p.getMatricola(), p.getCurrentClass(), p.getCurrentMatter()));
                session.setAttribute(PROF, p);
                rd.forward(request, response);
            } else {
                throw new ToastException(ERR, "invalid request");
            }

        }catch (ToastException te) {

            Toast t = new Toast(te.getTitle(), te.getMessage(), 1);
            request.setAttribute(TST, t);
            rd.forward(request, response);
        }
        catch(Exception e){
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

            if ("change_class".equals(cmd)) {
                String newClass = request.getParameter("current_class");
                p.setCurrentClass(newClass);
                p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(), p.getCurrentMatter()));
                p.setArguments(chp.reloadArgument(p.getMatricola(), p.getCurrentClass(), p.getMatter().get(0)));
                session.setAttribute(PROF, p);
                rd.forward(request, response);

            } else if ("change_matter".equals(cmd)) {
                String newMatter = request.getParameter("current_matter");
                p.setCurrentMatter(newMatter);
                p.setHomework(chp.updateHomeworkList(p.getMatricola(), p.getCurrentClass(), newMatter));
                p.setArguments(chp.reloadArgument(p.getMatricola(), p.getCurrentClass(), newMatter));
                session.setAttribute(PROF, p);
                rd.forward(request, response);


            } else if ("Register".equals(cmd)) {//mont //1 materia //1 classe e il registro
                ProfessorRegister register = chp.getRegister(p);
                session.setAttribute("register", register);
                response.sendRedirect("professorRegister.jsp");
            }else if("deleteArg".equals(cmd)){

                String sIndex = request.getParameter("index");
                List<Argument> list = chp.removeArg(p.getArguments(),sIndex);
                p.setArguments(list);
                session.setAttribute(PROF, p);
                rd.forward(request, response);

            } else {
                throw new ToastException(ERR, "Invalid request");
            }

        } catch (ToastException te){

            Toast t = new Toast(te.getTitle(), te.getMessage(), 1);
            request.setAttribute(TST, t);
            rd.forward(request,response);

        }   catch (Exception e){
            Toast t = new Toast(ERR, e.getMessage(), 1);
            request.setAttribute(TST, t);
            rd.forward(request, response);


        }
    }
}
