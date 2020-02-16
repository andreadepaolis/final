package bean;

import java.io.Serializable;
import java.util.Date;

public class HomeworkBean implements Serializable {


    private int matricolaProfessore;
    private String classe;
    private String materia;
    private String description;
    private Date data;

    public HomeworkBean(){
        //Bean
    }

    public int getMatricolaProfessore() {
        return matricolaProfessore;
    }

    public String getClasse() {
        return classe;
    }

    public String getMateria() {
        return materia;
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

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setData(Date data) {
        this.data = data;
    }
}


