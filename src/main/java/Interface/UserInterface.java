package Interface;

import Network.*;
import SQLite.CreateTable;
import SQLite.Delete;
import SQLite.Select;
import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class UserInterface extends JDialog {

    private JPanel contentPane;
    private JPanel buttons_Panel;
    private JPanel posts_Panel;
    private JButton AddButton;
    private JButton U_Button;
    private JTextField welcomeSummonerTextField;
    private JTextField PostTextField;
    private JScrollPane PostBox;
    private JButton deletePostButton;
    private JButton refreshButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    public static int RowID = 1;
    private int set = 0;
    private boolean server_status = true;  // 0 - off, 1 - on
    private static Semaphore semaphore = new Semaphore(1);
    private ArrayList<Triplet<JTextField,JTextField,JButton>> post = new ArrayList<Triplet<JTextField,JTextField,JButton>>();

    public UserInterface(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        contentPane =new JPanel();
        posts_Panel.setLayout(new FlowLayout());
        PostTextField.setText("Write Something...");

        String tableContent = "id integer PRIMARY KEY, USERNAME text NOT NULL, HELP_MESSAGE text NOT NULL, IP_ADDRESS text NOT NULL,UNIQUE(HELP_MESSAGE)";
        CreateTable.CreateTable("test.db","POSTS",tableContent);

        Thread thread_server = new Thread(new Runnable() {
            @Override
            public void run() {
                if(server_status == false) {
                    Server.closeSocket();
                } else {
                    Server.run();
            }}
        });
        thread_server.start();

        Thread thread_update_interface = new Thread(new Runnable() {
            @Override
            public void run() {
                long threadId = Thread.currentThread().getId();
                System.out.println(threadId);

                while(true) {
                    //Row[0] = username
                    //Row[1] = post
                    //Row[2] = ip
                    if(RowID == 1) {
                        if(BroadcastServer.message != null && set == 1) {
                            deletePost(BroadcastServer.message);
                            set = 0;
                        }
                    }
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
        thread_update_interface.start();

        U_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("In a future implementation ");
            }
        });
        AddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "MESSAGE"+addPost(UserService.user);
                BroadcastClient.run(message);


            }
        });
        deletePostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set = 1;
                BroadcastClient.run("DELETE"+UserService.user);

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setServerStatus(boolean value) {
        server_status = value;
    }

    private void onOK() {
        dispose();
    }
    private void onCancel() {
        dispose();
    }

    private String addPost(String Username) {
        String IPAddress = GetMyIPLocal.getMyIPLocal();
        String post =  PostTextField.getText();
        String parameterList = "USERNAME, HELP_MESSAGE, IP_ADDRESS";
        String valueList = "\'" + Username + "\', " + "\'" + post + "\', " + "\'" + IPAddress + "\'";

        System.out.println(valueList);

        //   Insert.Insert("test.db","POSTS",parameterList,valueList);

        return valueList;

    }

    private void removeComponent(Triplet<JTextField,JTextField,JButton> components) {
        for(Object obj : components) {
            posts_Panel.remove((Component) obj);
        }
    }

    public void deletePost(String user) {
        String sql_command = "DELETE FROM POSTS WHERE USERNAME = " + "\'"+ user +"\'";
        Delete.DeleteEntry("test.db","POSTS",sql_command);
        System.out.println("POST DELETED");

        Component[] componentList = posts_Panel.getComponents();

//Loop through the components
        for(int i = 0; i < post.size(); i++) {
            for(Object obj : post.get(i)) {
                if(obj instanceof JTextField && ((JTextField) obj).getText().equals(UserService.user)) {
                    removeComponent(post.get(i));
                    break;
                }

            }

        }

//IMPORTANT
        posts_Panel.revalidate();
        posts_Panel.repaint();
        RowID = 1;

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
        usernameTextField.setText( username);


        JButton RespondButton = new JButton("Respond Post");
        this.post.add(Triplet.with(usernameTextField,PostTextField,RespondButton));


        RespondButton.setBounds(500, 500, 100, 20);
        RespondButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(GetMyIPLocal.getMyIPLocal());
                System.out.println(ipAddress);

               server_status = false;

                System.out.println(ipAddress);

                //change back to ipAddress
                Client.connect("192.168.43.237", 55666);
                ChatInterface_V2.setClient_or_server(true);
                ChatInterface_V2.Chat();

            }
        });

        posts_Panel.add(usernameTextField);
        posts_Panel.add(PostTextField);
        posts_Panel.add(RespondButton);

        posts_Panel.revalidate();;
        posts_Panel.validate();

        RowID++;

        return;
    }

    public static void UserInterface() {
        UserInterface p = new UserInterface();
        p.pack();
        p.setSize(800,600);
        p.setLocation(600,0);
        p.setVisible(true);
    }
}
