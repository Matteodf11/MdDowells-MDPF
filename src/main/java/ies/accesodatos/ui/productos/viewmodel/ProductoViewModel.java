package ies.accesodatos.ui.productos.viewmodel;


import ies.accesodatos.categorias.services.CategoriaCommandService;
import ies.accesodatos.categorias.services.CategoriaQueryService;
import ies.accesodatos.productos.model.Producto;
import ies.accesodatos.productos.model.dto.CategoriaDTO;
import ies.accesodatos.productos.model.dto.ProductoAgregateDTO;
import ies.accesodatos.productos.services.ProductoCommandService;
import ies.accesodatos.productos.services.ProductoQueryService;
import ies.accesodatos.ui.commons.IViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductoViewModel implements IViewModel {
    //ProductosService service;
    ProductoDTOProperty selected;
    ProductoQueryService queryService;
    ProductoCommandService commandService;
    CategoriaQueryService categoriaQueryService;
    CategoriaCommandService categoriaCommandService;
    ObservableList<ProductoAgregateDTO> items;
    ObservableList<CategoriaDTO> categorias;
    ProductoProperty itemSelected;

    public ProductoViewModel() {
        this.selected = new ProductoDTOProperty();
        this.itemSelected = new ProductoProperty();
        this.itemSelected.set(new Producto());
        this.items = FXCollections.observableArrayList();
        this.categorias = FXCollections.observableArrayList();

    }
    public ProductoDTOProperty selectedProperty() {
        return selected;
    }

    public ProductoQueryService getQueryService() {
        return queryService;
    }

    public ObservableList<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(ObservableList<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public void setCategoriaQueryService(CategoriaQueryService categoriaService) {
        this.categoriaQueryService = categoriaService;
        loadCategorias();

    }
    private void loadCategorias(){
        this.categorias.clear();
        this.categorias.addAll(this.categoriaQueryService.getAll().stream().map(categoriaDTO -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(categoriaDTO.getId());
            dto.setNombre(categoriaDTO.getNombre());
            dto.setDescripcion(categoriaDTO.getDescripcion());
            dto.setImg_src(categoriaDTO.getImg_src());
            dto.setActivo(categoriaDTO.isActivo());
            return dto;

        }).toList());
    }
    public void setQueryService(ProductoQueryService queryService) {
        this.queryService = queryService;
        this.reload();
    }

    public ProductoCommandService getCommandService() {
        return commandService;
    }
    /*public  getCategoriasDTO(){
        this.categoriaQueryService.getAll();
    }*/
    public void setCommandService(ProductoCommandService commandService) {
        this.commandService = commandService;
        this.commandService.register(this);
    }

    public CategoriaQueryService getCategoriaQueryService() {
        return categoriaQueryService;
    }

    public CategoriaCommandService getCategoriaCommandService() {
        return categoriaCommandService;
    }

    public void setCategoriaCommandService(CategoriaCommandService categoriaCommandService) {
        this.categoriaCommandService = categoriaCommandService;
        this.categoriaCommandService.register(this);
    }

    public ObservableList<ProductoAgregateDTO> getProductos() {
        return items;
    }

    public void setProductos(ObservableList<ProductoAgregateDTO> items) {
        this.items = items;
    }


    public void setSelected(ProductoAgregateDTO selected) {
        this.selected.set(selected);
        int categoriaId = (selected.getCategoria() != null) ? selected.getCategoria().getId() : -1;
        Producto c = new Producto(
                selected.getId(),
                selected.getNombre(),
                selected.getDescripcion(),
                selected.getImg_src(),
                selected.isActivo(),
                selected.getPrecio(),
                categoriaId
        );
        this.itemSelected.set(c);
    }

    public ProductoDTOProperty getSelected() {
        return this.selected;
    }

    public ProductoProperty getProductoPropertySelected() {

        return this.itemSelected;
    }

    public void unSelect() {


        this.selected.set(new ProductoAgregateDTO());
        this.itemSelected.set(new Producto());
    }


    public void add(Producto item) {
        this.commandService.add(item);
        this.unSelect();
        //this.service.add(item);
        //this.service.categoriaProperty().set(item);

    }

    public void add(String nombre, String descripcion, String img, boolean activo, int categoriaid, float precio) {
        this.commandService.add(nombre, descripcion, img, activo, categoriaid, precio);

        this.selected.set(new ProductoAgregateDTO());
        this.itemSelected.set(new Producto());
    }

    public void update(Producto item) {
        this.commandService.update(item);

        this.selected.set(new ProductoAgregateDTO());
        this.itemSelected.set(new Producto());
    }

    public void remove(ProductoAgregateDTO item) {

        this.commandService.remove(item.getId());
        this.items.remove(item);
        this.unSelect();
    }

    public void removeSelected() {
        if (this.selected.get() != null && this.selected.get().getId() != -1) {
            this.remove(this.selected.get());
            this.unSelect();
        }
    }

    public ProductoAgregateDTO getById(int id) {
        return this.queryService.getById(id);
    }

    public void reload() {

        if (this.queryService != null) {
            this.items.clear();
            this.load();
        }

    }

    public void load() {
        if (this.queryService != null) {
            this.items.clear();
            this.items.addAll(this.queryService.getAll());
        }
        this.loadCategorias();
    }

    public ObservableList<ProductoAgregateDTO> getItems() {
        return this.items;
    }

    public void refresh() {
        ProductoAgregateDTO tempo = new ProductoAgregateDTO();
        this.items.add(tempo);
        this.items.remove(tempo);
        CategoriaDTO tempo2= new CategoriaDTO();
        this.categorias.add(tempo2);
        this.categorias.remove(tempo2);
        //this.servicerepository.refresh();
    }

    /**
     * evento al añadir nuevo
     */

   /* @Subscribe
    public void onEvent(Event event) {
        if (event.getData() instanceof Producto) {
            this.onProductoEvent(event);
        }else if(event.getData() instanceof  Categoria){
            this.onCategoriaEvent(event);
        }


    }
    private CategoriaDTO getCategoriaDTO(int id){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        ies.accesodatos.categorias.model.dto.CategoriaAgregateDTO otherdto = this.categoriaQueryService.getById(id);
        categoriaDTO.setId(otherdto.getId());
        categoriaDTO.setNombre(otherdto.getNombre());
        categoriaDTO.setDescripcion(otherdto.getDescripcion());
        categoriaDTO.setImg_src(otherdto.getImg_src());
        categoriaDTO.setActivo(otherdto.isActivo());

        return categoriaDTO;
    }
    private void onProductoEvent(Event event) {
        Producto original = (Producto) event.getData();
        ProductoAgregateDTO cdto;
        switch (event.getAction()) {
            case ADD:

                cdto = new ProductoAgregateDTO(original.getId(),
                        original.getNombre(),
                        original.getDescripcion(),
                        original.getImg_src(),
                        original.isActivo(), original.getPrecio(),this.getCategoriaDTO(original.getId()));
                if (cdto != null) {
                    this.items.add(cdto);
                }
                break;
            case UPDATE:
                cdto = this.items.stream().filter(item -> {
                            return item.getId() == original.getId();
                        }
                ).findFirst().orElse(null);
                if (cdto != null) {
                    cdto.setNombre(original.getNombre());
                    cdto.setDescripcion(original.getDescripcion());
                    cdto.setImg_src(original.getImg_src());
                    cdto.setActivo(original.isActivo());
                    cdto.setPrecio(original.getPrecio());
                    //si ha cambiado se actualiza
                    if(cdto.getCategoria().getId()!=original.getCategoriaId()){
                        cdto.setCategoria(this.getCategoriaDTO(original.getCategoriaId()));
                    }
                    this.refresh();
                }
                break;
            case DELETE:
                cdto = this.items.stream().filter(itemDTO -> {
                            return itemDTO.getId() == original.getId();
                        }
                ).findFirst().orElse(null);
                if (cdto != null) {
                    this.items.remove(cdto);
                }
                break;

        }
    }


    private void onCategoriaEvent(Event event) {
        Categoria original = (Categoria) event.getData();
        ies.accesodatos.productos.model.dto.CategoriaDTO cdto;
        switch (event.getAction()) {
            case ADD:
                cdto = new ies.accesodatos.productos.model.dto.CategoriaDTO(
                        original.getId(),
                        original.getNombre(),
                        original.getDescripcion(),
                        original.getImg_src(),
                        original.isActivo());

                if (cdto != null) {
                    this.categorias.add(cdto);
                }
                break;
            case UPDATE:
                cdto = this.categorias.stream().filter(categoriaDTO -> {
                            return categoriaDTO.getId() == original.getId();
                        }
                ).findFirst().orElse(null);
                if (cdto != null) {
                    cdto.setNombre(original.getNombre());
                    cdto.setDescripcion(original.getDescripcion());
                    cdto.setImg_src(original.getImg_src());
                    cdto.setActivo(original.isActivo());

                    this.refresh();
                }
                break;
            case DELETE:
                cdto = this.categorias.stream().filter(categoriaDTO -> {
                            return categoriaDTO.getId() == original.getId();
                        }
                ).findFirst().orElse(null);
                if (cdto != null) {
                    this.categorias.remove(cdto);
                }
                break;

        }
    }*/

}
