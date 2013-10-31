package clueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelRooms extends JPanel {
	JCheckBox kitchenBox, livingRoomBox, observatoryBox, planetariumBox, highEnergyLaserLabBox,
	saunaBox, dungeonBox, armouryBox, natatoriumBox;
	
	public PanelRooms(){
		
		kitchenBox = new JCheckBox("Kitchen");
		livingRoomBox = new JCheckBox("Living Room");
		observatoryBox = new JCheckBox("Observatory");
		planetariumBox = new JCheckBox("Planetarium");
		highEnergyLaserLabBox = new JCheckBox("High Energy Laser Lab");
		saunaBox = new JCheckBox("Sauna");
		dungeonBox = new JCheckBox("Dungeon");
		armouryBox = new JCheckBox("Armoury");
		natatoriumBox = new JCheckBox("Natatorium");
		
		setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		setLayout(new GridLayout(0, 2));
		
		add(kitchenBox);
		add(livingRoomBox);
		add(observatoryBox);
		add(planetariumBox);
		add(highEnergyLaserLabBox);
		add(saunaBox);
		add(dungeonBox);
		add(armouryBox);
		add(natatoriumBox);
		
	}

}
