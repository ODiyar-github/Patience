package control;

import model.BoardSituation;
import model.Card;
import model.Freecell;
import model.Rank;
import model.Stack;
import support.Move;

public class FreecellController extends GameController 
{

	public FreecellController(PatienceController patienceController, Freecell game) 
	{
		super(patienceController, game);
	}

//	/**
//	 * gibt den Stack zurück, auf dem die übergebene Karte liegt
//	 * @param card : Karte
//	 */
//	
//	public Stack cardToTransferStack(Card card) 
//	{
//		Stack transferStack = new Stack("", 0, 0, 0);
//		
//		LinkedList<Card> cardList = new LinkedList<Card>();
//		
//		for(int cardPos = card.getCurrentStack().getCardList().indexOf(card); cardPos < card.getCurrentStack().getCardList().size(); cardPos++)
//			cardList.add(card.getCurrentStack().getCardList().get(cardPos));
//			
//		transferStack.setCardList(cardList);	
//		return transferStack;
//	}
	
	/**
	 * prüft, ob der übergebene Zug nach den FreeCell-Regeln erlaubt ist 
	 * @param move : Move 
	 */

	public boolean isLegalMove(Move move)
	{
		Boolean isLegal = null;
		
		int arrPosFromDestinationStack = move.getDestinationStack().getArrPosition();
		
		if(move.getSourceStack().getArrPosition() == move.getDestinationStack().getArrPosition())
			isLegal = false;
		
		//DestinationStack ist einer der Homecells
		if(arrPosFromDestinationStack >= Freecell.FIRST_HOMECELL && arrPosFromDestinationStack <= Freecell.FOURTH_HOMECELL)
			isLegal = isLegalMoveDestinationHomecells(move);
		
		//DestinationStack ist einer Freecells
		if(arrPosFromDestinationStack >= Freecell.FIRST_FREECELL && arrPosFromDestinationStack <= Freecell.FOURTH_FREECELL)
			isLegal = isLegalMoveDestinationFreecells(move);
			
		//DestinationStack ist einer der Spielstapel
		if(arrPosFromDestinationStack >= Freecell.FIRST_STACK && arrPosFromDestinationStack <= Freecell.EIGTH_STACK)
			isLegal = isLegalMoveDestinationGamecell(move);
			
		return isLegal;
	}
	
	/**
	 *bewegt die Karte auf den Stapel
	 *@params transferStack : Karte,die bewegt werden soll, destinationStack : Stapel, auf den bewegt werden soll
	 */
	
	//warum wird eine karte statt einem Stapel übergeben, ist dafür nicht cardToTransferStack?
	
	@Override
	public void move(Move move) 
	{
		if(isLegalMove(move)){
			doMove(move);
			game.incrementCountMoves();
		}
		else{
			callRefreshBoard(null);
		}
		
		Boolean won = isGameWon();
		
		if(won){
			callRefreshGameFinished();
			this.patienceController.finishGame(won);
		}
	}
	
	@Override
	public BoardSituation simulateMove(BoardSituation boardSituation, Move move){
		if(isLegalMove(move)){
			boardSituation = doMove(boardSituation, move);
		}
		
		return boardSituation;
	}

	@Override
	public boolean cardFromTalon(Card card) 
	{
		//Nicht relevant für FreeCell
		return false;
	}

