package controller;

import database.StudentDao;
import model.Absences;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ControllerStudent {
    private static final Logger LOGGER = Logger.getLogger(ControllerStudent.class.getName());


    public ControllerStudent(){};

    public List<Absences> loadAbsences(int id) {


        return StudentDao.getMyAssenze(id);

    }

    public boolean verifyPin(String pin,int id) {

        String real_pin = StudentDao.getPin(id);
        return pin.equals(real_pin);

    }

    public int manageAbsence(Absences a) throws SQLException {

        a.setCheckbit(0);
         return StudentDao.updateAbsence(a);

    }
}
