package ies.accesodatos.empleados.model.dto;

import ies.accesodatos.empleados.model.Empleado;

public class EmpleadoDTO implements Cloneable, Comparable<EmpleadoDTO> {
    private int id;
    private String nombre;
    private boolean activo;

    private String correo;
    public EmpleadoDTO (){
        this.id=-1;
        this.activo=false;

        this.correo="";
        this.nombre="";
    }
    public EmpleadoDTO(int id, String nombre, boolean activo , String correo){
        this.id=id;
        this.nombre=nombre;
        this.activo=activo;

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

        usuario.setCorreo(this.correo);
        return usuario;

    }
    public void copy(EmpleadoDTO usuario){
        if(usuario!=null) {
            this.nombre = usuario.getNombre();
            this.activo = usuario.isActivo();

            this.correo = usuario.getCorreo();
            this.id = usuario.getId();
        }
    }
    @Override
    public int compareTo(EmpleadoDTO o) {
        if(o!=null)
            return this.nombre.compareTo(o.nombre);
        else
            return 1;
    }
}

