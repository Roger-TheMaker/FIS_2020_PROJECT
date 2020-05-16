package Interface;

import javax.swing.*;
import java.awt.event.*;

public class LoginPage extends JDialog {
    private JPanel contentPane;
    private JTextField UsernameEmailTextField;
    private JPasswordField PasswordTextField;
    private JButton LoginButton;
    private JButton RegisterButton;
    private JButton buttonOK;
    private JButton buttonCancel;

    public LoginPage() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        UsernameEmailTextField.setText("Username / Email");
        PasswordTextField.setText("password");



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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoginPage dialog = new LoginPage();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
