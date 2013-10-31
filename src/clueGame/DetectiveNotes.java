package clueGame;

import java.awt.GridLayout;

import javax.swing.JDialog;

public class DetectiveNotes  extends JDialog {
	
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
