package Persistence;

import Model.Message;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.util.ArrayList;
import java.util.List;

public class MessageContext<T> extends DbContext<T> {
    MessageContext(JdbcPooledConnectionSource connectionSource) {
        super((Class<T>) Message.class, connectionSource);
    }

    public List<String> getConversations(int recieverId) {
        try{
            // get Senders without duplicates
            List<Message> SentMessages = (List<Message>) context.queryBuilder().
                    groupBy("sender_id").
                    where().
                    eq("Receiver_id", recieverId).
                    query();

            List<Message> RecievedMessages = (List<Message>) context.queryBuilder().
                    groupBy("receiver_id").
                    where().
                    eq("Sender_id", recieverId).
                    query();

            // initialize senders list and add senders to it
            List<String> senderUsernames = new ArrayList<>();
            for (Message message : SentMessages) {
                senderUsernames.add(message.getSender().getUsername());
            }

            for (Message message : RecievedMessages) {
                // check if sender is already added
                if(!senderUsernames.contains(message.getReceiver().getUsername()))
                    senderUsernames.add(message.getReceiver().getUsername());
            }

            return senderUsernames;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Message> getMessages(int senderId, int recieverId) {
        try {
            var qb = context.queryBuilder();
            var where = qb.where();
            return (List<Message>) where.or(
                    where.eq("Sender_id", senderId).and().eq("Receiver_id", recieverId),
                    where.eq("Sender_id", recieverId).and().eq("Receiver_id", senderId)
            ).query();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
