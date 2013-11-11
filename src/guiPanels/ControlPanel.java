package guiPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 5883166420109755868L;
	private CluedoTextField whoseTurn;
	private JButton next, handbook, accuse;
	private CluedoAnonymousPanel die;

	public ControlPanel() {
		JFrame.setDefaultLookAndFeelDecorated(true);

		//setLayout(new GridLayout(0,2));
		setSize(new Dimension(200, 300));

		die = new CluedoAnonymousPanel("Die", "Roll", 5);
		add(BorderLayout.WEST,die);

		whoseTurn = new CluedoPresentationField("Whose Turn?", 20);
		add(BorderLayout.WEST,whoseTurn);

		handbook = new JButton("Handbook");
		accuse = new JButton("Make an Accusation");
		next = new JButton("End Turn");
		
		accuse.setEnabled(false);
		
		add(BorderLayout.EAST, handbook);
		add(BorderLayout.EAST,accuse);
		add(BorderLayout.EAST,next);

		setVisible(true);		
	}
	
	private class CluedoTextField extends JPanel {
		private static final long serialVersionUID = -822398577719509729L;
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
		private static final long serialVersionUID = 1879744014608648695L;

		public CluedoPresentationField(String labelName, int textLength) {
			super(labelName, textLength);
			field.enableInputMethods(false);
			field.setEditable(false);
		}
		
	}
	
	private class CluedoAnonymousPanel extends JPanel {
		private static final long serialVersionUID = -9123393609257775724L;
		private JTextField panelField;
		
		public CluedoAnonymousPanel(String panelName, String labelName, int fieldLength) {
			JLabel label = new JLabel(labelName);
			panelField = new JTextField(fieldLength);
			panelField.setFont(new Font("SansSerif", Font.BOLD, 12));
			panelField.setEditable(false);
			setBorder(new TitledBorder (new EtchedBorder(), panelName));
			add(label);
			add(panelField);
		}
		
		public <T> void updateValue(T value){
			panelField.setText(value.toString());
		}
	}
	
	public void updatePlayerTurnDisplay(String whoTurn){
		whoseTurn.field.setText(whoTurn);
	}
	
	public void updateDieRoll(int roll){
		die.updateValue(roll);
	}
	
	public void associateButtonListener(ActionListener handler, specifyButton whichButton){
		switch(whichButton){
		case NEXT:
			next.addActionListener(handler);
			break;
		
		case HANDBOOK:
			handbook.addActionListener(handler);
			break;
			
		case ACCUSE:
			accuse.addActionListener(handler);
			
		}
	}
	
	public enum specifyButton{
		NEXT,
		HANDBOOK,
		ACCUSE;
	}
	
	public void setAllowAccuse(boolean allow){
		accuse.setEnabled(allow);
	}
	
	public boolean getAllowAccuse(){
		return accuse.isEnabled();
	}
}
