package board;

import java.util.ArrayList;
import java.util.List;

public class Cardinal {
	public static List<BoardCell> cardinals(Board board, BoardCell location){
		List<BoardCell> ret = new ArrayList<BoardCell>();
		int index = board.calcIndex(location.getRow(), location.getColumn());

		if(board.inBounds(index+1) && location.getColumn() < board.getNumColumns()-1){
			if(board.getCellAt(index+1).getClass() == Walkway.class || (board.getCellAt(index+1).getClass() == RoomCell.class && ((RoomCell)board.getCellAt(index+1)).isDoorway())){
				ret.add(board.getCellAt(index+1));
			}
		}
		
		if(board.inBounds(index-1) && location.getColumn() > 0){
			if(board.getCellAt(index-1).getClass() == Walkway.class || (board.getCellAt(index-1).getClass() == RoomCell.class && ((RoomCell)board.getCellAt(index-1)).isDoorway())){
				ret.add(board.getCellAt(index-1));
			}
		}
		
		if(board.inBounds(index+board.getNumColumns()) && location.getRow() < board.getNumRows()-1){
			if(board.getCellAt(index+board.getNumColumns()).getClass() == Walkway.class || (board.getCellAt(index+board.getNumColumns()).getClass() == RoomCell.class && ((RoomCell)board.getCellAt(index+board.getNumColumns())).isDoorway())){
				ret.add(board.getCellAt(index+board.getNumColumns()));
			}
		}
		
		if(board.inBounds(index-board.getNumColumns()) && location.getRow() > 0){
			if(board.getCellAt(index-board.getNumColumns()).getClass() == Walkway.class || (board.getCellAt(index-board.getNumColumns()).getClass() == RoomCell.class && ((RoomCell)board.getCellAt(index-board.getNumColumns())).isDoorway())){
				ret.add(board.getCellAt(index-board.getNumColumns()));
			}
		}
		
		return ret;
	}
}
