package ca.mcgill.ecse223.kingdomino.playerui;

package ca.mcgill.ecse223.kingdomino.playerui;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.controller.*;
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
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.controller.KDController;
import ca.mcgill.ecse223.kingdomino.controller.KDQuery;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

import javax.swing.GroupLayout;

public class AssignPlayer_Screen extends JFrame {
	
public static void main(String[] args) {
		
		new AssignPlayer_Screen().setVisible(true);

	}
	
	public AssignPlayer_Screen() {
		
		initComponents();
	
		}
	
	public void initComponents() {
		
		Kingdomino kd = KDController.loadGame(null);
		
		this.setSize(500,400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Kingdomino");
		
		String [] users = {"a", "b", "c"}; //get method
		
		JList list = new JList(users);
		
		this.add(list);
		

	}
}