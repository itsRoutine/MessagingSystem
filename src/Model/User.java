package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Users")
public class User {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false, unique = true)
    private String Username;
    @DatabaseField(canBeNull = false)
    private String Password;

    @DatabaseField(canBeNull = false)
    private boolean isAdmin;

    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.Username = user.Username;
        this.Password = user.Password;
        this.isAdmin = user.isAdmin;
    }

    public User(String Username, String Password, boolean isAdmin) {
        this.Username = Username;
        this.Password = Password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return Username;
    }
}
