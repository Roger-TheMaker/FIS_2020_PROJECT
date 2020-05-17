package Interface;

import Encryption.MD5;
import SQLite.CreateTable;
import SQLite.Select;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Type;

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


        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String checkUsernameEmail = "";
                String checkPassword = "";
                char[] password = PasswordTextField.getPassword();
                String password_hashed = MD5.getMd5(String.valueOf(password));



                String tableContent = "id integer PRIMARY KEY, FirstName text NOT NULL, LastName text NOT NULL, " +
                        "Email text NOT NULL, Username text NOT NULL, Password text NOT NULL ";
                CreateTable.CreateTable("test.db","REGISTRATION",tableContent);


                String sql_check_usernameEmail = "SELECT * FROM REGISTRATION WHERE Username = " + "\'" + UsernameEmailTextField.getText() + "\'";
                String sql_check_password = "SELECT * FROM REGISTRATION WHERE Password = " + "\'" + password_hashed + "\'";

                //update the values for existance checking
                checkUsernameEmail = Select.CheckEntry("test.db",sql_check_usernameEmail);
                checkPassword = Select.CheckEntry("test.db",sql_check_password);

                if(checkUsernameEmail.equals("0") || checkPassword.equals("0"))
                    UsernameEmailTextField.setText("INVALID USERNAME OR PASSWORD");



            }
        });
        RegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrationPage registrationPage = new RegistrationPage();
                registrationPage.Registration();
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
        dialog.setSize(200,200);
        dialog.setVisible(true);
        System.exit(0);
    }
}