	@Override
	public boolean isGameWon(BoardSituation boardSituation) 
	{
		Stack stack = null;
		
		//Prüfen ob Homecells komplett sind		
		for(int stackPos = Freecell.FIRST_HOMECELL; stackPos <= Freecell.FOURTH_HOMECELL; stackPos++)
		{
			stack = boardSituation.getStackArray()[stackPos];
			if(stack.getCardList().size() < Rank.values().length)
				return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isMovable(Card card){
		return isMovable(card, game.getCurrentBoardsituation());
	}

	/** 
	 * Methode, die prüft, ob die übergebene Karte, in der übergebenen BoardSituation bewegt werden darf
	 * @param card
	 * @param boardsituation
	 * @return
	 */
	public boolean isMovable(Card card, BoardSituation boardsituation) 
	{
		int arrPosFromStack = card.getCurrentStack().getArrPosition();	
		Stack[] stacks = boardsituation.getStackArray();
		
		//Karten von Homecell nicht movable
		if(arrPosFromStack >= Freecell.FIRST_HOMECELL && arrPosFromStack <= Freecell.FOURTH_HOMECELL)
			return false;
		
		//Karten vom Freecell sind immer movable
		if(arrPosFromStack >= Freecell.FIRST_FREECELL && arrPosFromStack <= Freecell.FOURTH_FREECELL)
			return true;
			
		//Karten von den Spielstapel sind movable wenn sie innerhab den Stacks an einer movale Position sind
		if(arrPosFromStack >= Freecell.FIRST_STACK && arrPosFromStack <= Freecell.EIGTH_STACK)
		{
			int amountMovableCards = 0;
			
			//Prüfen wieviele Karen gleichzeitg verschiebbar
			for(int stackPos = Freecell.FIRST_FREECELL; stackPos <= Freecell.FOURTH_FREECELL; stackPos++)	
				if(stacks[stackPos].getCardList().isEmpty()) 
					amountMovableCards++;
			
			amountMovableCards++; //Da immernoch eine mehr movable ist
			
			Stack stackFromCard = card.getCurrentStack();
			int posFromCardInStack = stackFromCard.getCardList().indexOf(card);
			
			if(!((stackFromCard.getCardList().size() - amountMovableCards) <= posFromCardInStack))
				return false;
			
			Card currentCard = null;
			Card nextCard = null;
			//Letzte Karte nicht mehr prüfen, ist immer movable
			for(int cardPos = posFromCardInStack; cardPos < stackFromCard.getCardList().size() - 1; cardPos++)
			{
				currentCard = stackFromCard.getCardList().get(cardPos);
				nextCard = stackFromCard.getCardList().get(cardPos + 1);
				
				if(currentCard.isRed() == nextCard.isRed())
					return false;
				
				if(currentCard.getRank().ordinal() != nextCard.getRank().ordinal() + 1)
					return false;
			}			
		}

		return true;
	}	
	
	public int cardsLeft()
	{
		BoardSituation situation = game.getCurrentBoardsituation();
		int sum = 0;
		
		for(int i = Freecell.FIRST_STACK; i <= Freecell.FOURTH_FREECELL; i++){
			sum += situation.getStackArray()[i].getCardList().size();
		}
		
		return sum;
	}
	
	private Boolean isLegalMoveDestinationHomecells(Move move)
	{
		if(move.getTransferStack().getCardList().size() > 1)
			return false;
		
		//Spezialfall Ass
		if((move.getTransferStack().getCardList().get(0).getRank() == Rank.ACE) &&
			(move.getDestinationStack().getCardList().size() > 0))
				return false;
		
		//Alle anderen Karten
		if((move.getTransferStack().getTopCard().getRank() != Rank.ACE))
		{
			if(move.getDestinationStack().getCardList().size() == 0)
				return false;
			
			if(move.getTransferStack().getTopCard().getSuit() != move.getDestinationStack().getCardList().getLast().getSuit())
				return false;	

			if(move.getTransferStack().getTopCard().getRank().ordinal() != move.getDestinationStack().getTopCard().getRank().ordinal()+1)
				return false;
		}
		
		return true;
	}

	private Boolean isLegalMoveDestinationFreecells(Move move)
	{
		if(move.getTransferStack().getCardList().size() > 1)
			return false;
		
		//Karte nur legbar wenn der Stapel leer ist
		if(move.getDestinationStack().getCardList().size() > 0)
			return false;
		
		return true;
	}

	private Boolean isLegalMoveDestinationGamecell(Move move)
	{
		if(move.getDestinationStack().getCardList().size() == 0)
			return true;
		
		if(move.getTransferStack().getCardList().getFirst().isRed() == move.getDestinationStack().getCardList().getLast().isRed())
			return false;
		
		if(move.getTransferStack().getCardList().getFirst().getRank().ordinal() != move.getDestinationStack().getCardList().getLast().getRank().ordinal() - 1)
			return false;
		
		return true;
	}
	
//	@Override
//	public void doMove(Move move)
//	{
//		int sourcePos = move.getSourceStack().getArrPosition();
//		int destinationPos = move.getDestinationStack().getArrPosition();
//		//Stack transferStack = move.getTransferStack().clone();
//		
////		for(Card card : transferStack.getCardList()){
////			card.setVisible(true);
////		}
//		
//		BoardSituation currentSituation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
//		BoardSituation newSituation = currentSituation.clone();
//		
//		Stack newSourceStack = newSituation.getStackArray()[sourcePos];
//		for(Card card : newSourceStack.getCardList())
//		{
//			if(card.getRank() == move.getTransferStack().getCardList().get(0).getRank() && 
//					card.getSuit() == move.getTransferStack().getCardList().get(0).getSuit())
//			{
//				newSourceStack.getCardList().remove(card);
//			}
//		}
//		
//		Stack newDestinationStack = newSituation.getStackArray()[destinationPos];
//		for(Card card : move.getTransferStack().getCardList())
//		{
//			newDestinationStack.getCardList().add(card);
//		}
//		
//		//BoardController.removeFromStack(newSituation.getStackArray()[sourcePos], transferStack);
//		
//		//BoardController.addToStack(newSituation.getStackArray()[destinationPos], transferStack);
//		
//		//currentSituation.setNext(newSituation);
//		
//		patienceController.getPatience().getCurrentGame().setCurrentBoardsitution(newSituation);
//		
//		callRefreshBoard(move);
//	}
}
