package ies.accesodatos.productos.model.dto;

public class ProductoAgregateDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String img_src;
    private Double precio;
    private Boolean activo;
    private CategoriaDTO categoria;


    public ProductoAgregateDTO(int id, String nombre, String descripcion, String img_src, boolean activo, Double precio, CategoriaDTO categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;
        this.precio = precio;
        this.categoria = categoria;

    }

    public ProductoAgregateDTO(int id, String nombre, String descripcion, String img_src, boolean activo, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;
        this.precio = precio;


    }
    public ProductoAgregateDTO(){
        this.id = -1;
        this.nombre ="";
        this.descripcion ="";
        this.img_src ="";
        this.activo = false;
        this.precio = 0.0;


    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
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


    public String toString(){
        return "ID:"+ this.id+" NOMBRE:"+this.nombre+" IMAGEN:"+this.img_src+" ACTIVO:"+this.activo+" DESCRIPCIÓN:"+this.descripcion+","+this.precio+ "\n\tCategoria: "+this.categoria;
    }
}
