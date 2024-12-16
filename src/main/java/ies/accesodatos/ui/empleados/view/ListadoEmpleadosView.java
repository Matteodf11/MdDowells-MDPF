package ies.accesodatos.ui.empleados.view;


import ies.accesodatos.empleados.model.Empleado;
import ies.accesodatos.ui.commons.AWindows;
import ies.accesodatos.ui.empleados.viewmodel.EmpleadoViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.controlsfx.control.GridView;

public class ListadoEmpleadosView extends AWindows {
    @FXML
    private MFXButton add;

    @FXML
    private Label titulo;
    @FXML
    private MFXButton up;
    @FXML
    private MFXTextField searchField;

    @FXML
    private GridView<Empleado> grid;
    private EmpleadoViewModel viewModel;
    private ContextMenu contextMenu;
    FilteredList<Empleado> listaFiltrada;// = new FilteredList<>(personas, p -> true);

    public ListadoEmpleadosView() {
        super();

    }

    public void setViewModel(EmpleadoViewModel viewModel) {

        if (viewModel != null) {
            this.viewModel = viewModel;
            this.configContexMenu();

            //lista filtrada, por defecto son todos
            //se enlaza a los items
            listaFiltrada = new FilteredList<>(viewModel.getItems(), p -> {
                return true;
            });

            //se asigna la lista filtrada
            grid.setItems(this.listaFiltrada);

            this.refresh();

        }

    }

    public EmpleadoViewModel getViewModel() {
        return viewModel;
    }

    private void configContexMenu() {
        this.contextMenu = new ContextMenu();

        // Crear elementos del menú
        MenuItem item1 = new MenuItem("Borrar");

        // Añadir los elementos al menú contextual
        contextMenu.getItems().addAll(item1);

        // Configurar las acciones al seleccionar un item
        item1.setOnAction(e ->
        {
            if (this.viewModel.getSelected() != null && this.viewModel.getSelected().get().getId() != -1) {

                this.viewModel.removeSelected();


            }
        });

    }

    @FXML
    public void initialize() {
        //abrir editor
        add.setOnMouseClicked(mouseEvent -> {
            if (this.getRouter() != null) {
                this.viewModel.unSelect();
                this.getRouter().push("empleado");
            }
        });
        //subir de nivel
        this.up.setOnMouseClicked(event -> {
            //se va hacia atras
            if (this.router != null) {
                this.router.pop();
            }
        });

        this.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.listaFiltrada.setPredicate(item -> {
                // If filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare name with the filter text
                String lowerCaseFilter = newValue.toLowerCase();
                //el -1 es para el de crear
                return item.getId() == -1 || item.getNombre().toLowerCase().contains(lowerCaseFilter) || item.getCorreo().toLowerCase().contains(lowerCaseFilter);
            });
        });

        this.initGrid();


    }

    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            EmpleadoCell c = new EmpleadoCell();

            c.setOnDoubleClick(item -> {
                if (item.getId() == -1) {
                    this.viewModel.unSelect();
                } else {
                    this.viewModel.setSelected(item);
                }
                if (this.getRouter() != null) {
                    this.getRouter().push("empleado");
                }
            });
            c.setOnSecondaryClick((item, event) -> {
                if (item != null && item.getId() != -1) {
                    this.viewModel.setSelected(item);
                    contextMenu.show(this.grid, event.getScreenX(), event.getScreenY());
                }
            });
            return c;
        });

        grid.setCellWidth(160);  // Ancho de la celda
        grid.setCellHeight(160); // Alto de la celda
        grid.setHorizontalCellSpacing(10); // Espaciado horizontal entre celdas
        grid.setVerticalCellSpacing(10);   // Espaciado vertical entre celdas

    }

    @Override
    public void refresh() {

    }

    @Override
    public void reload() {
        this.viewModel.reload();
    }
}
