package ies.accesodatos.commons.repositories;

public interface  IWriteRepository<T,K> {
    public void save(T item);
   public  void add(T item);
    public void update(T item);
    public  void delete(T item);
    public void deleteById(K id);
    public  T getById(K id);

}
