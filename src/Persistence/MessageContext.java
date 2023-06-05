package Persistence;

import Model.Message;
import Model.User;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageContext<T> extends DbContext<T> {
    MessageContext(JdbcPooledConnectionSource connectionSource) {
        super((Class<T>) Message.class, connectionSource);
    }

    public List<String> getSendersUsername(int recieverId) {
        try{
            // get Senders without duplicates
            List<Message> messages = (List<Message>) context.queryBuilder().
                    groupBy("sender_id").
                    where().
                    eq("Receiver_id", recieverId).
                    query();

            // initialize senders list and add senders to it
            List<String> senderUsernames = new ArrayList<>();
            for (Message message : messages) {
                senderUsernames.add(message.getSender().getUsername());
            }

            return senderUsernames;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
