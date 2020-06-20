package Interface;

import Network.*;
import SQLite.CreateTable;
import SQLite.Delete;
import SQLite.Select;
import com.sun.rowset.internal.Row;
import org.javatuples.Triplet;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
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
    private static Semaphore semaphore = new Semaphore(1);
    private static ArrayList<Triplet<JTextField, JTextField, JButton>> post = new ArrayList<Triplet<JTextField, JTextField, JButton>>();

    public UserInterface() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        contentPane = new JPanel();
        posts_Panel.setLayout(new FlowLayout());
        PostTextField.setText("Write Something...");


        String tableContent = "id integer PRIMARY KEY, USERNAME text NOT NULL, HELP_MESSAGE text NOT NULL, IP_ADDRESS text NOT NULL,PORT_NUMBER text NOT NULL,UNIQUE(HELP_MESSAGE)";
        CreateTable.CreateTable("test.db", "POSTS", tableContent);

        System.out.println("SERVER IS STARTING...");
        Thread thread_server = new Thread(new Runnable() {
            public void run() {
                try {
                    Server.startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_server.start();
        System.out.println("SERVER STARTED...");
        Thread thread_update_interface = new Thread(new Runnable() {
            @Override
            public void run() {
                //RowID = 1;
                while (true) {
                    //Row[0] = username
                    //Row[1] = post
                    //Row[2] = ip
                    //Row[3] = port

                    if (RowID == 1) {
                        if (BroadcastServer.message != null && set == 1) {
                            set = 0;
                            deletePost(BroadcastServer.message);
                        }


                    }


                    String[] Row = new String[4];
                    String[] check = new String[4];

                    while ((Row = Select.getRow("test.db", "SELECT * FROM POSTS WHERE id = " + String.valueOf(RowID)))[0] != null) {
                        printPost(Row[0], Row[1], Row[2], Row[3]);
                        RowID++;
                    }

                    //System.out.println(RowID);

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
                String message = "MESSAGE" + addPost(UserService.user);
                BroadcastClient.run(message);


            }
        });
        deletePostButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                BroadcastClient.run("DELETE" + UserService.user);
                set = 1;

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                posts_Panel.repaint();
                posts_Panel.revalidate();
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

    private String addPost(String Username) {

        String IPAddress = GetMyIPLocal.getMyIPLocal();
        String post = PostTextField.getText();
        String parameterList = "USERNAME, HELP_MESSAGE, IP_ADDRESS, PORT_NUMBER";
        String valueList = "\'" + Username + "\', " + "\'" + post + "\', " + "\'" + IPAddress + "\', '" + String.valueOf(UserService.port_number) + "\'";

        System.out.println(valueList);

        //   Insert.Insert("test.db","POSTS",parameterList,valueList);
        return valueList;

    }

    private void removeComponent(Triplet<JTextField, JTextField, JButton> components) {
        for (Object obj : components) {
            posts_Panel.remove((Component) obj);
        }
    }

    public void deletePost(String user) {
        //   String sql_command = "DELETE FROM POSTS WHERE USERNAME = " + "\'" + user + "\'";
        //    Delete.DeleteEntry("test.db", "POSTS", sql_command);
        //  System.out.println("POST DELETED");

        Component[] componentList = posts_Panel.getComponents();

//Loop through the components
        for (int i = 0; i < post.size(); i++) {
            for (Object obj : post.get(i)) {
                if (obj instanceof JTextField && ((JTextField) obj).getText().equals(user)) {
                    removeComponent(post.get(i));
                    break;
                }

            }

        }

//IMPORTANT
        posts_Panel.revalidate();
        posts_Panel.repaint();

    }

    private void printPost(String usrname, String post, final String ipAddress, final String port_number) {
        if (usrname == null || post == null || ipAddress == null)
            return;


        JTextField PostTextField = new JTextField("");
        String help_message = post; //HELP_MESSAGE COLLUMN
        String username = usrname; //USERNAME COLLUMN
        PostTextField.setText(help_message);
        PostTextField.setPreferredSize(new Dimension(320, 26));

        JTextField usernameTextField = new JTextField("");
        usernameTextField.setPreferredSize(new Dimension(80, 26));
        usernameTextField.setText(username);


        JButton RespondButton = new JButton("Respond Post");
        this.post.add(Triplet.with(usernameTextField, PostTextField, RespondButton));


        RespondButton.setBounds(500, 500, 100, 20);
        RespondButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(GetMyIPLocal.getMyIPLocal());
                System.out.println(ipAddress);
                System.out.println(port_number);

                //      Server.serverClose();

                Client client = new Client();
                client.setData(ipAddress, Integer.parseInt(port_number));
                Thread client_thread = new Thread(client);
                client_thread.start();


            }
        });

        posts_Panel.add(usernameTextField);
        posts_Panel.add(PostTextField);
        posts_Panel.add(RespondButton);

        posts_Panel.revalidate();
        ;
        posts_Panel.validate();

        //RowID++;

        return;
    }


    public static void UserInterface() {
        UserInterface p = new UserInterface();
        p.pack();
        p.setSize(800, 600);
        p.setLocation(600, 0);
        p.setVisible(true);
        System.exit(1);
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
        contentPane.setLayout(new GridBagLayout());
        buttons_Panel = new JPanel();
        buttons_Panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(buttons_Panel, gbc);
        buttons_Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        AddButton = new JButton();
        AddButton.setText("Post Announcement");
        buttons_Panel.add(AddButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        U_Button = new JButton();
        U_Button.setText("Update announcements");
        buttons_Panel.add(U_Button, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        welcomeSummonerTextField = new JTextField();
        welcomeSummonerTextField.setText("  Welcome Summoner");
        buttons_Panel.add(welcomeSummonerTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JToolBar.Separator toolBar$Separator1 = new JToolBar.Separator();
        buttons_Panel.add(toolBar$Separator1, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JToolBar.Separator toolBar$Separator2 = new JToolBar.Separator();
        buttons_Panel.add(toolBar$Separator2, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PostTextField = new JTextField();
        PostTextField.setText("");
        buttons_Panel.add(PostTextField, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        deletePostButton = new JButton();
        deletePostButton.setText("Delete Post");
        buttons_Panel.add(deletePostButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        buttons_Panel.add(refreshButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        posts_Panel = new JPanel();
        posts_Panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 200.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(posts_Panel, gbc);
        posts_Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        PostBox = new JScrollPane();
        posts_Panel.add(PostBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
