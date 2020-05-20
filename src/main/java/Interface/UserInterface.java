package Interface;

import SQLite.CreateTable;
import SQLite.Insert;
import SQLite.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JDialog {

    private JPanel contentPane;
    private JPanel buttons_Panel;
    private JPanel posts_Panel;
    private JButton AddButton;
    private JButton V_Button;
    private JTextField welcomeSummonerTextField;

    private JTextField add_Field;

    private JButton buttonOK;
    private JButton buttonCancel;


    public UserInterface(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        contentPane =new JPanel();

        posts_Panel.setLayout(new FlowLayout());


        add_Field.setText("Write");


        V_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        AddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String checkgetName = "1";

                //welcomeSummonerTextField.setText("Welcome Summoner");

                JTextField text = new JTextField("");
                String help_message=add_Field.getText();
                text.setText(help_message);
                text.setPreferredSize( new Dimension( 320, 26) );


                JTextField username =new JTextField("");
                username.setPreferredSize( new Dimension( 80, 26) );
                String name =UserService.user;
                username.setText(name);



                String tableContent = "id integer PRIMARY KEY, USERNAME text NOT NULL, HELP_MESSAGE text NOT NULL ";

                CreateTable.CreateTable("test.db","POSTS",tableContent);


                String sql_check_name = "SELECT * FROM POSTS WHERE USERNAME = " + "\'" + name + "\'";

                checkgetName = Select.CheckEntry("test.db",sql_check_name);


                String parameterList = "USERNAME, HELP_MESSAGE";
                String valueList = "\'" + name + "\'" +", "+ "\'" + help_message + "\'";

                System.out.println(valueList);


                if(checkgetName.equals("0")) {
                    Insert.Insert("test.db","POSTS",parameterList,valueList);
                }


                JButton b = new JButton("Respond Post");
                b.setBounds(500, 500, 100, 20);

                posts_Panel.add(username);
                posts_Panel.add(text);
                posts_Panel.add(b);

                posts_Panel.revalidate();;
                posts_Panel.validate();

            }
        });
    }

    private void onOK() {
        dispose();
    }
    private void onCancel() {
        dispose();
    }

    public static void UserInterface() {
        //maybe static, maybe not
        UserInterface p = new UserInterface();
        p.pack();
        p.setSize(800,600);
        p.setLocation(600,0);
        p.setVisible(true);
    }
}
