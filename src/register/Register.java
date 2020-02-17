package register;

import utils.Month;
import model.Absences;
import model.Grades;
import model.Student;

import java.util.List;

public interface Register {


    List<Grades> getMyGrades(int id);

    List<Absences> getAbsences(int id);

    Student getStudent();

    List<Student>getAllUserForClass(String c);

    List<Grades> getMyGrades(int id, Month m, String materia);

    List<Absences> getAbsences(int id, Month m);
  //  List<Grades> sortBy()

//many sorting algoritms for user
}
