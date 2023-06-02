package Model;

public class Admin extends User{
    public Admin() {
    }

    public Admin(User user) {
        super(user);
    }

    public Admin(String Username, String Password)
    {
        super(Username, Password, true);
    }

}
