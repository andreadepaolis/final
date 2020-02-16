package bean;

import java.util.List;

public class GradesPageBean {

    private StudentBean s;
    private List<MatterBean> matter;
    private MatterBean current_matter;

    public GradesPageBean(){};

    public void setMatter(List<MatterBean> matter) {
        this.matter = matter;
    }

    public List<MatterBean> getMatter() {
        return matter;
    }

    public StudentBean getStudent() {
        return s;
    }

    public void setStudent(StudentBean s) {
        this.s = s;
    }

    public StudentBean getS() {
        return s;
    }

    public void setCurrent_matter(MatterBean current_matter) {
        this.current_matter = current_matter;
    }

    public MatterBean getCurrent_matter() {
        return current_matter;
    }
}
