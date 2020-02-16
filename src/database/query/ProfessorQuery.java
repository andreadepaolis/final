package database.query;

//import model.Assenze;
import model.Absences;
import model.Argument;
import model.Grades;
import model.Homework;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


public class ProfessorQuery {
    private static final Logger LOGGER = Logger.getLogger(ProfessorQuery.class.getName());

    public static ResultSet login(Statement stmt, int matricola, String password) {
        String sql = String.format("SELECT * FROM professor WHERE matricola ='%d' AND password = '%s'", matricola, password);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getStudentsOfClass(Statement stmt, String classe) {
        String sql = String.format("SELECT * FROM users WHERE class = '%s'", classe);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }

    }

    public static ResultSet getHomework(Statement stmt, int professorId) {

        String sql = String.format("SELECT * FROM Homework WHERE matricolaProfessore = '%d'",professorId);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }

    }

    public static int saveNewHomework(Statement stmt, Homework h) {

        int mp = h.getMatricolaProfessore();
        String c = h.getClasse();
        String m = h.getMatter();
        String d = h.getDescription();
        Date data = h.getData();
        String sql = String.format("INSERT INTO homework(matricolaProfessore,class,materia,descrizione,data) VALUES('%d','%s','%s','%s','%tF')", mp, c, m, d, data);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;
    }

    public static ResultSet getGradesByClass(Statement stmt, int professorid, String classe) {

        // String sql = String.format("SELECT * FROM grades WHERE matricolaProfessore = '%s' INNER JOIN users ON users.class = '%s'",professorid,classe);
        String sql = String.format("SELECT * FROM grades WHERE matricolaProfessore = '%s'", professorid);

        System.out.println(sql);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    /*public static int saveNewAssenza(Statement stmt, Assenze a) {
        int ms = a.getMatricolaStudente();
        String type = a.getTipo();
        Date data = a.getData();
        String sql = String.format("INSERT INTO assenze(matricolaStudente,data,tipo,checkbit) VALUES('%d','%tD','%s','%d')", ms, data, type, 1);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int saveNewGrades(Statement stmt, Grades g) {

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
            e.printStackTrace();
        }
        return 0;
    }

     */
    public static int saveNewGrades(Statement stmt, Grades g) {

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
            LOGGER.info(e.toString());
        }
        return 0;
    }

    public static ResultSet getClassi(Statement stmt, int professorid) {

        String sql = String.format("SELECT * FROM classi WHERE matricolaProfessore = '%s'", professorid);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getMaterie(Statement stmt, int matricola) {
        String sql = String.format("SELECT * FROM materia WHERE matricolaProfessore = '%s'", matricola);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getUserGradesForMateria(Statement stmt, int matricola, String materia) {
        String sql = String.format("SELECT * FROM grades WHERE matricolaStudente = '%s' AND materia = '%s'", matricola, materia);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static ResultSet getScheduleForProfessor(Statement stmt, int professorid) {
        String sql = String.format("SELECT * FROM scheduleinfo WHERE matricolaProfessore = '%s'", professorid);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }

    public static int saveNewAbsences(Statement stmt, Absences a) {

        int ms = a.getMatricolaStudente();

        String tipo = a.getTipo();

        Date d = a.getData();
        int checkbit = a.getCheckbit();

        String sql = String.format("INSERT INTO Assenza(matricolaStudente,data,tipo,checkbit) VALUES('%d','%tF','%s','%d')", ms, d, tipo, checkbit);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;
    }

    public static int deleteAbsences(Statement stmt, int matricola, Date d) {


        String sql = String.format("DELETE FROM Assenza WHERE matricolaStudente = '%s'AND data = '%tF'", matricola, d);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;


    }

    public static int deleteGrades(Statement stmt, int matricola, Date d, String current_matter) {
        String sql = String.format("DELETE FROM Grades WHERE matricolaStudente='%s' AND data='%tF' AND materia='%s'", matricola,d,current_matter);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;
    }

    public static int deleteHomework(Statement stmt, String description) {
        String sql = String.format("DELETE FROM Homework WHERE descrizione='%s'", description);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;



    }

    public static ResultSet getArgument(Statement stmt, int matricola, String s) {
        String sql = String.format("SELECT * FROM arguments WHERE matricolaProfessore = '%s' AND class = '%s'", matricola,s);
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
            return null;
        }
    }



    public static int saveNewArg(Statement stmt, int matricolaProfessore, String classe, String descprition, String materia, int index) {

        String sql = String.format("INSERT INTO arguments(matricolaProfessore,descrizione,class,materia,count) VALUES(%d,'%s','%s','%s',%d)", matricolaProfessore, descprition, classe, materia, index);

        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return 0;

    }
}