package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import clueGame.Card.CardType;

public abstract class Player {
	private String name;
	protected ArrayList<Card> myCards;
	protected ArrayList<Card> knownCards;
	private Color color;
	private BoardCell position;
	
	Player(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition){
		this();
		set(name, myCards, myColor, myPosition);
	}
	
	public Player(){
		super();
		knownCards = new ArrayList<Card>();
		myCards = new ArrayList<Card>();
	};
	
	public Player set(String name, ArrayList<Card> myCards, Color myColor, BoardCell myPosition){
		this.name = name;
		this.myCards = myCards;
		this.position = myPosition;
		this.color = myColor;
		return this;
	}
	
	public void drawPalyers(Graphics g){
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

	public ArrayList<Card> getKnownCards() {
		return knownCards;
	}

	public void setKnownCards(ArrayList<Card> knownCards) {
		this.knownCards = knownCards;
	}

	public BoardCell getPosition() {
		return position;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
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

	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}

	public ArrayList<Card> skc(ArrayList<Card> source){
		ArrayList<Card> ret = new ArrayList<Card>(source);
		ret.removeAll(myCards);
		ret.removeAll(knownCards);
		return ret;
	}
	
	public Card disproveSuggestion(Random rand, ArrayList<Card> params){
		ArrayList<Card> paramCopy = new ArrayList<Card>(params);
		params.removeAll(myCards);
		paramCopy.removeAll(params);
		return ((paramCopy.size() > 0 ) ? ClueGame.getRandFromCollection(rand, paramCopy) : null);
	}
	
	public abstract ArrayList<Card> generateSuggestion(Random rand, Card room);
	public abstract ArrayList<Card> accuse(Random rand);
}
