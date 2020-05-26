package Interface;

import SQLite.Select;
import SQLite.Update;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Adds_Interface extends JDialog{
    private JPanel contentPanel;
    private JPanel posts;
    private JButton update_Button;
    private JButton buttonOK;
    private int RowID = 1;

    public Adds_Interface(){
        setContentPane(contentPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        posts.setLayout(new FlowLayout());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    //Row[0] = username
                    //Row[1] = post
                    //Row[2] = ip
                    String Row[] = Select.getRow("test.db", "SELECT * FROM POSTS WHERE id = " + String.valueOf(RowID));
                    printPost(Row[0], Row[1], Row[2]);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        update_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Update.update("test.db");
                System.out.println("Posts Updated\n");
            }
        });
    }


    private void onOK() {
        dispose();
    }
    private void onCancel() {
        dispose();
    }


    private void printPost(String username, String post, String ipAddress) {
        if(username == null || post == null || ipAddress == null)
            return;


        JTextField PostTextField = new JTextField("");
        String help_message= post;
        String useername = username;
        PostTextField.setText(help_message);
        PostTextField.setPreferredSize( new Dimension( 320, 26) );

        JTextField usernameTextField =new JTextField("");
        usernameTextField.setPreferredSize( new Dimension( 100, 26) );
        usernameTextField.setText( UserService.user);


        JCheckBox checkBox= new JCheckBox("Urgency");

        /*
        if(checkBox.isSelected()) {
            System.out.println("Hei");
            PostTextField.setText("Hello");
        }

        Font f = null;
        f=new Font("Serif",Font.BOLD + Font.ITALIC, 14);
        if(checkBox.isSelected())
            PostTextField.setFont(f);
        */

        posts.add(usernameTextField);
        posts.add(PostTextField);
        posts.add(checkBox);

        posts.revalidate();;
        posts.validate();

        RowID++;
    }

    public static void Display() {
        Adds_Interface dialog = new Adds_Interface();
        dialog.pack();
        dialog.setTitle("Announcements");
        dialog.setSize(600,600);
        dialog.setVisible(true);
    }


}
