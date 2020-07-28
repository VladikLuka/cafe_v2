package by.javatr.cafe.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {

    public Connection retrieve() throws SQLException;

    public boolean release(Connection connection);

}
