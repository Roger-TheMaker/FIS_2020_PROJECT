package Interface;

import Network.Client;

import javax.swing.*;
import java.awt.event.*;

public class ChatInterface_V2 extends JDialog {
    private JPanel contentPane;
    private JButton SendButton;
    private JTextArea ConversationArea;
    private JTextField MessageTextField;
    private JButton buttonCancel;

    public ChatInterface_V2(Client client) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(SendButton);



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
                String message = MessageTextField.getText();
                ConversationArea.append(message+"\n");
                System.out.println(message);
               // client.sendMessage("asd");
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void Chat(Client client) {
        ChatInterface_V2 dialog = new ChatInterface_V2(client);
        dialog.pack();
        dialog.setVisible(true);
    }
}
