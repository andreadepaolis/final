package database.query;

import utils.CustomSQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Logger;

public abstract class StudentQuery {

    private static final Logger LOGGER = Logger.getLogger(StudentQuery.class.getName());


    public static ResultSet login(Statement stmt, int matricola, String password) throws SQLException {
        String sql = String.format("SELECT * FROM users where matricola ='%d' AND password = '%s'", matricola, password);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }
    public static ResultSet getById(Statement stmt, int userid) throws SQLException  {
        String sql = String.format("SELECT * FROM users where matricola =%s",userid);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getGrades(Statement stmt, int id) {
        String sql = String.format("SELECT * FROM grades where matricolaStudente =%d",id);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getAssenze(Statement stmt, int id) {
        String sql = String.format("SELECT * FROM assenza WHERE matricolaStudente =%d",id);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getHomework(Statement stmt, String classe) {
        String sql = String.format("SELECT * FROM homework WHERE class ='%s'",classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getSchedule(Statement stmt, String classe) {
        String sql = String.format("SELECT * FROM scheduleinfo WHERE class ='%s'",classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getGrades(Statement stmt, int matricola, String matter) {

        String sql = String.format("SELECT * FROM grades where matricolaStudente ='%d' AND materia ='%s'",matricola,matter);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getPin(Statement stmt, int id) {
        String sql = String.format("SELECT * FROM users where matricola ='%d'", id);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static int updateAbsences(Statement stmt, Date data, int matricolaStudente) {
        String sql = String.format("UPDATE assenza SET checkbit = 0 WHERE matricolaStudente ='%s' AND data='%tF'",matricolaStudente,data);
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;
        }

    public static ResultSet getArgument(Statement stmt, String currentMatter,String classe) throws CustomSQLException {

        String sql = String.format("SELECT * FROM Arguments WHERE materia = '%s' AND class = '%s'",currentMatter,classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            throw new CustomSQLException(e);
        }
    }
}
