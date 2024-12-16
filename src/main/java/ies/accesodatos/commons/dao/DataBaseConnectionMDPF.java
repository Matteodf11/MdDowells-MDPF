package ies.accesodatos.commons.dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnectionMDPF {
    private String config_path;
    private String connection_string;
    private Connection conexion;
    private String logname;
    private Logger logger;

    public DataBaseConnectionMDPF() {

    }

    public void open() throws Exception {
        FileReader fr = null;
        File f = new File(System.getProperty("user.dir") + this.getConfig_path());
        fr = new FileReader(f);
        Properties props = new Properties();
        try {
            props.load(fr);
        } catch (IOException ex) {
            this.loggerError(Level.SEVERE, ex);
        }
        this.logname = props.getProperty("logfile");
        String user = props.getProperty("database.user");
        String password = props.getProperty("database.password");
        this.connection_string = props.getProperty("database.path") + ";user=" + user + ";password=" + password;
        this.conexion = DriverManager.getConnection(this.connection_string);
    }

    private void loggerError(Level level, Exception ex) {
        System.out.println(ex);
    }

    private void loggerMessage(Level level, String msg) {
        System.out.println(msg);
    }

    public ResultSet executeQuery(String query) throws Exception {
        if (this.conexion != null) {
            try {
                Statement s = this.conexion.createStatement();
                this.loggerMessage(Level.INFO, query);
                return s.executeQuery(query);
            } catch (SQLException ex) {
                this.loggerError(Level.SEVERE, ex);
            }
        } else {
            throw new Exception("Conexion cerrada o nula");
        }
        return null;
    }

    public int deleteQuery(String query) throws Exception {
        if (this.conexion != null) {
            try {
                Statement s = this.conexion.createStatement();
                this.loggerMessage(Level.INFO, query);
                return s.executeUpdate(query);
            } catch (SQLException ex) {
                this.loggerError(Level.SEVERE, ex);
            }
        } else {
            throw new Exception("Conexion cerrada o nula");
        }
        return -1;
    }

    public int updateQuery(String query) throws Exception {
        if (this.conexion != null) {
            try {
                Statement s = this.conexion.createStatement();
                this.loggerMessage(Level.INFO, query);
                return s.executeUpdate(query);
            } catch (SQLException ex) {
                this.loggerError(Level.SEVERE, ex);
            }
        } else {
            throw new Exception("Conexion cerrada o nula");
        }
        return -1;
    }

    public int insertQuery(String query) throws Exception {
        int index = -1;
        if (this.conexion != null) {
            try {
                PreparedStatement ps = this.conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                int generatedKey = 0;
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }
                this.loggerMessage(Level.INFO, query);
                return generatedKey;
            } catch (SQLException ex) {
                this.loggerError(Level.SEVERE, ex);
            }
        } else {
            throw new Exception("Conexion cerrada o nula");
        }
        return -1;
    }

    public Connection getConnection() {
        return this.conexion;
    }

    public void close() throws SQLException {
        this.conexion.close();
        this.loggerMessage(Level.INFO, "cerrando conexión");
        this.conexion = null;
    }

    public String getConfig_path() {
        return config_path;
    }

    public void setConfig_path(String config_path) {
        this.config_path = config_path;
    }

    public boolean isOpen() {
        return this.conexion != null;
    }


}