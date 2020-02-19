package model;

public class Professor {

    private String pName;
    private String pLastname;
    private int pMatricola;


    public Professor(String pName, String pLastname, int pMatricola){
        this.pName = pName;
        this.pLastname = pLastname;
        this.pMatricola = pMatricola;
    }

    public String getpName() {
        return pName;
    }

    public String getpLastname() {
        return pLastname;
    }

    public int getpMatricola() {
        return pMatricola;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpLastname(String pLastname) {
        this.pLastname = pLastname;
    }

    public void setpMatricola(int pMatricola) {
        this.pMatricola = pMatricola;
    }

}
