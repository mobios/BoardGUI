package clueGame;

import java.awt.Graphics;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	
 	public RoomCell(int row, int column, char initial) {
		super(row, column);
		roomInitial = initial;
	}
	
	@Override
	public boolean isRoom(){
		
		
		return true;
	}
	
	@Override
	public void draw(Graphics g){
		
		
		
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
