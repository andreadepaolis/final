package model;

public class Professor {

    private String name;
    private String lastname;
    private int matricola;


    public Professor(String name, String lastname,int matricola){
        this.name = name;
        this.lastname = lastname;
        this.matricola = matricola;
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

}
