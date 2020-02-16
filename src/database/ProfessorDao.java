package database;

import bean.HomeworkBean;
import database.query.ProfessorQuery;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ProfessorDao {


    private static final Logger LOGGER = Logger.getLogger(ProfessorDao.class.getName());


    public static Professor validate(int matricola, String password) throws SQLException {


        DataBase db = DataBase.getInstance();
        Connection con = db.getConnection();


        try{
            Statement stmt = con.createStatement();

            ResultSet rs= ProfessorQuery.login(stmt,matricola,password);


            assert rs != null;
            if(rs.first()){
                rs.first();

                return new Professor(rs.getString("name"), rs.getString("lastname"), rs.getInt("matricola"));
            }

            else {
                return null;

            }

        }catch(Exception e) {
            LOGGER.info("Login error");
            return null;
        }
    }


    public static List<String> getMaterie(int matricola) {
        List<String> list = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getMaterie(stmt,matricola);

            if (!rs.first()) {
                return null;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                String materia = rs.getString("name");

                list.add(materia);

            } while (rs.next());

        } catch (SQLException e) {
            LOGGER.info("SQL error");
        }
        return list;
    }


    public static List<String> getClassi(int matricola) {

        List<String> list = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getClassi(stmt,matricola);

            if (!rs.first()) {
                return null;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                String classi = rs.getString("name");

                list.add(classi);

            } while (rs.next());

        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return list;
    }

    public static List<HomeworkBean> getHomework(int professorId, Date date, String classe) {

        List<HomeworkBean> list = new ArrayList<>();
        Connection con = DataBase.getInstance().getConnection();
        try {
            Statement stmt = con.createStatement();

            ResultSet rs = ProfessorQuery.getHomework(stmt, professorId);

            assert rs != null;
            if(rs.first()) {
                do {
                    HomeworkBean h = new HomeworkBean();
                    h.setMatricolaProfessore(rs.getInt("matricolaProfessore"));
                    h.setDescription(rs.getString("descrizione"));
                    h.setMateria(rs.getString("materia"));
                    h.setData(rs.getDate("data"));
                    h.setClasse(rs.getString("class"));

                    if(h.getClasse().equals(classe))
                         list.add(h);
                } while (rs.next());
            }
        } catch (Exception e) {
            LOGGER.info(e.toString());        }
        return list;
    }

    public static int newHomework(Homework h) {


        Connection con = DataBase.getInstance().getConnection();
        int result = 0;

        try {
            Statement stmt = con.createStatement();

            result = ProfessorQuery.saveNewHomework(stmt, h);


        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return result;

    }
    public static List<ScheduleInfo> getSchedule(int professorid) {

        List<ScheduleInfo> list = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getScheduleForProfessor(stmt,professorid);

            if (!rs.first()) {
                return null;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                ScheduleInfo si = new ScheduleInfo(rs.getInt("day"),rs.getInt("hours"),rs.getString("materia"),rs.getString("class"));
                list.add(si);

            } while (rs.next());

        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return list;
    }

    public static List<Grades> getMedia(int matricola, String materia) {

        List<Grades> list = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getUserGradesForMateria(stmt,matricola,materia);

            if (!rs.first()) {
                return null;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                Grades g = new Grades(rs.getString("materia"),rs.getInt("voto"));
                list.add(g);

            } while (rs.next());

        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return list;
    }
    public static int saveGrades(Grades g) {

        Connection con = DataBase.getInstance().getConnection();
        int result = 0;

        try {
            Statement stmt = con.createStatement();

            result = ProfessorQuery.saveNewGrades(stmt, g);


        } catch (Exception e) {
            LOGGER.info(e.toString());        }
        return result;

    }

    public static List<Student> getClasse(String classe) {

        Connection con = DataBase.getInstance().getConnection();

        List<Student> list = new ArrayList<Student>();
        try {
            Statement stmt = con.createStatement();

            ResultSet rs = ProfessorQuery.getStudentsOfClass(stmt, classe);
            if (!rs.first()) {
                return null;
            }
            rs.first();
            do {
                Student u = new Student(rs.getString("name"), rs.getString("lastname"), rs.getInt("matricola"), classe);

                list.add(u);

            } while (rs.next());
            rs.close();
            stmt.close();
            return list;
        } catch (Exception e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static int saveAbsence(Absences a) throws SQLException {

        Connection con = DataBase.getInstance().getConnection();
        int result = 0;
        Statement stmt = null;
        try {
             stmt = con.createStatement();

            result = ProfessorQuery.saveNewAbsences(stmt, a);

        } catch (Exception e) {
            LOGGER.info(e.toString());
        } finally {
            if(stmt != null)
                stmt.close();
        }
        return result;

    }

    public static int deleteAbsence(int matricola, Date d) {

        Connection con = DataBase.getInstance().getConnection();
        int result = 0;

        try {
            Statement stmt = con.createStatement();

            result = ProfessorQuery.deleteAbsences(stmt, matricola,d);


        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return result;

    }

    public static int deleteGrades(int matricola, Date d, String current_matter) {

        Connection con = DataBase.getInstance().getConnection();
        int result = 0;

        try {
            Statement stmt = con.createStatement();

            result = ProfessorQuery.deleteGrades(stmt, matricola,d,current_matter);


        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static int deleteHomework(String description) {
        Connection con = DataBase.getInstance().getConnection();
        int result = 0;

        try {
            Statement stmt = con.createStatement();

            result = ProfessorQuery.deleteHomework(stmt,description);


        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static List<Argument> getArguments(int matricola, String s) {

            Connection con = DataBase.getInstance().getConnection();

            List<Argument> list = new ArrayList<>();
            try {
                Statement stmt = con.createStatement();

                ResultSet rs = ProfessorQuery.getArgument(stmt, matricola, s);
                if (rs == null)
                  return null;

                    if (!rs.first()) {
                        return null;
                    }
                    rs.first();
                    do {
                        Argument arg = new Argument(matricola, rs.getString("descrizione"), rs.getString("materia"), rs.getString("class"), rs.getInt("count" +
                                ""));
                        list.add(arg);

                    } while (rs.next());

                    rs.close();
                    stmt.close();
                    return list;
                } catch(Exception e){
                LOGGER.info(e.toString());
                return null;
                }

    }
    public static int saveArgument(Argument arg) throws SQLException {

        Connection con = DataBase.getInstance().getConnection();
        int result = 0;
        Statement stmt = null;

        try {
             stmt = con.createStatement();

            result = ProfessorQuery.saveNewArg(stmt,arg.getMatricolaProfessore(),arg.getClasse(),arg.getDescprition(),arg.getMateria(),arg.getIndex());


        } catch (Exception e) {
            LOGGER.info(e.toString());
        }finally {
            if(stmt != null)
                 stmt.close();
        }
        return result;

    }
}
