package controller;

import bean.HomeworkBean;
import bean.ProfessorBean;
import bean.StudentBean;
import database.ProfessorDao;
import database.StudentDao;
import utils.MonthFactory;
import utils.month;
import model.*;
import register.ProfessorRegister;
import utils.InputController;

import java.text.ParseException;
import java.util.*;

public class ControllerHomeProfessor {


    public ProfessorBean full(ProfessorBean p) throws Exception {


        List<String> classi = ProfessorDao.getClassi(p.getMatricola());

        if (classi == null)
            throw new Exception("cant found any class");

        p.setClassi(classi);

        p.setCurrent_class(p.getClassi().get(0));

        List<String> matter = ProfessorDao.getMaterie(p.getMatricola());

        if (matter == null)
            throw new Exception("cant found any class");

        p.setMatter(matter);

        List<Argument> arguments = ProfessorDao.getArguments(p.getMatricola(),p.getClassi().get(0));
        if(arguments != null){
        List<Argument> sorted_arg = this.sortByIndex(arguments);
        p.setArguments(sorted_arg);
        }


        List<HomeworkBean> homeworks = ProfessorDao.getHomework(p.getMatricola(), p.getCurrentDate(),p.getClassi().get(0));
        List<HomeworkBean> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date min = cal.getTime();
        cal.add(Calendar.DATE, +7);
        Date max = cal.getTime();

        for(HomeworkBean h: homeworks){
            if(h.getData().before(max) && h.getData().after(min)){
                list.add(h);
            }

        }
        List<HomeworkBean> sorted_list = this.sortByDate(list);
        p.setHomework(sorted_list);


        List<ScheduleInfo> s = ProfessorDao.getSchedule(p.getMatricola());

        p.setSchedule(s);

        return p;
    }

    private List<Argument> sortByIndex(List<Argument> arguments) {

        arguments.sort((Comparator.comparing(Argument::getIndex)));
        return arguments;
    }


    private List<HomeworkBean> sortByDate(List<HomeworkBean> homeworks) {

        homeworks.sort(Comparator.comparing(HomeworkBean::getData));

        return homeworks;

    }

