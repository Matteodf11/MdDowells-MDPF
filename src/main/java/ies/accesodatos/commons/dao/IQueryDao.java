package ies.accesodatos.commons.dao;

import java.util.List;

public interface IQueryDao <T,K>{
    public T getById(K id);
    public List<T> getAll();


}
