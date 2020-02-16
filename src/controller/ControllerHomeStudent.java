package controller;

import bean.MatterBean;
import bean.StudentBean;
import database.StudentDao;
import model.Grades;
import model.Homework;
import model.ScheduleInfo;

import java.util.*;

public class ControllerHomeStudent {

    public ControllerHomeStudent(){};

    public StudentBean full(StudentBean s) {


        List<Homework> homeworks = StudentDao.getHomework(s.getClasse(),new Date());

        List<Homework> list = new ArrayList<>();
        if (homeworks != null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            Date min = cal.getTime();
            cal.add(Calendar.DATE, +7);
            Date max = cal.getTime();

            for(Homework h: homeworks){
               if(h.getData().before(max) && h.getData().after(min)){
                   list.add(h);
               }

            }
            List<Homework> sorted_list = this.sortByDate(list);
            s.setHomework(sorted_list);
        }

        List<ScheduleInfo> schedule = StudentDao.getSchedule(s.getClasse());

        s.setSchedule(schedule);

        return s;
    }

    private List<Homework> sortByDate(List<Homework> homeworks) {

        homeworks.sort(Comparator.comparing(Homework::getData));

        return homeworks;

    }

    public List<Homework> scrollHomework(String classe, Date d){

        List<Homework> homeworks = StudentDao.getHomework(classe,d);

        List<Homework> list = new ArrayList<>();
        if (homeworks != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE,-1);
            Date min = cal.getTime();
            cal.add(Calendar.DATE, +7);
            Date max = cal.getTime();

            for(Homework h: homeworks){
                if(h.getData().before(max) && h.getData().after(min))
                    list.add(h);


            }
            return this.sortByDate(list);
        }  else
             return null;
    }

    public List<Grades> getMyGrades(int matricola) {


        List<Grades> result = StudentDao.getMyGrades(matricola);

        if(result != null)
        return sortByDateGrades(result);
        else
             return null;
    }

    private List<Grades> sortByDateGrades(List<Grades> result) {

        result.sort(Comparator.comparing(Grades::getData));

          return result;
    }

    public List<MatterBean> getMatterBean(int matricola,String myclasse) {

        List<MatterBean> list  = new ArrayList<>();

        List<String> matter = StudentDao.getAllMatter(myclasse);

        if (matter != null) {
            for(String m : matter){
                MatterBean mb = new MatterBean();
                float media;
                mb.setMateria(m);
                List <Grades> g = StudentDao.getMyGrades(matricola,m);
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
}
