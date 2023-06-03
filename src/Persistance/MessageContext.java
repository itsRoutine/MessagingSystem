package Persistance;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class MessageContext<T> extends DbContext<T> {
    MessageContext(Class<T> clazz, JdbcPooledConnectionSource connectionSource) {
        super(clazz, connectionSource);
    }
}
