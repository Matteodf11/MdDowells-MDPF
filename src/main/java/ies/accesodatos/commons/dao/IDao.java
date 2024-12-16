package ies.accesodatos.commons.dao;

import java.util.List;

public interface IDao<T,K>{
    public T getById(K id);
    public List<T> getAll();
    public void update(T item);
    public void delete(T item);
    public void deleteById(K key);
    public void insert(T item);

}
