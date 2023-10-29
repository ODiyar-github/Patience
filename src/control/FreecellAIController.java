package control;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import control.AIController.Tuple;
import model.BoardSituation;
import model.Card;
import model.Freecell;
import model.Game;
import model.Stack;
import support.Move;

public class FreecellAIController extends AIController {
	private static final double EMPTY_FREECELL_POINTS = 3;
	private static final double EMPTY_STACK_POINTS = 3.5;
	private static final double HOMECELL_POINTS = 4;
	private static final double SORTED_POINTS = 1;
	private static final double BURIED_CARD_POINTS = -1;
	private static final int MAX_COMPUTE_TIME = 25000;
	private static final int ABORT_TIME = 35000;
	private static final int MAX_DEPTH = 10;
	private static final int MIN_DEPTH = 5;
	private static final int START_DEPTH = 7;
	private long startTime = 0;
	private int depth;
	

	public FreecellAIController(PatienceController patienceController){
		super(patienceController);
		depth = START_DEPTH;
	}
	
	/**
	 * @see AIController#aiTurn()
	 * 
	 *  
	 */
	public boolean aiTurn() {
		if(firstCall){
			patienceController.getGameController().callRefreshBoard(null);
			firstCall = false;
			return true;
		}
		if(patienceController.getPatience().getCurrentGame() == null){
			return false;
		}
		BoardSituation currentBoardSituation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		
		if(moveStack.isEmpty()){
			System.out.println("Berechne (Tiefe " + depth + ")...");
			
			startTime =  System.currentTimeMillis();
 			Tuple<Double, java.util.Stack<Move>> tuple = computeMoves(depth, currentBoardSituation);
			int duration = (int)(System.currentTimeMillis() - startTime);
			System.out.println("Berechnung fertig (nach " + (Math.round((double)duration / 1000.0)) + " Sekunden). Bewertung der Lösung: " + tuple.firstElement);
			moveStack = tuple.secondElement;
			
			if(!moveStack.isEmpty() && duration > MAX_COMPUTE_TIME && depth > MIN_DEPTH){
				depth--;
			}
			else if(moveStack.isEmpty()) {
				if(depth < MAX_DEPTH){
					depth++;
					aiTurn();
				}
				else{
					System.out.println("Ich komm nicht weiter :(");
					return false;
				}
			}
		}
		if(!moveStack.isEmpty()){
			Move move = moveStack.pop();
			patienceController.getGameController().move(move);
			System.out.println(move);
			return true;
		}
		return false;
	}
	
	/**
	 * @see AIController#tip()
	 */
	public Move tip() {
		int deepness = 4;
		BoardSituation currentBoardSituation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		startTime =  System.currentTimeMillis();
		java.util.Stack<Move> stack = computeMoves(deepness, currentBoardSituation).secondElement;
		
		while(stack.isEmpty() && deepness < 7){
			deepness++;
			startTime =  System.currentTimeMillis();
			stack = computeMoves(deepness, currentBoardSituation).secondElement;
		}
		if(!stack.isEmpty()){
			return stack.pop();
		}
		else{
			return null;
		}
	}

	@Override
	public boolean abort(BoardSituation situation) {
		if((System.currentTimeMillis() - startTime) > ABORT_TIME){
			return true;
		}
		return false;
	}

	@Override
	public double evaluate(BoardSituation situation) {
		if(patienceController.getGameController().isGameWon(situation)){
			return -1;
		}
		double sortedCardPoints = 0;
		double cardsInHomecells = 0;
		double emptyStacks = 0;
		double emptyFreecells = 0;
		Stack[] stackArray = situation.getStackArray();
		
		for(int i = Freecell.FIRST_STACK; i <= Freecell.EIGTH_STACK; i++){
			sortedCardPoints += sortedCardsPoints(stackArray[i]);
			if(stackArray[i].getCardList().isEmpty()){
				emptyStacks++;
			}
		}
		for(int i = Freecell.FIRST_HOMECELL; i <= Freecell.FOURTH_HOMECELL; i++){
			cardsInHomecells += stackArray[i].getCardList().size();
		}
		for(int i = Freecell.FIRST_FREECELL; i<= Freecell.FOURTH_FREECELL; i++){
			if(stackArray[i].getCardList().isEmpty()){
				emptyFreecells++;
			}
		}
		
		double points = sortedCardPoints + cardsInHomecells * HOMECELL_POINTS + emptyStacks * EMPTY_STACK_POINTS + emptyFreecells * EMPTY_FREECELL_POINTS;
		if(points == -1){
			return -1.1;
		}
		else{
			return points;
		}
	}
	
