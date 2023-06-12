package View;

import Persistence.DB;

import javax.swing.*;

public class MessagesTable {
    private JTable messagesTable;
    private JPanel messagesPane;

    public MessagesTable(DB db) {
        JFrame frame = new JFrame("MessagesTable");
        frame.setContentPane(messagesPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        createMessagesTable(db);
    }

    private void createMessagesTable(DB db) {
        messagesTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Sender", "Receiver", "Message", "Date"
                }
        ));

        var messages = db.messages.getAll();

        for (var message : messages) {
            ((javax.swing.table.DefaultTableModel) messagesTable.getModel()).addRow(new Object[]{
                    message.getSender().getUsername(),
                    message.getReceiver().getUsername(),
                    message.getContent(),
                    message.getDate()
            });
        }

        messagesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        messagesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        messagesTable.getColumnModel().getColumn(2).setPreferredWidth(1200);
        messagesTable.getColumnModel().getColumn(3).setPreferredWidth(300);
    }
}
