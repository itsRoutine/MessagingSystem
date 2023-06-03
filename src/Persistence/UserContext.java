package Persistence;

import Model.User;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class UserContext<T> extends DbContext<T>{
    public UserContext(JdbcPooledConnectionSource connectionSource) {
        super((Class<T>) User.class, connectionSource);
    }

    public User getByUsername(String username) {
        try {
            return (User) context.queryBuilder().
                    where().
                    eq("Username", username).
                    queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
