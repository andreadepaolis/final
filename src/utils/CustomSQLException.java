package utils;

import java.sql.SQLException;

public class CustomSQLException extends SQLException {

    public CustomSQLException(Throwable err){
        super("SQL Error",err);
    }
}
