package ies.accesodatos.ui.categorias.viewmodel;


import ies.accesodatos.categorias.model.Categoria;
import ies.accesodatos.categorias.model.dto.CategoriaAgregateDTO;
import ies.accesodatos.categorias.services.CategoriaCommandService;
import ies.accesodatos.categorias.services.CategoriaQueryService;
import ies.accesodatos.productos.services.ProductoCommandService;
import ies.accesodatos.ui.commons.IViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaViewModel implements IViewModel {
    //CategoriasService service;
     CategoriaDTOProperty selected;
    CategoriaQueryService queryService;
    CategoriaCommandService commandService;
    ProductoCommandService productoCommandService;
    ObservableList< CategoriaAgregateDTO> categorias;
    CategoriaProperty categoriaSelected;

    public CategoriaViewModel() {
        this.selected = new  CategoriaDTOProperty();
        this.categoriaSelected = new CategoriaProperty();
        this.categoriaSelected.set(new Categoria());
        this.categorias = FXCollections.observableArrayList();

    }

    public ProductoCommandService getProductoCommandService() {
        return productoCommandService;
    }

    public void setProductoCommandService(ProductoCommandService productoCommandService) {
        this.productoCommandService = productoCommandService;
        this.productoCommandService.register(this);
    }

    public  CategoriaDTOProperty selectedProperty() {
        return selected;
    }

    public CategoriaQueryService getQueryService() {
        return queryService;
    }

    public void setQueryService(CategoriaQueryService queryService) {
        this.queryService = queryService;
        this.reload();
    }

    public CategoriaCommandService getCommandService() {
        return commandService;
    }

    public void setCommandService(CategoriaCommandService commandService) {
        this.commandService = commandService;
        this.commandService.register(this);
    }

    public ObservableList< CategoriaAgregateDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(ObservableList< CategoriaAgregateDTO> categorias) {
        this.categorias = categorias;
    }


    public void setSelected( CategoriaAgregateDTO selected) {
        this.selected.set(selected);
        Categoria c = new Categoria(selected.getId(), selected.getNombre(), selected.getDescripcion(), selected.getImg_src(),
                selected.isActivo());
        this.categoriaSelected.set(c);

    }

    public  CategoriaDTOProperty getSelected() {
        return this.selected;
    }

    public CategoriaProperty getCategoriaPropertySelected() {

        return this.categoriaSelected;
    }

    public void unSelect() {


        this.selected.set(new  CategoriaAgregateDTO());
        this.categoriaSelected.set(new Categoria());
    }


    public void add(Categoria item) {
        this.commandService.add(item);
        this.unSelect();
        //this.service.add(item);
        //this.service.categoriaProperty().set(item);

    }

    public void add(String nombre, String descripcion, String img, boolean activo) {
        this.commandService.add(nombre, descripcion, img, activo);

        this.selected.set(new  CategoriaAgregateDTO());
        this.categoriaSelected.set(new Categoria());
    }

    public void update(Categoria item) {
        this.commandService.update(item);

        this.selected.set(new  CategoriaAgregateDTO());

        this.categoriaSelected.set(new Categoria());
    }

    public void remove( CategoriaAgregateDTO item) {
        this.categorias.remove(item);
        this.commandService.remove(item.getId());
        this.unSelect();
    }

    public void removeSelected() {
        if (this.selected.get() != null && this.selected.get().getId() != -1) {
            this.remove(this.selected.get());
            this.unSelect();
        }
    }

    public  CategoriaAgregateDTO getById(int id) {
        return this.queryService.getById(id);
    }

    public void reload() {

        if (this.queryService != null) {
            this.categorias.clear();
            this.load();
        }

    }

    public void load() {
        if (this.queryService != null) {
            this.categorias.clear();
            this.categorias.addAll(this.queryService.getAll());
            this.unSelect();
        }
    }

    public ObservableList< CategoriaAgregateDTO> getItems() {
        return this.categorias;
    }

    public void refresh() {
         CategoriaAgregateDTO tempo=new  CategoriaAgregateDTO();
        this.categorias.add(tempo);
        this.categorias.remove(tempo);

    }

    /**
     * evento al añadir nuevo
     */

    /*@Subscribe
    public void onEvent(Event event) {
        if (event.getData() instanceof Categoria) {
         //   this.onCategoriaEvent(event);
        }


    }

    private void onCategoriaEvent(Event event) {
        Categoria original = (Categoria) event.getData();
         CategoriaAgregateDTO cdto;
        switch (event.getAction()) {
            case ADD:
                cdto = new  CategoriaAgregateDTO(original.getId(), original.getNombre(), original.getDescripcion(), original.getImg_src(), original.isActivo());
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
    }

    private void onProductoEvent(Event event) {
        Producto original = (Producto) event.getData();
        ProductoDTO cdto;
         CategoriaAgregateDTO categoriaaborrar;
        switch (event.getAction()) {

            case ADD:

                cdto = new ProductoDTO(original.getId(),
                        original.getNombre(),
                        original.getDescripcion(),
                        original.getImg_src(),
                        original.isActivo(), original.getPrecio(),original.getCategoriaId());

                if (cdto != null) {
                    var categoriaDTO=this.categorias.stream().filter(cat -> {
                        return cat.getId() == original.getId();
                    }).findFirst().orElse(null);
                    if(categoriaDTO!=null) {
                        categoriaDTO.addProducto(cdto);
                    }
                }
                break;
            case UPDATE:
                  cdto=this.categorias.stream().flatMap(categoriaDTO -> categoriaDTO.getProductos().stream()).filter(item -> {
                            return item.getId() == original.getId();
                        }
                ).findFirst().orElse(null);
                if (cdto != null) {
                    cdto.setNombre(original.getNombre());
                    cdto.setDescripcion(original.getDescripcion());
                    cdto.setImg_src(original.getImg_src());
                    cdto.setActivo(original.isActivo());
                    cdto.setPrecio(original.getPrecio());
                    cdto.setCategoriaid(original.getCategoriaId());
                    //si ha cambiado se actualiza
                    if(cdto.getCategoriaid()!=original.getCategoriaId()){
                        //se borra de la categoria actual
                        this.categorias.forEach(categoriaDTO -> {
                            categoriaDTO.getProductos().removeIf(productoDTO -> {
                                return productoDTO.getId() == original.getId();
                            });
                        });
                        //se inserta en la nueva
                        this.categorias.forEach(categoriaDTO -> {
                            if(categoriaDTO.getId()==cdto.getCategoriaid()){
                               categoriaDTO.getProductos().add(cdto);
                            }
                        });

                    }
                    this.refresh();
                }
                break;
            case DELETE:
                this.categorias.stream().forEach(itemDTO -> {
                    itemDTO.getProductos().removeIf(productoDTO -> {
                        return productoDTO.getId() == original.getId();
                    });
                });

                break;

        }
    }*/
}
