package controller;

import database.StudentDao;
import model.Absences;

import java.sql.SQLException;
import java.util.List;

public class ControllerStudent {

    public ControllerStudent(){
        //C
    }

    public List<Absences> loadAbsences(int id) {


        return StudentDao.getMyAssenze(id);

    }

    public boolean verifyPin(String pin,int id) {

        String realPin = StudentDao.getPin(id);
        return pin.equals(realPin);

    }

    public int manageAbsence(Absences a) throws SQLException {

        a.setCheckbit(0);
         return StudentDao.updateAbsence(a);

    }
}
