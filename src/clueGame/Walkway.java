package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class Walkway extends BoardCell {
	private int x, y, w, h;
	
	public Walkway(int row, int column) {
		super(row, column);
		x = super.getX();
		y = super.getX();
		w = super.getWidth();
		h = super.getHeight();
	}

	@Override
	public boolean isWalkway(){
		return true;
	}
	
	@Override
	public void draw(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.drawRect(x, y, w, h);
		
	}
	
}
