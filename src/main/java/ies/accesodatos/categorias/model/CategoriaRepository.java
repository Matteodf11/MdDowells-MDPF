package ies.accesodatos.categorias.model;

import ies.accesodatos.commons.repositories.AWriteRepository;
import ies.accesodatos.productos.model.ProductoDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;


public class CategoriaRepository extends AWriteRepository<Categoria, Integer> {
    private final String directorioImagenes = "./images/categorias/";
    private CategoriaDao itemDao;

    public CategoriaRepository() {
        super();
    }

    public CategoriaRepository(CategoriaDao categoriaDao) {
        super();
        this.setDao(categoriaDao);
    }

    public void setDao(CategoriaDao itemDao) {
        this.itemDao = itemDao;
    }

    public CategoriaDao getCategoriaDao() {
        return itemDao;
    }

    @Override
    public void save(Categoria item) {
        if (item.getId() == -1) {
            this.add(item);
        } else {
            this.update(item);
        }
    }

    @Override
    public void add(Categoria item) {
        try {

            String imagenOriginal = item.getImg_src();
            Path rutaOriginal = Path.of(imagenOriginal);


            String nombreArchivo = rutaOriginal.getFileName().toString();
            String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);


            this.itemDao.insert(item);


            Path nuevaRuta = Path.of("./images/Categorias", item.getId() + "." + extension);


            Files.copy(rutaOriginal, nuevaRuta, StandardCopyOption.REPLACE_EXISTING);


            item.setImg_src(nuevaRuta.toString());


            this.itemDao.update(item);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Categoria item) {
        try {

            String[] rutaSeparada = item.getImg_src().split("[/\\\\]");
            String archivoOriginal = rutaSeparada[rutaSeparada.length - 1];
            String[] extensions = archivoOriginal.split("\\.");
            String extension = extensions[extensions.length - 1];
            String nuevoNombre = item.getId() + "." + extension;


            Path nuevaRuta = Path.of("./images/categorias", nuevoNombre);


            if (item.getImg_src().endsWith(nuevoNombre)) {
                this.itemDao.update(item);
                return;
            }


            Path rutaOriginal = Path.of(item.getImg_src());
            Files.copy(rutaOriginal, nuevaRuta, StandardCopyOption.REPLACE_EXISTING);


            item.setImg_src(nuevaRuta.toString());


            this.itemDao.update(item);
        } catch (IOException e) {

            System.err.println("Error al procesar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Categoria item) {
        Categoria original = this.itemDao.getById(item.getId());
        if (original != null && Files.exists(Path.of(original.getImg_src()))) {
            try {
                Files.delete(Path.of(original.getImg_src()));
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar la imagen de la categoría", e);
            }
        }

        this.itemDao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {
        try {

            this.itemDao.getConn().getConnection().setAutoCommit(false);


            ProductoDao productoDao = new ProductoDao();
            productoDao.setConexion(this.itemDao.getConn());


            var productos = productoDao.getAll();


            productos.forEach(producto -> {
                if (producto.getCategoriaId() == id) {
                    try {
                        Files.delete(Path.of(producto.getImg_src()));
                        productoDao.delete(producto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });


            Categoria categoria = this.itemDao.getById(id);

            try {

                Path imagenPath = Path.of(categoria.getImg_src());
                if (Files.exists(imagenPath)) {
                    Files.delete(imagenPath);
                }


                this.itemDao.deleteById(id);
            } catch (IOException e) {
                System.err.println("Error eliminando imagen: " + e.getMessage());
                e.printStackTrace();
            }


            this.itemDao.getConn().getConnection().commit();
        } catch (SQLException e) {

            try {
                System.err.println("Error en la transacción, realizando rollback...");
                this.itemDao.getConn().getConnection().rollback();
            } catch (SQLException rollbackError) {
                System.err.println("Error realizando rollback: " + rollbackError.getMessage());
                rollbackError.printStackTrace();
            }
            e.printStackTrace();
        } finally {

            try {
                this.itemDao.getConn().getConnection().setAutoCommit(true);
            } catch (SQLException autoCommitError) {
                System.err.println("Error restableciendo auto-commit: " + autoCommitError.getMessage());
                autoCommitError.printStackTrace();
            }
        }
    }

    @Override
    public Categoria getById(Integer id) {
        return this.itemDao.getById(id);
    }

}
