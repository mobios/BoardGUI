package clueGame;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JPanel;


public class Board extends JPanel {
	private static final long serialVersionUID = 8728427497602838343L;
	private ArrayList<BoardCell> cellsList;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private Map<Character,String> rooms;
	private Set<BoardCell> targets;
	private boolean[] visited;
	private int numRows, numColumns;
	ClueGame game;
	private String legendFile, layoutFile;
	
	public String getLegendFile() {
		return legendFile;
	}

	public String getLayoutFile() {
		return layoutFile;
	}

	public Board(String layout, String legend) {
		this();
		this.layoutFile = layout;
		this.legendFile = legend;
	}
	
	public Board() {
		super();
		numRows = 0;
		numColumns = 0;
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		rooms = new TreeMap<Character,String>();
		cellsList = new ArrayList<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		if(legendFile == null || legendFile.isEmpty())
			legendFile = "legend.txt";
		
		if(layoutFile == null || layoutFile.isEmpty())
			layoutFile = "layout.csv";
		
		loadConfigFiles();
		calcAdjacencies();
	}
	
	public void paintComponent(Graphics g){ // used to draw the board
		
		game = new ClueGame();
		ArrayList<Player> players = game.getPlayers();
		
		for (BoardCell cell: cellsList){
			cell.draw(g, this);
		}
		for (Player p: players){
			p.drawPalyers(g);
		}
		
	}
	
