package controller;

import bean.ProfessorBean;
import bean.StudentBean;
import bean.UserLoginBean;
import database.ProfessorDao;
import database.StudentDao;
import model.Professor;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

public class ControllerLogin {

    private static final Logger LOGGER = Logger.getLogger(ControllerLogin.class.getName());

        public ControllerLogin(){}


        public StudentBean validateStudent(UserLoginBean u){

            StudentBean s = null;
            try {
                s = StudentDao.validate(u.getMatricola(),u.getPassword());
            } catch (SQLException e) {
                LOGGER.info(e.toString());
            }
            return s;

        };

    public ProfessorBean validateProfessor(UserLoginBean u){

        Professor p = null;
        ProfessorBean pb = null;

        try {
            p = ProfessorDao.validate(u.getMatricola(),u.getPassword());

        if(p != null){

            pb = new ProfessorBean();
            pb.setMatricola(p.getMatricola());
            pb.setLastname(p.getLastname());
            pb.setName(p.getName());
            pb.setCurrentDate(new Date());
        }

        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
        return pb;
     };

    public UserLoginBean generateBean(String matricola, String password) {

        UserLoginBean u = new UserLoginBean();
        u.setMatricola(matricola);
        u.setPassword(password);

        return u;

    }

}