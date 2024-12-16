package ies.accesodatos.ui.empleados.viewmodel;


import ies.accesodatos.empleados.model.Empleado;
import ies.accesodatos.empleados.services.EmpleadoCommandService;
import ies.accesodatos.empleados.services.EmpleadoQueryService;
import ies.accesodatos.ui.commons.IViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpleadoViewModel implements IViewModel {
    //EmpleadosService service;
    EmpleadoProperty selected;
    EmpleadoQueryService queryService;
    EmpleadoCommandService commandService;
    ObservableList<Empleado> items;
    EmpleadoProperty empleadoSelected;

    public EmpleadoViewModel() {
        this.selected = new EmpleadoProperty();
        this.empleadoSelected = new EmpleadoProperty();
        this.empleadoSelected.set(new Empleado());
        this.items = FXCollections.observableArrayList();

    }

    public EmpleadoProperty selectedProperty() {
        return selected;
    }

    public EmpleadoQueryService getQueryService() {
        return queryService;
    }

    public void setQueryService(EmpleadoQueryService queryService) {
        this.queryService = queryService;
        this.reload();
    }

    public EmpleadoCommandService getCommandService() {
        return commandService;
    }

    public void setCommandService(EmpleadoCommandService commandService) {
        this.commandService = commandService;
        this.commandService.register(this);
    }

    public ObservableList<Empleado> getEmpleados() {
        return items;
    }

    public void setEmpleados(ObservableList<Empleado> categorias) {
        this.items = categorias;
    }


    public void setSelected(Empleado selected) {
        this.selected.set(selected);
        Empleado c = new Empleado(selected.getId(), selected.getNombre(), selected.isActivo(), selected.getClave(),
                selected.getCorreo());
        this.empleadoSelected.set(c);

    }

    public EmpleadoProperty getSelected() {
        return this.selected;
    }

    public EmpleadoProperty getEmpleadoPropertySelected() {

        return this.empleadoSelected;
    }

    public void unSelect() {


        this.selected.set(new Empleado());
        this.empleadoSelected.set(new Empleado());
    }


    public void add(Empleado item) {
        this.commandService.add(item);
        this.unSelect();
        //this.service.add(item);
        //this.service.categoriaProperty().set(item);

    }

    public void add(String nombre, String descripcion, String img, boolean activo) {
        this.commandService.add(nombre, descripcion, img, activo);

        this.selected.set(new Empleado());
        this.empleadoSelected.set(new Empleado());
    }

    public void update(Empleado item) {
        this.commandService.update(item);

        this.selected.set(new Empleado());
        this.empleadoSelected.set(new Empleado());
    }

    public void remove(Empleado item) {

        this.commandService.remove(item.getId());
        this.items.remove(item);
        this.unSelect();
    }

    public void removeSelected() {
        if (this.selected.get() != null && this.selected.get().getId() != -1) {
            this.remove(this.selected.get());
            this.unSelect();
        }
    }

    public Empleado getById(int id) {
        return this.queryService.getById(id);
    }

    public void reload() {

        if (this.queryService != null) {
            this.items.clear();
            this.load();
        }

    }

    public void load() {
        if (this.queryService != null) {
            this.items.clear();
            this.items.addAll(this.queryService.getAll());
        }
    }

    public ObservableList<Empleado> getItems() {
        return this.items;
    }

    public void refresh() {
        Empleado tempo=new Empleado();
        this.items.add(tempo);
        this.items.remove(tempo);
        //this.servicerepository.refresh();
    }

    /**
     * evento al añadir nuevo
     */

  /*  @Subscribe
    public void onEvent(Event event) {
        if(event.getData() instanceof  Empleado){
            this.onEmpleadoEvent(event);
        }


    }
private void onEmpleadoEvent(Event event){
    Empleado original = (Empleado) event.getData();
    Empleado cdto;
    switch (event.getAction()) {
        case ADD:
            cdto = new Empleado(original.getId(), original.getNombre(), original.isActivo(), original.getClave(), original.getCorreo());
            if (cdto != null) {
                this.items.add(cdto);
            }
            break;
        case UPDATE:
            cdto = this.items.stream().filter(empleado -> {
                        return empleado.getId() == original.getId();
                    }
            ).findFirst().orElse(null);
            if (cdto != null) {
                cdto.setNombre(original.getNombre());
                cdto.setClave(original.getClave());
                cdto.setCorreo(original.getCorreo());
                cdto.setActivo(original.isActivo());
                this.refresh();
            }
            break;
        case DELETE:
            cdto = this.items.stream().filter(empleado -> {
                        return empleado.getId() == original.getId();
                    }
            ).findFirst().orElse(null);
            if (cdto != null) {
                this.items.remove(cdto);
            }
            break;

    }
}*/

}
