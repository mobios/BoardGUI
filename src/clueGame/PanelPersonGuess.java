package clueGame;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelPersonGuess extends JPanel {
	private static final long serialVersionUID = -691069948068484320L;
	private JComboBox<String> personGuessCombo;
	
	public PanelPersonGuess(){
		
		personGuessCombo = createPersonCombo(); 
		
		setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		
		
		add(personGuessCombo, BorderLayout.SOUTH);
		
		
	}
	
	private JComboBox<String> createPersonCombo(){
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Unsure");
		comboBox.addItem("Colonel Mustard");
		comboBox.addItem("Miss. Purple");
		comboBox.addItem("Dirty Harry");
		comboBox.addItem("Kirk Lazarus");
		comboBox.addItem("Tugg Speedman");
		comboBox.addItem("Audrey Hepburn");
		return comboBox;
	}
	
}
