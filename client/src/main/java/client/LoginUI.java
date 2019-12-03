package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import java.awt.Toolkit;


class Login extends JFrame
{
    JButton SUBMIT;
    JButton CREATE;
    JPanel panel;
    JLabel label1,label2;
    final JTextField  text1,text2;
    Login()
    {
        label1 = new JLabel();
        label1.setText("Username:");
        text1 = new JTextField(15);
        text1.setMaximumSize(new Dimension(5,5));

        label2 = new JLabel();
        label2.setText("Password:");
        text2 = new JPasswordField(15);
        text2.setMaximumSize(new Dimension(5,5));

        SUBMIT=new JButton("Login");
        CREATE=new JButton("Create new Account");

        panel=new JPanel(new GridLayout(3,2));
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(SUBMIT);
        panel.add(CREATE);
        add(panel,BorderLayout.CENTER);
        SUBMIT.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String username=text1.getText();
                String password=text2.getText();
                if (!validator(username, password)){
                    JOptionPane.showMessageDialog(null, "Invalid username and password");
                }
            }});
        CREATE.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                CreateAccount page=new CreateAccount();
                page.setVisible(true);
            }});
        setTitle("LOGIN FORM");
    }

    public boolean validator(String username, String password){
//        checks database for username and password returns true if valid
        return false;
    }


}
class LoginUI
{
    public static void main(String arg[])
    {
//        JFrame gameWindow;
        try
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width * 2 / 3;
            int height = screenSize.height * 2 / 3;
//            gameWindow = new JFrame("Hnefatafl");
//            gameWindow.setPreferredSize(new Dimension(width, height));
            Login frame=new Login();
            frame.setSize(width,height);
            frame.setVisible(true);
        }
        catch(Exception e)
        {JOptionPane.showMessageDialog(null, e.getMessage());}
    }
}
