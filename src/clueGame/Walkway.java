package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class Walkway extends BoardCell {
	private int x, y, w, h;
	
	public Walkway(int row, int column) {
		super(row, column);
		x = (this.getColumn() *  super.getScale());
		y = (this.getRow() *  super.getScale());
		w = super.getWidth();
		h = super.getHeight();
	}

	@Override
	public boolean isWalkway(){
		return true;
	}
	
	@Override
	public void draw(Graphics g, Board b){
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w+1, h+1);
		
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
