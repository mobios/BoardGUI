package core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import board.Board;
import board.BoardCell;
import core.Card.CardType;

public abstract class Player {
	private String name;
	protected List<Card> myCards;
	protected List<Card> knownCards;
	private Color color;
	private BoardCell position;
	private Card roomPlayerIn;
	protected boolean turnFinished;
	
	Player(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition){
		this();
		set(name, myCards, myColor, myPosition);
	}
	
	public Player(){
		super();
		knownCards = new ArrayList<Card>();
		myCards = new ArrayList<Card>();
	};
	
	public Player(Player p) {
		this();
		
		if (this.equals(p)) {
			return;
		}
		
		set(p.name, new ArrayList<Card>(p.myCards), p.color, p.position);
		this.knownCards = p.knownCards;
	}
	
	public Player set(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition){
		this.name = name;
		this.myCards = myCards;
		this.position = myPosition;
		this.color = myColor;
		return this;
	}
	
	public void drawPlayers(Graphics g){
		g.setColor(color);
		g.fillOval(position.getX(), position.getY(), position.getWidth() - 1, position.getHeight() - 1);
	}
	
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
	
	public void setPosition(BoardCell position) {
		this.position = position;
	}
	
	public int getRow() {
		return position.getRow();
	}
	
	public int getColumn() {
		return position.getColumn();
	}

	public ArrayList<Card> getKnownCards() {
		return ((knownCards.getClass() == ArrayList.class) ? (ArrayList<Card>)(knownCards) : new ArrayList<Card>(knownCards));
	}

	public void setKnownCards(ArrayList<Card> knownCards) {
		this.knownCards = knownCards;
	}

	public BoardCell getPosition() {
		return position;
	}

	public ArrayList<Card> getMyCards() {
		return ((myCards.getClass() == ArrayList.class) ? (ArrayList<Card>)(myCards) : new ArrayList<Card>(myCards));
	}
	
	public ArrayList<CardType> getCardTypes(){
		ArrayList<CardType> ret = new ArrayList<CardType>();
		
		for(Card cardIterator : myCards){
			if(ret.contains(cardIterator.getType()))
				continue;
			ret.add(cardIterator.getType());
		}
		
		return ret;
	}

	public void addCard(Card cardParam) {
		if(myCards == null)
			myCards = new ArrayList<Card>();
		
		myCards.add(cardParam);
	}
	
	public Card popRandCard(CardType type, Random randGen){
		ArrayList<Card> possibilities = new ArrayList<Card>();
		
		for(Card cardIterator : myCards)
			if(cardIterator.getType() == type)
				possibilities.add(cardIterator);
		
		if(possibilities.isEmpty())
			return null;
		return possibilities.remove(Math.round((possibilities.size()-1) * randGen.nextFloat()));
	}
	
	public void exposeToCard(Card exposedCard){
		knownCards.add(exposedCard);
	}

	public abstract ArrayList<Card> generateSuggestion(Random rand);
	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}

	public void setRoomPlayerIn(Card roomPlayerIn) {
		this.roomPlayerIn = roomPlayerIn;
	}

	public Card getRoomPlayerIn() {
		return roomPlayerIn;
	}
	
	public ArrayList<Card> sieveKnownCards(ArrayList<Card> source){
		ArrayList<Card> ret = new ArrayList<Card>(source);
		ret.removeAll(myCards);
		ret.removeAll(knownCards);
		return ret;
	}
	
	public Card disproveSuggestion(Random rand, List<Card> params){
		ArrayList<Card> paramCopy = new ArrayList<Card>(params);
		params.removeAll(myCards);
		paramCopy.removeAll(params);
		return ((paramCopy.size() > 0 ) ? ClueGame.getRandFromList(rand, paramCopy) : null);
	}
	
	public abstract ArrayList<Card> accuse(Random rand);
	
	public abstract void doTurn(Random randGen, Board board);
	
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public boolean getTurnFinished() {
		return turnFinished;
	}
	
	public void setTurnFinished(boolean b) {
		turnFinished = b;
	}

}
