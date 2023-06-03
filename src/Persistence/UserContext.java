package Persistence;

import Model.User;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class UserContext<T> extends DbContext<T>{
    public UserContext(JdbcPooledConnectionSource connectionSource) {
        super((Class<T>) User.class, connectionSource);
    }
}
