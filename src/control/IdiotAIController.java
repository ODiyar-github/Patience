package control;
import java.util.LinkedList;
import java.util.List;

import model.BoardSituation;
import model.Card;
import model.Idiot;
import model.Rank;
import model.Stack;
import support.Move;

/**
 * @author sopr096
 *
 */
public class IdiotAIController extends AIController {
	
	/**
	 * Faktor mit dem höher liegende Karten höher bepunktet werden.
	 */
	private static final double DEPTH_FACTOR = 1.2;
	
	/**
	 * Pluspunkte, die es für ein leeres Feld gibt.
	 */
	private static final double EMPTY_FIELD_POINTS = 100;
	
	
	/**
	 * Differenz der Wertigkeit zwischen Ranks.
	 */
	private static final double RANK_DIFFERENCE = 5;


	public IdiotAIController(PatienceController patienceController){
		super(patienceController);
	}
	
	/**
	 * @see AIController#aiTurn()
	 */
	public boolean aiTurn(){
		if(firstCall){
			patienceController.getGameController().callRefreshBoard(null);
			firstCall = false;
			return true;
		}
		BoardSituation currentBoardSituation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		Stack[] stackArray = currentBoardSituation.getStackArray();
		
		if(moveStack.isEmpty()){
			Tuple<Double, java.util.Stack<Move>> tuple = computeMoves(-1, currentBoardSituation);
			//System.out.println(tuple.firstElement);
			moveStack = tuple.secondElement;
		}
		if(!moveStack.isEmpty()){
			Move move = moveStack.pop();
			patienceController.getGameController().move(move);
			System.out.println(move);
			return true;
		}
		else if(!stackArray[Idiot.TALON].getCardList().isEmpty()){
			patienceController.getGameController().cardFromTalon(stackArray[Idiot.TALON].getTopCard());
			System.out.println("Nachziehen.");
			return true;
		}
		return false;
	}
	/**
	 * @see AIController#tip()
	 */
	public Move tip(){
		BoardSituation currentBoardSituation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		Stack[] stackArray = currentBoardSituation.getStackArray();
		
		java.util.Stack<Move> tipStack = computeMoves(-1, currentBoardSituation).secondElement;
		if(!tipStack.isEmpty()){
			return tipStack.pop();
		}
		else if(!stackArray[Idiot.TALON].getCardList().isEmpty()){
			return new Move(stackArray[Idiot.TALON], stackArray[Idiot.TALON], BoardController.cardToTransferStack(stackArray[Idiot.TALON].getTopCard()));
		}
		return null;
	}

	@Override
	public boolean abort(BoardSituation situation) {
		return false;
	}

	@Override
	public double evaluate(BoardSituation situation) {
		double evaluation = 0;
		Stack[] stackArray = situation.getStackArray();
		Stack stack;
		List<Card> cardList;
		
		if(patienceController.getGameController().isGameWon(situation)){
			return -1;
		}
		
		for(int i = Idiot.FIRST_STACK; i <= Idiot.FOURTH_STACK; i++){
			stack = stackArray[i];
			cardList = stack.getCardList();
			if(cardList.size() == 0 && !stackArray[Idiot.TALON].getCardList().isEmpty()){
				evaluation += EMPTY_FIELD_POINTS;
			}
			double depth = 1;
			for(Card card : cardList){
				evaluation -= calculateRank(card, situation) * depth;
				depth *= DEPTH_FACTOR;
			}
		}
		
		if(evaluation == -1){
			return -1.1;
		}
		
		return evaluation;
	}
	
	private double calculateRank(Card card, BoardSituation situation){
		int rank;
		if(card.getRank() == Rank.ACE){
			rank = 13;
		}
		else{
			rank = card.getRank().ordinal();
		}
		
		for(Card talonCard : situation.getStackArray()[Idiot.TRAY].getCardList()){
			if(card.getRank() != Rank.ACE && talonCard.getSuit() == card.getSuit() && talonCard.getRank().ordinal() > card.getRank().ordinal()){
				rank += RANK_DIFFERENCE;
			}
		}
		
		return rank * RANK_DIFFERENCE;
	}

	@Override
	public List<Move> getPossibleMoves(BoardSituation situation) {
		List<Move> moveList = new LinkedList<Move>();
		Stack[] stackArray = situation.getStackArray();
		Move move;
		Stack sourceStack;
		Stack destinationStack;
		Stack transferStack;
		
		for(int i = Idiot.FIRST_STACK; i <= Idiot.FOURTH_STACK; i++){
			if(!stackArray[i].getCardList().isEmpty()){
				sourceStack = stackArray[i];
				transferStack = BoardController.cardToTransferStack(sourceStack.getTopCard());
				for(int j = Idiot.FIRST_STACK; j <= Idiot.TRAY; j++){
					destinationStack = stackArray[j];
					move = new Move(sourceStack, destinationStack, transferStack);
					
					if(((IdiotController)patienceController.getGameController()).isLegalMove(situation, move)){

						if(destinationStack.getArrPosition() == Idiot.TRAY || sourceStack.getCardList().size() > 1){
							moveList.add(move);
						}
					}
				}
			}
		}
		
		return moveList;
	}

}
