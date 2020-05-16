package Interface;

import javax.swing.*;
import java.awt.event.*;

public class RegistrationPage extends JDialog {
    private JPanel contentPane;
    private JTextField FirstNameTextField;
    private JTextField UsernameTextField;
    private JTextField LastNameTextField;
    private JTextField EmailTextField;
    private JPasswordField PassowordTextField;
    private JPasswordField PasswordConfirmationTextField;
    private JButton RegisterButton;
    private JButton buttonOK;
    private JButton buttonCancel;



    public RegistrationPage() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        FirstNameTextField.setText("First Name");
        LastNameTextField.setText("Last Name");
        EmailTextField.setText("Email");
        UsernameTextField.setText("Username");
        PassowordTextField.setText("password");
        PasswordConfirmationTextField.setText("password");




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
        RegistrationPage dialog = new RegistrationPage();
        dialog.pack();
        dialog.setTitle("Register Form");
        dialog.setVisible(true);
        System.exit(0);
    }
}
