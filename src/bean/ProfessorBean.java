package bean;

import model.Argument;
import model.ScheduleInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProfessorBean implements Serializable {


    private String name;
    private String lastname;
    private int matricola;
    private List<String> classi;
    private List<String> matter;
    private List<HomeworkBean> homework;
    private List<ScheduleInfo> schedule;
    private Date currentDate;
    private String currentClass;
    private String currentMatter;
    private List<Argument> arguments;



    public ProfessorBean(){
        //Bean
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public List<String> getClassi() {
        return classi;
    }

    public void setClassi(List<String> classi) {
        this.classi = classi;
    }

    public List<String> getMatter() {
        return matter;
    }

    public void setMatter(List<String> matter) {
        this.matter = matter;
    }

    public void setHomework(List<HomeworkBean> homeworks) {
        this.homework = homeworks;
    }

    public List<ScheduleInfo> getSchedule() {
        return schedule;
    }

    public List<HomeworkBean> getHomework() {
        return homework;
    }

    public void setSchedule(List<ScheduleInfo> schedule) {
        this.schedule = schedule;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public String getCurrentMatter() {
        return currentMatter;
    }

    public void setCurrentMatter(String currentMatter) {
        this.currentMatter = currentMatter;
    }
}
