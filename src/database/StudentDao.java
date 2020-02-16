package database;

import bean.StudentBean;
import database.query.StudentQuery;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public abstract class  StudentDao {

    private static final Logger LOGGER = Logger.getLogger(StudentDao.class.getName());


    public static StudentBean validate(int matricola, String password) throws SQLException {


        DataBase db = DataBase.getInstance();
        Connection con = db.getConnection();

        try{
            Statement stmt = con.createStatement();

            ResultSet rs= StudentQuery.login(stmt,matricola,password);


            assert rs != null;
            if(rs.first()){
                rs.first();
                StudentBean u = new StudentBean();
                u.setName(rs.getString("name"));
                u.setMatricola(rs.getInt("matricola"));
                u.setLastname(rs.getString("lastname"));
                u.setClasse(rs.getString("class"));
                return u;
            }

            else {
                return null;

            }

        }catch(Exception e) {
            LOGGER.info(e.toString());
            return null;
        }

    }
    public static ResultSet getGradesByClass(Statement stmt, int professorid, String classe) {

        String sql = String.format("SELECT * FROM grades WHERE matricolaProfessore = '%s'", professorid);

        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }
    public static Student getUserById(int id) {

        DataBase db = DataBase.getInstance();
        Connection con = db.getConnection();

        try{
            Statement stmt = con.createStatement();

            ResultSet rs= StudentQuery.getById(stmt,id);
            if (rs == null || !rs.first()) {
                return null;

            } else

                rs.first();
            //1 nome 2 lastname 3 matricola 4 classe
            return new Student(rs.getString(3),rs.getString(4),rs.getInt(1),rs.getString(5));


        }catch(Exception e) {
            LOGGER.info(e.toString());

            return null;
        }

    }

    public static List<Grades> getMyGrades(int id){
        Statement stmt = null;
        Connection con = null;
        List<Grades> allGrades = new ArrayList<>();

        try {

            DataBase db =  DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = StudentQuery.getGrades(stmt, id);


            if (!rs.first()){
                return list;

            }

            // riposizionamento del cursore
            rs.first();
            do{
                String materia = rs.getString("materia");
                int voto = rs.getInt("voto");
                String professor = rs.getString("nomeProfessore");
                String tipo = rs.getString("tipo");
                Date data = rs.getDate("data");

                Grades g = new Grades(materia, voto, tipo, professor, data);

                allGrades.add(g);

            }while(rs.next());

            rs.close();

        } catch (Exception s){
            LOGGER.info(s.toString());
        }
        return allGrades;
    }

    public static List<Absences> getMyAssenze(int id) {

        Statement stmt = null;
        Connection con = null;
        List<Absences> allAssenze = new ArrayList<>();

        try {

            DataBase db =  DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = StudentQuery.getAssenze(stmt, id);


            if (rs == null || !rs.first()) {
                return list;

            }

            // riposizionamento del cursore
            rs.first();
            do{

                String tipo = rs.getString("tipo");
                int checkbit = rs.getInt("checkbit");
                Date data = rs.getDate("data");

                Absences a = new Absences(id,tipo,data,checkbit);

                allAssenze.add(a);

            }while(rs.next());

            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        // STEP 5.2: Clean-up dell'ambiente

        return allAssenze;
    }

    public static List<Homework> getHomework(String classe, Date date) {

        List<Homework> allHomework = new ArrayList<>();

        DataBase db = DataBase.getInstance();
        Connection con = db.getConnection();

        try{
            Statement stmt = con.createStatement();

            ResultSet rs= StudentQuery.getHomework(stmt,classe);



            if(rs.first()){
                rs.first();

                do{

                    Homework hmw = new Homework(rs.getInt("matricolaProfessore"),rs.getString("class"),rs.getString("materia"),rs.getString("descrizione"),rs.getDate("data"));
                    allHomework.add(hmw);
                }while(rs.next());


        return allHomework;
            }

            else {
                return list;

            }

        }catch(Exception e) {
            LOGGER.info(e.toString());
            return null;
        }


    }

    public static List<ScheduleInfo> getSchedule(String classe) {
        DataBase db = DataBase.getInstance();
        Connection con = db.getConnection();

        List<ScheduleInfo> si = new ArrayList<>();

        try{
            Statement stmt = con.createStatement();

            ResultSet rs= StudentQuery.getSchedule(stmt,classe);

            assert rs != null;
            if(rs.first()){
                rs.first();

                do{
                    ScheduleInfo sch = new ScheduleInfo(rs.getInt("day"),rs.getInt("hours"),rs.getString("materia"),rs.getString("class"));
                    si.add(sch);
                }while(rs.next());


                return si;
            }

            else {
                return list;

            }

        }catch(Exception e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static List<Grades> getMyGrades(int matricola, String matter) {
        Statement stmt = null;
        Connection con = null;
        List<Grades> allGrades = new ArrayList<>();

        try {

            DataBase db =  DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = StudentQuery.getGrades(stmt, matricola,matter);


            assert rs != null;
            if (!rs.first()){
                return list;

            }
            // riposizionamento del cursore
            rs.first();
            do{
                String materia = rs.getString("materia");
                int voto = rs.getInt("voto");
                String professor = rs.getString("nomeProfessore");
                String tipo = rs.getString("tipo");
                Date data = rs.getDate("data");

                Grades g = new Grades(materia, voto, tipo, professor, data);

                allGrades.add(g);

            }while(rs.next());

            rs.close();

        } catch (Exception s){
            LOGGER.info(s.toString());
        }
        return allGrades;
    }

    public static List<String> getAllMatter(String myclasse) {

        Statement stmt = null;
        Connection con = null;
        List<String> matter = new ArrayList<>();

        try {

            DataBase db =  DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = StudentQuery.getSchedule(stmt,myclasse);


            assert rs != null;
            if (!rs.first()){
                return list;

            }
            // riposizionamento del cursore
            rs.first();
            do{
                String currentMatter = rs.getString("materia");
                if(!matter.contains(currentMatter))
                     matter.add(currentMatter);

            }while(rs.next());

            rs.close();

        } catch (Exception s){
            LOGGER.info(s.toString());
        }
        return matter;
    }

    public static String getPin(int id) {
        Statement stmt = null;
        Connection con = null;
        String pin = null;
        try {

            DataBase db =  DataBase.getInstance();
            con = db.getConnection();

            stmt = con.createStatement();
            ResultSet rs = StudentQuery.getPin(stmt,id);


            assert rs != null;
            if (!rs.first()){
                return null;

            }
            // riposizionamento del cursore
            rs.first();

            pin = rs.getString("pin");

            rs.close();
        } catch (Exception s){
            LOGGER.info(s.toString());
        }
        return pin;

    }

    public static int updateAbsence(Absences a) throws SQLException {
        Connection con = DataBase.getInstance().getConnection();
        Statement stmt = null;
        int result = 0;
        try {
             stmt = con.createStatement();

            result = StudentQuery.updateAbsences(stmt, a.getData(),a.getMatricolaStudente());

            stmt.close();

        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        finally {
            if(stmt != null)
                    stmt.close();
        }
        return result;
    }
}
