import Model.User;
import Persistence.DB;
import View.Login;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DB db = new DB();

        FlatLaf theme = new FlatArcDarkContrastIJTheme();
        FlatLaf.setup(theme);

        new Login(db);

    }
}