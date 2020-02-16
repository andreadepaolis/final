package database.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class StudentQuery {


    public static ResultSet login(Statement stmt, int matricola, String password) throws SQLException {
        String sql = String.format("SELECT * FROM users where matricola ='%d' AND password = '%s'", matricola, password);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ResultSet getById(Statement stmt, int userid) throws SQLException  {
        String sql = String.format("SELECT * FROM users where matricola =%s",userid);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getGrades(Statement stmt, int id) {
        String sql = String.format("SELECT * FROM grades where matricolaStudente =%d",id);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getAssenze(Statement stmt, int id) {
        String sql = String.format("SELECT * FROM assenza WHERE matricolaStudente =%d",id);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getHomework(Statement stmt, String classe) {
        String sql = String.format("SELECT * FROM homework WHERE class ='%s'",classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getSchedule(Statement stmt, String classe) {
        String sql = String.format("SELECT * FROM scheduleinfo WHERE class ='%s'",classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getGrades(Statement stmt, int matricola, String matter) {

        String sql = String.format("SELECT * FROM grades where matricolaStudente ='%d' AND materia ='%s'",matricola,matter);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getPin(Statement stmt, int id) {
        String sql = String.format("SELECT * FROM users where matricola ='%d'", id);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int updateAbsences(Statement stmt, Date data, int matricolaStudente) {
        String sql = String.format("UPDATE assenza SET checkbit = 0 WHERE matricolaStudente ='%s' AND data='%tF'",matricolaStudente,data);
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
        }
    }
