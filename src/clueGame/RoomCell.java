package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	private int x, y, w, h;
	
 	public RoomCell(int row, int column, char initial) {
		super(row, column);
		roomInitial = initial;
		x = (this.getColumn() * super.getScale());
		y = (this.getRow() *  super.getScale());
		w = super.getWidth();
		h = super.getHeight();
	}
	
	@Override
	public boolean isRoom(){
		
		
		return true;
	}
	
	@Override
	public void draw(Graphics g){
		
		g.setColor(Color.gray);
		g.fillRect(x, y, w, h);
		if (this.doorDirection == DoorDirection.DOWN){
			g.setColor(Color.BLUE);
			g.fillRect(x + 4, y + 20, w + 4, h - 20);
		} else if(this.doorDirection == DoorDirection.LEFT){
			g.setColor(Color.BLUE);
			g.fillRect(x + 20, y, w - 20, h + 4);
		}
		
	}
	
	@Override
	public boolean isDoorway(){
		if(doorDirection == null || doorDirection == DoorDirection.NONE)
			return false;
		return true;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setDoorDirection(DoorDirection doorDirection){
		this.doorDirection = doorDirection;
	}

	public char getRoomInitial() {
		return roomInitial;
	}
	
}
