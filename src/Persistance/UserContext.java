package Persistance;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class UserContext<T> extends DbContext<T>{
    public UserContext(Class<T> clazz, JdbcPooledConnectionSource connectionSource) {
        super(clazz, connectionSource);
    }
}
