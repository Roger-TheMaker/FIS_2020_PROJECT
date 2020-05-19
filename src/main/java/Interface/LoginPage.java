package Interface;

import Encryption.MD5;
import SQLite.CreateTable;
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
                String checkUsername = "";
                String checkPassword = "";
                String checkEmail = "";
                char[] password = PasswordTextField.getPassword();
                String password_hashed = MD5.getMd5(String.valueOf(password));



                String tableContent = "id integer PRIMARY KEY, FirstName text NOT NULL, LastName text NOT NULL, " +
                        "Email text NOT NULL, Username text NOT NULL, Password text NOT NULL ";
                CreateTable.CreateTable("test.db","REGISTRATION",tableContent);


                String sql_check_username = "SELECT * FROM REGISTRATION WHERE Username = " + "\'" + UsernameEmailTextField.getText() + "\'";
                String sql_check_email = "SELECT * FROM REGISTRATION WHERE Email = " + "\'" + UsernameEmailTextField.getText() + "\'";
                String sql_check_password = "SELECT * FROM REGISTRATION WHERE Password = " + "\'" + password_hashed + "\'";

                //update the values for existance checking
                checkUsername = Select.CheckEntry("test.db",sql_check_username);
                checkPassword = Select.CheckEntry("test.db",sql_check_password);
                checkEmail = Select.CheckEntry("test.db",sql_check_email);

                if((checkEmail.equals("1") || checkUsername.equals("1")) && checkPassword.equals("1")  ) {

                    System.out.println("Login successful");
                    Volunteer_Page.Show();

                }
                else
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
    }
}
