package controllerfx;

import bean.GradesPageBean;
import bean.ProfessorBean;
import bean.StudentBean;
import javafx.stage.Stage;
import register.ProfessorRegister;

public class ControllerScenes {
    private Stage primaryStage;
    private String currentURL;
    private static ProfessorBean professor;
    private static ProfessorRegister register;
    private static StudentBean student;
    private static GradesPageBean grades;

    public ControllerScenes(){}

    public ProfessorBean getCurrentProfessor(){ return this.professor; }

    public void setProfessor(ProfessorBean professor){
        this.professor = professor;
    }

    public ProfessorRegister getCurrentRegister() {
        return register;
    }

    public void setRegister(ProfessorRegister register){
        this.register = register;
    }

    public StudentBean getCurrentStudent(){ return this.student;}

    public void setStudent(StudentBean student){this.student = student;}

    public GradesPageBean getCurrentGradesPage(){ return this.grades;}

    public void setGradesStudent(GradesPageBean grades){this.grades = grades;}
}
