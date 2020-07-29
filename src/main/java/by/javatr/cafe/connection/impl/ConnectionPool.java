package by.javatr.cafe.connection.impl;

import by.javatr.cafe.config.ApplicationConfiguration;
import by.javatr.cafe.connection.IConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.*;


/**
 * Contains open connections
 */
public enum ConnectionPool implements IConnectionPool {

    CONNECTION_POOL;

    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private final ApplicationConfiguration properties;
    private final BlockingQueue<Connection> freeConnections = new LinkedBlockingQueue<>();
    private final BlockingQueue<Connection> busyConnections = new LinkedBlockingQueue<>();

    ConnectionPool(){
        properties = ApplicationConfiguration.INSTANCE;
        initConnection();
        logger.info("connectionPool init");
    }

    /**
     * Retrieve connection from pool
     * @return Connection
     */
    @Override
    public Connection retrieve() {

        Connection connection = null;
        if(freeConnections.size() != 0){
            try {
                connection = freeConnections.poll(properties.getTimeout(), TimeUnit.MILLISECONDS);
                if(connection == null){
                    logger.error("failed to getting connection, too long");
                }
            } catch (InterruptedException e) {
                logger.error("Interrupted ex in connection pool retrieve", e);
            }
        }else if(freeConnections.size() + busyConnections.size() < properties.getMaxSize()){
                createConnections(properties.getStepSize());
        }
        if(Objects.nonNull(connection)){
            busyConnections.add(connection);
        }
        return connection;
    }

    /**
     * Returns connection to the pool
     * @param connection connection to database
     * @return boolean
     */
    @Override
    public boolean release(Connection connection) {

        if (connection == null){
            return false;
        }
        if (busyConnections.contains(connection)){
            freeConnections.add(connection);
            return busyConnections.remove(connection);
        }
        return false;
    }

    /**
     * Initialize connection pool
     */
    private void initConnection(){
        checkPoolConfig(properties);
        createConnections(properties.getInitSize());
    }

    /**
     * Check connection pool configuration
     * @param properties contains connection pool properties
     */
    private void checkPoolConfig(ApplicationConfiguration properties){

            if(properties.getInitSize() < 1){
                logger.warn("Init_size is smaller than 1, setting initSize to: " + properties.getDefaultInitSize());
                properties.setInitSize(properties.getDefaultInitSize());
            }

            if (properties.getMaxSize() < properties.getInitSize()){
                logger.warn("initialSize is larger than maxSize, setting maxSize to: " + properties.getDefaultMaxSize());
                properties.setMaxSize(properties.getDefaultMaxSize());
            }

            if (properties.getInitSize() > properties.getMaxSize()){
                logger.warn("initSize is larger than maxSize, setting minSize to: " + properties.getDefaultInitSize());
                properties.setInitSize(properties.getDefaultInitSize());
            }

            if(properties.getTimeout() < 0){
                logger.warn("timeout is smaller than 0, setting timeout to: " + properties.getDefaultTimeout());
                properties.setTimeout(properties.getDefaultTimeout());
            }
    }

    /**
     * Fill connection pool with connections
     * @param connectCount the number of connections to be created
     */
    private void createConnections(int connectCount) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("DB DRIVER NOT FOUND");
        }
        String jdbcUrl;
        if(System.getenv("RDS_DB_NAME") != null) {
            String dbName = System.getenv("RDS_DB_NAME");
            String userName = System.getenv("RDS_USERNAME");
            String password = System.getenv("RDS_PASSWORD");
            String hostname = System.getenv("RDS_HOSTNAME");
            String port = System.getenv("RDS_PORT");
             jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password + "&useUnicode=true&serverTimezone=UTC";
        }else {
            jdbcUrl = properties.getDbUrl()+ "&user=" + properties.getDbUser() + "&password=" + properties.getDbPassword();
        }
//        jdbcUrl = "jdbc:mysql://database-1.cs3cgmjptspr.us-east-2.rds.amazonaws.com:3306/rds?user=admin&password=vladislav7890&useUnicode=true&serverTimezone=UTC";
        for (int i = 0; i < connectCount; i++) {
            try {
                ConnectionProxy connection = null;
                    connection = new ConnectionProxy(DriverManager.getConnection(jdbcUrl));
                freeConnections.put(connection);
            } catch (SQLException e){
                logger.error("an error occurred while creating the connection", e);
            } catch (InterruptedException e) {
                logger.error("an error occurred while putting connection in pool");
            }
        }
    }

    /**
     * Terminate connection pool
     */
    public void terminatePool(){
            try {

                for (int i = 0; i < busyConnections.size(); i++) {
                    ConnectionProxy connection = (ConnectionProxy) busyConnections.poll();
                    connection.shutDown();
                }

                for (int i = 0; i < freeConnections.size(); i++) {
                    ConnectionProxy connection = (ConnectionProxy) freeConnections.poll();
                    connection.shutDown();
                }
            }catch (SQLException e){
                logger.error("invalid terminate pool");
            }
    }

    public int getBusyConnections() {
        return busyConnections.size();
    }

    public int getFreeConnections() {
            return freeConnections.size();
    }
}
