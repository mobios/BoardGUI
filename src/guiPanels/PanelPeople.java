package guiPanels;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelPeople extends JPanel {
	private static final long serialVersionUID = 4309517310948422747L;
	JCheckBox  colMustardBox, missPBox, dHarryBox, kLazarusBox, tSpeedmanBox, aHepburnBox;
	
	public PanelPeople(){
		
		colMustardBox = new JCheckBox("Colonel Mustard");
		missPBox = new JCheckBox("Miss. Purple");
		dHarryBox = new JCheckBox("Dirty Harry");
		kLazarusBox = new JCheckBox("Kirk Lazarus");
		tSpeedmanBox = new JCheckBox("Tugg Speedman");
		aHepburnBox = new JCheckBox("Audrey Hepburn");
		
		setBorder(new TitledBorder (new EtchedBorder(), "People"));
		setLayout(new GridLayout(0, 2));
		
		add(colMustardBox);
		add(missPBox);
		add(dHarryBox);
		add(kLazarusBox);
		add(tSpeedmanBox);
		add(aHepburnBox);
	}
	
}
