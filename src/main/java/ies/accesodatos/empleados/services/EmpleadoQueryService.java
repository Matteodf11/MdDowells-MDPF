package ies.accesodatos.empleados.services;


import ies.accesodatos.empleados.model.EmpleadoQueryRepository;
import ies.accesodatos.empleados.model.Empleado;

import java.util.List;

public class EmpleadoQueryService {
    private final EmpleadoQueryRepository empleadoQueryRepository;

    public EmpleadoQueryService(EmpleadoQueryRepository empleadoRepository) {
        this.empleadoQueryRepository = empleadoRepository;
    }

    public List<Empleado> getAll() {
        var items = this.empleadoQueryRepository.getAll();
        return items;
    }

    public Empleado getById(int id) {
        var items = this.empleadoQueryRepository.getById(id);
        return items;
    }
}
