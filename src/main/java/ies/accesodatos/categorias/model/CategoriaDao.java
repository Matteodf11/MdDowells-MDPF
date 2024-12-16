package ies.accesodatos.categorias.model;

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

public class CategoriaDao extends ADao<Categoria, Integer> {
    private DataBaseConnectionMDPF conexion;
    private final String tableName = "categorias";
    private final String selectall = "select * from APP." + this.tableName;
    private final String selectbyid = "select * from APP." + this.tableName + " where ID=?";
    private final String deletebyid = "delete from APP." + this.tableName + " where ID=?";
    private final String insertsql = "insert into APP." + this.tableName + "(NOMBRE,DESCRIPCION," + "IMG_SRC,ACTIVO)VALUES(?,?,?,?)";
    private final String updatesql = "update APP." + this.tableName + " set NOMBRE=?,DESCRIPCION=?," + "IMG_SRC=?,ACTIVO=? WHERE ID=?";

    public CategoriaDao() {
        super();
    }

    public DataBaseConnectionMDPF getConn() {
        return conexion;
    }

    public void setConexion(DataBaseConnectionMDPF conexion) {
        this.conexion = conexion;
    }

    @Override
    public Categoria getById(Integer id) {
        Categoria categoria = new Categoria();
        try {
            PreparedStatement pst = this.getConn().getConnection().prepareStatement(this.selectbyid);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) categoria = this.registerToObject(rs);
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoria;
    }


    @Override
    public List<Categoria> getAll() {
        ArrayList<Categoria> scl = new ArrayList<>();
        Categoria tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = this.getConn().getConnection().prepareStatement(this.selectall);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = this.registerToObject(rs);
                scl.add(tempo);
            }
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scl;
    }

    @Override
    public void update(Categoria item) {
        try {
            PreparedStatement pst = this.getConn().getConnection().prepareStatement(this.updatesql);
            pst.setString(1, item.getNombre());
            pst.setString(2, item.getDescripcion());
            pst.setString(3, item.getImg_src());
            pst.setBoolean(4, item.isActivo());
            pst.setInt(5, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Categoria item) {
        try {
            PreparedStatement pst = this.getConn().getConnection().prepareStatement(this.deletebyid);
            pst.setInt(1, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteById(Integer key) {
        try {
            PreparedStatement pst = this.getConn().getConnection().prepareStatement(this.deletebyid);
            pst.setInt(1, key);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insert(Categoria item) {

        PreparedStatement pst;
        try {
            pst = this.getConn().getConnection().prepareStatement(this.insertsql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getNombre());
            pst.setString(2, item.getDescripcion());
            pst.setString(3, item.getImg_src());
            pst.setBoolean(4, item.isActivo());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Categoria registerToObject(ResultSet rs) {
        Categoria categoria = new Categoria();
        try {
            categoria.setId(rs.getInt("ID"));
            categoria.setNombre(rs.getString("NOMBRE"));
            categoria.setDescripcion(rs.getString("DESCRIPCION"));
            categoria.setActivo(rs.getBoolean("ACTIVO"));
            categoria.setImg_src(rs.getString("IMG_SRC"));
        } catch (SQLException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoria;
    }
}
