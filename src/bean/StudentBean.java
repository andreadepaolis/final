package bean;

import model.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StudentBean implements Serializable {


    private int matricola;
    private String name;
    private String lastname;
    private String classe;
    private List<Grades> grades;
    private List <Absences> absences;
    private List<ScheduleInfo> schedule;
    private List<Homework> homework;
    private Date currentDate;
    private String currentMatter;
    private List<String> matter;
    private List<Argument> arg;

    public StudentBean(){
        //Bean
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void setAbsences(List<Absences> absences) {
        this.absences = absences;
    }

    public int getMatricola() {
        return matricola;
    }

    public List<Grades> getGrades() {
        return grades;
    }

    public void setGrades(List<Grades> grades) {
        this.grades = grades;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setAssenze(List<Absences> absences) {
        this.absences = absences;
    }
    public List<Absences> getAbsences(){
        return absences;
    }

    public void setSchedule(List<ScheduleInfo> schedule) {
        this.schedule = schedule;
    }

    public List<ScheduleInfo> getSchedule() {
        return schedule;
    }

    public void setHomework(List<Homework> homework) {
        this.homework = homework;
    }

    public List<Homework> getHomework() {
        return homework;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setCurrentMatter(String currentMatter) {
        this.currentMatter = currentMatter;
    }

    public void setMatter(List<String> matter) {
        this.matter = matter;
    }

    public List<String> getMatter() {
        return matter;
    }

    public String getCurrentMatter() {
        return currentMatter;
    }

    public List<Argument> getArg() {
        return arg;
    }

    public void setArg(List<Argument> arg) {
        this.arg = arg;
    }
}
