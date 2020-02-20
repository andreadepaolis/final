package database;

import utils.CustomSQLException;

import java.sql.*;

public class DataBase {

    private static DataBase db;
    private  Connection con ;

    private DataBase() {
        // private constructor //
    }

    static DataBase getInstance(){
        if(db==null){
            db= new DataBase();
        }
        return db;
    }

    Connection getConnection() throws CustomSQLException {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            if (con == null) {
                String password = "password";
                String userName = "root";
                String url = "jdbc:mysql://localhost:3306/project12?serverTimezone=Europe/Rome";
                con = DriverManager.getConnection(url, userName, password);
            }
            return con;
        } catch (Exception e) {
            throw new CustomSQLException(e);
        }
    }


}


