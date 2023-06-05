package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Messages")
public class Message {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String content;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private User sender;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private User receiver;

    @DatabaseField(canBeNull = false)
    private boolean isRead;

    @DatabaseField(canBeNull = false)
    private Date date;

    public Message() {
    }

    public Message(String content, User sender, User receiver, boolean isRead, Date date) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.isRead = isRead;
        this.date = date;
    }

    // Getters and setters
    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Object getDate() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
