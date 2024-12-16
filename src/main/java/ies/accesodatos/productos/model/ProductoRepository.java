package ies.accesodatos.productos.model;


import ies.accesodatos.commons.repositories.AWriteRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ProductoRepository extends AWriteRepository<Producto, Integer> {
    private final String directorioImagenes = "./images/productos/";
    private ProductoDao itemDao;

    public ProductoRepository() {
        super();
    }

    public ProductoRepository(ProductoDao ProductoDao) {
        super();
        this.setDao(ProductoDao);
    }

    public void setDao(ProductoDao itemDao) {
        this.itemDao = itemDao;
    }

    public ProductoDao getProductoDao() {
        return itemDao;
    }

    @Override
    public void save(Producto item) {
        if (item.getId() == -1) {
            this.add(item);
        } else {
            this.update(item);
        }
    }

    public void add(Producto item) {
        try {
            String imagenOriginal = item.getImg_src();
            Path rutaOriginal = Path.of(imagenOriginal);
            
            String nombreArchivo = rutaOriginal.getFileName().toString();
            String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
            
            this.itemDao.insert(item);
            
            Path rutaNueva = Path.of("./images/productos", item.getId() + "." + extension);
            
            Files.copy(rutaOriginal, rutaNueva, StandardCopyOption.REPLACE_EXISTING);
            
            item.setImg_src(rutaNueva.toString());
            
            this.itemDao.update(item);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Producto item) {
        try {

            String[] rutaSeparada = item.getImg_src().split("[/\\\\]");
            String archivoOriginal = rutaSeparada[rutaSeparada.length - 1];
            String[] extensions = archivoOriginal.split("\\.");
            String extension = extensions[extensions.length - 1];
            String nuevoNombre = item.getId() + "." + extension;

            Path rutaNueva = Path.of("./images/productos", nuevoNombre);
           
            if (item.getImg_src().endsWith(nuevoNombre)) {
                this.itemDao.update(item);
                return;
            }
            
            Path rutaOriginal = Path.of(item.getImg_src());
            Files.copy(rutaOriginal, rutaNueva, StandardCopyOption.REPLACE_EXISTING);
            item.setImg_src(rutaNueva.toString());
            this.itemDao.update(item);
            
        } catch (IOException e) {
            System.err.println("Error al procesar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Producto item) {
        Producto original = this.itemDao.getById(item.getId());
        if (original != null && Files.exists(Path.of(original.getImg_src()))) {
            try {
                Files.delete(Path.of(original.getImg_src()));
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar la imagen del producto", e);
            }
        }

        this.itemDao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {
        Producto original = this.itemDao.getById(id);
        if (original != null && Files.exists(Path.of(original.getImg_src()))) {
            try {
                Files.delete(Path.of(original.getImg_src()));
            } catch (IOException e) {
                throw new RuntimeException("Error al eliminar la imagen del producto", e);
            }
        }

        this.itemDao.deleteById(id);
    }

    @Override
    public Producto getById(Integer id) {
        return this.itemDao.getById(id);
    }
    
}
