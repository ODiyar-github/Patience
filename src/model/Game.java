package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import model.BoardSituation;
import support.NiceStrings;

/**
 * @author sopr099
 * Beschreibt die Attribute die in jedem konrekten Game-Typ (Idiot/ Freecell/ Zank gesetzt werden müssen)
 */
public abstract class Game implements Serializable {

	/**
	 * Anzahl der Kartenstapel, die dieses Spiel benutzt.
	 */
	public static int numberOfStacks;
	

	/**
	 * Art des Spiels
	 */
	protected String gameType;

	/**
	 * Zeitpunkt, an dem das Spiel zuletzt gespeichert wurde.
	 */
	protected LocalDateTime date;

	/**
	 * Anzahl der Züge, die der Spieler getan hat. In Einspielerspielen wird jede Kartenbewegung gezählt. Bei Zweispielerspielen jeder Wechsel zwischen den Spielern.
	 */
	protected int countMoves;

	/**
	 * Ist der erste Spieler an der Reihe?
	 */
	protected boolean firstPlayersTurn;

	/**
	 * Die Stapel, die im Moment auf dem Spielfeld liegen
	 */
	protected BoardSituation currentBoardsituation;

//	/**
//	 * Liste aller Spielsituationen, die es bis jetzt gab, um undo und redo zu ermöglichen.
//	 */
//	protected LinkedList<BoardSituation> boardsituationList;

	/**
	 * Was ist der erste Spieler für ein Spieler?(Mensch, AI, ...)
	 */
	protected PlayerType firstPlayer;
	
	/**
	 * Was ist der zweite Spieler für ein Spieler?(Mensch, AI, ...)
	 */
	protected PlayerType secondPlayer;

	
	
	public Game(){}
	
	/**
	 * Aktuelles Bild des Games
	 */
	private String gameImagePath;
	public String getGameImagePath(){
		String date = NiceStrings.dateToString(this.getDate());
		String gameType = this.getGameType();
		this.gameImagePath=date+" "+gameType;
		return this.gameImagePath;
	}
	
	public BoardSituation getCurrentBoardsituation(){
		return this.currentBoardsituation;
	}
	
	public void setCurrentBoardsitution(BoardSituation boardsituation){
		this.currentBoardsituation=boardsituation;
	}

	public boolean isFirstPlayerTurn() {
		return firstPlayersTurn;
	}
	
	public void setFirstPlayersTurn(boolean firstPlayersTurn){
		this.firstPlayersTurn = firstPlayersTurn;
	}

	public PlayerType getFirstPlayer(){
		return firstPlayer;
	}
	
	public PlayerType getSecondPlayer(){
		return secondPlayer;
	}
	
	public int getCountMoves(){
		return countMoves;
	}
	
	public void incrementCountMoves(){
		countMoves++;
	}
	
	public LocalDateTime getDate(){
		return date;
	}
	
	public void setDate(LocalDateTime date){
		this.date = date;
	}
	
	public String getGameType(){
		return this.gameType;
	}
	
	public void setGameType(String gameType){
		this.gameType=gameType;
	}
}
