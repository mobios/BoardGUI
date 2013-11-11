package board;

public class Path {
	private BoardCell associatedCell;
	private int distance;
	
	public Path(BoardCell associatedCell, int distance) {
		super();
		this.associatedCell = associatedCell;
		this.distance = distance;
	}

	public BoardCell getCell() {
		return associatedCell;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((associatedCell == null) ? 0 : associatedCell.hashCode());
		result = prime * result + distance;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (associatedCell == null) {
			if (other.associatedCell != null)
				return false;
		} else if (!associatedCell.equals(other.associatedCell))
			return false;
		if (distance != other.distance)
			return false;
		return true;
	}
}
