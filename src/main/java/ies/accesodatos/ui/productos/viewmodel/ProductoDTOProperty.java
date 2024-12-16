package ies.accesodatos.ui.productos.viewmodel;



import ies.accesodatos.productos.model.dto.CategoriaDTO;
import ies.accesodatos.productos.model.dto.ProductoAgregateDTO;
import javafx.beans.property.*;

public class ProductoDTOProperty extends SimpleObjectProperty<ProductoAgregateDTO>implements Cloneable{
    //private final ObjectProperty<Categoria> property = new SimpleObjectProperty<>();
    private IntegerProperty idProperty;
    private StringProperty nombreProperty;
    private StringProperty descripcionProperty;
    private StringProperty img_srcProperty;
    private BooleanProperty activoProperty;
    private ObjectProperty<CategoriaDTO> categoriaProperty;

    public ProductoDTOProperty() {
        this.idProperty = new SimpleIntegerProperty(-1);
        this.nombreProperty = new SimpleStringProperty("");
        this.descripcionProperty = new SimpleStringProperty("");
        this.activoProperty = new SimpleBooleanProperty(false);
        this.img_srcProperty = new SimpleStringProperty("");
        this.categoriaProperty=new SimpleObjectProperty<>();
    }

    public ObjectProperty<CategoriaDTO> categoriaProperty() {
        return categoriaProperty;
    }
    public void setCategoriaProperty(CategoriaDTO categoria) {
        this.categoriaProperty.set(categoria);
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

    public void set(ProductoAgregateDTO item) {
        //para que salte
        super.set(item);
        setProperties(item);
    }
    public void setValue(ProductoAgregateDTO item) {
        super.setValue(item);
        setProperties(item);
    }

    private void setProperties(ProductoAgregateDTO item) {

        if(item!=null) {
            this.idProperty.set(item.getId());
            this.nombreProperty.set(item.getNombre());
            this.descripcionProperty.set(item.getDescripcion());
            this.img_srcProperty.set(item.getImg_src());
            this.activoProperty.set(item.isActivo());

            this.categoriaProperty.set(item.getCategoria());
        }
    }



}