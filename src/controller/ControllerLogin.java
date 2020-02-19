package controller;

import bean.ProfessorBean;
import bean.StudentBean;
import bean.UserLoginBean;
import database.ProfessorDao;
import database.StudentDao;
import model.Professor;
import utils.BasicExcpetion;
import utils.CustomException;
import utils.CustomSQLException;
import utils.ToastException;

import java.sql.SQLException;
import java.util.Date;

public class ControllerLogin {


        public ControllerLogin(){
            //C
        }


        public StudentBean validateStudent(UserLoginBean u) throws ToastException {

            StudentBean s = null;
            try {
                s = StudentDao.validate(u.getMatricola(),u.getPassword());
            } catch (SQLException | BasicExcpetion e ) {
                throw new ToastException("Login Error",e.getMessage());
            }
            return s;

        }

    public ProfessorBean validateProfessor(UserLoginBean u) throws ToastException {

        Professor p;
        ProfessorBean pb = null;

        try {
            p = ProfessorDao.validate(u.getMatricola(), u.getPassword());

            if (p != null) {

                pb = new ProfessorBean();
                pb.setMatricola(p.getpMatricola());
                pb.setLastname(p.getpLastname());
                pb.setName(p.getpName());
                pb.setCurrentDate(new Date());
            } else {
                throw new BasicExcpetion("invalid matricola or password");
            }

        } catch (CustomSQLException | CustomException e) {
            throw new ToastException("Login Error",e.getMessage());
        } catch(Exception e){
            throw new ToastException("Error",e.getMessage());

        }
        return pb;
     }

    public UserLoginBean generateBean(String matricola, String password) {

        UserLoginBean u = new UserLoginBean();
        u.setMatricola(matricola);
        u.setPassword(password);

        return u;

    }

}
