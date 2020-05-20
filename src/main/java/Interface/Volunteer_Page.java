package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Volunteer_Page extends JDialog {

    private JPanel contentPane;
    private JPanel buttons_Panel;
    private JPanel posts_Panel;
    private JButton AddButton;
    private JButton V_Button;
    private JTextField welcomeSummonerTextField;
    private JButton buttonOK;
    private JButton buttonCancel;
    //MAI VEDEM DACA ADAUGAM SI UN MESAJ SUS CU ANNOUNCEMENTS

    public Volunteer_Page(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        contentPane =new JPanel();

        posts_Panel.setLayout(new FlowLayout());

        V_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        AddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                JTextField text =new JTextField("Text");
                text.setPreferredSize( new Dimension( 320, 26) );

                JTextField username =new JTextField("Username");
                username.setPreferredSize( new Dimension( 80, 26) );

                JButton b = new JButton("Respond Post");
                b.setBounds(500, 500, 100, 20);
                posts_Panel.add(username);
                posts_Panel.add(text);
                posts_Panel.add(b);

                posts_Panel.revalidate();;
                posts_Panel.validate();

                //aici salvam butonul in baza de date odata cu adaugarea pe interfata
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
