package controller;

import bean.HomeworkBean;
import bean.ProfessorBean;
import bean.StudentBean;
import database.ProfessorDao;
import utils.CustomRandom;
import utils.MonthFactory;
import utils.month;
import model.*;
import register.ProfessorRegister;
import utils.InputController;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

public class ControllerHomeProfessor {

    private static final Logger LOGGER = Logger.getLogger(ControllerHomeProfessor.class.getName());

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
        List<Argument> sortedArg = this.sortByIndex(arguments);
        p.setArguments(sortedArg);
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
        List<HomeworkBean> sortedList = this.sortByDate(list);
        p.setHomework(sortedList);


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

        InputController inpCntl = InputController.getIstance();
        HomeworkBean hwb = new HomeworkBean();
        try {
            Date d = inpCntl.converDate(data);
            if(inpCntl.checkDate(d)){
                hwb.setMatricolaProfessore(matricolaProfessor);
                hwb.setData(d);
                hwb.setClasse(classe);
                hwb.setMateria(materia);
                hwb.setDescription(descrizione);
                return hwb;
            } else
                 return null;
        } catch (Exception e) {
            LOGGER.info(e.toString());
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
            LOGGER.info(e.toString());
            return null;
        }


    }

    public month getMonth(String year, String month) {

        MonthFactory mf = new MonthFactory();
        month m = null;
        try {

            int yearInt = Integer.parseInt(year);

            int index = Integer.parseInt(month);
            m = mf.createMonth(index, yearInt);

        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return m;
    }

    public StudentBean extractRandom(List<StudentBean> list) throws NoSuchAlgorithmException {

        CustomRandom c = new CustomRandom();
        return list.get(c.getRandom().nextInt(list.size()));
    }

    public boolean deleteAbsence(ProfessorRegister register, String colIndex, String rowIndex) {

        List<StudentBean> studentBean = register.getStudents();
        InputController inputCntl = InputController.getIstance();

        int studentIndex = inputCntl.StringToInt(rowIndex);
        int dayIndex = inputCntl.StringToInt(colIndex);
        StudentBean studentSelected = studentBean.get(studentIndex - 1);
        Date d = inputCntl.generateDate(dayIndex, register.getCurrent_month().getIndex(), register.getCurrent_month().getYear());
        int result = ProfessorDao.deleteAbsence(studentSelected.getMatricola(), d);
        return result > 0;

    }

    public boolean deleteGrades(ProfessorRegister register, String colIndex, String rowIndex) {
        List<StudentBean> studentBean = register.getStudents();
        InputController inputCntl = InputController.getIstance();

        int studentIndex = inputCntl.StringToInt(rowIndex);
        int dayIndex = inputCntl.StringToInt(colIndex);
        StudentBean studentSelected = studentBean.get(studentIndex - 1);
        Date d = inputCntl.generateDate(dayIndex, register.getCurrent_month().getIndex(), register.getCurrent_month().getYear());
        int result = ProfessorDao.deleteGrades(studentSelected.getMatricola(), d, register.getCurrent_matter());
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
        return arguments;
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
            return list;
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