	public void loadRoomConfig() throws BadConfigFormatException{
		FileReader legendFileReader;
		try {
			legendFileReader = new FileReader(this.legendFile);
		} 
		
		catch (FileNotFoundException e) {
			throw new BadConfigFormatException("Java complained about the file at: "+ legendFile +"\nHere is the exception:\n\n" + e);
		}

		Scanner legendFileScanner = new Scanner(legendFileReader);
		int row = -1;
		do{
			String templine = legendFileScanner.nextLine();
			if(templine.isEmpty())
				continue;
			row++;
			String[] args = templine.split(",");
			if(args.length != 2){
				legendFileScanner.close();
				throw new BadConfigFormatException("Legend file at: " + this.legendFile + " has an extra field...\nRow " + 
													row + " has " + (args.length-1) + " commas, " + (args.length-2) + " too many.\n");
			}
			
			if(args[0].isEmpty()){
				legendFileScanner.close();
				throw new BadConfigFormatException("Legend file at: " + this.legendFile + " has an empty field...\nRow " + 
													row + "is empty in at least the first column (i.e. before the comma).\n");
			}
			
			
			if(args[1].isEmpty()){
				legendFileScanner.close();
				throw new BadConfigFormatException("Legend file at: " + this.legendFile + " has an empty field...\nRow " + 
													row + "is empty at the second column (i.e. after the comma).\n");
			}
			
			
			if(args[0].length() > 1){
				legendFileScanner.close();
				throw new BadConfigFormatException("Legend file at: " + this.legendFile + " has a malfigured key...\nRow " + 
													row + " has a key '" + args[0] + "' that is " + args[0].length() + " characters long" +
													" instead of 1 character");
			}
			
			
			if(rooms.containsKey(args[0].charAt(0))){
				legendFileScanner.close();
				throw new BadConfigFormatException("Legend file at: " + this.legendFile + " has a duplicate field...\nRow " + 
													row + "contains a key that has already been defined. \nKey:" + args[0].charAt(0)+'\n');
			}
			
			
			rooms.put(args[0].charAt(0), args[1].trim());
		}
		while(legendFileScanner.hasNextLine());
		
		legendFileScanner.close();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		if(rooms.isEmpty()){
			throw new BadConfigFormatException("No legend! BoardConfig must be called AFTER RoomConfig!\n");
		}
		
		FileReader layoutFileReader;
		try{
			layoutFileReader = new FileReader(this.layoutFile);
		}
		
		catch (FileNotFoundException e){
			throw new BadConfigFormatException("Java complained about the file at: "+ layoutFile +"\nHere is the exception:\n\n" + e);
		}
		int row = -1;

		Scanner layoutFileScanner = new Scanner(layoutFileReader);
		
		do{
			String line = layoutFileScanner.nextLine();
			if(line.isEmpty())
				continue;
			++row;
			String[] args = line.split(",");
			
			if(numColumns == 0)
				numColumns = args.length;
			else
				if(numColumns != args.length){
					layoutFileScanner.close();
					throw new BadConfigFormatException("Board layout file at: " + this.layoutFile + 
														" has malaligned colums...\nRow "+ row + " has " + args.length + " fields "
														+ "which does not match expected width of " + numColumns + '\n');
				}
			int column = 0;
			for(String cell : args){
				BoardCell workingCell;
			
				if(cell.length() > 3){
					layoutFileScanner.close();
					throw new BadConfigFormatException("Board layout file at: " + this.layoutFile + 
							" has a malformatted cell...\nRow "+ row + " column " + column + " has " + cell.length()
							+ " charaters which does not match the expected 2 for doors, and 1 for normal cells\n");
				}
				
				
				if(!rooms.containsKey(cell.charAt(0))){
					layoutFileScanner.close();
					throw new BadConfigFormatException("Board layout file at: " + this.layoutFile + 
							" has a malformatted cell...\nRow "+ row + " column " + column + " specifies room key " + cell.charAt(0)
							+ " which does not match any keys loaded in legend file " + legendFile +"\n");
				}
					
				
				if(cell.charAt(0) == 'W'){
						workingCell = new Walkway(row, column);
					if(cell.length() > 1){
						layoutFileScanner.close();
						throw new BadConfigFormatException("Board layout file at: " + this.layoutFile + 
								" has a malformatted walkway...\nRow "+ row + " column " + column + " has " + cell.length()
								+ " charaters which does not match the expected 1 for walkways.\n");
						}
				}
				else{
				workingCell = new RoomCell(row, column, cell.charAt(0));
				if(cell.length() > 1)
					try{
						((RoomCell) workingCell).setDoorDirection(DoorDirection.convert(cell.charAt(1)));
					}
					catch(EnumConstantNotPresentException e){
						layoutFileScanner.close();
						throw new BadConfigFormatException(
							"Board layout file at: " + this.layoutFile + " has a malformatted room...\nRow "+ row + " column " + column + 
							" has a second character indicative of a door, but which does not specifiy a direction. The error specifics"
							+ " are as follows:\nThe door direction was specified as -- " + e.constantName() + "\nThis direction is not found in the"
									+ " enumeration type " + e.enumType() + " which accepts:\n" + DoorDirection.acceptableDirections() + "\nas direction keys.");
					}
				}
				
				++column;
				cellsList.add(workingCell);
			}
		}
		while(layoutFileScanner.hasNextLine());
		
		numRows = row+1;
		layoutFileScanner.close();
	}

