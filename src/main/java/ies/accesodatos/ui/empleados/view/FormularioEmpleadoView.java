package ies.accesodatos.ui.empleados.view;



import ies.accesodatos.ui.commons.AWindows;
import ies.accesodatos.ui.empleados.viewmodel.EmpleadoViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class FormularioEmpleadoView extends AWindows {
    @FXML
    private Label titulo;
    @FXML
    private Label validationNombre;
    @FXML
    private MFXTextField nombre;

    @FXML
    private MFXTextField correo;
    @FXML
    private Label validationcorreo;
    @FXML
    private MFXPasswordField clave;
    @FXML
    private MFXPasswordField clave2;
    @FXML
    private MFXToggleButton activo;
    @FXML
    private Label validationclave;
    @FXML
    private Label validationclave2;
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    private static final String[] specialCharacters = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");
    @FXML
    private MFXButton aceptar;
    @FXML
    private MFXButton cancelar;


    private EmpleadoViewModel viewModel;

    public FormularioEmpleadoView() {



    }

    public void setViewModel(EmpleadoViewModel viewModel) {
        this.viewModel = viewModel;
        //enlazado
        //el texto
        this.titulo.textProperty().bind(Bindings.when(this.viewModel.getSelected().idProperty().isEqualTo(-1)).then("Alta empleado").otherwise(
                Bindings.concat("Modificar empleado:",this.viewModel.getSelected().nombreProperty().get())
        ));
        //el nombre
        this.nombre.textProperty().bindBidirectional(this.viewModel.getSelected().nombreProperty());
        //correo
        this.correo.textProperty().bindBidirectional(this.viewModel.getSelected().correoProperty());
        //correo
        this.clave.textProperty().bindBidirectional(this.viewModel.getSelected().claveProperty());

        //activo
        this.activo.selectedProperty().bindBidirectional(this.viewModel.getSelected().activoProperty());

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
       // conditions = new ArrayList<>();
       // conditions.add(new Pair<>("No se ha seleccionado el fichero", path.textProperty().length().greaterThanOrEqualTo(1)));
        //nospecial = Bindings.createBooleanBinding(() -> !containsAny(path.getText(), "", specialCharacters), path.textProperty());
        // conditions.add(new Pair<>("No se permiten caracteres especiales", nospecial));
//        this.configField(path, validationImagen, conditions);

        conditions = new ArrayList<>();
        conditions.add(new Pair<>("Longitud mínima 8 caracteres", clave2.textProperty().length().greaterThanOrEqualTo(3)));
        conditions.add(new Pair<>("No coinciden las contraseñas", clave2.textProperty().isEqualTo(clave.textProperty())));

        this.configField(clave2, validationclave2, conditions);

        //solo se habilita si no tiene fallos
        aceptar.disableProperty().bind(this.nombre.getValidator().validProperty().not().or(
                this.nombre.getValidator().validProperty().not()
        ).or(this.clave.getValidator().validProperty().not().or(this.correo.getValidator().validProperty().not().or(this.clave2.getValidator().validProperty().not()))));


        aceptar.setOnMouseClicked(event -> {
            //se actualizan los datos
            this.viewModel.getSelected().update();
            if (this.viewModel.getSelected().idProperty().get() == -1) {
                this.viewModel.add(this.viewModel.getSelected().get());
            } else {
                this.viewModel.update(this.viewModel.getSelected().get());
            }
            if (this.router != null)
                this.getRouter().pop();

        });

        cancelar.setOnMouseClicked(event -> {
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
