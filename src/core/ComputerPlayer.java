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

public class ComputerPlayer extends Player {
	private List<RoomCell> visited;
	private int[][] adjMap;
	private RoomCell targetRoom;
	private RoomCell lastRoom;
		
	public ComputerPlayer(String name, ArrayList<Card> myCards, Color color, BoardCell location, Board board) {
		this();
		set(name, myCards, color, location, board);
	}
	
	public ComputerPlayer() {
		super();
		visited = new ArrayList<RoomCell>();
	};
	
	@Override
	public Player set(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition, Board board){
		return super.set(name, myCards, myColor, myPosition, board);
	}
	
	public BoardCell pickLocation(Random rgen, Board board, Set<BoardCell> targets){ // needs method body
		if(targetRoom.equals(getPosition()) || (getPosition().getClass() == RoomCell.class && ((RoomCell)getPosition()).getRoomInitial() == targetRoom.getRoomInitial())){
			List<BoardCell> notroom = new ArrayList<BoardCell>();
			for(BoardCell cell : targets){
				 if(targetRoom.equals(cell) || (cell.getClass() == RoomCell.class && ((RoomCell)cell).getRoomInitial() == targetRoom.getRoomInitial()))
					 continue;
				 notroom.add(cell);
			}
			return board.getRandEmptyCell(rgen, notroom);
		}
			
		if(targets.contains(targetRoom)){
			BoardCell retcell = targetRoom;
			targetRoom= null;
			return retcell;
		}

		for(BoardCell cell : targets)
			if(cell.getClass() == RoomCell.class && ((RoomCell)cell).getRoomInitial() == targetRoom.getRoomInitial())
				return cell;

		return pickClosestPath(rgen, new ArrayList<BoardCell>(targets));
	}

	public BoardCell pickClosestPath(Random rgen, List<BoardCell> possibles){
		List<BoardCell> endpick = new ArrayList<BoardCell>();
		int endpicksmall= 50000;
		for(BoardCell targets : possibles){
			int dist = adjMap[targets.getColumn()][targets.getRow()];
			if(dist < endpicksmall){
				endpick = new ArrayList<BoardCell>();
				endpicksmall = dist;
				endpick.add(targets);
			}
			
			else if(dist == endpicksmall)
				endpick.add(targets);
		}
		return ClueGame.getRandFromList(rgen, endpick);
	}
	
	@Override
	public ArrayList<Card> accuse(Random rgen) { // Only public to make junit tests... should be private for release
												// Does not make use of sieveKnownCards function YET
		ArrayList<Card> workingSet = new ArrayList<Card>(ClueGame2.getAllCards());
		workingSet.remove(myCards);
		workingSet.remove(knownCards);
		
		ArrayList<Card> personCards = new ArrayList<Card>(ClueGame2.getAllPeopleCards());
		ArrayList<Card> weaponCards = new ArrayList<Card>(ClueGame2.getAllWeaponCards());
		ArrayList<Card> roomCards = new ArrayList<Card>(ClueGame2.getAllRoomCards());
		
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
		Card room = null;
		for(Card card : ClueGame.getAllRoomCards()){
			if(card.getName().startsWith(((RoomCell)getPosition()).getRoomInitial()+"")){
				room = card;
				break;
			}
		}
		if(room != null)
			ret.add(room);
		
		ArrayList<Card> personCards = sieveKnownCards(ClueGame2.getAllPeopleCards());
		ArrayList<Card> weaponCards = sieveKnownCards(ClueGame2.getAllWeaponCards());
		ret.add(ClueGame2.getRandFromList(rand, personCards));
		ret.add(ClueGame2.getRandFromList(rand, weaponCards));
		
		return ret;
	}	
	
	public void makeMove(Random randGen, Board board) {
		if(targetRoom ==null){
			targetRoom = pickTarget(board.getAllDoors());
			adjMap = null;
		}
		
		if(adjMap == null)
			adjMap = board.generatePathing(getPosition(), targetRoom);
		
		setPosition(pickLocation(randGen, board, board.getTargets()));
	}

	@Override
	public Object[] doTurn(Random randGen, Board board) {
		try {
			Thread.sleep(850);
		} catch (InterruptedException e) {}
		
		if(rltd() < 2 && cltf() < 3){
			List<Card> acc = accuse(randGen);
			return new Object[]{2,acc.get(0),acc.get(1),acc.get(2)};
		}
		
		if(rltd() < 3 && cltf() < 5){
			if(randGen.nextFloat() > .17854f){
				List<Card> acc = accuse(randGen);
				return new Object[]{2,acc.get(0),acc.get(1),acc.get(2)};
			}
		}
		
		makeMove(randGen, board);
		if(getPosition().getClass() == RoomCell.class){
			List<Card> sugg = generateSuggestion(randGen);
			return new Object[]{1,sugg.get(0),sugg.get(1),sugg.get(2)};
		}
		
		if(getPosition() == targetRoom)
			targetRoom = null;
		
		return null;
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
		
		if(rltd() <= 1){
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
			
			if(!visited && (lastRoom == null || ((RoomCell)allRooms.get((int)dist[0])).getRoomInitial() != lastRoom.getRoomInitial()))
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
		int numofrooms = ClueGame2.getAllRoomCards().size();
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
		int numofcards = ClueGame2.getAllPeopleCards().size() + ClueGame2.getAllWeaponCards().size();
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

	
	@Override
	public void playerSuggested(BoardCell location) {
		setPosition(location);
		adjMap = null;
	}
	
}
