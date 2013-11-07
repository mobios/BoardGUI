package guiPanels;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelRoomGuess extends JPanel{
	private static final long serialVersionUID = 8744913873013883248L;
	private JComboBox<String> roomGuessCombo;
	
	public PanelRoomGuess(){
		
		roomGuessCombo = createRoomCombo();
		
		setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		
		add(roomGuessCombo);
		
	}
	
	private JComboBox<String> createRoomCombo(){
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Unsure");
		comboBox.addItem("Kitchen");
		comboBox.addItem("Living Room");
		comboBox.addItem("Observatory");
		comboBox.addItem("Planetarium");
		comboBox.addItem("High Energy Laser Lab");
		comboBox.addItem("Sauna");
		comboBox.addItem("Dungeon");
		comboBox.addItem("Armoury");
		comboBox.addItem("Natatorium");
		return comboBox;
		
	}

}
