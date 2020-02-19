package database.query;

import model.Absences;
import model.Grades;
import model.Homework;
import utils.CustomSQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;



public interface ProfessorQuery {


    static ResultSet login(Statement stmt, int matricola, String password) throws CustomSQLException {
        String sql = String.format("SELECT * FROM professor WHERE matricola ='%d' AND password = '%s'", matricola, password);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

     static ResultSet getStudentsOfClass(Statement stmt, String classe) throws CustomSQLException {
        String sql = String.format("SELECT * FROM users WHERE class = '%s'", classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);

        }

    }

     static ResultSet getHomework(Statement stmt, int professorId) throws CustomSQLException {

        String sql = String.format("SELECT * FROM Homework WHERE matricolaProfessore = '%d'",professorId);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }

    }

     static int saveNewHomework(Statement stmt, Homework h) throws CustomSQLException {

        int mp = h.getMatricolaProfessore();
        String c = h.getClasse();
        String m = h.getMatter();
        String d = h.getDescription();
        Date data = h.getData();
        String sql = String.format("INSERT INTO homework(matricolaProfessore,class,materia,descrizione,data) VALUES('%d','%s','%s','%s','%tF')", mp, c, m, d, data);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }


     static int saveNewGrades(Statement stmt, Grades g) throws CustomSQLException {

        int ms = g.getMatricolaStudente();
        String nameP = g.getNomeProfessore();
        String tipo = g.getTipo();
        int voto = g.getVoto();
        int pfid = g.getProfessorid();
        Date d = g.getData();
        String materia = g.getMateria();
        String sql = String.format("INSERT INTO Grades(matricolaStudente,matricolaProfessore,nomeProfessore,materia,voto,tipo,data) VALUES('%d','%d','%s','%s','%d','%s','%tF')", ms, pfid, nameP, materia, voto, tipo, d);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

     static ResultSet getClassi(Statement stmt, int professorid) throws CustomSQLException {

        String sql = String.format("SELECT * FROM classi WHERE matricolaProfessore = '%s'", professorid);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);

        }
    }

     static ResultSet getMaterie(Statement stmt, int matricola) throws CustomSQLException {
        String sql = String.format("SELECT * FROM materia WHERE matricolaProfessore = '%s'", matricola);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
                 throw new CustomSQLException(e);

        }
    }

     static ResultSet getUserGradesForMateria(Statement stmt, int matricola, String materia) throws CustomSQLException {
        String sql = String.format("SELECT * FROM grades WHERE matricolaStudente = '%s' AND materia = '%s'", matricola, materia);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);

        }
    }

     static ResultSet getScheduleForProfessor(Statement stmt, int professorid) throws CustomSQLException {
        String sql = String.format("SELECT * FROM scheduleinfo WHERE matricolaProfessore = '%s'", professorid);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);

        }
    }

     static int saveNewAbsences(Statement stmt, Absences a) throws CustomSQLException {

        int ms = a.getMatricolaStudente();

        String tipo = a.getTipo();

        Date d = a.getData();
        int checkbit = a.getCheckbit();

        String sql = String.format("INSERT INTO Assenza(matricolaStudente,data,tipo,checkbit) VALUES('%d','%tF','%s','%d')", ms, d, tipo, checkbit);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

     static int deleteAbsences(Statement stmt, int matricola, Date d) throws CustomSQLException {


        String sql = String.format("DELETE FROM Assenza WHERE matricolaStudente = '%s'AND data = '%tF'", matricola, d);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

     static int deleteGrades(Statement stmt, int matricola, Date d, String currentMatter) throws CustomSQLException {
        String sql = String.format("DELETE FROM Grades WHERE matricolaStudente='%s' AND data='%tF' AND materia='%s'", matricola,d,currentMatter);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

     static int deleteHomework(Statement stmt, String description) throws CustomSQLException {
        String sql = String.format("DELETE FROM Homework WHERE descrizione='%s'", description);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }

    }

     static ResultSet getArgument(Statement stmt, int matricola, String s) throws CustomSQLException {
        String sql = String.format("SELECT * FROM arguments WHERE matricolaProfessore = '%s' AND class = '%s'", matricola,s);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);

        }
    }



     static int saveNewArg(Statement stmt, int matricolaProfessore, String classe, String descprition, String materia, int index) throws CustomSQLException {

        String sql = String.format("INSERT INTO arguments(matricolaProfessore,descrizione,class,materia,count) VALUES(%d,'%s','%s','%s',%d)", matricolaProfessore, descprition, classe, materia, index);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }

    static int deleteArguments(Statement stmt, String desc) throws CustomSQLException {
        String sql = String.format("DELETE FROM arguments WHERE descrizione='%s'", desc);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new CustomSQLException(e);
        }
    }
}