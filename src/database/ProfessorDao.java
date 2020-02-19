package database;

import bean.HomeworkBean;
import database.query.ProfessorQuery;
import model.*;
import utils.BasicExcpetion;
import utils.CustomException;
import utils.CustomSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public abstract class ProfessorDao {


    private static final Logger LOGGER = Logger.getLogger(ProfessorDao.class.getName());

    private static final String MAT = "materia";
    private static final String CLASS = "class";
    private static final String ERR = "Error";


    private ProfessorDao() throws BasicExcpetion {
        throw new BasicExcpetion("Abstract class ");
    }

    public static Professor validate(int matricola, String password) throws CustomSQLException, CustomException {


        DataBase db = DataBase.getInstance();


        try {
            Connection con = db.getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = ProfessorQuery.login(stmt, matricola, password);


            assert rs != null;
            if (rs.first()) {
                rs.first();

                return new Professor(rs.getString("name"), rs.getString("lastname"), rs.getInt("matricola"));
            }

        } catch (SQLException s) {
            throw new CustomSQLException(s);

        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);

        }
        return null;
    }


    public static List<String> getMaterie(int matricola) throws CustomSQLException, CustomException {
        List<String> list = new ArrayList<>();
        Statement stmt;
        Connection con;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getMaterie(stmt, matricola);


            assert rs != null;
            if (!rs.first()) {
                return list;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                String materia = rs.getString("name");

                list.add(materia);

            } while (rs.next());

        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return list;
    }


    public static List<String> getClassi(int matricola) throws CustomSQLException, CustomException {

        List<String> list = new ArrayList<>();
        Statement stmt;
        Connection con;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getClassi(stmt, matricola);

            assert rs != null;
            if (!rs.first()) {
                return list;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                String classi = rs.getString("name");

                list.add(classi);

            } while (rs.next());

        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return list;
    }

    public static List<HomeworkBean> getHomework(int professorId, String classe) throws CustomSQLException, CustomException {

        List<HomeworkBean> list = new ArrayList<>();
        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = ProfessorQuery.getHomework(stmt, professorId);

            assert rs != null;
            if (rs.first()) {
                do {
                    HomeworkBean h = new HomeworkBean();
                    h.setMatricolaprofessore(rs.getInt("matricolaProfessore"));
                    h.setDescription(rs.getString("descrizione"));
                    h.setMateria(rs.getString(MAT));
                    h.setData(rs.getDate("data"));
                    h.setMyclasse(rs.getString(CLASS));

                    if (h.getMyclasse().equals(classe))
                        list.add(h);
                } while (rs.next());
            }
        }catch (SQLException e) {
            throw new CustomSQLException(e);
        }catch (Exception e) {
            throw new CustomException(ERR, e);
        }

        return list;
    }

    public static int newHomework(Homework h) throws CustomException, CustomSQLException {


        int result = 0;

        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            result = ProfessorQuery.saveNewHomework(stmt, h);


        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return result;

    }

    public static List<ScheduleInfo> getSchedule(int professorid) throws CustomSQLException, CustomException {

        List<ScheduleInfo> list = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getScheduleForProfessor(stmt, professorid);

            assert rs != null;
            if (!rs.first()) {
                return list;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                ScheduleInfo si = new ScheduleInfo(rs.getInt("day"), rs.getInt("hours"), rs.getString(MAT), rs.getString(CLASS));
                list.add(si);

            } while (rs.next());

        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return list;
    }

    public static List<Grades> getMedia(int matricola, String materia) throws CustomSQLException, CustomException {

        List<Grades> list = new ArrayList<>();
        Statement stmt = null;
        Connection con = null;
        try {

            DataBase db = DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = ProfessorQuery.getUserGradesForMateria(stmt, matricola, materia);

            if (!rs.first()) {
                return list;
            }

            // riposizionamento del cursore
            rs.first();

            do {
                Grades g = new Grades(rs.getString(MAT), rs.getInt("voto"));
                list.add(g);

            } while (rs.next());

        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return list;
    }

    public static int saveGrades(Grades g) throws CustomSQLException, CustomException {

        int result = 0;

        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            result = ProfessorQuery.saveNewGrades(stmt, g);


        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return result;

    }

    public static List<Student> getClasse(String classe) throws CustomSQLException, CustomException {


        List<Student> list = new ArrayList<>();
        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = ProfessorQuery.getStudentsOfClass(stmt, classe);
            assert rs != null;
            if (!rs.first()) {
                return list;
            }
            rs.first();
            do {
                Student u = new Student(rs.getString("name"), rs.getString("lastname"), rs.getInt("matricola"), classe);

                list.add(u);

            } while (rs.next());
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return list;
    }

    public static int saveAbsence(Absences a) throws SQLException, CustomException {

        Connection con = DataBase.getInstance().getConnection();
        int result = 0;
        Statement stmt = null;
        try {
            stmt = con.createStatement();

            result = ProfessorQuery.saveNewAbsences(stmt, a);

        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return result;

    }

    public static int deleteAbsence(int matricola, Date d) {

        int result = 0;

        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            result = ProfessorQuery.deleteAbsences(stmt, matricola, d);


        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return result;

    }

    public static int deleteGrades(int matricola, Date d, String currentMatter) throws CustomSQLException, CustomException {

        int result = 0;

        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();


            result = ProfessorQuery.deleteGrades(stmt, matricola, d, currentMatter);

        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return result;
    }

    public static int deleteHomework(String description) throws CustomSQLException, CustomException {
        int result = 0;

        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            result = ProfessorQuery.deleteHomework(stmt, description);


        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return result;
    }

    public static List<Argument> getArguments(int matricola, String s,String matter) throws CustomSQLException, CustomException {


        List<Argument> list = new ArrayList<>();
        try {
            Connection con = DataBase.getInstance().getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = ProfessorQuery.getArgument(stmt, matricola, s);
            if (rs == null)
                return list;

            if (!rs.first()) {
                return list;
            }
            rs.first();
            do {
                Argument arg = new Argument(matricola, rs.getString("descrizione"), rs.getString(MAT), rs.getString(CLASS), rs.getInt("count" + ""));
                if(arg.getMateria().equals(matter))
                        list.add(arg);

            } while (rs.next());

            rs.close();
            stmt.close();

        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        }
        return list;

    }

    public static int saveArgument(Argument arg) throws SQLException, CustomException {

        int result = 0;
        Statement stmt = null;

        try {
            Connection con = DataBase.getInstance().getConnection();

            stmt = con.createStatement();

            result = ProfessorQuery.saveNewArg(stmt, arg.getMatricolaProfessore(), arg.getClasse(), arg.getDescprition(), arg.getMateria(), arg.getIndex());


        } catch (SQLException se) {
            throw new CustomSQLException(se);
        } catch (Exception e) {
            throw new CustomException(ERR, e);
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return result;

    }
}
