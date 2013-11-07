package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, ArrayList<Card> myCards, Color color, BoardCell location) {
		super(name, myCards, color, location);
	}
	
	public HumanPlayer(){
		super();
	}
	
	@Override
	public ArrayList<Card> accuse(Random unused){
		return null;
	}

	@Override
	public ArrayList<Card> generateSuggestion(Random rand) {
		// TODO Auto-generated method stub
		return null;
	}

	public void makeMove(BoardCell whereTo) {
		setPosition(whereTo);
	}

	@Override
	public void doTurn(Random randGen, Board board) {
		return;
	}
	
	public void divulgeCards(clueGame.PanelInfo argPanel){
		for(Card card : myCards)
			argPanel.add(card.getType().name(), card.getName());
	}
}
