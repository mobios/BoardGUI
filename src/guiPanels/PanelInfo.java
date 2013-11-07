package guiPanels;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelInfo extends JPanel{
	private static final long serialVersionUID = -521373223935283250L;
	private deck thisDeck;
	
	public PanelInfo(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		thisDeck = new deck();
		add(thisDeck);
		setVisible(true);
	}
	
	public void add(String type, String cardName){
		thisDeck.add(type, cardName);
	}
	
	private class deck extends JPanel{
		private static final long serialVersionUID = 7283548936581033350L;
		public deck(){
			JFrame.setDefaultLookAndFeelDecorated(true);
			setBorder(new TitledBorder (new EtchedBorder(), "Cards"));
			setLayout(new GridLayout(0,2));
			setVisible(true);
		}
		
		public void add(String type, String cardName){
			add(new cardPanel(type, cardName));
		}
	}
	
	private class cardPanel extends JPanel{
		private static final long serialVersionUID = 3879086751079345166L;
		private JLabel type;
		private JTextField cardName;
		
		cardPanel(String paramCardType, String paramCardName){
			JFrame.setDefaultLookAndFeelDecorated(true);
			cardName = new JTextField();
			cardName.enableInputMethods(false);
			cardName.setEditable(false);
			cardName.setText(paramCardName);

			type = new JLabel(paramCardType);
			add(type);
			add(cardName);
			setVisible(true);
		}
	}
}
