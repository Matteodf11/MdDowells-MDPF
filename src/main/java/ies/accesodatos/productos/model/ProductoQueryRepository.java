package ies.accesodatos.productos.model;


import ies.accesodatos.categorias.model.Categoria;
import ies.accesodatos.categorias.model.CategoriaDao;
import ies.accesodatos.commons.repositories.AQueryRepository;
import ies.accesodatos.productos.model.dto.CategoriaDTO;
import ies.accesodatos.productos.model.dto.ProductoAgregateDTO;

import java.util.List;

public class ProductoQueryRepository extends AQueryRepository<ProductoAgregateDTO, Integer> {
    private ProductoDao itemDao = new ProductoDao();
    CategoriaDao categoriaDao = new CategoriaDao();

    public ProductoQueryRepository() {
        super();
    }

    public ProductoQueryRepository(ProductoDao ProductoDao, CategoriaDao CategoriaDao) {
        super();
        this.setDao(itemDao);
        this.itemDao=ProductoDao;
        this.categoriaDao=CategoriaDao;
    }

    public void setDao(ProductoDao itemDao) {
        this.itemDao = itemDao;
    }

    public ProductoDao getProductoDao() {
        return itemDao;
    }

    public void setProductoDao(ProductoDao ProductoDao) {
        this.itemDao = ProductoDao;
    }

    public ProductoDao getitemDao() {
        return itemDao;
    }

    @Override
    public ProductoAgregateDTO getById(Integer id) {
        var producto = itemDao.getById(id);
        CategoriaDTO cDTO;
        ProductoAgregateDTO productoDTO = new ProductoAgregateDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setActivo(producto.isActivo());
        productoDTO.setImg_src(producto.getImg_src());
        Categoria c=this.categoriaDao.getById(producto.getCategoriaId());
        if(c!=null) {
            cDTO = new CategoriaDTO(c);
        }else {
            cDTO=new CategoriaDTO();
        }
        productoDTO.setCategoria(cDTO);
        return productoDTO;
    }
    @Override
    public List<ProductoAgregateDTO> getAll() {
        var Productos=this.itemDao.getAll();
        return Productos.stream().map( c->{
            Categoria cat = categoriaDao.getById(c.getCategoriaId());
            CategoriaDTO cDTO = new CategoriaDTO(cat);
            ProductoAgregateDTO dto=new ProductoAgregateDTO(c.getId(),c.getNombre(),c.getDescripcion(),c.getImg_src(),c.isActivo(), c.getPrecio(),cDTO);
            return dto;
        } ).toList();
    }
}
