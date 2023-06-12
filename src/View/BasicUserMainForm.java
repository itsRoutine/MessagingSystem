package View;

import Model.Message;
import Model.User;
import Persistence.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BasicUserMainForm {
    private JButton startNewChatButton;
    private JPanel BasicUserMainForm;
    private JTabbedPane chats;
    private JTextField chatField;
    private JButton sendButton;
    private JList unreadMessagesBadge;
    private JButton messageToAllButton;
    private JButton viewAllMessagesButton;

    private final List<JTable> tables = new ArrayList<>();
    public BasicUserMainForm(DB db, User user) {

        // Hide message to all button if user is not admin
        messageToAllButton.setVisible(user.isAdmin());
        viewAllMessagesButton.setVisible(user.isAdmin());

        JFrame frame = new JFrame("BasicUserMainForm");
        frame.setContentPane(BasicUserMainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // fix frame size
        frame.setMinimumSize(new Dimension(800 , 500));

        // add one tab for each sender
        List<String> senders = db.messages.getConversations(user.getId());
        for (String sender : senders) {
            chats.addTab(sender,createChatTab(db, sender, user));
        }

        // set unread messages badge
        setUnreadMessagesBadge(db, user);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chats.getSelectedIndex() != -1) {
                    String sender = chats.getTitleAt(chats.getSelectedIndex());
                    User senderUser = db.users.getByUsername(sender);
                    Message msg = new Message(chatField.getText(), user, senderUser, false, new java.util.Date());
                    db.messages.create(msg);
                    chats.setComponentAt(chats.getSelectedIndex(), createChatTab(db, sender, user));
                    chatField.setText("");
                }
            }
        });
        // add focus listener to for each table
        for (JTable table : tables) {
            table.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    // mark all messages for the selected sender as read if it's not sent by the user
                    String sender = chats.getTitleAt(chats.getSelectedIndex());
                    User senderUser = db.users.getByUsername(sender);
                    db.messages.getMessages(user.getId(), senderUser.getId()).forEach(message -> {
                        if(!message.isRead() && !message.getSender().getUsername().equals(user.getUsername())) {
                            message.setRead(true);
                            db.messages.update(message);
                        }
                    });
                    chats.setComponentAt(chats.getSelectedIndex(), createChatTab(db, sender, user));
                    setUnreadMessagesBadge(db, user);
                }
            });
        }
        startNewChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get new chat username in a dialog
                String username = JOptionPane.showInputDialog("Enter username");
                if(username != null) {
                    User receiver = db.users.getByUsername(username);
                    if(receiver != null) {
                        // create new chat tab
                        chats.addTab(username, createChatTab(db, username, user));
                        // set new chat tab as selected
                        chats.setSelectedIndex(chats.getTabCount() - 1);
                    }
                    else
                        JOptionPane.showMessageDialog(null, "User not found");
                }
            }
        });
        messageToAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get message in a dialog
                String message = JOptionPane.showInputDialog("Enter message");
                if(message != null) {
                    // send message to all users
                    db.users.getAll().forEach(user1 -> {
                        if(!user1.getUsername().equals(user.getUsername())) {
                            Message msg = new Message(message, user, user1, false, new java.util.Date());
                            db.messages.create(msg);
                        }
                    });

                    // update all chats tab
                    for (int i = 0; i < chats.getTabCount(); i++) {
                        chats.setComponentAt(i, createChatTab(db, chats.getTitleAt(i), user));
                    }
                }
            }
        });
        viewAllMessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MessagesTable(db);
            }
        });
    }

    // create a chat tab with table of messages
    private JScrollPane createChatTab(DB db, String sender, User user) {

        // create JScrollPane
        JScrollPane scrollPane = new JScrollPane();

        // create table and add it to scroll pane
        JTable table = new JTable();
        scrollPane.setViewportView(table);

        // create table model
        String[] columnNames = {"Date","Message", "Read","Sender",};

        User senderUser = db.users.getByUsername(sender);
        List<Message> messages = db.messages.getMessages(user.getId(), senderUser.getId());

        // fill table data in reverse order
        Object[][] data = new Object[messages.size()][4];
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = messages.get(i);
            // add date in format "dd/MM/yyyy \n HH:mm:ss"
            data[messages.size() - i - 1][0] = new SimpleDateFormat("dd/MM/yyyy \n HH:mm:ss").format(message.getDate());
            data[messages.size() - i - 1][1] = message.getContent();
            data[messages.size() - i - 1][2] = message.isRead();
            data[messages.size() - i - 1][3] = message.getSender().getUsername();
        }

        // create table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // only read column is editable if sender is not the user
                return column == 2 && !data[row][3].equals(user.getUsername());
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
        table.setShowHorizontalLines(false);
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
            table.setRowHeight(i, chatHeightCalculator((String)table.getValueAt(i, 1)));
        }

        // add table to list of tables
        tables.add(table);

        // return scroll pane
        return scrollPane;
    }

    private int chatHeightCalculator(String content) {
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

    private void setUnreadMessagesBadge(DB db, User user) {
        List<String> senders = db.messages.getConversations(user.getId());
        List<Message> messages = new ArrayList<>();
        for (String sender : senders) {
            User senderUser = db.users.getByUsername(sender);
            messages.addAll(db.messages.getMessages(user.getId(), senderUser.getId()));
        }
        // add new unreadMessages count for each sender
        var data = new String[senders.size()];
        for(String sender : senders) {
            int unreadMessages = 0;
            for (Message message : messages) {
                if(message.getSender().getUsername().equals(sender) && !message.isRead())
                    unreadMessages++;
            }
            if(unreadMessages != 0)
                data[senders.indexOf(sender)] = sender + " (" + unreadMessages + ")";
        }
        unreadMessagesBadge.setListData(data);
    }
}
