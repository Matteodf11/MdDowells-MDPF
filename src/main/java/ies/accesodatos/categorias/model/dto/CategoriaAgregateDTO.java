package ies.accesodatos.categorias.model.dto;

import ies.accesodatos.categorias.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaAgregateDTO implements  Comparable<Categoria> {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String img_src;

    private Boolean activo;
    private List<ProductoDTO> productos;
    public  CategoriaAgregateDTO(int id, String nombre, String descripcion, String img_src, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;
        this.productos= new ArrayList<>();

    }
    public  CategoriaAgregateDTO(){
        this.id = -1;
        this.nombre ="";
        this.descripcion ="";
        this.img_src ="";
        this.activo = false;
        this.productos= new ArrayList<>();

    }
    public void addProducto(ProductoDTO producto){
        this.productos.add(producto);
    }
    public void setProductos(List<ProductoDTO> productos){
        this.productos.addAll(productos);
    }
    public List<ProductoDTO> getProductos() {
        return productos;
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
        StringBuilder cadena=new StringBuilder("ID:"+this.id+" Nombre:"+this.nombre+" Descripcion:"+this.descripcion+" Imagen:"+this.img_src+" Activo:"+this.activo);
        this.productos.forEach(productoDTO -> {
            cadena.append("\n\tProducto:"+productoDTO.toString());
        });
        return cadena.toString();
    }

    @Override
    public int compareTo(Categoria o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
