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
	private CluedoTextField whoseTurn;
	private JButton next, accuse, suggest;
	private SomePanel die;

	public ControlPanel() {
		JFrame.setDefaultLookAndFeelDecorated(true);

		//setLayout(new GridLayout(0,2));
		setSize(new Dimension(200, 300));
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		//setLayout(new GridLayout(0,2));,
		die = new SomePanel("Die", "Roll", 5);
		add(BorderLayout.WEST,die);

		whoseTurn = new CluedoPresentationField("Whose Turn?", 20);
		add(BorderLayout.WEST,whoseTurn);


		suggest = new JButton("Make a suggestion");
		accuse = new JButton("Make an Accusation");
		next = new JButton("Next Player");

		add(BorderLayout.EAST,suggest);
		add(BorderLayout.EAST,accuse);
		add(BorderLayout.EAST,next);

		setVisible(true);		
	}
	
	private class CluedoTextField extends JPanel {
		protected JTextField field;
		
		public CluedoTextField(String labelName, int textLength) {
			JLabel label = new JLabel(labelName);
			field = new JTextField(textLength);
			field.setFont(new Font("SansSerif", Font.BOLD, 12));
			add(label);
			add(field);
		}
	}
	
	private class CluedoPresentationField extends CluedoTextField{

		public CluedoPresentationField(String labelName, int textLength) {
			super(labelName, textLength);
			field.enableInputMethods(false);
			field.setEditable(false);
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
	
	public void setPlayerTurnDisplay(String whoTurn){
		whoseTurn.field.setText(whoTurn);
	}
	
	public void associateButtonListener(ActionListener handler, specifyButton whichButton){
		switch(whichButton){
		case NEXT:
			next.addActionListener(handler);
			break;
			
		case ACCUSE:
			accuse.addActionListener(handler);
			break;
			
		case SUGGEST:
			suggest.addActionListener(handler);
		}
	}
	
	public enum specifyButton{
		NEXT,
		ACCUSE,
		SUGGEST;
	}
}
