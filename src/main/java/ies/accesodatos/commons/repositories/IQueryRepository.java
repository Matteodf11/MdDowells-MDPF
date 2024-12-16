package ies.accesodatos.commons.repositories;

import java.util.List;

public interface  IQueryRepository<T,K> {
    public T getById(K id);
    public List<T> getAll();
}
