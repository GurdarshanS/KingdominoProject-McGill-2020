package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class KingUI_PlayerCreate extends JFrame {
	
	private int WIDTH;
	private int HEIGHT;
	private int textW;
	private int textH;
	private JLabel name;
	private JTextField insertName;

	
	public KingUI_PlayerCreate(){
		
		userInterface();
	}
	public void userInterface() {
		WIDTH = 950;
		HEIGHT = 800;
		textW = textH = 75;
		
		// Layout Manager
		Container c =  getContentPane();
		setLayout(new GroupLayout(c));
		
		// Swing components
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Create your account");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		// back button
		JButton back = new JButton("BACK");
		back.setFont(new Font("Times", Font.BOLD, 15));
		back.setBounds(0, 0, textW*2, textH);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// return to main page
			}
		});
		// create name
		JButton done = new JButton("CREATE");
		done.setFont(new Font("Times", Font.BOLD, 15));
		done.setBounds(WIDTH/2-textW, HEIGHT-100, textW*2, textH);
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		// box with info
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		name = new JLabel("Insert Username Below", SwingConstants.CENTER);
		name.setText("Create User Profile");
		name.setBorder(border);
		name.setFont(new Font("Times", Font.BOLD, 20));
		name.setBounds(WIDTH/2-200, textH/2, 400, 400);
		
		JLabel label = new JLabel("label", SwingConstants.CENTER);
		label.setText("Insert a username");
		label.setFont(new Font("Times", Font.BOLD, 15));
		label.setBounds(WIDTH/2, HEIGHT+textH/2, 400, 400);
		
		// text box
		insertName = new JTextField();
		insertName.setBounds(WIDTH/2-120, HEIGHT/2+textH, 250, 50);
		
		// Add
		c.add(back);
		c.add(done);
		c.add(insertName);
		c.add(name);
		c.add(label);
	}
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
				JFrame frame = new KingUI_PlayerCreate();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}


