package ies.accesodatos.empleados.model;

import ies.accesodatos.commons.dao.ADao;
import ies.accesodatos.commons.dao.DataBaseConnectionMDPF;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpleadoDao extends ADao<Empleado,Integer> {
    private DataBaseConnectionMDPF conexion;
    private String tableName = "empleados";
    private String selectall = "select * from APP." + this.tableName;
    private String selectbyid = "select * from APP." + this.tableName + " where ID=?";
    private String deletebyid = "delete from APP." + this.tableName + " where ID=?";
    private String insertsql = "insert into APP." + this.tableName + "(NOMBRE,ACTIVO," + "CLAVE,CORREO)VALUES(?,?,?,?)";
    private String updatesql = "update APP." + this.tableName + " set NOMBRE=?,ACTIVO=?," + "CLAVE=?,CORREO=? WHERE ID=?";

    public DataBaseConnectionMDPF getConn() {
        return conexion;
    }

    public void setConexion(DataBaseConnectionMDPF conexion) {
        this.conexion = conexion;
    }

    public EmpleadoDao() {
        super();
    }
    @Override
    public Empleado getById(Integer id) {
        Empleado empleado = new Empleado();
        try {
            PreparedStatement pst =
                    this.getConn().getConnection().prepareStatement(this.selectbyid);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                empleado = this.registerToObject(rs);
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return empleado;
    }
    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement pst =
                    this.getConn().getConnection().prepareStatement(this.deletebyid);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
    @Override
    public List<Empleado> getAll() {
        ArrayList<Empleado> scl = new ArrayList<>();
        Empleado tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst =
                        this.getConn().getConnection().prepareStatement(this.selectall
                        );
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = this.registerToObject(rs);
                scl.add(tempo);
            }
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return scl;
    }
    @Override
    public void update(Empleado item) {
        try {
            PreparedStatement pst =
                    this.getConn().getConnection().prepareStatement(this.updatesql
                    );
            pst.setString(1, item.getNombre());
            pst.setBoolean(2, item.isActivo());
            pst.setString(3, item.getClave());
            pst.setString(4, item.getCorreo());
            pst.setInt(5, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
    @Override
    public void delete(Empleado item) {
        try {
            PreparedStatement pst =
                    this.getConn().getConnection().prepareStatement(this.deletebyid);
            pst.setInt(1, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
    @Override
    public void insert(Empleado item) {
        PreparedStatement pst;
        try {
            pst =
                    this.getConn().getConnection().prepareStatement(this.insertsql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getNombre());
            pst.setBoolean(2, item.isActivo());
            pst.setString(3, item.getClave());
            pst.setString(4, item.getCorreo());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
    //pasar de registro a objeeto
    private Empleado registerToObject(ResultSet r) {
        Empleado item = new Empleado();
        try {
            item.setId(r.getInt("ID"));
            item.setNombre(r.getString("NOMBRE"));
            item.setClave(r.getString("CLAVE"));
            item.setCorreo(r.getString("CORREO"));
            item.setActivo(r.getBoolean("ACTIVO"));
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return item;
    }
}