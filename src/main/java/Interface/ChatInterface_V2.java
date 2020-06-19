package Interface;

import Network.Client;
import Network.Server;

import javax.swing.*;
import java.awt.event.*;

public class ChatInterface_V2 extends JDialog {
    private JPanel contentPane;
    private JButton SendButton;
    private JTextArea ConversationArea;
    private JTextField MessageTextField;
    private JButton buttonCancel;

    // 0 - server , 1 - client
    private static boolean client_or_server = false;

    public ChatInterface_V2() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(SendButton);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (true) {
                    if (client_or_server == false) {
                        message = Server.receiveMessage();

                        if (!message.equals("")) {
                            ConversationArea.append(message + "\n");
                        }
                    } else {
                        message = Client.receiveMessage();
                        if (!message.equals("")) {
                            ConversationArea.append(message + "\n");
                        }
                    }
                }
            }
        });
        thread1.start();

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage(MessageTextField.getText());
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void setClient_or_server(boolean value) {
        client_or_server = value;
    }
    public static void Chat() {
        ChatInterface_V2 dialog = new ChatInterface_V2();
        dialog.pack();
        dialog.setVisible(true);
    }
}
