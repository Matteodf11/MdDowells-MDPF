package ies.accesodatos.ui.categorias.viewmodel;


import ies.accesodatos.categorias.model.Categoria;
import javafx.beans.property.*;

public class CategoriaProperty extends SimpleObjectProperty<Categoria>implements Cloneable{
    //private final ObjectProperty<Categoria> property = new SimpleObjectProperty<>();
    private IntegerProperty idProperty;
    private StringProperty nombreProperty;
    private StringProperty descripcionProperty;
    private StringProperty img_srcProperty;
    private BooleanProperty activoProperty;

    public CategoriaProperty() {
        this.idProperty = new SimpleIntegerProperty(-1);
        this.nombreProperty = new SimpleStringProperty("");
        this.descripcionProperty = new SimpleStringProperty("");
        this.activoProperty = new SimpleBooleanProperty(false);
        this.img_srcProperty = new SimpleStringProperty("");

    }



    public IntegerProperty idProperty() {
        return idProperty;
    }



    public StringProperty nombreProperty() {
        return nombreProperty;
    }



    public StringProperty descripcionProperty() {
        return descripcionProperty;
    }



    public StringProperty img_srcProperty() {
        return img_srcProperty;
    }

    public boolean isActivoProperty() {
        return activoProperty.get();
    }

    public BooleanProperty activoProperty() {
        return activoProperty;
    }

    public void set(Categoria item) {
        //para que salte
        super.set(item);
        setProperties(item);
    }
    public void setValue(Categoria item) {
        super.setValue(item);
        setProperties(item);
    }

    private void setProperties(Categoria item) {

        if(item!=null) {
            this.idProperty.set(item.getId());
            this.nombreProperty.set(item.getNombre());
            this.descripcionProperty.set(item.getDescripcion());
            this.img_srcProperty.set(item.getImg_src());
            this.activoProperty.set(item.isActivo());
        }
    }

    //se actualizan los valores a la propiedad
    public void update(Categoria item) {
        item.setId(this.idProperty.get());
        item.setNombre(this.nombreProperty.get());
        item.setDescripcion(this.descripcionProperty.get());
        item.setImg_src(this.img_srcProperty.get());
        item.setActivo(this.activoProperty.get());
    }
    public void update(){
        this.get().setId(this.idProperty.get());
        this.get().setNombre(this.nombreProperty.get());
        this.get().setDescripcion(this.descripcionProperty.get());
        this.get().setImg_src(this.img_srcProperty.get());
        this.get().setActivo(this.activoProperty.get());
    }

}