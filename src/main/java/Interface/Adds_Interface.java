package Interface;

import Network.Client;
import Network.Server;
import SQLite.Select;

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

            }
        });
    }
    private void onOK() {
        dispose();
    }
    private void onCancel() {
        dispose();
    }


    private void printPost(String usrname, String post, String ipAddress) {
        if(usrname == null || post == null || ipAddress == null)
            return;

        JTextField PostTextField = new JTextField("");
        String help_message= post;
        String useername = usrname;
        PostTextField.setText(help_message);
        PostTextField.setPreferredSize( new Dimension( 320, 26) );

        JTextField usernameTextField =new JTextField("");
        usernameTextField.setPreferredSize( new Dimension( 100, 26) );
        usernameTextField.setText( UserService.user);


        JButton RespondButton = new JButton("Respond Announcement");
        RespondButton.setBounds(500, 500, 100, 20);

        JCheckBox checkBox= new JCheckBox("Urgency");

        //Implementation
        RespondButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server server = new Server();
                server.closeSocket();

                Client client = new Client();
                client.connect(ipAddress,55666);

                ChatInterface.ChatBox(client);
            }
        });
        posts.add(usernameTextField);
        posts.add(PostTextField);
        posts.add(RespondButton);
        posts.add(checkBox);

        posts.revalidate();;
        posts.validate();

        RowID++;
    }

    public static void Display() {
        Adds_Interface dialog = new Adds_Interface();
        dialog.pack();
        dialog.setTitle("Announcements");
        dialog.setSize(800,600);
        dialog.setVisible(true);
    }


}
