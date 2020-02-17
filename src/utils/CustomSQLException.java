package utils;

import java.sql.SQLException;

public class CustomSQLException extends SQLException {

    public CustomSQLException(String message, Throwable err){
        super(message,err);
    }
}
