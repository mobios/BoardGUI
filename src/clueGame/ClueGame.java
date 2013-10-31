package clueGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame {
	private static ArrayList<Card> allCards;
	private static ArrayList<Card> allRoomCards;
	private static ArrayList<Card> allPeopleCards;
	private static ArrayList<Card> allWeaponCards;
	
	private ArrayList<Card> solution;
	private ArrayList<Player> players;
	
	private String playerConfig, CardsConfig;
	private final int solutionNum = Card.CardType.size;
	private Board board;
	private static boolean boardLoad = false, playerLoad = false, cardLoad = false, deal = false, sol = false;		//Most definately need
	private Random randGen;																							//to refactor state checking
	
	private JMenuBar menuBar;
	private DetectiveNotes notes;
	
/*	public ClueGame(String pConfig, String cConfig, String lConfig, String legCongic){
		this();
		this.playerConfig = pConfig;
		this.CardsConfig = cConfig;
		pb = new Board(lConfig, legCongic);
	}*/
	
	public ClueGame() {
		super();
		allCards = new ArrayList<Card>();
		allRoomCards = new ArrayList<Card>();
		allPeopleCards = new ArrayList<Card>();
		allWeaponCards = new ArrayList<Card>();
		
		solution = new ArrayList<Card>();
		players = new ArrayList<Player>();
		randGen = new Random(0);
		
		playerConfig = "players.txt";
		CardsConfig = "cards.txt";
		board = new Board();
		boardLoad = true;
		loadGameConfigFiles();
		deal();
		selectAnswer();
		
		menuBar = new JMenuBar();
		notes = new DetectiveNotes();
		
		setTitle("Clue Game");
		setSize(700, 725);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		add(board);
		
	}




	public void updateBoard(){ // for the game display



	}

	private JMenu createFileMenu() { // to create a MenuBar Menu named file

		JMenu menu = new JMenu("File"); 
		menu.add(createFileDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;

	}
	private JMenuItem createFileExitItem(){

		JMenuItem exitItem = new JMenuItem("Exit");

		class MenuItemListener implements ActionListener {

			public void actionPerformed(ActionEvent e){
				
				System.exit(0);
			}
		}

		exitItem.addActionListener(new MenuItemListener());
		return exitItem;
	}
	
	private JMenuItem createFileDetectiveNotesItem(){
		
		JMenuItem dNotesItem = new JMenuItem("Detective Notes");
		
		class MenuItemListener implements ActionListener { 
			public void actionPerformed(ActionEvent e){ 
				notes.setVisible(true);
			}
		}
		
		dNotesItem.addActionListener(new MenuItemListener());
		return dNotesItem;
	}


	public void setDebugSeed(long seed){
		randGen.setSeed(seed);
	}

	public void deal(){
		ArrayList<Card> deck = new ArrayList<Card>(allCards);
		assertState(cardLoad, "deal()", " card config loading");
		
		for(Player p : players){
			int it = (int)Math.ceil((Math.abs(randGen.nextGaussian())*3.5d)+.0000000001d); //moved to higher scope to debug
			while(it < 3){
				it += (int)Math.ceil((Math.abs(randGen.nextGaussian())*2.4d)+.0000000001d);
				it = (it > 5) ? it - (int)Math.ceil((Math.abs(randGen.nextGaussian())*2.8d)+.0000000001d) : it;
			}
			for(; it > 0 && deck.size() > 0; --it)
				p.addCard(deck.remove(Math.abs(randGen.nextInt(deck.size()))));
		}
			
		while(deck.size() > players.size())		
			for(Player p : players)
				p.addCard(deck.remove(randGen.nextInt(deck.size())));
			
		ArrayList<Player> lpl = new ArrayList<Player>();
		for(int i = deck.size(); i > 0;){
			Player pp = randPlayer(players);
			int pc = i/2+(int)Math.round(i/2*randGen.nextGaussian());
			if(pc < 0 || pc >= i)
				continue;
			if(lpl.contains(pp))
				continue;
			pp.addCard(deck.remove(pc));
			i--;
		}
		deal = true;
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException{
		assertState(boardLoad, "LoadPlayerConfig()", " board configuration loading");
		
		FileReader playerFileReader;
		
		try {
			playerFileReader = new FileReader(this.playerConfig);
		} 
		
		catch (FileNotFoundException e) {
			throw new BadConfigFormatException("The file at: "+ playerConfig +" cannot be found...\nHere is the full exception:\n\n" + e);
		}
		
		Scanner pfs = new Scanner(playerFileReader);
		int ln = -1;
		
		String badline = "The player configuration file at: " + playerConfig + " has a malformatted line...";
		
		while(pfs.hasNextLine()){
			++ln;
			String line = pfs.nextLine();
			if(line.trim().isEmpty())
				continue;
			
			String args[] = line.split(",");
			if(args.length != 6){
				pfs.close();
				throw new BadConfigFormatException(badline + "\nLine number " + ln + " has " + args.length + " fields instead of the expected six -- " + "\n  It has " + ((args.length != 0) ? args.length -1 : args.length) +
						" commas, instead of five.");
			}
			int i = 1;
			float hue;
			float saturation;
			float lum;
			int row;
			int column;
			
			try{
				hue = new Float(args[i]);
				i++;
				
				saturation = new Float(args[i]);
				i++;
				
				lum = new Float(args[i]);
				i++;
			}
			catch (NumberFormatException e){
				pfs.close();
				throw new BadConfigFormatException(badline + "\nField number " + i + " in line " + ln + " is not able to be represented as a floating point number.");
			}
			
			try{
				row = new Integer(args[i]);
				i++;
				
				column = new Integer(args[i]);
			}
			catch (NumberFormatException e){
				pfs.close();
				throw new BadConfigFormatException(badline + "\nField number " + i + " in line " + ln + " is not able to be represented as an integral data type number.");
			}
			
			if(!board.getCellAt(calcIndex(row, column)).isWalkway()){
				pfs.close();
				throw new BadConfigFormatException("Player " + args[0] + " on line " + ln +" has a specified start point of row: " + row + " and column: " + column +", which is not"
						+ " a walkway in the layout configuration file at: " + board.getLayoutFile() + "\nThe cell at that location is: " + board.getCellAt(calcIndex(row, column)).getClass());
			}
			
			players.add(((ln == 0) ? new HumanPlayer() : new ComputerPlayer()).set(args[0].trim(), null, Color.getHSBColor(hue, saturation, lum), board.getCellAt(calcIndex(row, column))));
		}
		pfs.close();
		playerLoad = true;
	}
	
	public void loadCardsConfig() throws BadConfigFormatException{
		assertState(boardLoad, "deal()", " board loading");
		assertState(playerLoad, "deal()", " player config loading");
		
		FileReader cardsFileReader;
		
		try {
			cardsFileReader = new FileReader(this.CardsConfig);
		} 
		
		catch (FileNotFoundException e) {
			throw new BadConfigFormatException("The file at: "+ playerConfig +" cannot be found...\nHere is the full exception:\n\n" + e);
		}
		
		Scanner cfs = new Scanner(cardsFileReader);
		int ln = -1;
		
		String badfile = "The card configuration file at: " + playerConfig + " ";
		String badline = badfile + "has a malformatted line...\n";
		String badfield = badfile + "has an invalid field...\n";
		
		while(cfs.hasNextLine()){
			++ln;
			String line = cfs.nextLine();
			if(line.trim().isEmpty())
				continue;
			
			String args[] = line.split(",");
			args[1] = args[1].trim();
			if(args.length != 2){
				cfs.close();
				throw new BadConfigFormatException(	badline + "Line number " + ln + " has " + args.length + " fields instead of the expected two -- " + "\n  It has " + ((args.length != 0) ? args.length -1 : args.length) +
													" commas, instead of one.");
			}
			
			if(args[0].isEmpty()){
				cfs.close();
				throw new BadConfigFormatException(badline + "Line number " + ln + " has no character type specifier!\nAcceptable specifiers are:\n" + Card.CardType.getValidTypes());
			}
			
			if(args[0].length() != 1){
				cfs.close();
				throw new BadConfigFormatException(badline + "Line number " + ln + " is not a single character type specifier!\nAcceptable specifiers are:\n" + Card.CardType.getValidTypes());				
			}
			
			if(args[1].isEmpty()){
				cfs.close();
				throw new BadConfigFormatException(badline + "Line number "+ ln + " has no card value string!\n\n  --The line is finished after the comma!");
			}
			
			char cic = args[0].charAt(0);
			Card.CardType cst = Card.CardType.getCardType(cic);
			if(cst == null){
				cfs.close();
				throw new BadConfigFormatException(badfield + "Line number " + ln + " has an invalid character type specifier!\n Acceptable specifiers are:\n"+ Card.CardType.getValidTypes());
			}
			
			switch(cst){
			case ROOM:
				if(!board.getRooms().containsValue(args[1])){
					cfs.close();
					throw new BadConfigFormatException(badfield + "Line number " + ln + " specifies room name " + args[1] + " that's not present in the loaded rooms file at " + board.getLegendFile() +". \n"
							+ "I have these rooms loaded:\n" + board.getRooms());
				}
				allRoomCards.add(new Card(args[1], cst));
				break;
				
			case PERSON:
				if(!getPlayerNames().contains(args[1])){
					cfs.close();
					throw new BadConfigFormatException(badfield + "Line number " + ln + " specifies player " + args[1] + " that's not present in the loaded player config file at " +
														playerConfig + "\nI have these players loaded:\n" + getPlayerNames());
				}
				allPeopleCards.add(new Card(args[1], cst));
				break;
				
			default:
				allWeaponCards.add(new Card(args[1], cst));
				break;

			}
			allCards.add(new Card(args[1],Card.CardType.getCardType(cic)));
		}
		cfs.close();
		cardLoad = true;
	}
	
	public void loadGameConfigFiles(){
		try{
			loadPlayerConfig();
			loadCardsConfig();
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
		
		
	}
	
	public void selectAnswer(){
		assertState(deal, "selectAnswer()", " deal()");
		
		ArrayList<Player> tempPlayers = new ArrayList<Player>(players);
		selectAnswer(Card.CardType.WEAPON, Card.CardType.WEAPON, tempPlayers);
	}
	
	private void selectAnswer(Card.CardType type, Card.CardType start, ArrayList<Player> tempPlayers){ // Generates a random solution
		ArrayList<Player> largest = null;
		for(Player p: tempPlayers){
			if(largest == null){
				largest = new ArrayList<Player>();
				largest.add(p);
				continue;
			}
			
			if(largest.get(0).getMyCards().size() < p.getMyCards().size()){
				largest = new ArrayList<Player>();
				largest.add(p);
				continue;
			}
			
			if(largest.get(0).getMyCards().size() == p.getMyCards().size()){
				largest.add(p);
				continue;
			}
		}
		if(largest == null)
			return;
		
		tempPlayers.removeAll(largest);
			
		while(largest.size() > 0 && type != null){
			Player unlucky = randPlayer(largest);
			if(unlucky.getCardTypes().contains(type)){
				solution.add(unlucky.popRandCard(type, randGen));
				type = type.advance(start);
			}
			largest.remove(unlucky);
		}
		
		selectAnswer(type, start, tempPlayers);
		sol = true;
	}
		
	public boolean checkAccusation(Player accuser){// STANDARD ACCUSATION GATEWAY
		return checkAccusation(accuser.accuse(randGen));
	}
	
	public boolean checkStringAccusation(String person, String weapon, String room){  // NOT FOR RELEASE USE, DEVELOPMENT ONLY		
		return checkAccusation(Card.stringSuggestion(person, weapon, room));
	}

	private boolean checkAccusation(ArrayList<Card> cards){// INTERNAL CLASS USE ONLY
		assertState(sol, "All CheckAccusation()'s", "deal()");
		boolean debugbreakout =  solution.containsAll(Arrays.asList(cards));
		if(debugbreakout && solution.size() == cards.size())
			return true;
		return false;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public int calcIndex(int rows, int cols){
		return board.calcIndex(rows, cols);
	}
	
	public String getPlayerNames(){
		String ret = "";
		for(Player p : players)
			ret += p.getName() + "\n";
		return ret;
	}
	
	public String[] getSolutionStrings(){
		String[] ret = new String[solutionNum];
		for(int i = 0; i < solutionNum; i++)
			ret[i] = solution.get(i).getName();
		return ret;
	}

	private Player randPlayer(ArrayList<Player> listArg){
		if(listArg == null || listArg.isEmpty())
			return null;
		int possibleIndex=-1;
		while(possibleIndex < 0 || possibleIndex >= listArg.size())
			possibleIndex = Math.round(listArg.size()-1 * randGen.nextFloat());
		return listArg.get(possibleIndex);
	}

	public static ArrayList<Card> getAllCards() {
		assertState(cardLoad,"GetAllCards", "loadCardsConfig()");
		return allCards;
	}
	
	public static ArrayList<Card> getAllRoomCards() {
		assertState(cardLoad,"GetAllRoomCards", "loadCardsConfig()");
		return allRoomCards;
	}
	
	public static ArrayList<Card> getAllPeopleCards() {
		assertState(cardLoad,"GetAllPeopleCards", "loadCardsConfig()");
		return allPeopleCards;
	}
	
	public static ArrayList<Card> getAllWeaponCards() {
		assertState(cardLoad,"GetAllWeaponCards()", "loadCardsConfig()");
		return allWeaponCards;
	}
	
	public static ArrayList<String> getAllRoomIdent(){
		ArrayList<String> ret = new ArrayList<String>();
		for(Card c : getAllRoomCards())
			ret.add(c.getName());
		return ret;
	}
	
	public static ArrayList<String> getAllPeopleIdent(){
		ArrayList<String> ret = new ArrayList<String>();
		for(Card c : getAllPeopleCards())
			ret.add(c.getName());
		
		return ret;
	}
	
	public static ArrayList<String> getAllWeaponIdent(){
		ArrayList<String> ret = new ArrayList<String>();
		for(Card c : getAllWeaponCards())
			ret.add(c.getName());
		
		return ret;
	}
	
	public static void assertState(boolean stateSpecifier, String after, String before){
		if(!stateSpecifier)
			throw new IllegalStateException("   -FATAL-  \n" + after + " --MUST-- be called after " + before + "!");
	}
	
	public static <T> void assertArgument(T given, Collection<T> possibilities){
		if(possibilities.contains(given))
			return;
		
		StackTraceElement[] callingStack = Thread.currentThread().getStackTrace();
		StackTraceElement caller = callingStack[3];
		StackTraceElement callee = callingStack[2];
		
		String parameters = "";
		for(T obj : possibilities)
			parameters += obj + "\n";
		
		
		throw new IllegalArgumentException("\n\n              -FATAL-  \n  --INVALID ARGUMENT--  \n\n" + "IN CLASS: " + caller.getClassName() +
				"\nIN FILE: " + caller.getFileName() + "\nON LINE: " + caller.getLineNumber() +
				"\nMETHOD " + caller.getMethodName() + " CALLS " + callee.getMethodName() + 
				((callee.getClass() != caller.getClass()) ? ("\nIN CLASS: " + callee.getClassName() + 
				"\nIN FILE: " + callee.getFileName()) : "") + "\nWITH PARAMETER " + given + 
				" OF TYPE " + given.getClass() + "\n\nThis does not meet any of the possible parameters:" + parameters);
	}
	
	public static <T> T getRandFromCollection(Random rand, List<T> list){
		return list.get(rand.nextInt(list.size()));
	}

	public Board getBoard() {
		return board;
	}

	public Random getRandGen() {
		return randGen;
	}
	
	public static void main(String[] args) {
		
		ClueGame game = new ClueGame();
		game.setVisible(true);
		
	}
	
}
