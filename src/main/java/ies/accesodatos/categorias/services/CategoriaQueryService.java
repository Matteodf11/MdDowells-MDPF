package ies.accesodatos.categorias.services;



import ies.accesodatos.categorias.model.Categoria;
import ies.accesodatos.categorias.model.CategoriaQueryRepository;

import ies.accesodatos.categorias.model.dto.CategoriaAgregateDTO;
import ies.accesodatos.empleados.model.Empleado;


import java.util.ArrayList;
import java.util.List;

public class CategoriaQueryService {
    private final CategoriaQueryRepository categoriaQueryRepository;

    public CategoriaQueryService(CategoriaQueryRepository categoriaRespository) {
        this.categoriaQueryRepository = categoriaRespository;
    }

    public List<CategoriaAgregateDTO> getAll() {
        var items = this.categoriaQueryRepository.getAll();
        return items;
    }

    public CategoriaAgregateDTO getById(int id) {
        var items = this.categoriaQueryRepository.getById(id);
        return items;
    }
}