	public boolean loadConfigFiles(){			//Return true on success (even though failing would call an exception)
		try{
			loadRoomConfig();
			loadBoardConfig();
		}
		catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
			try {
				FileWriter stderr = new FileWriter("error.log");
				stderr.write(e.getMessage());
				stderr.flush();
				stderr.close();
			}
			
			catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("Loading configuration files failed, check the output or log for more information.");
		}
		return true;
	}
	
	public int calcIndex(int rowNum, int colNum){
		if(rowNum < 0 || colNum < 0)
			return -1;
		
		if(rowNum >= numRows || colNum >= numColumns)
			return -1;
		
		return rowNum*numColumns + colNum;
	}
	
	public void calcAdjacencies() {
		for(int cellIndex = numRows*numColumns -1; cellIndex > -1; --cellIndex){
			adjMtx.put(cellIndex, calcImmediateAdj(cellIndex));
		}
		
	}
	
	public boolean inBounds(int index){
		return (index < 0 || index > numRows*numColumns-1) ? false : true;
	}
	
	public LinkedList<Integer> calcImmediateAdj(int index){
		LinkedList<Integer> returnAdjacencyList = new LinkedList<Integer>();
		if(getCellAt(index).isRoom() && !getCellAt(index).isDoorway())
			return returnAdjacencyList;
		
		if(getCellAt(index).isDoorway())
			switch(((RoomCell)getCellAt(index)).getDoorDirection()){
			case UP:
				if(inBounds(index-numColumns)){
					returnAdjacencyList.add(index-numColumns);
					return returnAdjacencyList;
				}
				throw new ArrayIndexOutOfBoundsException(index-numColumns);
			
			case DOWN:
				if(inBounds(index+numColumns)){
					returnAdjacencyList.add(index+numColumns);
					return returnAdjacencyList;
				}
				throw new ArrayIndexOutOfBoundsException(index+numColumns);
			
			case LEFT:
				if(inBounds(index-1)){
					returnAdjacencyList.add(index-1);
					return returnAdjacencyList;
				}
				throw new ArrayIndexOutOfBoundsException(index-1);
		
			case RIGHT:
				if(inBounds(index+1)){
					returnAdjacencyList.add(index+1);
					return returnAdjacencyList;
				}
				throw new ArrayIndexOutOfBoundsException(index+1);
				
			case NONE:
				return returnAdjacencyList;
			}
		int rows = index/numColumns;
		int cols = index%numColumns;
		
		if(inBounds(calcIndex(rows,cols-1)) && (getCellAt(index-1).isWalkway() || (getCellAt(index-1).isDoorway() && ((RoomCell)getCellAt(index-1)).getDoorDirection() == DoorDirection.RIGHT)))
			returnAdjacencyList.push(index-1);

		if(inBounds(calcIndex(rows,cols+1)) && (getCellAt(index+1).isWalkway() || (getCellAt(index+1).isDoorway() && ((RoomCell)getCellAt(index+1)).getDoorDirection() == DoorDirection.LEFT)))
			returnAdjacencyList.push(index+1);

		if(inBounds(calcIndex(rows-1,cols)) && (getCellAt(index-numColumns).isWalkway() || (getCellAt(index-numColumns).isDoorway() && ((RoomCell)getCellAt(index-numColumns)).getDoorDirection() == DoorDirection.DOWN)))
			returnAdjacencyList.push(index-numColumns);

		if(inBounds(calcIndex(rows+1,cols)) && (getCellAt(index+numColumns).isWalkway() || (getCellAt(index+numColumns).isDoorway() && ((RoomCell)getCellAt(index+numColumns)).getDoorDirection() == DoorDirection.UP)))
			returnAdjacencyList.push(index+numColumns);
		
		return returnAdjacencyList;
	}	
		
	public void calcTargets(int row, int col, int steps){ // not working yet 
		
		visited = new boolean[numRows * numColumns];
		int thisSpot = calcIndex(row, col);
		
		targets.clear();
		
		for(int cellIndex = 0; cellIndex < (numRows * numColumns); ++cellIndex)
			visited[cellIndex] = false;
		
		visited[thisSpot] = true;
		Targets(thisSpot, steps);
	}
	
	public void Targets(int cell, int steps) {
	
		LinkedList<Integer> adjacentCells = new LinkedList<Integer>();
		visited[cell] = true;
		
		for (int adjCell : getAdjList(cell)) {
			if (!visited[adjCell]) {
				adjacentCells.add(adjCell);
			}
		}

		for (int adjCell : adjacentCells) {   // for each unvisited adjacent cell
			if (steps == 1 || getCellAt(adjCell).isDoorway()){						
				targets.add(getCellAt(adjCell));
			} else {
				Targets(adjCell, steps-1);
			}
		}
		visited[cell] = false;
	}

	public RoomCell getRoomCellAt( int row, int col){
		if(cellsList.get(calcIndex(row, col)).isRoom())
			return (RoomCell)cellsList.get(calcIndex(row, col));
		throw new ArrayIndexOutOfBoundsException();
	}

	public ArrayList<BoardCell> getCells() {
		return cellsList;
	}
	
	public BoardCell getCellAt(int cell){ 
		return cellsList.get(cell);
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public LinkedList<Integer> getAdjList(int cell) {
		return adjMtx.get(cell);
	}
	
	public Set<BoardCell> getTargets(){ 
		
		return targets;
		
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
		
}