    public HomeworkBean generateHomeworkBean(String classe, String descrizione, String materia, String data, int matricolaProfessor) {

        InputController inp_cntl = InputController.getIstance();
        HomeworkBean hwb = new HomeworkBean();
        try {
            Date d = inp_cntl.converDate(data);
            if(inp_cntl.checkDate(d)){
                hwb.setMatricolaProfessore(matricolaProfessor);
                hwb.setData(d);
                hwb.setClasse(classe);
                hwb.setMateria(materia);
                hwb.setDescription(descrizione);
                return hwb;
            } else
                 return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean save(HomeworkBean hmwbean) throws ParseException {

        InputController input = InputController.getIstance();
        if(!input.checkDate(hmwbean.getData()))return false;
            Homework H = new Homework(hmwbean.getMatricolaProfessore(), hmwbean.getClasse(), hmwbean.getMateria(), hmwbean.getDescription(), hmwbean.getData());
        int result = ProfessorDao.newHomework(H);
        return result > 0;
    }


    public ProfessorRegister getFullRegister(String classe, month m, String materia) {
        ProfessorRegister register = new ProfessorRegister();
        register.setCurrent_class(classe);
        register.setCurrent_matter(materia);
        register.setCurrent_month(m);
        try {
            List<Student> allUserForClass = ProfessorDao.getClasse(classe);
            List<StudentBean> allStudentsBean = new ArrayList<>();
            assert allUserForClass != null;
            for (Student s : allUserForClass) {
                StudentBean sb = new StudentBean();
                sb.setLastname(s.getLastname());
                sb.setName(s.getName());
                sb.setMatricola(s.getMatricola());
                sb.setClasse(s.getClasse());
                allStudentsBean.add(sb);
            }

            allStudentsBean.sort((s1, s2) -> s1.getLastname().compareToIgnoreCase(s2.getLastname()));
            for (StudentBean u : allStudentsBean) {

                List<Grades> temp = register.getMyGrades(u.getMatricola(), m, materia);
                List<Absences> temp2 = register.getAbsences(u.getMatricola(), m);

                if (temp != null) {
                    List<Grades> grades = new ArrayList<>(temp);
                    u.setGrades(grades);
                }
                if (temp2 != null) {
                    List<Absences> absences = new ArrayList<>(temp2);
                    u.setAbsences(absences);
                }


            }
            register.setStudents(allStudentsBean);
            return register;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public month getMonth(String year, String month) {

        MonthFactory mf = new MonthFactory();
        month m = null;
        try {

            int year_int = Integer.parseInt(year);

            int index = Integer.parseInt(month);
            m = mf.createMonth(index, year_int);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public StudentBean extract_random(List<StudentBean> list) {

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean deleteAbsence(ProfessorRegister register, String colIndex, String rowIndex) {

        List<StudentBean> studentBean = register.getStudents();
        InputController input_cntl = InputController.getIstance();

        int student_index = input_cntl.StringToInt(rowIndex);
        int day_index = input_cntl.StringToInt(colIndex);
        StudentBean StudentSelected = studentBean.get(student_index - 1);
        Date d = input_cntl.generateDate(day_index, register.getCurrent_month().getIndex(), register.getCurrent_month().getYear());
        int result = ProfessorDao.deleteAbsence(StudentSelected.getMatricola(), d);
        return result > 0;

    }

    public boolean deleteGrades(ProfessorRegister register, String colIndex, String rowIndex) {
        List<StudentBean> studentBean = register.getStudents();
        InputController input_cntl = InputController.getIstance();

        int student_index = input_cntl.StringToInt(rowIndex);
        int day_index = input_cntl.StringToInt(colIndex);
        StudentBean StudentSelected = studentBean.get(student_index - 1);
        Date d = input_cntl.generateDate(day_index, register.getCurrent_month().getIndex(), register.getCurrent_month().getYear());
        int result = ProfessorDao.deleteGrades(StudentSelected.getMatricola(), d, register.getCurrent_matter());
        return result > 0;
    }

    public List<HomeworkBean> updateHomeworkList(int professorid,String classe) {


        List<HomeworkBean> homeworks = ProfessorDao.getHomework(professorid, new Date(),classe);
        return this.sortByDate(homeworks);
    }

    public boolean removeHmw(HomeworkBean hmw) {

        try {

             int result = ProfessorDao.deleteHomework(hmw.getDescription());

            return result > 0;
        } catch (Exception e) {
            return false;

        }
    }

    public List<Argument> reloadArgument(int matricola,String classe){
        List<Argument> arguments = ProfessorDao.getArguments(matricola,classe);
        if(arguments != null){
            return this.sortByIndex(arguments);
        }
        return null;
    }

    public List<HomeworkBean> scrollHomework(int id, String s, Date currentDate) {


        List<HomeworkBean> homeworks = ProfessorDao.getHomework(id,currentDate,s);

        List<HomeworkBean> list = new ArrayList<>();
        if (homeworks != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DATE,-1);
            Date min = cal.getTime();
            cal.add(Calendar.DATE, +7);
            Date max = cal.getTime();

            for(HomeworkBean h: homeworks){
                if(h.getData().before(max) && h.getData().after(min))
                    list.add(h);


            }
            return this.sortByDate(list);
        }  else
            return null;
    }

    public int checkIndex(List<Argument> list, String classe,String materia) {

        if(list == null)
            return  0;
        return list.size();
    }

    public boolean saveArg(Argument arg) {

        try {

            int result = ProfessorDao.saveArgument(arg);

            return result > 0;
        } catch (Exception e) {
            return false;

        }
    }
}