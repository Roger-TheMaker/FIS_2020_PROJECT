package Interface;

import Encryption.MD5;
import SQLite.Select;

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
        char[] password = PasswordTextField.getPassword();
        final String password_hashed = MD5.getMd5(String.valueOf(password));


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
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String checkUsernameEmail = "";
                String checkPassword = "";

                String sql_check_username_email = "SELECT CASE WHEN EXISTS (" +
                        "SELECT email " +
                        "FROM REGISTRATION " +
                        "WHERE email = '" + UsernameEmailTextField.getText() + "'" +
                        ") " +
                        "THEN CAST(1 AS BIT) " +
                        "ELSE CAST(0 AS BIT) END ";

                String sql_check_password = "SELECT CASE WHEN EXISTS (" +
                        "SELECT email " +
                        "FROM REGISTRATION " +
                        "WHERE email = '" + password_hashed + "'" +
                        ") " +
                        "THEN CAST(1 AS BIT) " +
                        "ELSE CAST(0 AS BIT) END ";

                //update the values for existance checking
                checkUsernameEmail = Select.CheckEntry("test.db",sql_check_username_email);
                checkPassword = Select.CheckEntry("test.db",sql_check_password);

                if(checkUsernameEmail.equals("0") || checkPassword.equals("0"))
                    UsernameEmailTextField.setText("INVALID USERNAME OR PASSWORD");


            }
        });
        RegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrationPage.Registration();
            }
        });
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void Login() {
        LoginPage dialog = new LoginPage();
        dialog.pack();
        dialog.setTitle("Login");
        dialog.setVisible(true);
        System.exit(0);
    }
}
