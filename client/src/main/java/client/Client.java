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

	public clientCommunicationHandler comm;

	HashMap<Integer, ClientGame> games = new HashMap<Integer, ClientGame>();

	JPanel panel, panel1, panel2, main, panel3, panel4;
	JLabel user_label, password_label, message, create_label, email, confirm_label;
	JTextField userName_text, email_text;
	JPasswordField password_text, confirm_password;
	JButton submit, create, back;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = screenSize.width * 2 / 3;
	int height = screenSize.height * 2 / 3;
	String player1;

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
				sendCredentials(username,password);
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
				if (validateEmail(email) && passwordsMatch(password,confirmPassword)){
					validateCreate(username,email,password);
				} else {
					invalidCreation();
				}
			}
		});
		return main;
	}

	public JPanel rules(ArrayList<String> list) {
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
		main.add(textArea);

		submit = new JButton("Got it!");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				main.removeAll();
				main.revalidate();
				main.repaint();
				getContentPane().remove(main);
				main = users(list);
				add(main, BorderLayout.CENTER);
				setTitle("Welcome!");
				setSize(width, height);
				setVisible(true);
			}});
		main.add(submit);
		return main;
	}

	public JPanel users(ArrayList<String> list) {
//		set list to users in database
		for (int i = 0; i < list.size(); i++) {
			JLabel lbl = new JLabel();
			String player2 = list.get(i);
			lbl.setText(player2);
			JButton btn = new JButton("Invite");
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sendInvite(player1, player2);
				}
			});
		}

		submit = new JButton("Got it!");
		main.add(submit);
		return main;
	}

	public void inviteDeclinced() {
		JOptionPane.showMessageDialog(null, "Your invite was declined");
	}
	public void inviteFail() {
		JOptionPane.showMessageDialog(null, "Your invite could not be sent.");
		
	}
	public void inviteSuccess() {
		JOptionPane.showMessageDialog(null, "Your invite was sent.");
	}

	public void inviteAccepted(int GameID, String opponent) {
		//Create a new game
		ClientGame game = new ClientGame(GameID, 0, opponent);
		games.put(GameID, game);
	}
	
	public void updateGame(int GameID, String opponent) {
		//Create a new game
		games.get(GameID);
	}
	

	public void sendCredentials(String username, String password) {
//		Checks database for credentials
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("messageType", "Invite");
		map.put("inviteType", "Request");
		map.put("Name", username);
		map.put("Password", password);
		player1 = username;
		comm.outbound(map);
	}

	public void sendInvite(String from, String to) {
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("messageType", "Invite");
		map.put("From", from);
		map.put("To", to);
		map.put("inviteType", "request");
		comm.outbound(map);
	}

	public void invalidCredentials(){
		message.setText(" Invalid user and password.. ");
	}

	public void invalidCreation() {
		JOptionPane.showMessageDialog(null, "Cannot create account try again");
	}

	public void validCredentials(ArrayList<String> list){
		main.removeAll();
		main.revalidate();
		main.repaint();
		getContentPane().remove(main);
		main = rules(list);
		add(main, BorderLayout.CENTER);
		setTitle("Welcome!");
		setSize(width, height);
		setVisible(true);

	}

	public void validateCreate(String username, String email, String password){
//		Checks that username and email aren't already taken
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("Name", username);
		map.put("Email", email);
		map.put("Password", password);
		comm.outbound(map);
	}

	public boolean validateEmail(String email){
//		Checks email includes @
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean passwordsMatch(String password, String confirmPassword){
		return password.equals(confirmPassword);
	}

	public static void main (String[]args){
		Client temp = new Client();
		try {
			Socket sock = new Socket("localhost", 20001);
			temp.comm = new clientCommunicationHandler(sock,temp);
			temp.comm.start();
		} catch (IOException e){

		}
	}

}