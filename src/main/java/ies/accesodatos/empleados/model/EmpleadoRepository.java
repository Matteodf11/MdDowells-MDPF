package ies.accesodatos.empleados.model;

import ies.accesodatos.commons.repositories.AWriteRepository;

public class EmpleadoRepository extends AWriteRepository<Empleado,Integer> {
    private EmpleadoDao itemDao;
    public EmpleadoRepository() {
        super();
    }
    public EmpleadoRepository(EmpleadoDao itemDao) {
        super();
        this.setDao(itemDao);
    }
    public void setDao(EmpleadoDao itemDao) {
        this.itemDao = itemDao;
    }
    public EmpleadoDao getiDao() {
        return itemDao;
    }
    @Override
    public void save(Empleado item) {
        if(item.getId()==-1){
            this.add(item);
        }else{
            this.update(item);
        }
    }
    @Override
    public void add(Empleado item) {
        this.itemDao.insert(item);
    }
    @Override
    public void update(Empleado item) {
        this.itemDao.update(item);
    }
    @Override
    public void delete(Empleado item) {
        this.itemDao.delete(item);
    }
    @Override
    public void deleteById(Integer id) {
        this.itemDao.deleteById(id);
    }
    @Override
    public Empleado getById(Integer id) {
        return this.itemDao.getById(id);
    }
}