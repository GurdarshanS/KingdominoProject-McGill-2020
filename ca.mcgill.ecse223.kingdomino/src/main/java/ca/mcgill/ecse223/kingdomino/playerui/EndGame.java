package ca.mcgill.ecse223.kingdomino.playerui;


	
	import java.awt.Color;
	import java.awt.Container;
	import java.awt.Dimension;
	import java.awt.Font;

	import javax.swing.BorderFactory;
	import javax.swing.ButtonGroup;
	import javax.swing.GroupLayout;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.SwingConstants;
	import javax.swing.WindowConstants;
	import javax.swing.border.Border;
	import javax.swing.GroupLayout;
	import javax.swing.BoxLayout;
	import javax.swing.JCheckBox;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.SwingUtilities;
	import javax.swing.UIManager;
	import javax.swing.WindowConstants;

	public class EndGame extends JFrame{
		
		
		public EndGame() {
			
			initComponents();
		}
		
		public void initComponents() {
			
			this.setSize(500,400);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setTitle("Kingdomino");
			//this.setContentPane(getJContentPane());
		
			JLabel winner = new JLabel("WINNER!");
			JLabel player = new JLabel("player goes here");
			winner.setFont(new Font("Times", Font.BOLD, 40));
			player.setFont(new Font("Times", Font.BOLD, 40));
			
			GroupLayout layout = new GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			layout.setHorizontalGroup(
					layout.createSequentialGroup()
					.addGap(400)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(player)
							.addComponent(winner)
							)
					);
			
			layout.setVerticalGroup(
					layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addGap(500)
					.addGroup(layout.createSequentialGroup()
						.addComponent(player)
						.addComponent(winner)
						)
					);
							
		}

}
