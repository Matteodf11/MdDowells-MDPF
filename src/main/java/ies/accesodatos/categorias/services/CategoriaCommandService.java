package ies.accesodatos.categorias.services;


import ies.accesodatos.categorias.model.Categoria;
import ies.accesodatos.categorias.model.CategoriaRepository;
import ies.accesodatos.commons.services.ACommandService;
import ies.accesodatos.commons.services.Event;

public class CategoriaCommandService extends ACommandService {
    private final CategoriaRepository repository;

    public CategoriaCommandService(CategoriaRepository categoriaRepository) {
        this.repository = categoriaRepository;
    }

    public void add(Categoria item) {
        this.repository.add(item);
        this.post("Alta categoria", Event.ACTION.ADD, item);
    }

    public void add(String nombre, String descripcion, String imagen, boolean activo) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setImg_src(imagen);
        categoria.setActivo(activo);
        this.repository.save(categoria);
        this.post("Alta categoria", Event.ACTION.ADD, categoria);
    }

    public void delete(Categoria categoria) {
        this.repository.delete(categoria);
        this.post("Alta categoria", Event.ACTION.ADD, categoria);
    }

    public void remove(int id) {
        Categoria categoria = this.repository.getById(id);
        this.repository.deleteById(id);
        this.post("Borrado categoria", Event.ACTION.DELETE, categoria);
    }

    public void changeImagen(int id, String value) {
        Categoria c = repository.getById(id);
        if (c != null) {
            c.setImg_src(value);
            this.repository.save(c);
            this.post("Modificar categoria(imagen)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeName(int id, String name) {
        Categoria c = repository.getById(id);
        if (c != null) {
            c.setNombre(name);
            this.repository.save(c);
            this.post("Modificar categoria(nombre)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeDescripcion(int id, String value) {
        Categoria c = repository.getById(id);
        if (c != null) {
            c.setDescripcion(value);
            this.repository.save(c);
            this.post("Modificar categoria(clave)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeState(int id, boolean state) {
        Categoria c = repository.getById(id);
        if (c != null) {
            c.setActivo(state);
            this.repository.save(c);
            this.post("Modificar categoria (estado)", Event.ACTION.UPDATE, c);
        }
    }

    public void changeAll(int id, String name, String descripcion, String imagen, Boolean state) {
        Categoria c = repository.getById(id);
        if (c != null) {
            c.setNombre(name);
            c.setDescripcion(descripcion);
            c.setImg_src(imagen);
            c.setActivo(state);
            this.repository.save(c);
            this.post("Modificar categoria (total)", Event.ACTION.UPDATE, c);
        }
    }

    public void update(Categoria categoria) {
        this.repository.save(categoria);
        this.post("Modificar categoria (total)", Event.ACTION.UPDATE, categoria);
    }
}






