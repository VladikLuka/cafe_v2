package by.javatr.cafe.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Enumeration for configuration the application. Has property <b>INSTANCE</b>
 */
public enum ApplicationConfiguration {
    /** Property - instance */
    INSTANCE;

    Logger logger = LogManager.getLogger(ApplicationConfiguration.class);

    /**
     * Url database
     */
    private String dbUrl;
    /**
     * Username database
     */
    private String dbUser;
    /**
     * Database password
     */
    private String dbPassword;
    /**
     * Default max size of connections in pool
     */
    private final int defaultMaxSize = 32;
    /**
     * Default min size of connection in pool
     */
    private final int defaultInitSize = 12;
    /**
     * Default timeout of retrieving connection from connection pool in milliseconds
     */
    private final int defaultTimeout = 16000;
    /**
     * Default max size of connections in pool
     */
    private int maxSize;
    /**
     * Default min size of connections in pool
     */
    private int initSize;
    /**
     * Step of adding connections to base
     */
    private int stepSize;
    /**
     * Timeout of retrieving connection from connection pool in milliseconds
     */
    private int timeout;
    /**
     * Type of db
     */
    private String db;
    /**
     * Standard package of container
     */
    private String diPackage;

    /**
     * Init properties of application
     */
    ApplicationConfiguration() {
        initDbProperties();
        intiDiProperties();
        logger.info("properties has been initialized");
    }


    /**
     * Initialize database properties
     */
    private void initDbProperties() {
        final ResourceBundle dbBundle = ResourceBundle.getBundle("db");


        if (Objects.nonNull(dbBundle.getString("dbUrl"))) {
            dbUrl = dbBundle.getString("dbUrl");
        }
        if (Objects.nonNull(dbBundle.getString("dbUser"))) {
            dbUser = dbBundle.getString("dbUser");
        }
        if (Objects.nonNull(dbPassword = dbBundle.getString("dbPassword"))) {
            dbPassword = dbBundle.getString("dbPassword");
        }
        if (Objects.nonNull(db = dbBundle.getString("db"))) {
            this.db = dbBundle.getString("db");
        }
        if (Objects.nonNull(dbBundle.getString("connectPool_Max_Size"))) {
            maxSize = Integer.parseInt(dbBundle.getString("connectPool_Max_Size"));
        }
        if (Objects.nonNull(dbBundle.getString("connectPool_Init_Size"))) {
            initSize = Integer.parseInt(dbBundle.getString("connectPool_Init_Size"));
        }
        if (Objects.nonNull(dbBundle.getString("connectPool_Step_Size"))) {
            stepSize = Integer.parseInt(dbBundle.getString("connectPool_Step_Size"));
        }

        if (Objects.nonNull(dbBundle.getString("connectPool_Step_Size"))) {
            timeout = Integer.parseInt(dbBundle.getString("timeout"));
        }

    }


    /**
     * Initialize dependency container properties
     */
    private void intiDiProperties(){
        final ResourceBundle diBundle = ResourceBundle.getBundle("di");

        if(Objects.nonNull(diBundle.getString("diPackage"))){
            diPackage = diBundle.getString("diPackage");
        }

    }


    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getDefaultMaxSize() {
        return defaultMaxSize;
    }

    public int getDefaultInitSize() {
        return defaultInitSize;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getInitSize() {
        return initSize;
    }

    public void setInitSize(int initSize) {
        this.initSize = initSize;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getDiPackage() {
        return diPackage;
    }

    public void setDiPackage(String diPackage) {
        this.diPackage = diPackage;
    }
}
