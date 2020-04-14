package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.View;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class DraftTest {

	
	public static JFrame frame = new JFrame("Kingdomino");
	public static JPanel contPanel = new JPanel();
	public static CardLayout c1 = new CardLayout();
	
	public static void main(String[] args) {
		
		new DraftTest().frame.setVisible(true);
		
	}
	
	public DraftTest() {
		
		initComponents();
	}

	private void initComponents() {
		
		Kingdomino kd = KDController.loadGame("testgame.data");
		View.printPlayers(kd);
		KDController.draftReadySM();
		System.out.println(kd.getStateMachine().getGamestatusFullName());
		View.printDraft(kd);
		frame.setSize(1350, 850);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Border line = BorderFactory.createLineBorder(Color.black);
		JPanel dom1 = new JPanel(new GridLayout(1,2));
		JPanel dom2 = new JPanel(new GridLayout(1,2));
		JPanel dom3 = new JPanel(new GridLayout(1,2));
		JPanel dom4 = new JPanel(new GridLayout(1,2));
		JPanel panel = new JPanel();
		JButton choose = new JButton("Choose #1");
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Player player = KingdominoApplication.getKingdomino()
				//Rotation r = new Rotation(Player p1, int x, int y, DominoInKingdom dom);
				c1.show(contPanel, "2");
			}
		});
		JButton choose2 = new JButton("Choose #2");
		choose2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Rotation r = new Rotation(Player p1, int x, int y, DominoInKingdom dom);
				c1.show(contPanel, "2");
			}
		});
		JButton choose3 = new JButton("Choose #3");
		choose3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Rotation r = new Rotation(Player p1, int x, int y, DominoInKingdom dom);
				c1.show(contPanel, "2");
			}
		});
		JButton choose4 = new JButton("Choose #4");
		choose4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	Rotation r = new Rotation(Player p1, int x, int y, DominoInKingdom dom);
				c1.show(contPanel, "2");
			}
		});
		
		JLabel one = new JLabel("1");
		one.setFont(new Font("Times", Font.PLAIN, 20));
		JLabel two = new JLabel("2");
		two.setFont(new Font("Times", Font.PLAIN, 20));
		JLabel three = new JLabel("3");
		three.setFont(new Font("Times", Font.PLAIN, 20));
		JLabel four = new JLabel("4");
		four.setFont(new Font("Times", Font.PLAIN, 20));
		JLabel title = new JLabel("Domino Draft");
		title.setFont(new Font("Times", Font.BOLD, 60));
		title.setBorder(line);
		JLabel command = new JLabel("Select your next domino!");
		command.setFont(new Font("Times", Font.BOLD, 20));
		
		JPanel[] firstDomino = new JPanel[2];
		for(int i=0; i<2; i++) {
			firstDomino[i] = new JPanel();
			firstDomino[i].setBackground(Color.WHITE);
			firstDomino[i].setBorder(line);
			dom1.add(firstDomino[i]);
		}
		JPanel[] second = new JPanel[2];
		for(int i=0; i<2; i++) {
			second[i] = new JPanel();
			second[i].setBackground(Color.WHITE);
			second[i].setBorder(line);
			dom2.add(second[i]);
		}
		JPanel[] third = new JPanel[2];
		for(int i=0; i<2; i++) {
			third[i] = new JPanel();
			third[i].setBackground(Color.WHITE);
			third[i].setBorder(line);
			dom3.add(third[i]);
		}
		JPanel[] fourth = new JPanel[2];
		for(int i=0; i<2; i++) {
			fourth[i] = new JPanel();
			fourth[i].setBackground(Color.WHITE);
			fourth[i].setBorder(line);
			dom4.add(fourth[i]);
		}
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		contPanel.setLayout(c1);
		contPanel.add(panel, "1");
		c1.show(contPanel, "1");
		frame.add(contPanel);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGap(260)
				.addGroup(layout.createSequentialGroup())
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(title)
						.addComponent(command)
						.addGroup(layout.createSequentialGroup()
								.addComponent(dom1, 200, 200, 200)
								.addGap(10)
								.addComponent(dom2, 200, 200, 200)
								.addGap(10)
								.addComponent(dom3, 200, 200, 200)
								.addGap(10)
								.addComponent(dom4, 200, 200, 200)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(one)
								.addGap(200)
								.addComponent(two)
								.addGap(200)
								.addComponent(three)
								.addGap(200)
								.addComponent(four)
						)
						.addGroup(layout.createSequentialGroup())
						.addGroup(layout.createSequentialGroup())
						.addGroup(layout.createSequentialGroup())
						.addGroup(layout.createSequentialGroup()
								.addComponent(choose)
								.addGap(105)
								.addComponent(choose2)
								.addGap(105)
								.addComponent(choose3)
								.addGap(105)
								.addComponent(choose4)
								)
						)
				)

		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createParallelGroup())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addGap(50)
					.addComponent(title)
					.addGap(50)
					.addComponent(command)
					.addGap(150)
					.addGroup(layout.createParallelGroup()
							.addComponent(dom1, 200, 200, 200)
							.addGap(10)
							.addComponent(dom2, 200, 200, 200)
							.addGap(10)
							.addComponent(dom3, 200, 200, 200)
							.addGap(10)
							.addComponent(dom4, 200, 200, 200)
					)
					.addGroup(layout.createParallelGroup()
							.addComponent(one)
							.addGap(200)
							.addComponent(two)
							.addGap(200)
							.addComponent(three)
							.addGap(200)
							.addComponent(four)
					)
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup())
					.addGroup(layout.createParallelGroup()
							.addComponent(choose)
							.addGap(105)
							.addComponent(choose2)
							.addGap(105)
							.addComponent(choose3)
							.addGap(105)
							.addComponent(choose4)
							)
					)
					
				)
		);
	}
}