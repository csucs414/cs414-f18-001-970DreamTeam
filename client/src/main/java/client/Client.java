package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.*;

import javax.swing.*;

public class Client extends JFrame{

	JPanel panel, panel1, panel2, main, panel3, panel4;
	JLabel user_label, password_label, message, create_label, email, confirm_label;
	JTextField userName_text, email_text;
	JPasswordField password_text, confirm_password;
	JButton submit, create, back;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = screenSize.width * 2 / 3;
	int height = screenSize.height * 2 / 3;

	Client() {

		// User Label
		user_label = new JLabel();
		user_label.setText("User Name: ");
		userName_text = new JTextField(10);
		userName_text.setMaximumSize(userName_text.getPreferredSize());

		// Password

		password_label = new JLabel();
		password_label.setText("Password: ");
		password_text = new JPasswordField(10);
		password_text.setMaximumSize(password_text.getPreferredSize());

		// Create

		create_label = new JLabel();
		create_label.setText("Don't have account?");
		create = new JButton("Create");


		submit = new JButton("SUBMIT");

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));

		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));

		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));

		panel.add(user_label);
		panel.add(userName_text);
		panel1.add(password_label);
		panel1.add(password_text);

		message = new JLabel();
		panel2.add(message);
		panel2.add(submit);

		panel3.add(create_label);
		panel3.add(create);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		main.add(panel);
		main.add(panel1);
		main.add(panel2);
		main.add(panel3);

		// Adding the listeners to components..
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String username = userName_text.getText();
				String password = String.valueOf(password_text.getPassword());
				if (!validateCredentials(username, password)) {
					message.setText(" Invalid user and password.. ");
				} else {
					// Valid credentials go to home screen
				}
			}
		});

		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.removeAll();
				main.revalidate();
				main.repaint();
				getContentPane().remove(main);
				main = createAccount();
				add(main, BorderLayout.CENTER);
				setTitle("Create Account!");
				setSize(width, height);
				setVisible(true);
			}
		});

		add(main, BorderLayout.CENTER);
		setTitle("Please Login Here !");
		setSize(width, height);
		setVisible(true);

	}

	public JPanel createAccount () {
		// Username

		user_label = new JLabel();
		user_label.setText("User Name: ");
		userName_text = new JTextField(10);
		userName_text.setMaximumSize(userName_text.getPreferredSize());

		// Password

		password_label = new JLabel();
		password_label.setText("Password: ");
		password_text = new JPasswordField(10);
		password_text.setMaximumSize(password_text.getPreferredSize());

		// Confirm Password

		confirm_label = new JLabel();
		confirm_label.setText("Confirm Password: ");
		confirm_password = new JPasswordField(10);
		confirm_password.setMaximumSize(confirm_password.getPreferredSize());

		// Email

		email = new JLabel();
		email.setText("Email: ");
		email_text = new JTextField(10);
		email_text.setMaximumSize(email_text.getPreferredSize());

		submit = new JButton("SUBMIT");
		back = new JButton("Cancel");

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));

		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));

		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));

		panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.LINE_AXIS));

		panel.add(user_label);
		panel.add(userName_text);

		panel1.add(email);
		panel1.add(email_text);

		panel2.add(password_label);
		panel2.add(password_text);

		panel3.add(confirm_label);
		panel3.add(confirm_password);

		panel4.add(back);
		panel4.add(submit);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		main.add(panel);
		main.add(panel1);
		main.add(panel2);
		main.add(panel3);
		main.add(panel4);

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				main.removeAll();
				main.revalidate();
				main.repaint();
				getContentPane().remove(main);
				new Client();
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String username = userName_text.getText();
				String email = email_text.getText();
				String password = String.valueOf(password_text.getPassword());
				String confirmPassword = String.valueOf(confirm_password.getPassword());
				if (validateCreate(username,email) && validateEmail(email) && passwordsMatch(password,confirmPassword)){
//					Valid account creation go back to home page
				} else {
					JOptionPane.showMessageDialog(null, "Cannot create account try again");
				}
			}
		});
		return main;
	}

	public boolean validateCredentials(String username, String password) {
//		Checks database for credentials
		return false;
	}

	public boolean validateCreate(String username, String email){
//		Checks that username and email aren't already taken
		return true;
	}

	public boolean validateEmail(String email){
//		Checks email includes @
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean passwordsMatch(String password, String confirmPassword){
		return password.equals(confirmPassword);
	}

	public static void main (String[]args){
		new Client();
	}

}
