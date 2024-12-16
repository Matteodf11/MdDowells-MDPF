package ies.accesodatos.empleados.model;

public class Empleado implements Cloneable, Comparable<Empleado> {
    private int id;
    private String nombre;
    private boolean activo;
    private String clave;
    private String correo;
    public Empleado(){
        this.id=-1;
        this.activo=false;
        this.clave="";
        this.correo="";
        this.nombre="";
    }
    public Empleado(int id, String nombre, boolean activo, String clave, String correo){
        this.id=id;
        this.nombre=nombre;
        this.activo=activo;
        this.clave=clave;
        this.correo=correo;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Empleado clone(){
        Empleado usuario=new Empleado();
        usuario.setId(this.id);
        usuario.setNombre(this.nombre);
        usuario.setActivo(this.activo);
        usuario.setClave(this.clave);
        usuario.setCorreo(this.correo);
        return usuario;

    }
    public void copy(Empleado usuario){
        if(usuario!=null) {
            this.nombre = usuario.getNombre();
            this.activo = usuario.isActivo();
            this.clave = usuario.getClave();
            this.correo = usuario.getCorreo();
            this.id = usuario.getId();
        }
    }
    @Override
    public int compareTo(Empleado o) {
        if(o!=null)
            return this.nombre.compareTo(o.nombre);
        else
            return 1;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", activo=" + activo +
                ", clave='" + clave + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
