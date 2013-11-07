package core;

import guiPanels.PanelPeople;
import guiPanels.PanelPersonGuess;
import guiPanels.PanelRoomGuess;
import guiPanels.PanelRooms;
import guiPanels.PanelWeaponGuess;
import guiPanels.PanelWeapons;

import java.awt.GridLayout;

import javax.swing.JDialog;

public class DetectiveNotes  extends JDialog {
	private static final long serialVersionUID = 7258315283132574365L;
	PanelPeople peoplePanel;
	PanelRooms roomsPanel;
	PanelWeapons weaponsPanel;
	PanelPersonGuess personGuessPanel;
	PanelRoomGuess roomGuessPanel;
	PanelWeaponGuess weaponGuessPanel;
	
	public DetectiveNotes(){
		
		peoplePanel = new PanelPeople();
		roomsPanel = new PanelRooms();
		weaponsPanel = new PanelWeapons();
		personGuessPanel = new PanelPersonGuess();
		roomGuessPanel = new PanelRoomGuess();
		weaponGuessPanel = new PanelWeaponGuess();
		
		setTitle("Detective Notes");
		setSize(500, 550);
		setLayout(new GridLayout(3, 2));
		
		add(peoplePanel);
		add(personGuessPanel);
		add(roomsPanel);
		add(roomGuessPanel);
		add(weaponsPanel);
		add(weaponGuessPanel);
		
	}
	
	
}
