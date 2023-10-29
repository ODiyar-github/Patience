package control;
import java.util.ArrayList;
import java.util.List;

import model.BoardSituation;
import model.Card;
import model.Game;
import model.Stack;
import support.Move;
import view.BoardAUI;

public abstract class GameController {

	protected PatienceController patienceController;
	
	/**
	 * Das Spiel, auf das der Controller zugreift.
	 */
	protected Game game;

	private List<BoardAUI> boardAUIList;

	/**
	 *  
	 */
	public GameController(PatienceController patienceController, Game game) {
				this.patienceController=patienceController;
				this.game = game;
				this.boardAUIList = new ArrayList<BoardAUI>();
	}
	
	/**
	 * führt den übergebenen Zug aus und Referesht das Board
	 * @param move
	 */
	public void doMove(Move move) {
		BoardSituation currentBoardSituation = game.getCurrentBoardsituation();
		
		BoardSituation newSituation = doMove(currentBoardSituation, move);
		
		currentBoardSituation.setNext(newSituation);
		
		this.game.setCurrentBoardsitution(newSituation);
		
		callRefreshBoard(move);
	}
	
	
	/**
	 * Gibt das Ergebnis des übergebenen Zugs aus, ohne ihn auszuführen.
	 * @param boardSituation
	 * 		BoardSituation, auf der der Zug ausgeführt werden soll
	 * @param move
	 * 		Zug, der ausgeführt werden soll
	 * @return
	 * 		das Ergebnis
	 */
	public BoardSituation doMove(BoardSituation boardSituation, Move move){
		int sourcePos = move.getSourceStack().getArrPosition();
		int destinationPos = move.getDestinationStack().getArrPosition();
		Stack transferStack = move.getTransferStack().clone();
		
		for(Card card : transferStack.getCardList()){
			card.setVisible(true);
		}
		
		BoardSituation newSituation = boardSituation.clone();
		
		BoardController.removeFromStack(newSituation.getStackArray()[sourcePos], transferStack);
		
		BoardController.addToStack(newSituation.getStackArray()[destinationPos], transferStack);
		
		return newSituation;
	}
	
	/**
	 *  Führt den Zug aus, der durch die ausgewählten Karten beschrieben wird, wenn er erlaubt ist und fürht alle automatischen Aktionen aus, die damit zusammenhängen.
	 */
	public void move(Card transferCard, Stack destinationStack){
		//die transferCard und der destinationStack werden zu einem move zusammen gefügt.
		Stack sourceStack = transferCard.getCurrentStack();
		Stack transferStack = BoardController.cardToTransferStack(transferCard);
		
		Move move = new Move(sourceStack, destinationStack, transferStack);
		
		//dieser move wird ausgeführt
		move(move);
	}
	
	/** 
	 * Wird in den Klassen Idiot-, Freecell- und ZankController implementiert.
	 * Führt den Zug aus, der durch die ausgewählten Karten beschrieben wird, wenn er erlaubt ist und fürht alle automatischen Aktionen aus, die damit zusammenhängen.
	 * @param move
	 * 	der move der ausgeführt werden soll
	 */
	public abstract void move(Move move);
	
	/**
	 * simuliert einen move, ohne das Spiel zu verändern. Es wird die Boardsituation zurückgegeben, die nach diesem move vorhanden wäre
	 * @param boardSituation
	 * @param move
	 * @return
	 */
	public abstract BoardSituation simulateMove(BoardSituation boardSituation, Move move);

	/**
	 * aktualisiert das angezeigte Spielfeld
	 * @param move der Zug, der das Spielfeld geändert hat und, den die GUI animinieren soll.
	 */
	void callRefreshBoard(Move move) {
		for(BoardAUI boardAUI : boardAUIList){
			boardAUI.refreshBoard(move);
		}
	}
	
	void callRefreshGameFinished(){
		for(BoardAUI boardAUI : boardAUIList){
			boardAUI.refreshGameFinished();
		}
	}
	
	/**
	 *  fügt eine BoardAUI hinzu
	 *  @param boardAUI
	 */
	
	public void addBoardAUI(BoardAUI boardAUI) {
		boardAUIList.add(boardAUI);
	}
	
	
	/**
	 * Macht den letzten Zug rückgängig
	 */
	public void undo(){
		BoardSituation previous = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getPrevious();
		
		if(previous != null){
			patienceController.getPatience().getCurrentGame().setCurrentBoardsitution(previous);
		}
		
		callRefreshBoard(null);
	}
	
	/**
	 * Macht das Rückgängigmachen rückgängig
	 */
	public void redo(){
		BoardSituation next = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getNext();
		
		if(next != null){
			patienceController.getPatience().getCurrentGame().setCurrentBoardsitution(next);
		}
		
		callRefreshBoard(null);
	}
	
	/**
	 * Gibt zurück, ob es eine Spielsituation gibt, die über undo erreicht werden kann
	 * @return
	 */
	public boolean hasPrevious(){
		return patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getPrevious() != null;
	}
	
	/**
	 * Gibt zurück, ob es eine Spielsituation gibt, die über redo erreicht werden kann
	 * @return
	 */
	public boolean hasNext(){
		return patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getNext() != null;
	}
	
	/**
	 *  wird in den Klassen Idiot-, Freecell- und ZankController implementiert
	 *  prüft, ob der übergebene Zug nach den jeweiligen Regeln erlaubt ist
	 * @param move
	 * @return
	 */
	public abstract boolean isLegalMove(Move move);
	
	/**
	 * Methode für Züge, die nur einen Klick auf den Talon und kein Ziel erwarten. Wenn die übergebene Karte, auf dem Talon liegt, wird true zurückgegeben
	 * und der Zug, der in diesem konkreten Spiel mit dem Talon verbunden ist, ausgeführt. Wenn die Karte nicht vom Talon kommt, wird false
	 * zurückgegeben und sonst nichts getan. Die GUI kann auf ein vom Spieler bestimmtes Ziel warten.
	 * @param card
	 * 		Karte, die der Spieler ausgewählt hat.
	 * @return
	 * 		Hat diese Karte gereicht,  um einen Zug auszuführen, bzw. wird kein Ziel mehr benötigt?
	 */
	public abstract boolean cardFromTalon(Card card);
	
	/**
	 * Hat der Spieler, der an der Reihe ist, das Spiel gewonnen?
	 * @return
	 * 		true, wenn der Spieler gewonnen hat. false sonst.
	 */
	public abstract boolean isGameWon(BoardSituation boardSituation);
	
	public boolean isGameWon(){
		return isGameWon(game.getCurrentBoardsituation());
	}
	
	/**
	 * Gibt zurück, ob man die übergebene Karte nach den Regeln des aktuellen Spiels bewegen kann.
	 * @param card
	 * @return
	 */
	public abstract boolean isMovable(Card card);

	
	/**
	 * Gibt zurück, wie viele Karten noch auf dem Spielfeld über sind und, die man noch hätte weglegen müssen.
	 * @return
	 */
	public abstract int cardsLeft();
}
