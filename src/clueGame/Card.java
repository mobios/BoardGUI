package clueGame;

import java.util.ArrayList;
import java.util.Arrays;

public class Card implements Comparable<Card>{
	private CardType type;
	public String name;
	
	public enum CardType {
		ROOM, WEAPON, PERSON;
		public static final char room = 'R';
		public static final char weapon = 'W';
		public static final char person = 'P';
		
		public static final int size = CardType.values().length;

		public static CardType getCardType(char identifier){
			switch(identifier){
			case room:
				return ROOM;
			case weapon:
				return WEAPON;
			case person:
				return PERSON;
			default:
				return null;
			}
		}
		
		public static String getValidTypes(){
			String ret ="";
			ret += "  Room: " + "'"+room+"'\n";
			ret += "  Weapon: " + "'"+weapon+"'\n";
			ret += "  Person: " + "'"+person+"'\n";
			return ret;
		}
		
		public CardType advance(CardType start){
			switch(this){
			case ROOM:
				if(WEAPON == start)
					return null;
				return WEAPON;
				
			case WEAPON:
				if(PERSON == start)
					return null;
				return PERSON;
				
			case PERSON:
				if(ROOM == start)
					return null;
				return ROOM;
				
			default:
				return null;
			}
		}
	}
	
	public Card(String name, CardType type){
		super();
		this.name = name;
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}


	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Card arg0) {
		if(this.name == arg0.name && this.type == arg0.type)
			return 1;
		return 0;
	}
	
	public static ArrayList<Card> stringSuggestion(String person, String weapon, String room){
		ClueGame.assertArgument(person, ClueGame.getAllPeopleIdent());
		ClueGame.assertArgument(weapon, ClueGame.getAllWeaponIdent());
		ClueGame.assertArgument(room, ClueGame.getAllRoomIdent());
		

		return new ArrayList<Card>(Arrays.asList(new Card[]{new Card(person, Card.CardType.PERSON), new Card(weapon, Card.CardType.WEAPON), new Card(room, Card.CardType.ROOM)}));
	}
}
