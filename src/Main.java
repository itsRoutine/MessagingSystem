import Model.*;
import Persistence.DB;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        User Admin = new Admin("Admin", "Admin");
        User Basic = new Basic("Basic", "Basic");

        DB db = new DB();

        //db.users.create(Admin);
        //db.users.create(Basic);

        Message message1 = new Message("Hello 1", (User) db.users.getById(1), (User) db.users.getById(2), false, new Date(System.currentTimeMillis()));
        Message message2 = new Message("Hello 2", (User) db.users.getById(2) , (User) db.users.getById(1) , false, new Date(System.currentTimeMillis()));

        db.messages.create(message1);
        db.messages.create(message2);

        // array list of messages
        ArrayList<Message> messages = new ArrayList<>();
        db.messages.getAll().forEach(messages::add);

        // print all messages sender and receiver names and content
        for (Message message : messages) {
            System.out.println(message.getSender().getUsername() + " -> " + message.getReceiver().getUsername() + ": " + message.getContent());
        }

        db.closeConnection();
    }
}