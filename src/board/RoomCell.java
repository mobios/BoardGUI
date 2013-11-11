package board;

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
	public void draw(Graphics g, Board b){

		if (highlighted) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GRAY);
		}
		g.fillRect(x, y, w, h);
		if (this.doorDirection == DoorDirection.DOWN){
			g.setColor(Color.BLUE);
			g.fillRect(x, y + 26, w + 4, h - 24);
		} else if(this.doorDirection == DoorDirection.LEFT){
			g.setColor(Color.BLUE);
			g.fillRect(x, y, w - 24, h + 4);
		} else if(this.doorDirection == DoorDirection.RIGHT){
			g.setColor(Color.BLUE);
			g.fillRect(x + 26, y, w - 24, h + 4);
		} else if(this.doorDirection == DoorDirection.UP){
			g.setColor(Color.BLUE);
			g.fillRect(x, y, w + 4, h - 24);
		} else if(this.doorDirection == DoorDirection.NONE){
			g.setColor(Color.BLUE);
			if(this.roomInitial == 'K'){
				g.drawString("Kitchen", x, y);
			}else if(this.roomInitial == 'L'){
				g.drawString("Living Room", x, y);
			}else if(this.roomInitial == 'O'){
				g.drawString("Observatory", x + 10, y - 3);
			}else if(this.roomInitial == 'P'){
				g.drawString("Planetarium", x, y);
			}else if(this.roomInitial == 'H'){
				g.drawString("High Energy Laser Lab", x - 15 , y - 3);
			}else if(this.roomInitial == 'S'){
				g.drawString("Sauna", x, y);
			}else if(this.roomInitial == 'D'){
				g.drawString("Dungeon", x, y);
			}else if(this.roomInitial == 'A'){
				g.drawString("Armoury", x, y - 3);
			}else if(this.roomInitial == 'N'){
				g.drawString("Natatorium", x, y);
			}
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
