import Persistence.DB;
import View.Login;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerContrastIJTheme;

public class Main {
    public static void main(String[] args) {
        DB db = new DB();

        FlatLaf theme = new FlatMaterialDarkerContrastIJTheme();
        FlatLaf.setup(theme);

        new Login(db);
    }
}