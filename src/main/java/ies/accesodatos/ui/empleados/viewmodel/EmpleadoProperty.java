package ies.accesodatos.ui.empleados.viewmodel;


import ies.accesodatos.empleados.model.Empleado;
import javafx.beans.property.*;

public class EmpleadoProperty extends SimpleObjectProperty<Empleado>implements Cloneable{
    //private final ObjectProperty<Categoria> property = new SimpleObjectProperty<>();
    private IntegerProperty idProperty;
    private StringProperty nombreProperty;
    private StringProperty correoProperty;
    private StringProperty claveProperty;
    private BooleanProperty activoProperty;

    public EmpleadoProperty() {
        this.idProperty = new SimpleIntegerProperty(-1);
        this.nombreProperty = new SimpleStringProperty("");
        this.correoProperty = new SimpleStringProperty("");
        this.activoProperty = new SimpleBooleanProperty(false);
        this.claveProperty= new SimpleStringProperty("");

    }


    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
    }

    public String getNombreProperty() {
        return nombreProperty.get();
    }

    public StringProperty nombreProperty() {
        return nombreProperty;
    }

    public void setNombreProperty(String nombreProperty) {
        this.nombreProperty.set(nombreProperty);
    }

    public String getCorreoProperty() {
        return correoProperty.get();
    }

    public StringProperty correoProperty() {
        return correoProperty;
    }

    public void setCorreoProperty(String correoProperty) {
        this.correoProperty.set(correoProperty);
    }

    public String getClaveProperty() {
        return claveProperty.get();
    }

    public StringProperty claveProperty() {
        return claveProperty;
    }

    public void setClaveProperty(String claveProperty) {
        this.claveProperty.set(claveProperty);
    }



    public void setActivoProperty(boolean activoProperty) {
        this.activoProperty.set(activoProperty);
    }

    public boolean isActivoProperty() {
        return activoProperty.get();
    }

    public BooleanProperty activoProperty() {
        return activoProperty;
    }

    public void set(Empleado item) {
        //para que salte
        super.set(item);
        setProperties(item);
    }
    public void setValue(Empleado item) {
        super.setValue(item);
        setProperties(item);
    }

    private void setProperties(Empleado item) {

        if(item!=null) {
            this.idProperty.set(item.getId());
            this.nombreProperty.set(item.getNombre());
            this.correoProperty.set(item.getCorreo());
            this.claveProperty.set(item.getClave());
            this.activoProperty.set(item.isActivo());
        }
    }

    //se actualizan los valores a la propiedad
    public void update(Empleado item) {
        item.setId(this.idProperty.get());
        item.setNombre(this.nombreProperty.get());
        item.setCorreo(this.correoProperty.get());
        item.setClave(this.claveProperty.get());
        item.setActivo(this.activoProperty.get());
    }
    public void update(){
        this.get().setId(this.idProperty.get());
        this.get().setNombre(this.nombreProperty.get());
        this.get().setCorreo(this.correoProperty.get());
        this.get().setClave(this.claveProperty.get());
        this.get().setActivo(this.activoProperty.get());
    }

}