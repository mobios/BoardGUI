/*
 * NAMES: David Grisham and Leah Moldauer
 * Control Panel GUI for Clue Game
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 5883166420109755868L;
	private SomeTextField whoseTurn;
	private JButton next, accuse;
	private SomePanel die, guess, guessResult;

	public ControlPanel() {
		setSize(new Dimension(200, 300));
		
		JFrame.setDefaultLookAndFeelDecorated(true);
			
		setLayout(new GridLayout(4,2));
		
		whoseTurn = new SomeTextField("Whose Turn?", 20);
		add(whoseTurn);
		
		die = new SomePanel("Die", "Roll", 5);
		add(die);
		
		next = new JButton("Next Player");
		accuse = new JButton("Make an Accusation");
		add(next);
		add(accuse);
		
		guess = new SomePanel("Guess", "Guess", 20);
		add(guess);
		
		add(new JPanel());
		
		guessResult = new SomePanel("Guess Result", "Response", 10);
		add(guessResult);
		
		setVisible(true);		
	}
	
	private class SomeTextField extends JPanel {
		private JTextField field;
		
		public SomeTextField(String labelName, int textLength) {
			JLabel label = new JLabel(labelName);
			field = new JTextField(textLength);
			field.setFont(new Font("SansSerif", Font.BOLD, 12));
			add(label);
			add(field);
		}
	}
	
	private class SomePanel extends JPanel {
		private JTextField panelField;
		
		public SomePanel(String panelName, String labelName, int fieldLength) {
			JLabel label = new JLabel(labelName);
			panelField = new JTextField(fieldLength);
			panelField.setFont(new Font("SansSerif", Font.BOLD, 12));
			panelField.setEditable(false);
			setBorder(new TitledBorder (new EtchedBorder(), panelName));
			add(label);
			add(panelField);
		}
	}
}
