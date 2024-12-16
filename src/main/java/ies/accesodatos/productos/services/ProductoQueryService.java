package ies.accesodatos.productos.services;




import ies.accesodatos.productos.model.ProductoQueryRepository;
import ies.accesodatos.productos.model.dto.ProductoAgregateDTO;


import java.util.ArrayList;
import java.util.List;

public class ProductoQueryService {
    private final ProductoQueryRepository categoriaQueryRepository;

    public ProductoQueryService(ProductoQueryRepository categoriaRespository) {
        this.categoriaQueryRepository = categoriaRespository;
    }

    public List<ProductoAgregateDTO> getAll() {
        var items = this.categoriaQueryRepository.getAll();
        return items;
    }

    public ProductoAgregateDTO getById(int id) {
        var items = this.categoriaQueryRepository.getById(id);
        return items;
    }
}
