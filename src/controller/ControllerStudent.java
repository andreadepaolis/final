package controller;

import database.StudentDao;
import model.Absences;
import utils.CustomException;
import utils.CustomSQLException;
import utils.ToastException;
import java.util.List;

public class ControllerStudent {

    private static final String ERR = "err";
    public ControllerStudent(){
        //C
    }

    public List<Absences> loadAbsences(int id) throws ToastException {


        try {
            return StudentDao.getMyAssenze(id);
        } catch (CustomSQLException e) {
            throw new ToastException(ERR, e.getMessage());

        }

    }

    public boolean verifyPin(String pin,int id) throws ToastException {

        String realPin;
        try {
            realPin = StudentDao.getPin(id);
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }
        return pin.equals(realPin);

    }

    public int manageAbsence(Absences a) throws ToastException {

        a.setCheckbit(0);
        try {
            return StudentDao.updateAbsence(a);
        } catch (CustomException |CustomSQLException e) {
            throw new ToastException(ERR, e.getMessage());

        }

    }
}
