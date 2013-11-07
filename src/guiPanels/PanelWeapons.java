package guiPanels;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelWeapons extends JPanel{
	private static final long serialVersionUID = 4225677045812288631L;
	JCheckBox shurikenBox, claymoreBox, icePickBox, polearmBox, nightshadeBox, claymoreMineBox, garroteBox;
	
	public PanelWeapons(){
		
		shurikenBox = new JCheckBox("Shuriken");
		claymoreBox = new JCheckBox("Claymore");
		icePickBox = new JCheckBox("Ice Pick");
		polearmBox = new JCheckBox("Polearm");
		nightshadeBox = new JCheckBox("Nightshade");
		claymoreMineBox = new JCheckBox("Claymore Mine");
		garroteBox = new JCheckBox("Garrote");
		
		setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		setLayout(new GridLayout(0, 2));
		
		add(shurikenBox);
		add(claymoreBox);
		add(icePickBox);
		add(polearmBox);
		add(nightshadeBox);
		add(claymoreMineBox);
		add(garroteBox);
		
	}
	
}
