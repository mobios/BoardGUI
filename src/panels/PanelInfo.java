package panels;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelInfo extends JPanel{
	private static final long serialVersionUID = -521373223935283250L;

	public PanelInfo(){
		
	}
	
	public class deck extends JPanel{
		public void add(String type, String cardName){
			add(new card(type, cardName));
		}
		
	}
	
	public class card extends JPanel{
		private JLabel type;
		private JTextField cardName;
		card(String paramCardType, String paramCardName){
			cardName = new JTextField();
			cardName.enableInputMethods(false);
			cardName.setEditable(false);
			cardName.setText(paramCardName);

			type = new JLabel(paramCardType);
			add(type);
			add(cardName);
		}
	}
}
