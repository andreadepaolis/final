package database;

import java.sql.*;

public class DataBase {

    private static DataBase db;
    private static Connection con ;
    private static Statement stmt;


    private DataBase() {
        // private constructor //
    }

    static DataBase getInstance(){
        if(db==null){
            db= new DataBase();
        }
        return db;
    }

    Connection getConnection() {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            if (con == null) {
                String password = "password";
                String userName = "root";
                String url = "jdbc:mysql://localhost:3306/project12";
                con = DriverManager.getConnection(url, userName, password);
            }
            return con;
        } catch (Exception e) {
            System.out.print("Error : " + e.getMessage());
            return con;
        }
    }

    public void closeConnection(Connection connect) throws SQLException {

        if(connect != null){
            try {
                connect.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

}


