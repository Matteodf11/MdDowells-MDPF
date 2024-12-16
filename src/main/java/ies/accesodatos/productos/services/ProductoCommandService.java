package ies.accesodatos.productos.services;



import ies.accesodatos.commons.services.ACommandService;
import ies.accesodatos.commons.services.Event;
import ies.accesodatos.productos.model.Producto;
import ies.accesodatos.productos.model.ProductoRepository;

public class ProductoCommandService extends ACommandService {
    private final ProductoRepository repository;

    public ProductoCommandService(ProductoRepository productoRepository) {
        this.repository = productoRepository;
    }

    public void add(Producto item) {
        this.repository.add(item);
        this.post("Alta producto", Event.ACTION.ADD, item);
    }

    public void add(String nombre, String descripcion, String img, boolean activo, int categoriaid, double precio) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setImg_src(img);
        producto.setActivo(activo);
        producto.setCategoriaId(categoriaid);
        producto.setPrecio(precio);
        this.repository.save(producto);
        this.post("Alta producto", Event.ACTION.ADD, producto);
    }

    public void delete(Producto producto) {
        this.repository.delete(producto);
        this.post("Alta producto", Event.ACTION.ADD, producto);
    }

    public void remove(int id) {
        Producto producto = this.repository.getById(id);
        this.repository.deleteById(id);
        this.post("Borrado producto", Event.ACTION.DELETE, producto);
    }

    public void changeImagen(int id, String value) {
        Producto c = repository.getById(id);
        if (c != null) {
            c.setImg_src(value);
            this.repository.save(c);
            this.post("Modificar producto(imagen)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeName(int id, String name) {
        Producto c = repository.getById(id);
        if (c != null) {
            c.setNombre(name);
            this.repository.save(c);
            this.post("Modificar producto(nombre)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeDescripcion(int id, String value) {
        Producto c = repository.getById(id);
        if (c != null) {
            c.setDescripcion(value);
            this.repository.save(c);
            this.post("Modificar producto(clave)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeState(int id, boolean state) {
        Producto c = repository.getById(id);
        if (c != null) {
            c.setActivo(state);
            this.repository.save(c);
            this.post("Modificar producto (estado)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeAll(int id, String name, String descripcion, String imagen, Boolean state) {
        Producto c = repository.getById(id);
        if (c != null) {
            c.setNombre(name);
            c.setDescripcion(descripcion);
            c.setImg_src(imagen);
            c.setActivo(state);
            this.repository.save(c);
            this.post("Modificar producto (total)", Event.ACTION.UPDATE, c);
        }
    }

    public void update(Producto producto) {
        this.repository.save(producto);
        this.post("Modificar producto (total)", Event.ACTION.UPDATE, producto);
    }
}
