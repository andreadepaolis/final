package model;

import java.util.Date;

public class Homework {

    private int matricolaProfessore;
    private String classe;
    private String matter;
    private String description;
    private Date data;

    public Homework(int matricolaProfessore,String classe,String matter,String description,Date data){

        this.matricolaProfessore = matricolaProfessore;
        this.classe = classe;
        this.matter = matter;
        this.description = description;
        this.data = data;
    }

    public int getMatricolaProfessore() {
        return matricolaProfessore;
    }

    public String getClasse() {
        return classe;
    }

    public String getMatter() {
        return matter;
    }

    public String getDescription() {
        return description;
    }

    public Date getData() {
        return data;
    }

    public void setMatricolaProfessore(int matricolaProfessore) {
        this.matricolaProfessore = matricolaProfessore;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
