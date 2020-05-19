package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Volunteer_Page extends  JDialog {

    private JPanel contentPane;
    private JPanel buttons_Panel;
    private JPanel posts_Panel;
    private JButton AddButton;
    private JButton V_Button;
    private JTextField welcomeSummonerTextField;
    private JTextField announcementsTextField;
    private JButton buttonOK;
    private JButton buttonCancel;

    public Volunteer_Page(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        V_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        AddButton.addActionListener(new ActionListener() {
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

    public static void Show() {
        //maybe static, maybe not
        Volunteer_Page p = new Volunteer_Page();
        p.pack();
        p.setSize(800,600);
        p.setLocation(600,0);
        p.setVisible(true);
    }
}
