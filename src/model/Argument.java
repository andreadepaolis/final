package model;

public class Argument {

        private int  matricolaProfessore;
        private String descprition;
        private String materia;
        private String classe;
        private int index;


        public Argument(int matricolaProfessore,String descprition,String materia,String classe,int index){
            this.matricolaProfessore = matricolaProfessore;
            this.descprition = descprition;
            this.materia = materia;
            this.classe = classe;
            this.index =index;
        }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setMatricolaProfessore(int matricolaProfessore) {
        this.matricolaProfessore = matricolaProfessore;
    }

    public int getMatricolaProfessore() {
        return matricolaProfessore;
    }

    public String getDescprition() {
        return descprition;
    }

    public void setDescprition(String descprition) {
        this.descprition = descprition;
    }

}
