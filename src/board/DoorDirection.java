package board;

public enum DoorDirection {
	UP,
	DOWN,
	LEFT,
	RIGHT,
	NONE;
	
	public static DoorDirection convert(char specifier){
		switch(specifier){
		case 'U':
			return UP;
			
		case 'D':
			return DOWN;
		
		case 'L':
			return LEFT;
			
		case 'R':
			return RIGHT;
			
		case 'N':
			return NONE;
		}
		
		throw new EnumConstantNotPresentException(DoorDirection.class, Character.toString(specifier));
	}
	
	public static String acceptableDirections(){
		return "'U' for UP\n'D' for DOWN\n'L' for LEFT\n'R' for RIGHT";
	}

}