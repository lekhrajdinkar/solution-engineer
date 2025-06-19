package web.services;

import java.util.List;

public interface BaseSrv<T, ID> {
    public List<T> findAll();
    public T findById(ID id);
    public ID save(T t);
    public T update(T t);
    public T delete(T t);
}
