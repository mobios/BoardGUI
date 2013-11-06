package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public abstract class BoardCell {
	private int row, column, width, height, scale;
	protected boolean highlighted;
	
	public BoardCell(int row, int column){
		this.row = row;
		this.column = column;
		width = 30;
		height = 30;
		scale = 30;
		highlighted = false;
	}
	
	public void setHighlighted(boolean b) {
		highlighted = b;
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
	
	public abstract void draw(Graphics g, Board b);

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(column*scale, row*scale, width, height);
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
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
	
	public static List<BoardCell> sieveDoor(List<BoardCell> t){
		List<BoardCell> ret = new ArrayList<BoardCell>();
		
		for(BoardCell cell : t)
			if(cell.isDoorway())
				ret.add(cell);
		
		return ret;
	}



	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public abstract int getX();
	
	public abstract int getY();
	
	
}
