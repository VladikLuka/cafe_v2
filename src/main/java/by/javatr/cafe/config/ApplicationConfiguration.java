package by.javatr.cafe.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.ResourceBundle;

public enum ApplicationConfiguration {

    INSTANCE;

    Logger logger = LogManager.getLogger(ApplicationConfiguration.class);

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private final int default_Max_Size = 32;
    private final int default_init_Size = 12;
    private final int default_Timeout = 16000;
    private int max_Size;
    private int init_Size;
    private int step_Size;
    private int timeout;
    private String db;
    private String diPackage;

    ApplicationConfiguration() {
        initProperties();
        logger.info("properties has been initialized");
    }


    private void initProperties() {
        final ResourceBundle dbBundle = ResourceBundle.getBundle("db");
        final ResourceBundle diBundle = ResourceBundle.getBundle("di");


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
            max_Size = Integer.parseInt(dbBundle.getString("connectPool_Max_Size"));
        }
        if (Objects.nonNull(dbBundle.getString("connectPool_Init_Size"))) {
            init_Size = Integer.parseInt(dbBundle.getString("connectPool_Init_Size"));
        }
        if (Objects.nonNull(dbBundle.getString("connectPool_Step_Size"))) {
            step_Size = Integer.parseInt(dbBundle.getString("connectPool_Step_Size"));
        }

        if (Objects.nonNull(dbBundle.getString("connectPool_Step_Size"))) {
            timeout = Integer.parseInt(dbBundle.getString("timeout"));
        }

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

    public int getDefault_Max_Size() {
        return default_Max_Size;
    }

    public int getDefault_init_Size() {
        return default_init_Size;
    }

    public int getDefault_Timeout() {
        return default_Timeout;
    }

    public int getMax_Size() {
        return max_Size;
    }

    public void setMax_Size(int max_Size) {
        this.max_Size = max_Size;
    }

    public int getInit_Size() {
        return init_Size;
    }

    public void setInit_Size(int init_Size) {
        this.init_Size = init_Size;
    }

    public int getStep_Size() {
        return step_Size;
    }

    public void setStep_Size(int step_Size) {
        this.step_Size = step_Size;
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
