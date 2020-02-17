package model;

public class Student{

    private int matricola;
    private String name;
    private String lastname;
    private String classe;


    public Student(String name,String lastname,int matricola,String classe){
        this.name = name;
        this.matricola = matricola;
        this.lastname = lastname;
        this.classe = classe;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public int getMatricola() {
        return matricola;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getLastname() {
        return lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
