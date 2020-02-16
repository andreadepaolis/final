package register;

import bean.StudentBean;
import database.ProfessorDao;
import database.StudentDao;
import utils.month;
import model.Absences;
import model.Grades;

import model.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfessorRegister implements Register {

    private List<Grades> grades;
    private List<Absences> absences;
    private List<StudentBean> students;
    private String currentClass;
    private month currentMonth;
    private String currentMatter;

    public ProfessorRegister(){
    //Register
    }

    public month getCurrentMonth() {
        return currentMonth;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public List<Absences> getAbsences() {
        return absences;
    }

    public List<StudentBean> getStudents() {
        return students;
    }

    public String getCurrentMatter() {
        return currentMatter;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public void setCurrentMatter(String currentMatter) {
        this.currentMatter = currentMatter;
    }

    public void setCurrentMonth(month currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setGrades(List<Grades> grades) {
        this.grades = grades;
    }

    public List<Grades> getGrades() {
        return grades;
    }


    public void setAbsences(List<Absences> absences) {
        this.absences = absences;
    }

    public List<StudentBean> getUsers() {
        return students;
    }

    public void setStudents(List<StudentBean> students) {
        this.students = students;
    }

    @Override
    public List<Grades> getMyGrades(int id) {
        List<Grades> result;
        result = StudentDao.getMyGrades(id);
        return result;

    }
    @Override
    public List<Absences> getAbsences(int id) {
        List<Absences> result;
        result = StudentDao.getMyAssenze(id);
        return result;
    }

    @Override
    public Student getStudent() {
        return null;
    }

    @Override
    public List<Student> getAllUserForClass(String c) {
        List<Student> users;
        users = ProfessorDao.getClasse(c);
        return users;
    }
    @Override
    public List<Grades> getMyGrades(int id, month m, String materia){
        List<Grades> result = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.set(m.getYear(),m.getIndex()-1,0);
        Calendar end = Calendar.getInstance();
        end.set(m.getYear(),m.getIndex()-1,m.getDay());
        List<Grades> temp  = StudentDao.getMyGrades(id);
        if(temp != null) {
            for (Grades g : temp) {
                if (g.getData().before(end.getTime()) && start.getTime().before(g.getData())&& g.getMateria().equals(materia)){
                    result.add(g);
                }

            }
            return result;
        }
        return result;
    }
    @Override
    public List<Absences> getAbsences(int id, month m){
        List<Absences> result = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.set(m.getYear(),m.getIndex()-1,0);
        Calendar end = Calendar.getInstance();
        end.set(m.getYear(),m.getIndex()-1,m.getDay());
        List<Absences> temp  = StudentDao.getMyAssenze(id);
        if(temp != null) {
            for (Absences a : temp) {
                if (a.getData().before(end.getTime()) && start.getTime().before(a.getData()))
                    result.add(a);
            }
            return result;
        }
        return list;
    }

    public int newGrades(int ms,String materia,int voto, String tipo,int professorid,String professor,Date data){
        Grades g = new Grades(ms,materia,voto,tipo,professorid,professor,data);
        return ProfessorDao.saveGrades(g);
    }

    public double getMedia(int matricola,String materia) {

         double media = 0;
         List<Grades> voti = ProfessorDao.getMedia(matricola,materia);
         int count = 0;
         if(voti == null){
             return 0;
         }
         for(Grades g : voti){
             count++;
             media += g.getVoto();
         }
         if(count != 0)
            return Math.round((media*10/count))/10.0;
         else
             return 0;

    }

}
