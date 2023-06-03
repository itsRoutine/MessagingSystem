package Persistence;

import Model.Message;
import Model.User;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

public class DB {
    public MessageContext<Message> messages;
    public UserContext<User> users;
    private JdbcPooledConnectionSource connectionSource;
    public DB(){
        try{
            connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:src\\Persistence\\db.sqlite");
        } catch (Exception e){
            e.printStackTrace();
        }
        this.messages = new MessageContext<>(connectionSource);
        this.users = new UserContext<>(connectionSource);
    }
}
