package ies.accesodatos.empleados.model;

import ies.accesodatos.commons.repositories.AQueryRepository;

import java.util.List;

public class EmpleadoQueryRepository extends AQueryRepository<Empleado, Integer> {
    private EmpleadoDao itemDao = new EmpleadoDao();

    public EmpleadoQueryRepository() {
        super();
    }

    public EmpleadoQueryRepository(EmpleadoDao empleadoDao) {
        super();
        this.setDao(itemDao);
        this.itemDao = empleadoDao;
    }

    public void setDao(EmpleadoDao itemDao) {
        this.itemDao = itemDao;
    }

    public EmpleadoDao getEmpleadoDao() {
        return itemDao;
    }

    public void setEmpleadoDao(EmpleadoDao empleadoDao) {
        this.itemDao = empleadoDao;
    }

    public EmpleadoDao getitemDao() {
        return itemDao;
    }

    @Override
    public Empleado getById(Integer id) {
        var empleado = itemDao.getById(id);
        Empleado empleadoDTO = new Empleado();
        empleadoDTO.setId(empleado.getId());
        empleadoDTO.setNombre(empleado.getNombre());
        empleadoDTO.setActivo(empleado.isActivo());
        empleadoDTO.setCorreo(empleado.getCorreo());
        return empleadoDTO;
    }

    @Override
    public List<Empleado> getAll() {
        var categorias = this.itemDao.getAll();
        var items = categorias.stream().map(c -> {
            Empleado dto = new Empleado(c.getId(), c.getNombre(), c.isActivo(), c.getCorreo(), c.getClave());
            return dto;
        }).toList();
        return items;
    }
}
