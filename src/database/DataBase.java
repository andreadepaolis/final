package database;


import java.sql.*;

class DataBase {

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

    Connection getConnection() throws  SQLException {

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
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }


}


