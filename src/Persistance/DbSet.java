package Persistance;

import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface DbSet<T> {
    List<T> getAll();
    Object getById(long id);
    void create(Object obj);
    void create(List<T> obj);
    void update(Object obj);
    void update(List<T> obj);
    void deleteById(long id);

    Dao<T, Long> getContext();
}
