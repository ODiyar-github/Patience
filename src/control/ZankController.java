package control;
import java.util.LinkedList;
import java.util.List;

import model.BoardSituation;
import model.Card;
import model.Rank;
import model.Stack;
import model.Suit;
import model.Zank;
import support.Move;

/**
 * @author sopr099
 * Controller für Zankpartie
 */
public class ZankController extends GameController {

	public ZankController(PatienceController patienceController, Zank game) {
		super(patienceController, game);
	}
	
	/**
	 * Legt, falls es ein legaler Zug ist die transferCard auf den destinationStack.
	 * Danach wird über isGameWon() überprüft ob die Partie gewonnen ist und die Partie 
	 * gegebenenfalls beendet
	 * 
	 * @param tranferCard Die Karte die verschoben werden soll
	 * @param destinationStack Der Stapel auf den die Karte gelegt werden soll
	 */
	@Override
	public void move(Move move) {
		LinkedList<Move> moveList = move(game.getCurrentBoardsituation(), move);
		boolean firstMove = true;
		
		for(Move toDoMove : moveList){
			doMove(toDoMove);
			
			if(firstMove && isWrongMove(toDoMove, game.getCurrentBoardsituation())){
				automaticalMovesAtStart();
				endTurn();
				return;
			}
				
			firstMove = false;
			
			game.incrementCountMoves();
		}
		
		Boolean won = isGameWon();
		if(won){
			callRefreshGameFinished();
			this.patienceController.finishGame(won);
			return;
		}
		
		if(isLastMove(move)){
			endTurn();
			return;
		}
		
		moveTrayToTalon(game.getCurrentBoardsituation());
		callRefreshBoard(null);
	}
	
