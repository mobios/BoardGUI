package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import board.Board;
import board.BoardCell;
import board.RoomCell;
import board.Walkway;

public class ComputerPlayer extends Player {
	private BoardCell hPrevCell;					// I have a masochistic love for the WinAPI
	private List<RoomCell> visited;
	private RoomCell targetRoom;
	
	
	
	private class adjacency{
		public RoomCell cell;
		public int distance;
	}
	
	public ComputerPlayer(String name, ArrayList<Card> myCards, Color color, BoardCell location) {
		this();
		set(name, myCards, color, location);
	}
	
	public ComputerPlayer() {
		super();
		hPrevCell = (BoardCell) new Walkway(0,0);				// should be default action, but let's just make sure
		visited = new ArrayList<RoomCell>();
	};
	
	@Override
	public Player set(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition){
		return super.set(name, myCards, myColor, myPosition);
	}
	
	public BoardCell pickLocation(Random rgen, Set<BoardCell> targets){ // needs method body
		if(targets.contains(targetRoom)){
			BoardCell retcell = targetRoom;
			targetRoom= null;
			return retcell;
		}

		return pickClosestPath(new ArrayList<BoardCell>(targets), targetRoom);
	}

	public BoardCell pickClosestPath(List<BoardCell> possibles, BoardCell target){
		Object[][] distances = new Object[possibles.size()][2];
		for(int i = 0; i < possibles.size(); i++){
			distances[i][0] = i;
			distances[i][1] = calcDistance(possibles.get(i),target);
		}
		
		int j = 0;
		for(int i = 0; i < possibles.size(); i++)
			if((double)distances[i][1] < (double)distances[j][1])
				j = i;
		
		return possibles.get(j);
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
		ret.add(ClueGame.getRandFromList(rand, personCards));
		ret.add(ClueGame.getRandFromList(rand, weaponCards));
		
		return ret;
	}	
	

	public void makeMove(Random randGen, Board board) {
		if(targetRoom ==null)
			targetRoom = pickTarget(board.getAllDoors());
		
		BoardCell oldCell = getPosition();
		BoardCell newCell = pickLocation(randGen, board.getTargets());
		setPosition(newCell);
		hPrevCell = oldCell;
		if(newCell.getClass() == RoomCell.class)
			visited.add((RoomCell) newCell);
		
		//Needs to repaint the board
	}

	@Override
	public void doTurn(Random randGen, Board board) {
		try {
			Thread.sleep(850);
		} catch (InterruptedException e) {}
		makeMove(randGen, board);
		
		
	}
	
	public List<BoardCell> sievePreviousRoomCards(List<BoardCell> source){
		List<BoardCell> retlist = new ArrayList<BoardCell>();
		for(BoardCell cell : source){
			if(cell.getClass() != RoomCell.class)
				retlist.add(cell);
			else if(!visited.contains(((RoomCell)cell).getRoomInitial()))
				retlist.add(cell);
		}
		
		return retlist;
	}
	
	public RoomCell pickTarget(List<RoomCell> allRooms){
		Object [][] dai = new Object[allRooms.size()][2];
		Object [][] daio = new Object[allRooms.size()][2];
		List<Object[]> daios = new ArrayList<Object[]>();
		
		for(int i = 0; i < allRooms.size(); i++){
			dai[i][1] = calcDistance(super.getPosition(), allRooms.get(i));
			dai[i][0] = i;
		}
		
		for(int i = 0, j = 0; i < dai.length; i++){
			for(int ii = 0; ii < dai.length; ii++)
				if((double)dai[ii][1] < (double)dai[j][1]){	
					j = ii;
				}
			daio[i] = dai[j];
			dai[j][1] = 20000.d;
		}
		
		if(rltd() == 0){
			return allRooms.get((int)daio[0][0]);
		}
		
		Collection<Card> allRoomCards = new HashSet<Card>();
		for(Card card : myCards)
			if(card.getType() == Card.CardType.ROOM)
				allRoomCards.add(card);
		
		for(Card card : knownCards)
			if(card.getType() == Card.CardType.ROOM)
				allRoomCards.add(card);
			
		for(Object[] dist : daio){
			boolean visited = false;
			for(Card room : allRoomCards)
				if(room.getName().startsWith(""+allRooms.get((int)dist[0]).getRoomInitial()))
					visited = true;
			
			if(!visited)
				daios.add(dist);
		}
		
		int j=0;
		for(int i=0; i < daios.size();i++ )
			if((double)daios.get(i)[1] < (double)daios.get(j)[1])
				j = i;
		
		
		return (RoomCell)allRooms.get((int)daios.get(j)[0]);
	}

	public double calcDistance(BoardCell target, BoardCell position){
		return Math.sqrt(Math.pow(target.getColumn()-position.getColumn(),2)+Math.pow(target.getRow()-position.getRow(),2));
	}
	
	private int rltd(){
		int numofrooms = ClueGame.getAllRoomCards().size();
		int roomsinhand = 0;
		
		for(Card card : knownCards){
			if(card.getType() != Card.CardType.ROOM)
				continue;
			roomsinhand++;
		}		
		
		for(Card card : myCards){
			if(card.getType() != Card.CardType.ROOM)
				continue;
			if(knownCards.contains(card))
				continue;
			
			roomsinhand++;
		}
		return numofrooms - roomsinhand;
	}
	
	private int cltf(){
		int numofcards = ClueGame.getAllPeopleCards().size() + ClueGame.getAllWeaponCards().size();
		int cardsinhand = 0;
		
		for(Card card : knownCards){
			if(card.getType() == Card.CardType.ROOM)
				continue;
			cardsinhand++;
		}
		
		for(Card card : myCards){
			if(card.getType() == Card.CardType.ROOM)
				continue;
			if(knownCards.contains(card))
				continue;
			
			cardsinhand++;
		}
		return numofcards - cardsinhand;
	}
}
