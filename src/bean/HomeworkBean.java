package bean;

import java.io.Serializable;
import java.util.Date;

public class HomeworkBean implements Serializable {


    private int matricolaprofessore;
    private String myclasse;
    private String mat;
    private String description;
    private Date data;

    public HomeworkBean(){
        //Bean
    }

    public int getMatricolaprofessore() {
        return matricolaprofessore;
    }

    public String getMyclasse() {
        return myclasse;
    }

    public String getMateria() {
        return mat;
    }

    public String getDescription() {
        return description;
    }

    public Date getData() {
        return data;
    }

    public void setMatricolaprofessore(int matricolaprofessore) {
        this.matricolaprofessore = matricolaprofessore;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMyclasse(String myclasse) {
        this.myclasse = myclasse;
    }

    public void setMateria(String materia) {
        this.mat = materia;
    }

    public void setData(Date data) {
        this.data = data;
    }
}


