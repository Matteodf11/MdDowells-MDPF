package ies.accesodatos.productos.model.dto;

import ies.accesodatos.categorias.model.Categoria;

public class CategoriaDTO  {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String img_src;

    private Boolean activo;

    public CategoriaDTO(int id, String nombre, String descripcion, String img_src, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;


    }
    public CategoriaDTO(){
        this.id = -1;
        this.nombre ="";
        this.descripcion ="";
        this.img_src ="";
        this.activo = false;
    }

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
        this.descripcion = categoria.getDescripcion();
        this.img_src = categoria.getImg_src();
        this.activo = categoria.isActivo();

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


    public String toString(){
        return "ID:"+ this.id+ " Nombre:"+this.nombre+ " Imagen:"+this.img_src+ " Activo:"+this.activo+ " Descripcion:"+this.descripcion;
    }


}
