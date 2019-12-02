package client;

import javax.swing.*;
import java.awt.*;

class CreateAccount extends JFrame
{
    CreateAccount()
    {
        setDefaultCloseOperation(javax.swing.
                WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Account");
        setSize(400, 200);
        JPanel panel;
        JLabel label1,label2,label3,label4,blank;
        final JTextField text1,text2,text3,text4;
        JButton CREATE;

        label1 = new JLabel();
        label1.setText("Email:");
        text1 = new JTextField(5);
        text1.setMaximumSize(new Dimension(5,5));

        label2 = new JLabel();
        label2.setText("Username:");
        text2 = new JPasswordField(5);
        text2.setMaximumSize(new Dimension(5,5));

        label3 = new JLabel();
        label3.setText("Password:");
        text3 = new JPasswordField(5);
        text3.setMaximumSize(new Dimension(5,5));

        label4 = new JLabel();
        label4.setText("Confirm Password:");
        text4 = new JPasswordField(5);
        text4.setMaximumSize(new Dimension(5,5));

        CREATE=new JButton("Create");

        blank = new JLabel();

        panel=new JPanel(new GridLayout(5,2));
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(label3);
        panel.add(text3);
        panel.add(label4);
        panel.add(text4);
        panel.add(blank);
        panel.add(CREATE);
        add(panel,BorderLayout.CENTER);
    }
}