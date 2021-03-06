package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import board.Board;
import board.BoardCell;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, ArrayList<Card> myCards, Color color, BoardCell location, Board board) {
		super(name, myCards, color, location, board);
	}
	
	public HumanPlayer(){
		super();
	}
	
	public HumanPlayer(Player p) {
		super(p);
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
	public Object[] doTurn(Random randGen, Board board) {
		return null;
	}
	
	public void divulgeCards(guiPanels.PanelInfo argPanel){
		for(Card card : myCards)
			argPanel.add(card.getType().name(), card.getName());
	}

	@Override
	public void playerSuggested(Player p) {		
		BoardCell location;
		//ensures that the suggested player doesn't get moved on top of the suggesting player
		if(board.getCellAt(board.calcIndex(p.getPosition().getRow()+1, 
			p.getPosition().getColumn())).isRoom()) {
				location = board.getCellAt(board.calcIndex(p.getPosition().getRow()+1, 
						p.getPosition().getColumn()));
		} else if(board.getCellAt(board.calcIndex(p.getPosition().getRow()-1, 
				p.getPosition().getColumn())).isRoom()) {
			location = board.getCellAt(board.calcIndex(p.getPosition().getRow()-1, 
					p.getPosition().getColumn()));
		} else if(board.getCellAt(board.calcIndex(p.getPosition().getRow(), 
				p.getPosition().getColumn()+1)).isRoom()) {
			location = board.getCellAt(board.calcIndex(p.getPosition().getRow(), 
					p.getPosition().getColumn()+1));
		} else if(board.getCellAt(board.calcIndex(p.getPosition().getRow(), 
				p.getPosition().getColumn()-1)).isRoom()) {
			location = board.getCellAt(board.calcIndex(p.getPosition().getRow(), 
					p.getPosition().getColumn()-1));
		} else {
			location = p.getPosition();//just in case the above four are not true for some reason
		}
		
		setPosition(location);
		
	}
}
