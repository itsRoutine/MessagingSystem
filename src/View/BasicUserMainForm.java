package View;

import Model.Message;
import Model.User;
import Persistence.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class BasicUserMainForm {
    private JButton startNewChatButton;
    private JButton markAllAsReadButton;
    private JButton markSelectedAsReadButton;
    private JButton markSelectedAsUnredButton;
    private JPanel BasicUserMainForm;
    private JTabbedPane chats;
    private JTextField textField1;
    private JButton sendButton;

    public BasicUserMainForm(DB db, User user) {
        JFrame frame = new JFrame("BasicUserMainForm");
        frame.setContentPane(BasicUserMainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // add one tab for each sender
        List<String> senders = db.messages.getSendersUsername(user.getId());
        for (String sender : senders) {
            chats.addTab(sender,createChatTab(db, sender, user));
        }
    }

    // create a chat tab with table of messages
    private JTable createChatTab(DB db, String sender, User user) {
        // create table
        JTable table = new JTable();

        // create table model
        String[] columnNames = {"Date","Message", "Read","Sender",};

        User senderUser = db.users.getByUsername(sender);
        List<Message> messages = db.messages.getMessages(user.getId(), senderUser.getId());

        Object[][] data = new Object[messages.size()][4];
        for (int i = 0; i < messages.size(); i++) {
            data[i][0] = messages.get(i).getDate().toString().substring(0, 19).replace('T', ' ');
            data[i][1] = messages.get(i).getContent();
            data[i][2] = messages.get(i).isRead();
            data[i][3] = messages.get(i).getSender().getUsername();
        }

        // create table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                Class<?> returnValue;
                if(column == 2)
                    returnValue = Boolean.class;
                else if(column == 0)
                    returnValue = TextAreaRenderer.class;
                else
                    returnValue = Object.class;

                return returnValue;
            }
        };

        // set table model and render content cell as text area
        table.setModel(tableModel);
        table.getColumnModel().getColumn(1).setCellRenderer(new TextAreaRenderer());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        // set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(500);
        table.getColumnModel().getColumn(2).setPreferredWidth(35);
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setPreferredWidth(0);

        // set row heights
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setRowHeight(i, heightCalculator((String)table.getValueAt(i, 1)));
        }


        return table;
    }

    private int heightCalculator(String content) {
        // every 60 characters add 1 row
        int rows = content.length() / 60;
        if (content.length() % 60 != 0)
            rows++;
        return rows * 18;
    }

    // text area renderer
    private static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((String)value);
            if(isSelected)
                setBackground(table.getSelectionBackground());
            else
                setBackground(table.getBackground());
            return this;
        }
    }
}