	private boolean isWrongMove(Move move, BoardSituation situation){
		if(move.getDestinationStack().getArrPosition() >= Zank.FIRST_CLUB_STACK){
			return false;
		}
		else{
			Stack sourceStack = move.getSourceStack();
			Stack transferStack = move.getTransferStack();
			Stack destinationStack;
			for(int j = Zank.FIRST_CLUB_STACK; j <= Zank.SECOND_HEART_STACK; j++){
				destinationStack = situation.getStackArray()[j];
				move = new Move(sourceStack, destinationStack, transferStack);
				
				if(isLegalMove(move)){
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public BoardSituation simulateMove(BoardSituation boardSituation, Move move) {
		LinkedList<Move> moveList = move(boardSituation, move);
		
		for(Move toDoMove : moveList){
			boardSituation = doMove(boardSituation, toDoMove);
		}
		
		Boolean won = isGameWon(boardSituation);
		if(won){
			return boardSituation;
		}
		
		if(isLastMove(move)){
			return boardSituation;
		}
		
		moveTrayToTalon(boardSituation);
		
		return boardSituation;
	}
	
	/**
	 * Führt den Zug aus, der durch die ausgewählten Karten beschrieben wird, wenn er erlaubt ist und fürht alle automatischen Aktionen aus, die damit zusammenhängen,
	 * ohne das aktuelle Spiel zu verändern. Es wird eine Liste der moves zurückgegeben, die ausgeführt werden müssten
	 * 
	 * @param boardSituation
	 * @param move
	 * @return
	 */
	public LinkedList<Move> move(BoardSituation boardSituation, Move move) {
		Stack sourceStack = move.getSourceStack();
		Stack[] stackArr = boardSituation.getStackArray();
		boolean isFirstPlayerTurn = game.isFirstPlayerTurn();
		
		LinkedList<Move> moveList = new LinkedList<Move>();
		
		if(isLegalMove(move)){
			moveList.add(move);
			boardSituation = doMove(boardSituation, move);
		}
		//Wenn die Karte vom Talon genommen und nicht korrekt angelegt wurde, muss der Zug beendet werden.
		else if(isFirstPlayerTurn && sourceStack.getArrPosition() == Zank.FIRST_TALON){
			//bewegt die oberste Karte des Talons auf den Ablagestapel, wodurch der Zug beendet wird.
			move.setDestinationStack(stackArr[sourceStack.getArrPosition() + 2]);
			moveList.add(move);
			boardSituation = doMove(boardSituation, move);
		}
		else if(!isFirstPlayerTurn && sourceStack.getArrPosition() == Zank.SECOND_TALON){
			//bewegt die oberste Karte des Talons auf den Ablagestapel, wodurch der Zug beendet wird.
			move.setDestinationStack(stackArr[sourceStack.getArrPosition() + 2]);
			moveList.add(move);
			boardSituation = doMove(boardSituation, move);
		}
		
		//möglicherweise, müssen Karten von außen in die Mitte gelegt werden
		LinkedList<Move> automaticalMoveList = automaticalMoves(boardSituation);
		
		moveList.addAll(automaticalMoveList);
		
		return moveList;
	}

	/**
	 * Prüft anhand der Spiel logik, ob der gemachte zug gültig ist oder nicht.
	 * Dabei werden von vorneherein nur Moves zur Überprüfungs zugelassen für die isMovable() gilt
	 * @param move = prüft, ob der Zug gültig ist.
	 */
	public boolean isLegalMove(Move move) {
		Stack transferStack = move.getTransferStack();
		Stack destinationStack = move.getDestinationStack();
		Card transferCard = transferStack.getTopCard();
		Card destinationCard = destinationStack.getTopCard();
		int arrPosition = destinationStack.getArrPosition();
		boolean firstPlayerTurn = game.isFirstPlayerTurn();
		
		//Man darf nicht auf sich selbst legen
		if(arrPosition == move.getSourceStack().getArrPosition()){
			return false;
		}
		
		//Man darf nicht auf Talons legen
		if(arrPosition >= Zank.FIRST_TALON && arrPosition <= Zank.SECOND_TALON) {
			return false;
		}
		
		//Überprüft die Gültigkeit falls der Zielstapel einer der äußeren Stapel ist
		if(arrPosition >= Zank.FIRST_OUTER_STACK && arrPosition <= Zank.EIGHTH_OUTER_STACK) {
			return isLegalMoveToOuterStack(transferCard, destinationCard);
		}
		
		//Überprüft die Gültigkeit falls der Zielstaper einer der inneren Stapel ist
		if(arrPosition >= Zank.FIRST_CLUB_STACK && arrPosition <= Zank.SECOND_HEART_STACK) {
			return isLegalMoveToInnerStack(transferCard, destinationCard, suitOfInnerStack(arrPosition));
		}
		
		return isLegalMoveToPlayerStack(transferCard, destinationCard, arrPosition, firstPlayerTurn);
	}
	
	private Suit suitOfInnerStack(int arrPosition){
		if(arrPosition >= Zank.FIRST_CLUB_STACK && arrPosition <= Zank.SECOND_CLUB_STACK) {
			return Suit.CLUB;
		}
		
		if(arrPosition >= Zank.FIRST_SPADE_STACK && arrPosition <= Zank.SECOND_SPADE_STACK) {
			return Suit.SPADE;
		}
		
		if(arrPosition >= Zank.FIRST_HEART_STACK && arrPosition <= Zank.SECOND_HEART_STACK) {
			return Suit.HEART;
		}
		
		if(arrPosition >= Zank.FIRST_DIAMOND_STACK && arrPosition <= Zank.SECOND_DIAMOND_STACK) {
			return Suit.DIAMOND;
		}
		
		return null;
	}
	
	private boolean isLegalMoveToOuterStack(Card transferCard, Card destinationCard){
		
		//Auf leere äußere Stacks darf man immer legen
		if(destinationCard == null){
			return true;
		}
		else if(transferCard.isRed() == destinationCard.isRed()) {
			return false;
		}
		else{
			return transferCard.getRank().ordinal()+1 == destinationCard.getRank().ordinal();
		}
	}
	
	private boolean isLegalMoveToInnerStack(Card transferCard, Card destinationCard,  Suit suit){
		if(transferCard.getSuit() != suit) {
			return false;
		}
		else if(destinationCard==null){
			return transferCard.getRank() == Rank.ACE;
		}
		else if(transferCard.getRank().ordinal() == destinationCard.getRank().ordinal() + 1) {
				return true;
		}
		else {
				return false;
		}
	}
	
	private boolean isLegalMoveToPlayerStack(Card transferCard, Card destinationCard, int arrPosition, boolean firstPlayerTurn){
		if(firstPlayerTurn) {
			if(arrPosition == Zank.FIRST_TRAY) {
				return true;
			}
			else if(arrPosition == Zank.FIRST_SUBSTACK) {
				return false;
			}
		}
		else if(arrPosition == Zank.SECOND_TRAY) {
				return true;
			}
		else if(arrPosition == Zank.SECOND_SUBSTACK) {
				return false;
		}
		return (transferCard.getSuit() == destinationCard.getSuit()) && (Math.abs(transferCard.getRank().ordinal() - destinationCard.getRank().ordinal()) == 1);
	}
	
	/**
	 * Führt die automatischen Züge aus, die die äußeren Karten auf die inneren Stapel legen.
	 */
	public LinkedList<Move> automaticalMoves(BoardSituation situation){
		Stack sourceStack;
		Stack destinationStack;
		Stack transferStack;
		Card transferCard;
		LinkedList<Move> moveList = new LinkedList<Move>();
		Move move;

		
		boolean moveDone = true;
		
		while(moveDone){
			moveDone = false;
			for(int i = Zank.FIRST_OUTER_STACK; i <= Zank.EIGHTH_OUTER_STACK; i++){
				sourceStack = situation.getStackArray()[i];
				transferCard = sourceStack.getTopCard();
				if(transferCard == null)break;
				transferStack = BoardController.cardToTransferStack(transferCard);
				for(int j = Zank.FIRST_CLUB_STACK; j <= Zank.SECOND_HEART_STACK; j++){
					destinationStack = situation.getStackArray()[j];
					move = new Move(sourceStack, destinationStack, transferStack);
					
					if(isLegalMove(move)){
						moveList.add(move);
						situation = doMove(situation, move);
						moveDone = true;
						break;
					}
				}
			}
		}
		return moveList;
	}
	
	/**
	 * Überprüft welcher Spieler am Zug ist und deckt die oberste Karte seines Talons auf
	 * wird nur innerhalb von initializeTurn() aufgerufen 
	 */
	private void flipCard(boolean firstPlayerTurn){
		Stack[] currentStackArr = game.getCurrentBoardsituation().getStackArray();
		Card upperCard =  currentStackArr[Zank.FIRST_TALON].getTopCard();
		
		if(!firstPlayerTurn){
			upperCard = currentStackArr[Zank.SECOND_TALON].getTopCard();
		}
		if(upperCard != null){
			upperCard.setVisible(true);
		}
	}
	

	 /* Initialisiert die Runde, ob bei dem aktuellen Spieler der Nachziehstapel leer ist 
	 * und reagier entsprechend, außerdem wird die oberste Karte des Nachziehstapel umgedreht 
	 */
	public void initializeTurn() {
		boolean firstPlayerTurn = game.isFirstPlayerTurn();
		
		moveTrayToTalon(game.getCurrentBoardsituation());
		
		flipCard(firstPlayerTurn);
		
		callRefreshBoard(null);
	}
	
	public void automaticalMovesAtStart(){
		List<Move> automaticalMoves = automaticalMoves(game.getCurrentBoardsituation());
		
		for(Move move : automaticalMoves){
			doMove(move);
		}
	}


	@Override
	public boolean cardFromTalon(Card card) {
		if(card.getCurrentStack().getArrPosition() == Zank.FIRST_TALON || card.getCurrentStack().getArrPosition() == Zank.SECOND_TALON){
			//flipCard(game.isFirstPlayerTurn());
			return true;
		}
		return false;
	}

	/**
	 * Gibt zurück ob der aktuelle Spieler die Gewinnbedingungen für eine Zank-Partie erfüllt
	 */
	@Override
	public boolean isGameWon(BoardSituation boardSituation) {
		boolean firstPlayerTurn = game.isFirstPlayerTurn();
		
		Stack[] stackArr = boardSituation.getStackArray();
		Stack firstTalon = stackArr[Zank.FIRST_TALON];
		Stack firstSubStack = stackArr[Zank.FIRST_SUBSTACK];
		Stack firstTray = stackArr[Zank.FIRST_TRAY];
		Stack secondTalon = stackArr[Zank.SECOND_TALON];
		Stack secondSubStack = stackArr[Zank.SECOND_SUBSTACK];
		Stack secondTray = stackArr[Zank.SECOND_TRAY];
		
		//Überprüft ob der aktuelle Spieler seinen Nachzieh und Ersatzstapel komplett geleert hat
		if(firstPlayerTurn) {
			if(firstTalon.getCardList().isEmpty() && firstSubStack.getCardList().isEmpty() && firstTray.getCardList().isEmpty()) {
				return true;
			}
		}
		else {
			if(secondTalon.getCardList().isEmpty() && secondSubStack.getCardList().isEmpty() && secondTray.getCardList().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/* 
	 * Überprüft an Hand des Ursprungsstapels ob die übergebene Karte bewegbar ist
	 */
	@Override
	public boolean isMovable(Card card) {
		boolean firstPlayerTurn = game.isFirstPlayerTurn();
		int arrPosFromStack = card.getCurrentStack().getArrPosition();
		
		if(arrPosFromStack >= Zank.FIRST_OUTER_STACK && arrPosFromStack <= Zank.EIGHTH_OUTER_STACK) {
			return true;
		}
		
		
		if(firstPlayerTurn) {
			return arrPosFromStack == Zank.FIRST_SUBSTACK || arrPosFromStack == Zank.FIRST_TALON || arrPosFromStack == Zank.FIRST_TRAY;
		}
		else {
			return arrPosFromStack == Zank.SECOND_SUBSTACK || arrPosFromStack == Zank.SECOND_TALON || arrPosFromStack == Zank.SECOND_TRAY;
		}
	}
	
	/**
	 * @param firstPlayerTurn
	 * 		Wenn true übergeben wird wird die Anzahl der übrigen Karten des ersten Spielers zurückgegeben,
	 * 		sonst die des zweiten Spielers
	 * @retur
	 *  	Anzahl der übrigens Karten
	 */
	public int cardsLeft(boolean firstPlayerTurn){
		BoardSituation situation = game.getCurrentBoardsituation();

		int sum = 0;
		
		if(firstPlayerTurn){
			sum += situation.getStackArray()[Zank.FIRST_TALON].getCardList().size();
			sum += situation.getStackArray()[Zank.FIRST_SUBSTACK].getCardList().size();
			sum += situation.getStackArray()[Zank.FIRST_TRAY].getCardList().size();
		}
		else{
			sum += situation.getStackArray()[Zank.SECOND_TALON].getCardList().size();
			sum += situation.getStackArray()[Zank.SECOND_SUBSTACK].getCardList().size();
			sum += situation.getStackArray()[Zank.SECOND_TRAY].getCardList().size();
		}
		
		return sum;
	}
	
	/**
	 * Prüft ob der Spieler durch diesen Move seinen Zug beendet hat.
	 * @param move
	 * 		Der Move den der Spieler getan hat
	 * @return
	 */
	private boolean isLastMove(Move move){
		Stack sourceStack = move.getSourceStack();
		Stack destinationStack = move.getDestinationStack();
		
		if(game.isFirstPlayerTurn()){
			return sourceStack.getArrPosition() == Zank.FIRST_TALON && destinationStack.getArrPosition() == Zank.FIRST_TRAY;
		}
		else{
			return sourceStack.getArrPosition() == Zank.SECOND_TALON && destinationStack.getArrPosition() == Zank.SECOND_TRAY;
		}
	}
	
	/**
	 * Wechselt zum anderen Spieler und initialisiert seinen nächsten Zug
	 */
	public void endTurn(){
		game.setFirstPlayersTurn(!game.isFirstPlayerTurn());
		initializeTurn();
	}
	
	public void pass(){
		System.out.println("Passen");
		
		Stack[] stackArr = game.getCurrentBoardsituation().getStackArray();
		Stack talon = stackArr[Zank.FIRST_TALON];
		Stack tray = stackArr[Zank.FIRST_TRAY];
		
		if(!game.isFirstPlayerTurn() ) {
			talon = stackArr[Zank.SECOND_TALON];
			tray = stackArr[Zank.SECOND_TRAY];
		}
		
		if(!talon.getCardList().isEmpty()){
			Move move = new Move(talon, tray, BoardController.cardToTransferStack(talon.getTopCard()));
			doMove(move);
		}
		
		endTurn();
	}
	
	/**
	 * Wenn, das Spiel vorzeitig beendet wird (z.B. wenn kein Zug mehr möglich ist) gewinnt der Spieler, der weniger Karten über hat.
	 * Diese Methode errechnet, ob der aktuelle Spiele nach diesen Regeln gewonnen hat.
	 * @return
	 */
	public boolean currentPlayerBetter(){
		int firstPlayerSum = cardsLeft(true);
		int secondPlayerSum = cardsLeft(false);
		
		if(game.isFirstPlayerTurn()){
			return firstPlayerSum < secondPlayerSum;
		}
		
		return secondPlayerSum < firstPlayerSum;
	}
	
	/**
	 * Legt die Karten des Ablagestapels auf den Talon, wenn der Talon leer ist.
	 */
	private void moveTrayToTalon(BoardSituation boardSituation){
		Stack [] stackArray = boardSituation.getStackArray();
		Stack talon = stackArray[Zank.FIRST_TALON];
		Stack tray = stackArray[Zank.FIRST_TALON];
	
		if(!(game.isFirstPlayerTurn())) {
			talon = stackArray[Zank.SECOND_TALON];
			tray = stackArray[Zank.SECOND_TRAY];
		}

		if(talon.getCardList().isEmpty()) {
				BoardController.flipStack(tray);
				
				talon.setArrPosition(talon.getArrPosition() + 2);
				tray.setArrPosition(tray.getArrPosition() - 2);
				
				stackArray[talon.getArrPosition()] = talon;
				stackArray[tray.getArrPosition()] = tray;
				
				BoardController.switchCoordinates(talon, tray);
		}
	}

	@Override
	public int cardsLeft() {
		return cardsLeft(game.isFirstPlayerTurn());
	}
}
