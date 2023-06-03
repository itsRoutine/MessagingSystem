package Persistance;

import Model.Message;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class MessageContext<T> extends DbContext<T> {
    MessageContext(JdbcPooledConnectionSource connectionSource) {
        super((Class<T>) Message.class, connectionSource);
    }
}
