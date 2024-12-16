package ies.accesodatos.categorias.model;

import ies.accesodatos.categorias.model.dto.CategoriaAgregateDTO;
import ies.accesodatos.categorias.model.dto.ProductoDTO;
import ies.accesodatos.commons.repositories.AQueryRepository;
import ies.accesodatos.productos.model.ProductoDao;


import java.util.List;

public class CategoriaQueryRepository extends AQueryRepository<CategoriaAgregateDTO, Integer> {
    private CategoriaDao itemDao = new CategoriaDao();
    private ProductoDao productoDao = new ProductoDao();

    public CategoriaQueryRepository() {
        super();
    }

    public CategoriaQueryRepository (CategoriaDao categoriaDao , ProductoDao productoDao) {
        super();
        this.setDao(itemDao);
        this.itemDao = categoriaDao;
        this.productoDao = productoDao;

    }

    public void setDao(CategoriaDao itemDao) {
        this.itemDao = itemDao;
    }

    public CategoriaDao getCategoriaDao() {
        return itemDao;
    }

    public void setCategoriaDao(CategoriaDao CategoriaDao) {
        this.itemDao = CategoriaDao;
    }

    public CategoriaDao getitemDao() {
        return itemDao;
    }

    @Override
    public CategoriaAgregateDTO getById(Integer id) {
        var Categoria=itemDao.getById(id);
        CategoriaAgregateDTO categoriaDTO=new CategoriaAgregateDTO();
        categoriaDTO.setId(Categoria.getId());
        categoriaDTO.setNombre(Categoria.getNombre());
        categoriaDTO.setActivo(Categoria.isActivo());
        categoriaDTO.setDescripcion(Categoria.getDescripcion());
        categoriaDTO.setImg_src(Categoria.getImg_src());
        var productos = productoDao.getAll();
        productos.forEach(producto -> {
            if (producto.getCategoriaId() == id) {
                ProductoDTO productoDTO = new ProductoDTO();
                productoDTO.setId(producto.getId());
                productoDTO.setNombre(producto.getNombre());
                productoDTO.setDescripcion(producto.getDescripcion());
                productoDTO.setPrecio(producto.getPrecio());
                productoDTO.setImg_src(producto.getImg_src());
                productoDTO.setActivo(producto.isActivo());
                productoDTO.setCategoriaid(categoriaDTO.getId());

                categoriaDTO.addProducto(productoDTO);
            }
        });
        return categoriaDTO;
    }
    @Override
    public List<CategoriaAgregateDTO> getAll() {
        var categorias=this.itemDao.getAll();
        return categorias.stream().map( c->{
            CategoriaAgregateDTO dto=new CategoriaAgregateDTO(c.getId(),c.getNombre(),c.getDescripcion(),c.getImg_src(),c.isActivo());
            var productos = productoDao.getAll();
            productos.forEach(producto -> {
                if (producto.getCategoriaId() == dto.getId()) {
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setId(producto.getId());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setDescripcion(producto.getDescripcion());
                    productoDTO.setPrecio(producto.getPrecio());
                    productoDTO.setImg_src(producto.getImg_src());
                    productoDTO.setActivo(producto.isActivo());
                    productoDTO.setCategoriaid(dto.getId());

                    dto.addProducto(productoDTO);
                }
            });
            return dto;
        } ).toList();
    }
}
