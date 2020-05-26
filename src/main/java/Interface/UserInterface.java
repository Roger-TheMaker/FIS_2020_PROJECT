package Interface;

import Network.Client;
import Network.GetMyIPLocal;
import Network.Server;
import SQLite.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JDialog {

    private JPanel contentPane;
    private JPanel buttons_Panel;
    private JPanel posts_Panel;
    private JButton AddButton;
    private JTextField welcomeSummonerTextField;
    private JTextField PostTextField;
    private JButton deleteButton;
    private JTextField DeleteTextField;
    private JButton updateAnnouncemetsButton;
    private JButton activateUrgencyModeButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    private int RowID = 1;

    public UserInterface(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        posts_Panel.setLayout(new FlowLayout());
        PostTextField.setText("Write Something...");
        DeleteTextField.setText("Delete Something...");

        String tableContent = "id integer PRIMARY KEY, USERNAME text NOT NULL, HELP_MESSAGE text NOT NULL, IP_ADDRESS text NOT NULL ";
        CreateTable.CreateTable("test.db","POSTS",tableContent);


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

        AddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            addPost();

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String text = DeleteTextField.getText();
                Delete.delete("test.db", text);
                System.out.println(text+ " deleted");
            }
        });


        updateAnnouncemetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Adds_Interface.Display();

            }
        });

    }

    private void onOK() {
        dispose();
    }
    private void onCancel() {
        dispose();
    }

    private void addPost() {
        String IPAddress = GetMyIPLocal.getMyIPLocal();
        String post =  PostTextField.getText();
        String parameterList = "USERNAME, HELP_MESSAGE, IP_ADDRESS";
        String valueList = "\'" + UserService.user + "\', " + "\'" + post + "\', " + "\'" + IPAddress + "\'";

        System.out.println(valueList);

        Insert.Insert("test.db","POSTS",parameterList,valueList);

    }

    private void printPost(String usrname, String post, String ipAddress) {
        if(usrname == null || post == null || ipAddress == null)
            return;

        JTextField PostTextField = new JTextField("");
        String help_message= post; //HELP_MESSAGE COLLUMN
        String username = usrname; //USERNAME COLLUMN
        PostTextField.setText(help_message);
        PostTextField.setPreferredSize( new Dimension( 320, 26) );

        JTextField usernameTextField =new JTextField("");
        usernameTextField.setPreferredSize( new Dimension( 80, 26) );
        usernameTextField.setText( UserService.user);


        JButton RespondButton = new JButton("Respond Announcement");
        RespondButton.setBounds(500, 500, 100, 20);

        JCheckBox checkbox=new JCheckBox("Urgency");

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

        posts_Panel.add(usernameTextField);
        posts_Panel.add(PostTextField);
        posts_Panel.add(RespondButton);

        posts_Panel.revalidate();;
        posts_Panel.validate();

        RowID++;
    }



    public static void UserInterface() {
        UserInterface p = new UserInterface();
        p.pack();
        p.setSize(800,600);
        p.setLocation(800,0);
        p.setVisible(true);
    }
}
