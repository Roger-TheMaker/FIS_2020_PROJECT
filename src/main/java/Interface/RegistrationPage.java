package Interface;

import Encryption.MD5;
import SQLite.CreateTable;
import SQLite.Insert;
import SQLite.Select;

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

        RegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //variables are for checking if the email/username already exists
                //the SQL statement will return "1" the e/u already exists
                String checkgetEmail = "1";
                String checkgetUsername = "1";

                String username = UsernameTextField.getText();
                String email = EmailTextField.getText();
                String firstname = FirstNameTextField.getText();
                String lastname = LastNameTextField.getText();
                char[] password = PassowordTextField.getPassword();
                String password_hashed = MD5.getMd5(String.valueOf(password));

                String tableContent = "id integer PRIMARY KEY, FirstName text NOT NULL, LastName text NOT NULL, " +
                        "Email text NOT NULL, Username text NOT NULL, Password text NOT NULL ";
                CreateTable.CreateTable("test.db","REGISTRATION",tableContent);


                String sql_email_check = "SELECT CASE WHEN EXISTS (" +
                        "SELECT email " +
                        "FROM REGISTRATION " +
                        "WHERE email = '" + email + "'" +
                        ") " +
                        "THEN CAST(1 AS BIT) " +
                        "ELSE CAST(0 AS BIT) END ";

                String sql_username_check = "SELECT CASE WHEN EXISTS (" +
                        "SELECT username " +
                        "FROM REGISTRATION " +
                        "WHERE username = '" + username + "'" +
                        ") " +
                        "THEN CAST(1 AS BIT) " +
                        "ELSE CAST(0 AS BIT) END ";

                //update the values for existance checking
                checkgetEmail = Select.CheckEntry("test.db",sql_email_check);
                checkgetUsername = Select.CheckEntry("test.db",sql_username_check);


                String parameterList = "FirstName, LastName, Email, Username, Password";

                String valueList = "" + FirstNameTextField +", "+ LastNameTextField+", "+EmailTextField+", " +
                                        UsernameTextField+", " + password_hashed;

                if(checkgetEmail.equals("0") && checkgetUsername.equals("0")) {
                    Insert.Insert("test.db","REGISTRATION",parameterList,valueList);
                    FirstNameTextField.setText("ACCOUNT CREATED");
                }
                else {
                    if(checkgetEmail.equals("1") && checkgetUsername.equals("1")) {
                        EmailTextField.setText("Email already exist");
                        UsernameTextField.setText("Username already exists");
                    }
                    else if(checkgetEmail.equals("1")) {
                        EmailTextField.setText("Email already exists");
                    }
                    else if(checkgetUsername.equals("1")) {
                        UsernameTextField.setText("Username already exists");
                    }
                }
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

    public static void Registration() {
        RegistrationPage dialog = new RegistrationPage();
        dialog.pack();
        dialog.setTitle("Register Form");
        dialog.setVisible(true);
        System.exit(0);
    }
}
