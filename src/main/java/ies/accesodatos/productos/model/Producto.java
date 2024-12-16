package ies.accesodatos.productos.model;

import ies.accesodatos.categorias.model.Categoria;
import ies.accesodatos.productos.model.dto.CategoriaDTO;

public class Producto implements Cloneable{
    private Integer id;
    private String nombre;
    private String descripcion;
    private String img_src;
    private Double precio;
    private Boolean activo;


    private int categoriaId;


    public Producto(int id, String nombre, String descripcion, String img_src, boolean activo, Double precio, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;
        this.precio = precio;

        this.categoriaId=categoria.getId();
    }
    public Producto(int id, String nombre, String descripcion, String img_src, boolean activo, Double precio, int categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;
        this.precio = precio;

        this.categoriaId=categoriaId;
    }
    public Producto(){
        this.id = -1;
        this.nombre ="";
        this.descripcion ="";
        this.img_src ="";
        this.activo = false;
        this.precio = 0.0;

        this.categoriaId=-1;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre=nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion=descripcion;
    }
    public String getImg_src() {
        return img_src;
    }
    public void setImg_src(String img_src) {
        this.img_src=img_src;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo=activo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Double getPrecio() {
        return precio;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }




    @Override
    public Producto clone() {
        try {
            Producto clone = (Producto) super.clone();
            clone.setId(this.getId());
            clone.setNombre(new String(this.getNombre()));
            clone.setDescripcion(new String(this.getDescripcion()));
            clone.setImg_src(new String(this.getImg_src()));
            clone.setPrecio(this.getPrecio());

            clone.setActivo(this.isActivo());
            clone.setCategoriaId(this.categoriaId);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public void copy(Producto c, boolean withId){
        if(withId) {
            this.setId(c.getId());
        }
        this.setId(c.getId());
        this.setNombre(c.getNombre());
        this.setDescripcion(c.getDescripcion());
        this.setImg_src(c.getImg_src());
        this.setActivo(c.isActivo());
        this.setPrecio(c.getPrecio());
        this.setCategoriaId(c.getCategoriaId());
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img_src='" + img_src + '\'' +
                ", precio=" + precio +
                ", activo=" + activo +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
