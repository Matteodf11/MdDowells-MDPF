package ies.accesodatos.categorias.model.dto;

public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String img_src;
    private Double precio;
    private Boolean activo;
    private int categoriaid;



    public ProductoDTO(int id, String nombre, String descripcion, String img_src, boolean activo, Double precio,int categoriaid) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;
        this.precio = precio;


    }
    public ProductoDTO(){
        this.id = -1;
        this.nombre ="";
        this.descripcion ="";
        this.img_src ="";
        this.activo = false;
        this.precio = 0.0;
        this.categoriaid = -1;


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

    public int getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(int categoriaid) {
        this.categoriaid = categoriaid;
    }

    public String toString(){
        return this.id+","+this.nombre+","+this.img_src+","+this.activo+","+this.descripcion+","+this.precio;
    }
}
