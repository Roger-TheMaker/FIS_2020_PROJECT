package Interface;

import Encryption.MD5;
import Network.Server;
import SQLite.CreateTable;
import SQLite.Select;

import javax.security.auth.SubjectDomainCombiner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JDialog {
    private JPanel contentPane = new JPanel();
    private JTextField UsernameEmailTextField;
    private JPasswordField PasswordTextField;
    private JButton LoginButton;
    private JButton RegisterButton;
    private JPanel panel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private static int loginStatus = 0;

    public LoginPage() {
        //   $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        UsernameEmailTextField.setText("Username / Email");
        PasswordTextField.setText("password");


        // call onCancel()
        // when cross is clicked
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
                CreateTable.CreateTable("test.db", "REGISTRATION", tableContent);


                String sql_check_username = "SELECT * FROM REGISTRATION WHERE Username = " + "\'" + UsernameEmailTextField.getText() + "\'";
                String sql_check_email = "SELECT * FROM REGISTRATION WHERE Email = " + "\'" + UsernameEmailTextField.getText() + "\'";
                String sql_check_password = "SELECT * FROM REGISTRATION WHERE Password = " + "\'" + password_hashed + "\'";

                //update the values for existance checking
                checkUsername = Select.CheckEntry("test.db", sql_check_username);
                checkPassword = Select.CheckEntry("test.db", sql_check_password);
                checkEmail = Select.CheckEntry("test.db", sql_check_email);

                if ((checkEmail.equals("1") || checkUsername.equals("1")) && checkPassword.equals("1")) {

                    loginStatus = 1;
                    UserService.user = UsernameEmailTextField.getText();
                    onCancel();
                } else {
                    UsernameEmailTextField.setText("INVALID USERNAME OR PASSWORD");
                }


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

    public static int getLoginStatus() {
        return loginStatus;
    }


    public static void Login() {
        LoginPage dialog = new LoginPage();
        dialog.pack();
        dialog.setTitle("Login");
        dialog.setSize(200, 200);
        dialog.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        RegisterButton = new JButton();
        RegisterButton.setText("Register");
        panel.add(RegisterButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PasswordTextField = new JPasswordField();
        panel.add(PasswordTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        UsernameEmailTextField = new JTextField();
        panel.add(UsernameEmailTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        LoginButton = new JButton();
        LoginButton.setText("Login");
        panel.add(LoginButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
