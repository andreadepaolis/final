package bean;

import model.Grades;

import java.io.Serializable;
import java.util.List;

public class MatterBean implements Serializable {

    private String materia;
    private String professor;
    private float media;
    private List<Grades> gradesForMatter;



    public MatterBean(){
        //Bean
    };

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getProfessor() {
        return professor;
    }

    public float getMedia() {
        return media;
    }

    public List<Grades> getGradesForMatter() {
        return gradesForMatter;
    }

    public void setGradesForMatter(List<Grades> gradesForMatter) {
        this.gradesForMatter = gradesForMatter;
    }

    public void setMedia(float media) {
        this.media = media;
    }
}

