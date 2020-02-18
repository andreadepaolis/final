package controller;

import bean.GradesPageBean;
import bean.MatterBean;
import bean.StudentBean;
import database.StudentDao;
import model.Argument;
import model.Grades;
import model.Homework;
import model.ScheduleInfo;
import utils.CustomException;
import utils.CustomSQLException;
import utils.ToastException;

import java.util.*;

public class ControllerHomeStudent {

    private static final String ERR = "Error";
    public ControllerHomeStudent(){
        //C
    }

    public StudentBean full(StudentBean s) throws ToastException {

        try {
            List<Homework> homeworks = StudentDao.getHomework(s.getClasse());

            List<Homework> list = new ArrayList<>();
            if (homeworks != null) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                Date min = cal.getTime();
                cal.add(Calendar.DATE, +7);
                Date max = cal.getTime();

                for (Homework h : homeworks) {
                    if (h.getData().before(max) && h.getData().after(min)) {
                        list.add(h);
                    }

                }
                List<Homework> sortedList = this.sortByDate(list);
                s.setHomework(sortedList);
            }

            List<ScheduleInfo> schedule = StudentDao.getSchedule(s.getClasse());

            s.setSchedule(schedule);
            List<String> temp = new ArrayList<>();
            for (ScheduleInfo sc : s.getSchedule()) {
                if (!temp.contains(sc.getMateria())) {
                    temp.add(sc.getMateria());
                }
            }
            s.setMatter(temp);
            s.setCurrentMatter(temp.get(0));

            List<Argument> temp2 = StudentDao.getArgumentsForMatter(s.getCurrentMatter(), s.getClasse());
            s.setArg(temp2);
            return s;
        } catch (CustomSQLException | CustomException e ){
            throw new ToastException(ERR,e.getMessage());

        }
    }

    private List<Homework> sortByDate(List<Homework> homeworks) {

        homeworks.sort(Comparator.comparing(Homework::getData));

        return homeworks;

    }

    public List<Homework> scrollHomework(String classe, Date d) throws ToastException {

        List<Homework> homeworks = null;
        try {
            homeworks = StudentDao.getHomework(classe);
        } catch (CustomSQLException e) {
            throw new ToastException(ERR,e.getMessage());
        }

        List<Homework> list = new ArrayList<>();
        if (homeworks == null)
            return list;

            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE,-1);
            Date min = cal.getTime();
            cal.add(Calendar.DATE, +1);
            Date max = cal.getTime();

            for(Homework h: homeworks){
                if(h.getData().before(max) && h.getData().after(min))
                    list.add(h);


            }
            return this.sortByDate(list);
    }

    public List<Grades> getMyGrades(int matricola) throws CustomSQLException {


        List<Grades> result = StudentDao.getMyGrades(matricola);

        if(result != null)
            return sortByDateGrades(result);
        else
             return result;
    }

    private List<Grades> sortByDateGrades(List<Grades> result) {

        result.sort(Comparator.comparing(Grades::getData));

          return result;
    }


    public GradesPageBean fullGradesPage(StudentBean s) throws ToastException {
        GradesPageBean page = new GradesPageBean();
        page.setStudent(s);
        List<MatterBean> list = this.getMatterBean(s.getMatricola(),s.getClasse());
        page.setMatter(list);
        page.setCurrentMatter(list.get(0));
        return page;
    }




    private List<MatterBean> getMatterBean(int matricola, String myclasse) throws ToastException {

        try {
            List<MatterBean> list = new ArrayList<>();

            List<String> matter = StudentDao.getAllMatter(myclasse);

            if (matter != null) {
                for (String m : matter) {
                    MatterBean mb = new MatterBean();
                    float media;
                    mb.setMateria(m);
                    List<Grades> g = StudentDao.getMyGrades(matricola, m);
                    if (g != null) {
                        media = this.avg(g);
                    } else
                        media = 0;
                    mb.setMedia(media);
                    mb.setGradesForMatter(g);
                    list.add(mb);
                }
            }
            return list;

        }catch (CustomSQLException | CustomException e){

            throw new ToastException(ERR,e.getMessage());
        }
    }

    private float avg(List<Grades> g){

        float media = 0;
        int count = 0;
        for(Grades temp : g){
            media += temp.getVoto();
            count ++;
        }
        if(count != 0)
            return media/count;
        else
             return 0;
    }

    public List<Argument> reload(String currentMatter, String classe) throws ToastException {

        try {
            return StudentDao.getArgumentsForMatter(currentMatter, classe);
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR,e.getMessage());
        }

    }
}
