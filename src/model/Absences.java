package model;

import java.util.Date;

public class Absences {


    private int  matricolaStudente;
    private String tipo;
    private Date data;
    private int checkbit;

    public Absences(int matricolaStudente,String tipo,Date data,int checkbit){

        this.matricolaStudente = matricolaStudente;
        this.tipo = tipo;
        this.data = data;
        this.checkbit = checkbit;

    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public int getCheckbit() {
        return checkbit;
    }

    public int getMatricolaStudente() {
        return matricolaStudente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCheckbit(int checkbit) {
        this.checkbit = checkbit;
    }

    public void setMatricolaStudente(int matricolaStudente) {
        this.matricolaStudente = matricolaStudente;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
