package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/petshop";
    private static final String USER = "root";
    private static final String PASS = "Iubescapa12*";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method establishes the connection with the database
     * @return an object of type Connection, which contains the connection with the database
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database. Invalid input data set.");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * The method returns an instance of the connection
     * @return a single instance of the created connection
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * This method closes the connection with the database
     * @param connection the connection that has to be closed
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection.");
            }
        }
    }

    /**
     * This method closes the statement
     * @param statement the statement that has to be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            }
            catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement.");
            }
        }
    }

    /**
     * This method closes the result set
     * @param resultSet the result set that has to be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet.");
            }
        }
    }
}
