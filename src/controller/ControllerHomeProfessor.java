package controller;

import bean.HomeworkBean;
import bean.ProfessorBean;
import bean.StudentBean;
import database.ProfessorDao;
import utils.*;
import model.*;
import register.ProfessorRegister;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class ControllerHomeProfessor {

    private static final Logger LOGGER = Logger.getLogger(ControllerHomeProfessor.class.getName());
    private static final String ERR = "Error";

    public ProfessorBean full(ProfessorBean p) throws ToastException {


        List<String> classi;
        try {
            classi = ProfessorDao.getClassi(p.getMatricola());
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }

        p.setClassi(classi);

        p.setCurrentClass(p.getClassi().get(0));

        List<String> matter = null;
        try {

            matter = ProfessorDao.getMaterie(p.getMatricola());

        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }

        p.setMatter(matter);
        p.setCurrentMatter(matter.get(0));

        List<Argument> arguments;
        try {
            arguments = ProfessorDao.getArguments(p.getMatricola(), p.getClassi().get(0), matter.get(0));
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());

        }

        if (arguments != null) {
            List<Argument> sortedArg = this.sortByIndex(arguments);
            p.setArguments(sortedArg);
        }


        List<HomeworkBean> homeworks = new ArrayList<>();
        try {
            homeworks = ProfessorDao.getHomework(p.getMatricola(), p.getClassi().get(0));
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }
        List<HomeworkBean> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date min = cal.getTime();
        cal.add(Calendar.DATE, +1);
        Date max = cal.getTime();

        for (HomeworkBean h : homeworks) {
            if (h.getData().before(max) && h.getData().after(min)) {
                list.add(h);
            }

        }
        List<HomeworkBean> sortedList = this.sortByDate(list);
        p.setHomework(sortedList);


        List<ScheduleInfo> s = new ArrayList<>();
        try {
            s = ProfessorDao.getSchedule(p.getMatricola());
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }

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
            if (inpCntl.checkDate(d)) {
                hwb.setMatricolaprofessore(matricolaProfessor);
                hwb.setData(d);
                hwb.setMyclasse(classe);
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

    public boolean save(HomeworkBean hmwbean) throws ToastException {

        InputController input = InputController.getIstance();
        try {
            if (!input.checkDate(hmwbean.getData()))
                return false;
        } catch (Exception e) {
            throw new ToastException(ERR, e.getMessage());
        }

        Homework h = new Homework(hmwbean.getMatricolaprofessore(), hmwbean.getMyclasse(), hmwbean.getMateria(), hmwbean.getDescription(), hmwbean.getData());
        int result = 0;
        try {
            result = ProfessorDao.newHomework(h);
        } catch (CustomException | CustomSQLException e) {
            throw new ToastException(ERR, e.getMessage());
        }
        return result > 0;
    }


    public ProfessorRegister getFullRegister(String classe, Month m, String materia) throws ToastException {
        ProfessorRegister register = new ProfessorRegister();
        register.setCurrentClass(classe);
        register.setCurrentMatter(materia);
        register.setCurrentMonth(m);
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
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR,e.getMessage());

        }

    }

    public Month getMonth(String year, String month) {

        MonthFactory mf = new MonthFactory();
        Month m = null;
        try {

            int yearInt = Integer.parseInt(year);

            int index = Integer.parseInt(month);
            m = mf.createMonth(index, yearInt);

        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return m;
    }

    public StudentBean extractRandom(List<StudentBean> list) throws ToastException {

        CustomRandom c = null;
        try {
            c = new CustomRandom();
        } catch (NoSuchAlgorithmException e) {
            throw new ToastException(ERR, e.getMessage());

        }
        return list.get(c.getRandom().nextInt(list.size()));
    }

    public boolean deleteAbsence(ProfessorRegister register, String colIndex, String rowIndex) {

        List<StudentBean> studentBean = register.getStudents();
        InputController inputCntl = InputController.getIstance();

        int studentIndex = inputCntl.stringToInt(rowIndex);
        int dayIndex = inputCntl.stringToInt(colIndex);
        StudentBean studentSelected = studentBean.get(studentIndex - 1);
        Date d = inputCntl.generateDate(dayIndex, register.getCurrentMonth().getIndex(), register.getCurrentMonth().getYear());
        int result = ProfessorDao.deleteAbsence(studentSelected.getMatricola(), d);
        return result > 0;

    }

    public boolean deleteGrades(ProfessorRegister register, String colIndex, String rowIndex) throws ToastException {
        List<StudentBean> studentBean = register.getStudents();
        InputController inputCntl = InputController.getIstance();

        int studentIndex = inputCntl.stringToInt(rowIndex);
        int dayIndex = inputCntl.stringToInt(colIndex);
        StudentBean studentSelected = studentBean.get(studentIndex - 1);
        Date d = inputCntl.generateDate(dayIndex, register.getCurrentMonth().getIndex(), register.getCurrentMonth().getYear());
        int result = 0;
        try {
            result = ProfessorDao.deleteGrades(studentSelected.getMatricola(), d, register.getCurrentMatter());
        } catch (CustomSQLException | CustomException se) {
            throw new ToastException(ERR, se.getMessage());

        }
        return result > 0;
    }

    public List<HomeworkBean> updateHomeworkList(int professorid, String classe, String matter) throws ToastException {

        try {
            List<HomeworkBean> homeworks = ProfessorDao.getHomework(professorid, classe);
            List<HomeworkBean> result = new ArrayList<>();
            for (HomeworkBean h : homeworks) {
                if (h.getMateria().equals(matter))
                    result.add(h);

            }
            return this.sortByDate(result);
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }
    }

    public boolean removeHmw(HomeworkBean hmw) throws ToastException {

        try {

            int result = ProfessorDao.deleteHomework(hmw.getDescription());

            return result > 0;

        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }
    }

    public List<Argument> reloadArgument(int matricola, String classe, String matter) throws ToastException {
        List<Argument> arguments = null;
        try {
            arguments = ProfessorDao.getArguments(matricola, classe, matter);
        } catch (CustomSQLException | CustomException se) {
            throw new ToastException(ERR, se.getMessage());
        }
        if (arguments != null) {
            return this.sortByIndex(arguments);
        }
        return arguments;
    }

    public List<HomeworkBean> scrollHomework(int id, String s, Date currentDate) throws ToastException {


        List<HomeworkBean> homeworks = new ArrayList<>();
        try {
            homeworks = ProfessorDao.getHomework(id, s);
        } catch (CustomSQLException | CustomException e) {
            throw new ToastException(ERR, e.getMessage());
        }

        List<HomeworkBean> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -1);
        Date min = cal.getTime();
        cal.add(Calendar.DATE, +7);
        Date max = cal.getTime();

        for (HomeworkBean h : homeworks) {
            if (h.getData().before(max) && h.getData().after(min))
                list.add(h);


        }
        return this.sortByDate(list);

    }

    public int checkIndex(List<Argument> list) {

        if (list == null)
            return 0;

        return list.size();
    }

    public boolean saveArg(Argument arg) throws ToastException {

        try {
            int result = ProfessorDao.saveArgument(arg);

            return result > 0;

        } catch (CustomException | SQLException e) {
            throw new ToastException(ERR, e.getMessage());
        }
    }

    public void saveAbsence(int matricola, String tipo, Date d) throws ToastException {

        Absences a = new Absences(matricola, tipo, d, 1);
        try{
            ProfessorDao.saveAbsence(a);

        } catch (CustomException | SQLException e) {
            throw new ToastException(ERR, e.getMessage());
        }
    }

    public void findAndRemove(List<HomeworkBean> homework, String descprition) throws ToastException {


          for(HomeworkBean h : homework){
              if(h.getDescription().equals(descprition)) {
                  this.removeHmw(h);
              }
          }
    }

    public ProfessorBean updateHomeworkView(ProfessorBean p,int amount) throws ToastException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(p.getCurrentDate());
        cal.add(Calendar.DATE, amount);
        p.setCurrentDate(cal.getTime());
        List<HomeworkBean> h = this.scrollHomework(p.getMatricola(), p.getCurrentClass(), p.getCurrentDate());
        p.setHomework(h);
        return p;
    }

    public ProfessorRegister getRegister(ProfessorBean p) throws ToastException {


        Calendar cal = Calendar.getInstance();
        MonthFactory f = new MonthFactory();
        Date d = new Date();
        cal.setTime(d);
        Month m = f.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        String materia = p.getMatter().get(0);
        String classe = p.getClassi().get(0);
        ProfessorRegister register = this.getFullRegister(classe, m, materia);

        if (register == null) throw new ToastException(ERR,"Critical Error");
        return register;
    }
}