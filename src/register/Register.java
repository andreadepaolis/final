package register;

import utils.CustomException;
import utils.CustomSQLException;
import utils.Month;
import model.Absences;
import model.Grades;
import model.Student;

import java.util.List;

public interface Register {


    List<Grades> getMyGrades(int id) throws CustomSQLException;

    List<Absences> getAbsences(int id) throws CustomSQLException;

    Student getStudent();

    List<Student>getAllUserForClass(String c) throws CustomSQLException, CustomException;

    List<Grades> getMyGrades(int id, Month m, String materia) throws CustomSQLException;

    List<Absences> getAbsences(int id, Month m) throws CustomSQLException;

}
