package ies.accesodatos.ui.productos.view;


import ies.accesodatos.productos.model.dto.ProductoAgregateDTO;
import ies.accesodatos.ui.categorias.viewmodel.CategoriaViewModel;
import ies.accesodatos.ui.commons.AWindows;
import ies.accesodatos.ui.productos.viewmodel.ProductoViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.controlsfx.control.GridView;

public class ListadoProductosView extends AWindows {
    @FXML
    private MFXButton add;

    @FXML
    private Label titulo;
    @FXML
    private MFXButton up;
    @FXML
    private MFXTextField searchField;

    @FXML
    private GridView<ProductoAgregateDTO> grid;
    private ProductoViewModel viewModel;
    private CategoriaViewModel categoriaViewModel;
    private ContextMenu contextMenu;
    FilteredList<ProductoAgregateDTO> listaFiltrada;// = new FilteredList<>(personas, p -> true);

    public ListadoProductosView() {
        super();

    }

    public void setViewModel(ProductoViewModel viewModel) {

        if (viewModel != null) {
            this.viewModel = viewModel;
            this.configContexMenu();
            this.titulo.setText("Listado de productos");
            //lista filtrada, por defecto son todos
            //se enlaza a los items
            listaFiltrada = new FilteredList<>(viewModel.getItems(), p -> {
                if (this.categoriaViewModel!=null && this.categoriaViewModel.getSelected() != null && this.categoriaViewModel.getSelected().idProperty().get() != -1)
                    return p.getCategoria().getId() == this.categoriaViewModel.getSelected().idProperty().get();
                else
                    return true;

            });

            //se asigna la lista filtrada
            grid.setItems(this.listaFiltrada);

            this.refresh();

        }

    }

    public void setCategoriaViewModel(CategoriaViewModel categoriaViewModel) {
        this.categoriaViewModel = categoriaViewModel;
        this.titulo.textProperty().bind(Bindings.concat("Listado de productos de:",this.categoriaViewModel.getCategoriaPropertySelected().nombreProperty()));
        this.categoriaViewModel.getSelected().addListener((observableValue, categoriaDTO, t1) -> {
            listaFiltrada.setPredicate( p -> {
                if (p!=null && t1!=null &&  t1.getId() != -1) {
                    boolean resultado=p.getCategoria().getId() == t1.getId();
                    return resultado;
                }
                else
                    return true;

            });
        });

    }

    public ProductoViewModel getViewModel() {
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
                this.getRouter().push("producto");
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
                    if (this.categoriaViewModel.getSelected() != null && this.categoriaViewModel.getSelected().idProperty().get() != -1)
                        return item.getCategoria().getId() == this.categoriaViewModel.getSelected().idProperty().get();
                    else
                        return true;

                }

                // Compare name with the filter text
                String lowerCaseFilter = newValue.toLowerCase();
                //el -1 es para el de crear
                if (this.categoriaViewModel.getSelected() != null && this.categoriaViewModel.getSelected().idProperty().get() != -1)
                    return item.getCategoria().getId() == this.categoriaViewModel.getSelected().idProperty().get()
                            &&
                            (
                                    item.getNombre().toLowerCase().contains(lowerCaseFilter)
                                    || item.getDescripcion().toLowerCase().contains(lowerCaseFilter)
                            );
                else
                    return item.getId() == -1 || item.getNombre().toLowerCase().contains(lowerCaseFilter) || item.getDescripcion().toLowerCase().contains(lowerCaseFilter);
            });
        });

        this.initGrid();


    }

    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            ProductoCell c = new ProductoCell();

            c.setOnDoubleClick(item -> {
                if (item.getId() == -1) {
                    this.viewModel.unSelect();
                } else {
                    this.viewModel.setSelected(item);
                }
                if (this.getRouter() != null) {
                    this.getRouter().push("producto");
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
