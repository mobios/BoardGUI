package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell hPrevCell;					// I have a masochistic love for the WinAPI
	
	public ComputerPlayer(String name, ArrayList<Card> myCards, Color color, BoardCell location) {
		this();
		set(name, myCards, color, location);
	}
	
	public ComputerPlayer() {
		super();
		hPrevCell = (BoardCell) new Walkway(0,0);				// should be default action, but let's just make sure
	};
	
	@Override
	public Player set(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition){
		return super.set(name, myCards, myColor, myPosition);
		
	}
	
	public BoardCell pickLocation(Random rgen, Set<BoardCell> targets){ // needs method body
		ArrayList<BoardCell> t = new ArrayList<BoardCell>(targets);
		t.remove(hPrevCell);
		ArrayList<BoardCell> posDoor = BoardCell.sieveDoor(t);
			
		BoardCell rnd = ((posDoor.size() > 0) ? posDoor.get(rgen.nextInt(posDoor.size())) : t.get(rgen.nextInt(t.size())));
		hPrevCell = rnd;
		if (rnd != null) {
			setTargetChosen(true);
		}
		return rnd;
	}

	@Override
	public ArrayList<Card> accuse(Random rgen) { // Only public to make junit tests... should be private for release
										// Does not make use of sieveKnownCards function YET
		ArrayList<Card> workingSet = new ArrayList<Card>(ClueGame.getAllCards());
		workingSet.remove(myCards);
		workingSet.remove(knownCards);
		
		ArrayList<Card> personCards = new ArrayList<Card>(ClueGame.getAllPeopleCards());
		ArrayList<Card> weaponCards = new ArrayList<Card>(ClueGame.getAllWeaponCards());
		ArrayList<Card> roomCards = new ArrayList<Card>(ClueGame.getAllRoomCards());
		
		personCards.removeAll(workingSet);
		weaponCards.removeAll(workingSet);
		roomCards.removeAll(workingSet);
		
		workingSet = new ArrayList<Card>();
		workingSet.add(personCards.get(rgen.nextInt(personCards.size())));
		workingSet.add(weaponCards.get(rgen.nextInt(weaponCards.size())));
		workingSet.add(roomCards.get(rgen.nextInt(roomCards.size())));
		return workingSet;												//THIS FUNCTION IS A PRIME REFACTOR CANIDATE
	}

	@Override
	public ArrayList<Card> generateSuggestion(Random rand) {
		
		ArrayList<Card> ret = new ArrayList<Card>();
		ret.add(this.getRoomPlayerIn());
		
		ArrayList<Card> personCards = sieveKnownCards(ClueGame.getAllPeopleCards());
		ArrayList<Card> weaponCards = sieveKnownCards(ClueGame.getAllWeaponCards());
		ret.add(ClueGame.getRandFromCollection(rand, personCards));
		ret.add(ClueGame.getRandFromCollection(rand, weaponCards));
		
		return ret;
	}	
}
