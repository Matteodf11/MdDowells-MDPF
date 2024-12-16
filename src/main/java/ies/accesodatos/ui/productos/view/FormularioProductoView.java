package ies.accesodatos.ui.productos.view;


import ies.accesodatos.categorias.model.Categoria;
import ies.accesodatos.productos.model.dto.CategoriaDTO;
import ies.accesodatos.ui.commons.AWindows;
import ies.accesodatos.ui.productos.viewmodel.ProductoViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class FormularioProductoView extends AWindows {
    @FXML
    private Label validationPrecio;
    @FXML
    private MFXTextField precio;
    @FXML
    private MFXComboBox categoria;
    @FXML
    private Label validationCategoria;
    @FXML
    private Label titulo;
    @FXML
    private Label validationDescripcion;
    @FXML
    private MFXTextField descripcion;
    @FXML
    private MFXButton selectImageButton;
    @FXML
    private MFXTextField path;
    @FXML
    private Label validationImagen;
    @FXML
    private MFXTextField nombre;
    @FXML
    private MFXToggleButton activo;
    @FXML
    private Label validationNombre;

    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    private static final String[] specialCharacters = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");
    @FXML
    private MFXButton aceptar;
    @FXML
    private MFXButton cancelar;
    @FXML
    private MFXButton productos;


    private ProductoViewModel viewModel;

    public FormularioProductoView() {


    }

    public void setViewModel(ProductoViewModel viewModel) {
        this.viewModel = viewModel;
        this.categoria.setItems(this.viewModel.getCategorias());
        this.categoria.getItems().sort((o1, o2) -> o1.toString().compareTo(o2.toString()));

        //enlazado
        //el texto
        this.titulo.textProperty().bind(Bindings.when(this.viewModel.getProductoPropertySelected().idProperty().isEqualTo(-1)).then("Alta producto").otherwise(
                Bindings.concat("Modificar producto:", this.viewModel.getProductoPropertySelected().nombreProperty())
        ));
        //el nombre
        this.nombre.textProperty().bindBidirectional(this.viewModel.getProductoPropertySelected().nombreProperty());
        //descripcion
        this.descripcion.textProperty().bindBidirectional(this.viewModel.getProductoPropertySelected().descripcionProperty());
        //fichero
        this.path.textProperty().bindBidirectional(this.viewModel.getProductoPropertySelected().img_srcProperty());
        //precio
        this.precio.textProperty().bindBidirectional(this.viewModel.getProductoPropertySelected().precioPropertyProperty(), new NumberStringConverter());

        //activo
        this.activo.selectedProperty().bindBidirectional(this.viewModel.getProductoPropertySelected().activoProperty());
        //boton producots
        // this.productos.disableProperty().bind(this.viewModel.getCategoriaPropertySelected().idProperty().isEqualTo(-1));
        this.categoria.setConverter(new StringConverter() {
            @Override
            public String toString(Object o) {
                if (o != null && o instanceof CategoriaDTO)
                    return ((CategoriaDTO) o).getNombre();
                else
                    return "";
            }

            @Override
            public Object fromString(String s) {
                if (s != null && s.trim().length() > 0)
                    return viewModel.getCategorias().stream().filter(categoriaDTO -> {
                        return categoriaDTO.getNombre().equals(s);
                    }).findFirst().orElse(null);
                return null;
            }
        });
        this.categoria.valueProperty().bindBidirectional(this.viewModel.getSelected().categoriaProperty());

    }

    private void configField(MFXTextField field, Label label, ArrayList<Pair<String, BooleanBinding>> conditions) {
        conditions.forEach(stringBooleanBindingPair -> field.getValidator().constraint(
                Constraint.Builder.build()
                        .setSeverity(Severity.ERROR)
                        .setMessage(stringBooleanBindingPair.getKey())
                        .setCondition(
                                stringBooleanBindingPair.getValue()
                        )
                        .get()
        ));
        field.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                label.setVisible(false);
                field.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        });

        field.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraints = field.validate();
                if (!constraints.isEmpty()) {
                    field.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                    label.setText(constraints.get(0).getMessage());
                    label.setVisible(true);
                }
            }
        });
    }

    @FXML
    public void initialize() {
        //se configuran los botones con las condiciones que ha de tener
        //nombre
        ArrayList<Pair<String, BooleanBinding>> conditions = new ArrayList<>();
        conditions.add(new Pair<>("Longitud mínima 8 caracteres", nombre.textProperty().length().greaterThanOrEqualTo(8)));
        BooleanBinding nospecial = Bindings.createBooleanBinding(() -> !containsAny(nombre.getText(), "", specialCharacters), nombre.textProperty());
        conditions.add(new Pair<>("No se permiten caracteres especiales", nospecial));
        this.configField(nombre, validationNombre, conditions);

        //descripcion
        //  conditions = new ArrayList<>();
        //  conditions.add(new Pair<>("Longitud mínima 8 caracteres", descripcion.textProperty().length().greaterThanOrEqualTo(8)));
        //  nospecial = Bindings.createBooleanBinding(() -> !containsAny(descripcion.getText(), "", specialCharacters), descripcion.textProperty());
        //  conditions.add(new Pair<>("No se permiten caracteres especiales", nospecial));
        //  this.configField(descripcion, validationDescripcion, conditions);

        //fichero
        conditions = new ArrayList<>();
        conditions.add(new Pair<>("No se ha seleccionado el fichero", path.textProperty().length().greaterThanOrEqualTo(1)));
        //nospecial = Bindings.createBooleanBinding(() -> !containsAny(path.getText(), "", specialCharacters), path.textProperty());
        // conditions.add(new Pair<>("No se permiten caracteres especiales", nospecial));
        this.configField(path, validationImagen, conditions);

        //precio
        conditions = new ArrayList<>();
        nospecial = Bindings.createBooleanBinding(() -> precio.getText().matches("\\d+(,\\d+)?"), precio.textProperty());
        conditions.add(new Pair<>("Ha de ser un número", nospecial));
        this.configField(precio, validationPrecio, conditions);
        //categoria
        /*conditions = new ArrayList<>();
        nospecial = Bindings.createBooleanBinding(() -> categoria.getValue() != null && ((Categoria) categoria.getValue()).getId() != -1, categoria.valueProperty());
        conditions.add(new Pair<>("Seleccionar una categoria", nospecial));
        this.configField(categoria, validationCategoria, conditions);
*/
        this.selectImageButton.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // this.imageView.setImage(new Image(file.toURI().toString()));
                this.path.textProperty().set(file.getAbsolutePath());
            }
        });
        aceptar.setDisable(this.nombre.getValidator().validProperty().get());
        //solo se habilita si no tiene fallos
        aceptar.disableProperty().bind(this.nombre.getValidator().validProperty().not().
                or(this.descripcion.getValidator().validProperty().not()).
                or(this.path.getValidator().validProperty().not()).
                or(this.precio.getValidator().validProperty().not()).
                or(this.categoria.getValidator().validProperty().not()));


        aceptar.setOnMouseClicked(event -> {
            //se actualizan los datos
            this.viewModel.getProductoPropertySelected().categoriaIdPropertyProperty().set(this.viewModel.selectedProperty().categoriaProperty().get().getId());

            this.viewModel.getProductoPropertySelected().update();
            if (this.viewModel.getProductoPropertySelected().idProperty().get() == -1) {
                this.viewModel.add(this.viewModel.getProductoPropertySelected().get());
            } else {
                this.viewModel.update(this.viewModel.getProductoPropertySelected().get());
            }
            this.categoria.setValue(this.viewModel.getCategorias().get(0));
            if (this.router != null)
                this.getRouter().pop();

        });

        cancelar.setOnMouseClicked(event -> {
            if(!this.viewModel.getCategorias().isEmpty())
                this.categoria.setValue(this.viewModel.getCategorias().getFirst());
            this.getRouter().pop();

        });



    }


    @Override
    public void refresh() {

    }

    @Override
    public void reload() {

    }
}
