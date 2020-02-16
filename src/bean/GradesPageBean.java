package bean;

import java.io.Serializable;
import java.util.List;

public class GradesPageBean  implements Serializable {

    private StudentBean s;
    private List<MatterBean> matter;
    private MatterBean currentMatter;

    public GradesPageBean(){
        //Bean
    }

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

    public void setCurrentMatter(MatterBean currentMatter) {
        this.currentMatter = currentMatter;
    }

    public MatterBean getCurrentMatter() {
        return currentMatter;
    }
}
