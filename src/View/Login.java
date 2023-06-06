package View;

import Model.User;
import Persistence.DB;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanContrastIJTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Login {
    private JPanel Login;
    private JTabbedPane tabbedPane;
    private JTextField loginTextField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JTextField registerTextField;
    private JPasswordField registerPasswordField;
    private JButton registerButton;
    private JComboBox themes;

    public Login(DB db) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // set combobox values
        setupComboBox();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginTextField.getText();
                String password = loginPasswordField.getText();
                User user = db.users.getByUsername(username);
                if (user != null && user.getPassword().equals(password)) {
                    new BasicUserMainForm(db, user);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registerTextField.getText();
                String password = registerPasswordField.getText();
                User user = db.users.getByUsername(username);
                if (user == null) {
                    db.users.create(new User(username, password, false));
                    registerTextField.setText("");
                    registerPasswordField.setText("");
                    tabbedPane.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists");
                }
            }
        });
        themes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String theme = (String) themes.getSelectedItem();
                assert theme != null;
                switch (theme) {
                    case "Dark" -> setTheme(new FlatMaterialDarkerContrastIJTheme());
                    case "Atom Dark" -> setTheme(new FlatAtomOneDarkIJTheme());
                    case "Light" -> setTheme(new FlatLightLaf());
                    case "Dracula" -> setTheme(new FlatDraculaIJTheme());
                    case "Arc Dark Contrast" -> setTheme(new FlatArcDarkContrastIJTheme());
                    case "Material Deep Ocean" -> setTheme(new FlatMaterialDeepOceanContrastIJTheme());
                }
            }
        });
    }

    void setupComboBox() {
        // set combobox values
        String[] themes = {"Dark", "Atom Dark", "Light", "Dracula", "Arc Dark Contrast", "Material Deep Ocean"};
        for (String theme : themes) {
            this.themes.addItem(theme);
        }
    }

    private void setTheme(FlatLaf theme) {
        FlatLaf.setup(theme);
        FlatLaf.updateUI();
    }
}
