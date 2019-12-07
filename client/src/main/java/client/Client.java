package client;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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
		fill();
	}

	public void fill () {
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
				if (validateCredentials(username, password) == false) {
					message.setText(" Invalid user and password.. ");
				} else {
					// Valid credentials go to home screen
					main.removeAll();
					main.revalidate();
					main.repaint();
					getContentPane().remove(main);
					main = home();
					add(main, BorderLayout.CENTER);
					setTitle("Welcome!");
					setSize(width, height);
					setVisible(true);
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
				fill();
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String username = userName_text.getText();
				String email = email_text.getText();
				String password = String.valueOf(password_text.getPassword());
				String confirmPassword = String.valueOf(confirm_password.getPassword());
				if (validateCreate(username,email) && validateEmail(email) && passwordsMatch(password,confirmPassword)){
					main.removeAll();
					main.revalidate();
					main.repaint();
					getContentPane().remove(main);
					main = home();
					add(main, BorderLayout.CENTER);
					setTitle("Welcome!");
					setSize(width, height);
					setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Cannot create account try again");
				}
			}
		});
		return main;
	}

	public JPanel home() {
		JTextArea textArea = new JTextArea(
				"Rules\n" +
						"\n" +
						"Setup:\n" +
						"* Two players. Attackers (black pieces) and Defenders (white pieces).\n" +
						"* 11x11 board. Twice as many attackers as defenders.\n" +
						"* Attacker moves first.\n" +
						"* Defenders have King piece.\n" +
						"\n" +
						"Objectives:\n" +
						"* Attackers: Capture the King.\n" +
						"* Defenders: King escapes.\n" +
						"* King is captured by getting surrounded by Attack on 4 sides or 3 sides and a restricted space.\n" +
						"* The Kind escapes by reaching 1 of the 4 corner squares. \n" +
						"* Regular pieces captured by getting surrounded on opposite sides by 2 pieces or 1 and a restricted space. (sandwiched)\n" +
						"* The King can participate in the capture if its is the moving piece.\n" +
						"* A player may only capture an enemy piece if they surround it with a move on their turn.\n" +
						"\n" +
						"Moving:\n" +
						"* Each piece (including King) can move vertical or horizontal. Similar to a Rook in chess.\n" +
						"* 1 piece may move per turn.\n" +
						"* Only king may stop on the Throne (center square).\n" +
						"* A piece can only move through empty spaces.\n" +
						"* A piece may pass inbetween enemy pieces and not get captured.\n" +
						"* The throne and 4 corners are considered restricted spaces.\n" +
						"* The throne is always hostile to attackers and can be used to capture their pieces.\n" +
						"* The throne is only hostile to defenders if it is vacated by the King.\n" +
						"* The corners are hostile to both players.\n" +
						"* If a player cannot move they lose.\n" +
						"* If neither player has enough pieces to capture the enemy, the game is a draw.");
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);

		ArrayList<String> list = new ArrayList<String>();
		list.add("Hi");
		list.add("there");
		list.add("by");
		Map<Integer,String> map=new HashMap<Integer,String>();
//		set list to users in database
		for (int i = 0; i < list.size(); i++){
			map.put(i, list.get(i));
		}
		for(Map.Entry m:map.entrySet()){
			System.out.println(m.getKey()+" "+m.getValue());

		}
		main.add(textArea);
		return main;
	}

	public boolean validateCredentials(String username, String password) {
//		Checks database for credentials
		HashMap<Integer,String> map=new HashMap<Integer,String>();
		map.put(0, username);
		map.put(1, password);
		socketCreation(map);
		return true;
	}

	public void socketCreation(HashMap<Integer,String> map) {
		try {
			Socket sock = new Socket("localhost", 20001);
			ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
			output.writeObject(map);
			output.close();
			sock.close();
//			new clientCommunicationHandler(sock);
		} catch (IOException e){

		}

	}

	public boolean validateCreate(String username, String email){
//		Checks that username and email aren't already taken
		HashMap<Integer,String> map=new HashMap<Integer,String>();
		map.put(0, username);
		map.put(1, email);
		socketCreation(map);
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