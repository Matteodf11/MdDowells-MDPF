package ies.accesodatos.ui.categorias.viewmodel;


import ies.accesodatos.categorias.model.dto.CategoriaAgregateDTO;
import ies.accesodatos.categorias.model.dto.ProductoDTO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class  CategoriaDTOProperty extends SimpleObjectProperty< CategoriaAgregateDTO>implements Cloneable{
    //private final ObjectProperty<Categoria> property = new SimpleObjectProperty<>();
    private IntegerProperty idProperty;
    private StringProperty nombreProperty;
    private StringProperty descripcionProperty;
    private StringProperty img_srcProperty;
    private BooleanProperty activoProperty;
    private ObservableList<ProductoDTO> productos;

    public  CategoriaDTOProperty() {
        this.idProperty = new SimpleIntegerProperty(-1);
        this.nombreProperty = new SimpleStringProperty("");
        this.descripcionProperty = new SimpleStringProperty("");
        this.activoProperty = new SimpleBooleanProperty(false);
        this.img_srcProperty = new SimpleStringProperty("");
        this.productos= FXCollections.observableArrayList();
    }

    public ObservableList<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(ObservableList<ProductoDTO> productos) {
        this.productos = productos;
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

    public void set( CategoriaAgregateDTO item) {
        //para que salte
        super.set(item);
        setProperties(item);
    }
    public void setValue( CategoriaAgregateDTO item) {
        super.setValue(item);
        setProperties(item);
    }

    private void setProperties( CategoriaAgregateDTO item) {

        if(item!=null) {
            this.idProperty.set(item.getId());
            this.nombreProperty.set(item.getNombre());
            this.descripcionProperty.set(item.getDescripcion());
            this.img_srcProperty.set(item.getImg_src());
            this.activoProperty.set(item.isActivo());
            this.productos.clear();
            this.productos.addAll(item.getProductos());
        }
    }



}