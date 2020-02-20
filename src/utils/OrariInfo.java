package utils;

import model.ScheduleInfo;

public class OrariInfo {

    private String day;
    private String hours;
    private String materia;
    private String classe;

    public OrariInfo(ScheduleInfo schedule){
        int giorno = schedule.getDay();
        switch(giorno){
            case 0:
                this.day = "LUNEDI";
                break;
            case 1:
                this.day = "MARTEDI";
                break;
            case 2:
                this.day = "MERCOLEDI";
                break;
            case 3:
                this.day = "GIOVEDI";
                break;
            case 4:
                this.day = "VENERDI";
                break;
            default:
                this.day = "SABATO";
        }

        this.hours = schedule.getHours() + ":00";
        this.materia = schedule.getMateria();
        this.classe = schedule.getClasse();
    }

    public String getDay() {
        return day;
    }

    public String getHours(){
        return hours;
    }

    public String getMateria() {
        return materia;
    }

    public String getClasse() {
        return classe;
    }
}
