package Interface;

import Network.Client;
import Network.Generic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatInterface extends JDialog {
    private JPanel contentPane;
    private JButton SendButton;
    private JCheckBox SetSolvedCheckBox;
    private JTextField MessageTextField;
    private JTextArea ChatBox;
    private JButton buttonOK;
    private JButton buttonCancel;

    public <T extends Generic> ChatInterface(final T ConnectionOption) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                String message = ConnectionOption.receiveMessage();
                while(!message.equals("")) {
                    ChatBox.append(message);
                    message = ConnectionOption.receiveMessage();
                }}
        });
        thread.start();

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectionOption.sendMessage("NAME has left the chat");
                ConnectionOption.closeConnection();
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        SendButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String message = MessageTextField.getText();
                ChatBox.append( message + "\n");
                ConnectionOption.sendMessage(message);
                MessageTextField.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ConnectionOption.sendMessage("NAME has left the chat");
                ConnectionOption.closeConnection();
            }

        });

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static <T extends Generic> void ChatBox(T ConnectionOption) {
        ChatInterface dialog = new ChatInterface(ConnectionOption);
        dialog.pack();
        dialog.setVisible(true);
    }
}
