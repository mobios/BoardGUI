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
		x = super.getX();
		y = super.getX();
		w = super.getWidth();
		h = super.getHeight();	
	}
	
	@Override
	public boolean isRoom(){
		
		
		return true;
	}
	
	@Override
	public void draw(Graphics g){
		
		g.setColor(Color.BLUE);
		g.drawRect(x, y, w, h);
		
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
