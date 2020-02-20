package controllerfx;

import bean.GradesPageBean;
import bean.ProfessorBean;
import bean.StudentBean;
import register.ProfessorRegister;

public class ControllerScenes {
    protected static ProfessorBean professor;
    protected static ProfessorRegister register;
    protected static StudentBean student;
    protected static GradesPageBean grades;

    public ProfessorBean getCurrentProfessor(){ return professor; }

    public static void setProfessor(ProfessorBean professore){
        professor = professore;
    }

    public ProfessorRegister getCurrentRegister() {
        return register;
    }

    public static void setRegister(ProfessorRegister registro){
        register = registro;
    }

    public StudentBean getCurrentStudent(){ return student;}

    public static void setStudent(StudentBean studente){student = studente;}

    public GradesPageBean getCurrentGradesPage(){ return grades;}

    public static void setGradesStudent(GradesPageBean voti){grades = voti;}
}
