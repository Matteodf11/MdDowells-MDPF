package ies.accesodatos.ui.categorias.view;



import ies.accesodatos.ui.categorias.viewmodel.CategoriaViewModel;
import ies.accesodatos.ui.commons.AWindows;
import io.github.palexdev.materialfx.controls.MFXButton;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class FormularioCategoriaView extends AWindows {

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


    private CategoriaViewModel viewModel;

    public FormularioCategoriaView() {



    }

    public void setViewModel(CategoriaViewModel viewModel) {
        this.viewModel = viewModel;
        //enlazado
        //el texto
        this.titulo.textProperty().bind(Bindings.when(this.viewModel.getCategoriaPropertySelected().idProperty().isEqualTo(-1)).then("Alta categoria").otherwise(
                Bindings.concat("Modificar categoria:",this.viewModel.getCategoriaPropertySelected().nombreProperty())
        ));
        //el nombre
        this.nombre.textProperty().bindBidirectional(this.viewModel.getCategoriaPropertySelected().nombreProperty());
        //descripcion
        this.descripcion.textProperty().bindBidirectional(this.viewModel.getCategoriaPropertySelected().descripcionProperty());
        //fichero
        this.path.textProperty().bindBidirectional(this.viewModel.getCategoriaPropertySelected().img_srcProperty());
        //activo
        this.activo.selectedProperty().bindBidirectional(this.viewModel.getCategoriaPropertySelected().activoProperty());
        //boton producots
        this.productos.disableProperty().bind(this.viewModel.getCategoriaPropertySelected().idProperty().isEqualTo(-1));

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
        aceptar.disableProperty().bind(this.nombre.getValidator().validProperty().not().or(
                this.descripcion.getValidator().validProperty().not()
        ).or(this.path.getValidator().validProperty().not()));


        aceptar.setOnMouseClicked(event -> {
            //se actualizan los datos
            this.viewModel.getCategoriaPropertySelected().update();
            if (this.viewModel.getCategoriaPropertySelected().idProperty().get() == -1) {
                this.viewModel.add(this.viewModel.getCategoriaPropertySelected().get());
            } else {
                this.viewModel.update(this.viewModel.getCategoriaPropertySelected().get());
            }
            if (this.router != null)
                this.getRouter().pop();

        });

        cancelar.setOnMouseClicked(event -> {
            this.getRouter().pop();

        });
        productos.setOnMouseClicked(mouseEvent -> {
            if (this.router != null) {
                this.getRouter().push("productos");
            }
        });


    }


    @Override
    public void refresh() {

    }

    @Override
    public void reload() {

    }
}
