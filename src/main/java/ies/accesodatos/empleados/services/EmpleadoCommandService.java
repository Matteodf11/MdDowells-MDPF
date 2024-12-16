package ies.accesodatos.empleados.services;


import ies.accesodatos.empleados.model.EmpleadoRepository;
import ies.accesodatos.commons.services.ACommandService;
import ies.accesodatos.commons.services.Event;
import ies.accesodatos.empleados.model.Empleado;

public class EmpleadoCommandService extends ACommandService {
    private final EmpleadoRepository repository;

    public EmpleadoCommandService(EmpleadoRepository empleadoRepository) {
        this.repository = empleadoRepository;
    }

    public void add(Empleado item) {
        this.repository.add(item);
        this.post("Alta empleado", Event.ACTION.ADD, item);
    }

    public void add(String nombre, String clave, String correo, boolean activo) {
        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setClave(clave);
        empleado.setCorreo(correo);
        empleado.setActivo(activo);
        this.repository.save(empleado);
        this.post("Alta empleado", Event.ACTION.ADD, empleado);
    }

    public void delete(Empleado empleado) {
        this.repository.delete(empleado);
        this.post("Alta empleado", Event.ACTION.ADD, empleado);
    }

    public void remove(int id) {
        Empleado empleado = this.repository.getById(id);
        this.repository.deleteById(id);
        this.post("Borrado empleado", Event.ACTION.DELETE, empleado);
    }

    public void update(Empleado empleado) {
        this.repository.save(empleado);
        this.post("Modificar empleado (total)", Event.ACTION.UPDATE, empleado);
    }
}
