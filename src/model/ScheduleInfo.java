package model;

public class ScheduleInfo {

     private int day;
     private int hours;
     private String materia;
     private String classe;

     public ScheduleInfo(int day, int hours, String materia, String classe){

          this.day =day;
          this.hours = hours;
          this.materia = materia;
          this.classe = classe;

     }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMateria() {
        return materia;
    }

    public String getClasse() {
        return classe;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
