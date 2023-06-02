package Model;

public class Basic extends User{
    public Basic() {
    }

    public Basic(User user) {
        super(user);
    }

    public Basic(String Username, String Password)
    {
        super(Username, Password, false);
    }
}