	private int sortedCardsPoints(Stack stack){
		LinkedList<Card> cardList = stack.getCardList();
		Iterator<Card> iterator = cardList.descendingIterator();
		Card last = null;
		Card current = null;
		int points = 0;
		
		while(iterator.hasNext()){
			last = current;
			current = iterator.next();
			if(last != null){
				if((last.isRed() != current.isRed()) && last.getRank().ordinal() + 1 == current.getRank().ordinal()){
					points++;
				}
				else if(points > 0){
					points *= SORTED_POINTS;
					points += BURIED_CARD_POINTS / ((double)current.getRank().ordinal() + 1);
					while(iterator.hasNext()){
						current = iterator.next();
						points += BURIED_CARD_POINTS / ((double)current.getRank().ordinal() + 1);
					}
				}
			}
		}
		return points;
	}

	@Override
	public List<Move> getPossibleMoves(BoardSituation situation) {
		Stack[] stackArray = situation.getStackArray();
		Stack sourceStack;
		Stack destinationStack;
		Stack transferStack;
		Card transferCard;
		Move move;
		Boolean emptyStackFound = false;
		GameController gameController = patienceController.getGameController();
		List<Move> moveList = new LinkedList<Move>();
		
		for(int i = Freecell.FIRST_STACK; i <= Freecell.EIGTH_STACK; i++){
			sourceStack = stackArray[i];
			Iterator<Card> iterator = sourceStack.getCardList().descendingIterator();
			while(iterator.hasNext()){
				emptyStackFound = false;
				transferCard = iterator.next();
				if(!((FreecellController)gameController).isMovable(transferCard, situation)){
					break;
				}
				transferStack = BoardController.cardToTransferStack(transferCard);
				for(int j = Freecell.FIRST_STACK; j <= Freecell.EIGTH_STACK; j++){
					destinationStack = stackArray[j];
					move = new Move(sourceStack, destinationStack, transferStack);
					if(destinationStack.getCardList().isEmpty() && !emptyStackFound){
						emptyStackFound = true;
						moveList.add(move);
					}
					else if(!destinationStack.getCardList().isEmpty() && gameController.isLegalMove(move)){
						moveList.add(move);
					}
				}
				if(transferStack.getCardList().size() == 1){
					for(int j = Freecell.FIRST_FREECELL; j <= Freecell.FOURTH_FREECELL; j++){
						destinationStack = stackArray[j];
						move = new Move(sourceStack, destinationStack, transferStack);
						if(gameController.isLegalMove(move)){
							moveList.add(move);
							break;
						}
					}
					for(int j = Freecell.FIRST_HOMECELL; j <= Freecell.FOURTH_HOMECELL; j++){
						destinationStack = stackArray[j];
						move = new Move(sourceStack, destinationStack, transferStack);
						if(gameController.isLegalMove(move)){
							moveList.add(move);
							break;
						}
					}
				}
			}
		}
		
		for(int i = Freecell.FIRST_FREECELL; i <= Freecell.FOURTH_FREECELL; i++){
			sourceStack = stackArray[i];
			if(!sourceStack.getCardList().isEmpty()){
				emptyStackFound = false;
				transferStack = BoardController.cardToTransferStack(sourceStack.getTopCard());
				for(int j = Freecell.FIRST_STACK; j <= Freecell.EIGTH_STACK; j++){
					destinationStack = stackArray[j];
					move = new Move(sourceStack, destinationStack, transferStack);
					if(destinationStack.getCardList().isEmpty() && !emptyStackFound){
						emptyStackFound = true;
						moveList.add(move);
					}
					else if(!destinationStack.getCardList().isEmpty() && gameController.isLegalMove(move)){
						moveList.add(move);
					}
				}
				for(int j = Freecell.FIRST_HOMECELL; j <= Freecell.FOURTH_HOMECELL; j++){
					destinationStack = stackArray[j];
					move = new Move(sourceStack, destinationStack, transferStack);
					if(gameController.isLegalMove(move)){
						moveList.add(move);
						break;
					}
				}
			}
		}
		
		return moveList;
	}
}
