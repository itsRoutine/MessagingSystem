package View;

import Model.User;
import Persistence.DB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel Login;
    private JTabbedPane tabbedPane;
    private JTextField loginTextField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;

    public Login(DB db) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginTextField.getText();
                String password = loginPasswordField.getText();
                User user = db.users.getByUsername(username);
                if (user != null && user.getPassword().equals(password)) {
                    // Todo: open the main form
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
    }
}