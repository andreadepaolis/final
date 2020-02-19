package controllerfx;

import bean.GradesPageBean;
import bean.ProfessorBean;
import bean.StudentBean;
import register.ProfessorRegister;

public class ControllerScenes {
    public static ProfessorBean professor;
    public static ProfessorRegister register;
    public static StudentBean student;
    public static GradesPageBean grades;

    public ProfessorBean getCurrentProfessor(){ return professor; }

    public void setProfessor(ProfessorBean professore){
        professor = professore;
    }

    public ProfessorRegister getCurrentRegister() {
        return register;
    }

    public void setRegister(ProfessorRegister registro){
        register = registro;
    }

    public StudentBean getCurrentStudent(){ return student;}

    public void setStudent(StudentBean studente){student = studente;}

    public GradesPageBean getCurrentGradesPage(){ return grades;}

    public void setGradesStudent(GradesPageBean voti){grades = voti;}
}
