package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatInterface_V2 extends JDialog implements Runnable {
    private JPanel contentPane;
    private JButton SendButton;
    private JTextArea ConversationArea;
    private JTextField MessageTextField;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JButton buttonCancel;

    public ChatInterface_V2() {
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

            }
        });
    }

    public void setConversationMessage(String message) {
        ConversationArea.append(message + "\n");
    }

    public String getMessage() {
        if (MessageTextField.getText().equals("")) {
            return "";
        }
        return MessageTextField.getText();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public void run() {
        ChatInterface_V2 dialog = new ChatInterface_V2();
        dialog.pack();
        dialog.setVisible(true);
    }

}
