package ies.accesodatos.categorias.model;


public class Categoria implements Cloneable, Comparable<Categoria> {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String img_src;

    private Boolean activo;

    public Categoria(int id, String nombre, String descripcion, String img_src, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img_src = img_src;
        this.activo = activo;

    }
    public Categoria(){
        this.id = -1;
        this.nombre ="";
        this.descripcion ="";
        this.img_src ="";
        this.activo = false;


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

    @Override
    public Categoria clone() {
        try {
            Categoria clone = (Categoria) super.clone();
            clone.setId(this.getId());
            clone.setNombre(new String(this.getNombre()));
            clone.setDescripcion(new String(this.getDescripcion()));
            clone.setImg_src(new String(this.getImg_src()));
            clone.setActivo(this.isActivo());

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public void copy(Categoria c, boolean withId){
        if(withId) {
            this.setId(c.getId());
        }
        this.setId(c.getId());
        this.setNombre(c.getNombre());
        this.setDescripcion(c.getDescripcion());
        this.setImg_src(c.getImg_src());
        this.setActivo(c.isActivo());

    }


    @Override
    public int compareTo(Categoria o) {
        return this.getNombre().compareTo(o.getNombre());
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img_src='" + img_src + '\'' +
                ", activo=" + activo +
                '}';
    }
}
