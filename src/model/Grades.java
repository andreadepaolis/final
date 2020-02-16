package model;

import java.util.Date;

public class Grades {
    private String materia;
    private int voto;
    private String tipo;
    private int professorid;
    private Date data;
    private String nomeProfessore;
    private int matricolaStudente;

    public Grades(String materia,int voto, String tipo,String nomeProfessore,Date data){
        this.materia = materia;
        this.tipo = tipo;
        this.voto = voto;
        this.data = data;
        this.nomeProfessore = nomeProfessore;
    }

    public Grades(String materia,int voto){
        this.materia = materia;
        this.voto = voto;
    }

    public Grades(int matricolaStudente,String materia,int voto, String tipo,int professorid,String nomeProfessore, Date data){
        this.matricolaStudente = matricolaStudente;
        this.materia = materia;
        this.tipo = tipo;
        this.voto = voto;
        this.data = data;
        this.professorid = professorid;
        this.nomeProfessore = nomeProfessore;
    }


    public int getMatricolaStudente() {
        return matricolaStudente;
    }

    public int getProfessorid() {
        return professorid;
    }

    public void setMatricolaStudente(int matricolaStudente) {
        this.matricolaStudente = matricolaStudente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setProfessorid(int professorid) {
        this.professorid = professorid;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVoto() {
        return voto;
    }

    public String getMateria() {
        return materia;
    }

    public Date getData() {
        return data;
    }



    public void setData(Date data) {
        this.data = data;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setNomeProfessore(String nomeProfessore) {
        this.nomeProfessore = nomeProfessore;
    }
    public String getNomeProfessore() {
        return nomeProfessore;
    }
}
