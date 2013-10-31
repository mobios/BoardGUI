package clueGame;

import java.awt.GridLayout;

import javax.swing.JDialog;

public class DetectiveNotes  extends JDialog {
	
	PanelPeople peoplePanel;
	
	public DetectiveNotes(){
		
		peoplePanel = new PanelPeople();
		
		setTitle("Login Dialog");
		setSize(800, 350);
		setLayout(new GridLayout(3, 2));
		
		add(peoplePanel);
		
	}
	
	
}
