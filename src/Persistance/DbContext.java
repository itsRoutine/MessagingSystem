package Persistance;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.util.List;

public class DbContext<T> implements DbSet<T>{

    protected Dao<T, Long> context;

    DbContext(Class<T> clazz, JdbcPooledConnectionSource connectionSource){
        try{
            context = DaoManager.createDao(connectionSource, clazz);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() {
        try {
            return context.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getById(long id) {
        try {
            return context.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Object obj) {
        try {
            context.create((T) obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(List<T> obj) {
        try {
            for (T o : obj) {
                context.create(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object obj) {
        try {
            context.update((T) obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(List<T> obj) {
        try {
            for(T o : obj){
                context.update(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            context.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dao<T, Long> getContext() {
        return context;
    }


}
