package clueGame;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelWeaponGuess extends JPanel {
	private static final long serialVersionUID = -1990477964137845401L;
	private JComboBox<String> weaponGuessCombo;
	
	public PanelWeaponGuess(){
		
		weaponGuessCombo = createWeaponCombo();
		
		setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		
		add(weaponGuessCombo);
		
	}

	private JComboBox<String> createWeaponCombo() {
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Unsure");
		comboBox.addItem("Shuriken");
		comboBox.addItem("Claymore");
		comboBox.addItem("Ice Pick");
		comboBox.addItem("Polearm");
		comboBox.addItem("Nightshade");
		comboBox.addItem("Claymore Mine");
		comboBox.addItem("Garrote");
		return comboBox;
	}
	
}
