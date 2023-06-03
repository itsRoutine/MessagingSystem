package View;

import Persistence.DB;

import javax.swing.*;

public class Login {
    private JPanel Login;
    private JTabbedPane tabbedPane;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;

    public Login(DB db) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
