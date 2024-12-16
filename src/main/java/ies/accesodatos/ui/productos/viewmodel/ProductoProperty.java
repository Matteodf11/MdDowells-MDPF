package ies.accesodatos.ui.productos.viewmodel;


import ies.accesodatos.productos.model.Producto;
import javafx.beans.property.*;

public class ProductoProperty extends SimpleObjectProperty<Producto>implements Cloneable{
    //private final ObjectProperty<Categoria> property = new SimpleObjectProperty<>();
    private IntegerProperty idProperty;
    private StringProperty nombreProperty;
    private StringProperty descripcionProperty;
    private StringProperty img_srcProperty;
    private BooleanProperty activoProperty;
    private DoubleProperty precioProperty;
    private IntegerProperty categoriaIdProperty;

    public ProductoProperty() {
        this.idProperty = new SimpleIntegerProperty(-1);
        this.nombreProperty = new SimpleStringProperty("");
        this.descripcionProperty = new SimpleStringProperty("");
        this.activoProperty = new SimpleBooleanProperty(false);
        this.img_srcProperty = new SimpleStringProperty("");
        this.precioProperty = new SimpleDoubleProperty(0);
        this.categoriaIdProperty = new SimpleIntegerProperty(-1);
    }

    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idPropertyProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
    }

    public String getNombreProperty() {
        return nombreProperty.get();
    }

    public StringProperty nombrePropertyProperty() {
        return nombreProperty;
    }

    public void setNombreProperty(String nombreProperty) {
        this.nombreProperty.set(nombreProperty);
    }

    public String getDescripcionProperty() {
        return descripcionProperty.get();
    }

    public StringProperty descripcionPropertyProperty() {
        return descripcionProperty;
    }

    public void setDescripcionProperty(String descripcionProperty) {
        this.descripcionProperty.set(descripcionProperty);
    }

    public String getImg_srcProperty() {
        return img_srcProperty.get();
    }

    public StringProperty img_srcPropertyProperty() {
        return img_srcProperty;
    }

    public void setImg_srcProperty(String img_srcProperty) {
        this.img_srcProperty.set(img_srcProperty);
    }

    public BooleanProperty activoPropertyProperty() {
        return activoProperty;
    }

    public void setActivoProperty(boolean activoProperty) {
        this.activoProperty.set(activoProperty);
    }

    public Double getPrecioProperty() {
        return precioProperty.get();
    }

    public DoubleProperty precioPropertyProperty() {
        return precioProperty;
    }

    public void setPrecioProperty(float precioProperty) {
        this.precioProperty.set(precioProperty);
    }

    public int getCategoriaIdProperty() {
        return categoriaIdProperty.get();
    }

    public IntegerProperty categoriaIdPropertyProperty() {
        return categoriaIdProperty;
    }

    public void setCategoriaIdProperty(int categoriaIdProperty) {
        this.categoriaIdProperty.set(categoriaIdProperty);
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

    public void set(Producto item) {
        //para que salte
        super.set(item);
        setProperties(item);
    }
    public void setValue(Producto item) {
        super.setValue(item);
        setProperties(item);
    }

    private void setProperties(Producto item) {

        if(item!=null) {
            this.idProperty.set(item.getId());
            this.nombreProperty.set(item.getNombre());
            this.descripcionProperty.set(item.getDescripcion());
            this.img_srcProperty.set(item.getImg_src());
            this.activoProperty.set(item.isActivo());
            this.precioProperty.set(item.getPrecio());
            this.categoriaIdProperty.set(item.getCategoriaId());

        }
    }

    //se actualizan los valores a la propiedad
    public void update(Producto item) {
        item.setId(this.idProperty.get());
        item.setNombre(this.nombreProperty.get());
        item.setDescripcion(this.descripcionProperty.get());
        item.setImg_src(this.img_srcProperty.get());
        item.setActivo(this.activoProperty.get());
        item.setPrecio(this.precioProperty.get());
        item.setCategoriaId(this.categoriaIdProperty.get());
    }
    public void update(){
        this.get().setId(this.idProperty.get());
        this.get().setNombre(this.nombreProperty.get());
        this.get().setDescripcion(this.descripcionProperty.get());
        this.get().setImg_src(this.img_srcProperty.get());
        this.get().setActivo(this.activoProperty.get());
        this.get().setPrecio(this.precioProperty.get());
        this.get().setCategoriaId(this.categoriaIdProperty.get());
    }

}