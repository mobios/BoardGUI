package clueGame;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class BoardCell {
	private int row, column;
	
	public BoardCell(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway(){
		return false;
	}
	
	public boolean isRoom(){
		return false;
	}
	
	public boolean isDoorway(){		
		return false;
	}
	
	public abstract void draw(Graphics g);

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		if(this.getClass() == obj.getClass() && obj.getClass() == RoomCell.class)
			if(((RoomCell)this).getRoomInitial() == ((RoomCell)obj).getRoomInitial())
				return true;
		
		BoardCell other = (BoardCell) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	public static ArrayList<BoardCell> sieveDoor(ArrayList<BoardCell> arr){
		ArrayList<BoardCell> ret = new ArrayList<BoardCell>();
		
		for(BoardCell cell : arr)
			if(cell.isDoorway())
				ret.add(cell);
		
		return ret;
	}
	
}
