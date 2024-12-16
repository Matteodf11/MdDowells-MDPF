package ies.accesodatos.productos.model;


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

public class ProductoDao extends ADao<Producto, Integer> {
    private final String tableName = "productos";
    private final String selectall = "select * from APP." + this.tableName;
    private final String selectbyid = "select * from APP." + this.tableName + " where ID=?";
    private final String deletebyid = "delete from APP." + this.tableName + " where ID=?";
    private final String insertsql = "insert into APP." + this.tableName + "(CATEGORIA_ID, NOMBRE ,DESCRIPCION," + "IMG_SRC,ACTIVO,PRECIO)VALUES(?,?,?,?,?,?)";
    private final String updatesql = "update APP." + this.tableName + " set CATEGORIA_ID=?,NOMBRE=?,DESCRIPCION=?," + "ACTIVO=?,IMG_SRC=?, PRECIO=? WHERE ID=?";
    private DataBaseConnectionMDPF conexion;

    public ProductoDao() {
        super();
    }

    public DataBaseConnectionMDPF getConn() {
        return conexion;
    }

    public void setConexion(DataBaseConnectionMDPF conexion) {
        this.conexion = conexion;
    }

    @Override
    public Producto getById(Integer id) {
        Producto categoria = new Producto();
        try {
            PreparedStatement pst = this.getConn().getConnection().prepareStatement(this.selectbyid);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) categoria = this.registerToObject(rs);
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ies.accesodatos.productos.model.ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoria;
    }


    @Override
    public List<Producto> getAll() {
        ArrayList<Producto> scl = new ArrayList<>();
        Producto tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = this.getConn().getConnection().prepareStatement(this.selectall);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ies.accesodatos.productos.model.ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = this.registerToObject(rs);
                scl.add(tempo);
            }
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ies.accesodatos.productos.model.ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scl;
    }

    @Override
    public void update(Producto item) {
        try {
            PreparedStatement pst =
                    this.getConn().getConnection().prepareStatement(this.updatesql
                    );
            pst.setInt(1, item.getCategoriaId());
            pst.setString(2, item.getNombre());
            pst.setString(3, item.getDescripcion());
            pst.setBoolean(4, item.isActivo());
            pst.setString(5, item.getImg_src());
            pst.setDouble(6, item.getPrecio());
            pst.setInt(7, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void delete(Producto item) {
        try {
            PreparedStatement pst = this.getConn().getConnection().prepareStatement(this.deletebyid);
            pst.setInt(1, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ies.accesodatos.productos.model.ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ies.accesodatos.productos.model.ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insert(Producto item) {

        PreparedStatement pst;
        try {
            pst = this.getConn().getConnection().prepareStatement(this.insertsql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, item.getCategoriaId());
            pst.setString(2, item.getNombre());
            pst.setString(3, item.getDescripcion());
            pst.setString(4, item.getImg_src());
            pst.setBoolean(5, item.isActivo());

            pst.setDouble(6, item.getPrecio());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ies.accesodatos.productos.model.ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Producto registerToObject(ResultSet rs) {
        Producto categoria = new Producto();
        try {
            categoria.setId(rs.getInt("ID"));
            categoria.setCategoriaId(rs.getInt("CATEGORIA_ID"));
            categoria.setNombre(rs.getString("NOMBRE"));
            categoria.setDescripcion(rs.getString("DESCRIPCION"));
            categoria.setActivo(rs.getBoolean("ACTIVO"));
            categoria.setImg_src(rs.getString("IMG_SRC"));
            categoria.setPrecio(rs.getDouble("PRECIO"));
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoria;
    }
}
