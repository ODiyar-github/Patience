package control;
import java.util.LinkedList;

import model.BoardSituation;
import model.Card;
import model.Idiot;
import model.Rank;
import model.Stack;

import support.Move;


public class IdiotController extends GameController {
	
	

	public IdiotController(PatienceController patienceController, Idiot game) {
		super(patienceController, game);
		System.out.println("super");
	}

	
	/**
	 * prüft, ob der übergebene Zug nach den IdiotPatience-Regeln erlaubt ist 
	 * @param move : Move 
	 */
	public boolean isLegalMove(BoardSituation boardSituation, Move move) {
		//transferStack wird hier nicht genutzt, da bei der Idioten-Patience nur einzelne Karten verschoben werden
		final Stack source = move.getSourceStack();
		final Stack destination = move.getDestinationStack();
		if(source==destination){ //Zug nicht erlaubt, wenn zweimal auf gleichen Stapel geklickt wurde
			return false;
		}		
		if(destination.getArrPosition()==0){return false;}
		
		if(destination.getArrPosition() != 0 && destination.getArrPosition() != 5){ //Ziel ist Spielstapel
			return destination.getCardList().isEmpty();
		}
		
		final Card card = source.getTopCard();
		final Stack[] stacks = boardSituation.getStackArray();
		for(int i=1;i<5;i++){
			if(		!stacks[i].getCardList().isEmpty()
					&&i!=source.getArrPosition()
					&& stacks[i].getTopCard().getSuit()==card.getSuit()
					&& (stacks[i].getTopCard().getRank().ordinal()>card.getRank().ordinal()
							|| stacks[i].getTopCard().getRank()==Rank.ACE)
					&& card.getRank() != Rank.ACE){
				return true;
			}
		}
		return false;
	}


	
	/**
	 * prüft,ob die übergebene Karte auf dem Talon liegt, wenn ja 4 neue und true, wenn nein false zurück
	 */
	@Override
	public boolean cardFromTalon(Card card) { 
		if(card.getCurrentStack().getArrPosition() > 0)
			return false;
		
		//System.out.println("vor for");
		BoardSituation oldSituation = game.getCurrentBoardsituation();
		BoardSituation situation = oldSituation;
		Stack sourceStack;
		Stack transferStack;
		Stack destinationStack;
		for(int i = Idiot.FIRST_STACK; i <= Idiot.FOURTH_STACK;i++)
		{
			//System.out.println(i);
			destinationStack = situation.getStackArray()[i];
			sourceStack = situation.getStackArray()[Idiot.TALON];
			transferStack = new Stack("", 0, 0, 0, situation.getStackArray()[0].getTopCard());
			Move move = new Move(sourceStack, destinationStack, transferStack);
			//System.out.println("Stapel" +i);
			situation = doMove(situation, move);
			game.setCurrentBoardsitution(situation);
			callRefreshBoard(move);
		}
		
		oldSituation.setNext(situation);
		
		game.setCurrentBoardsitution(situation);
//		callRefreshBoard(null);

		game.incrementCountMoves();
		return true;
	}


	/**
	 * prüft,ob das Spiel gewonnen wurde
	 */
	@Override
	public boolean isGameWon() {
		Stack[] stacks = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		
		if(stacks[Idiot.TRAY].getCardList().size() != 48){
			return false;
		}
		for(int i = Idiot.FIRST_STACK; i <= Idiot.FOURTH_STACK; i++){
			if(stacks[i].getCardList().size() != 1){
				return false;
			}
		}
		
		return true;
	}

	/**
	 * prüft, ob die übergebene Karte beweglich ist
	 */
	@Override
	public boolean isMovable(Card card) {
		return (card.getCurrentStack().getTopCard()==card) && card.getCurrentStack().getArrPosition() < Idiot.TRAY;
	}


	@Override
	public void move(Move move) {
		Card transferCard = move.getSourceStack().getTopCard();
		if(isMovable(transferCard)){
			if(isLegalMove(move)){
				doMove(move);
				game.incrementCountMoves();
			}
			else{
				callRefreshBoard(null);
			}
			
			if(isGameWon()){
				callRefreshGameFinished();
			}
			
		}
	}


	@Override
	public BoardSituation simulateMove(BoardSituation boardSituation, Move move) {
		if(isLegalMove(boardSituation, move)){
			boardSituation = doMove(boardSituation, move);
		}
		return boardSituation;
	}


	@Override
	public boolean isGameWon(BoardSituation boardSituation) {
		Stack[] stacks = boardSituation.getStackArray();
//		if(!stacks[0].getCardList().isEmpty()){return false;}
//		for(int i=1;i<5;i++){ 
//			if(stacks[i].getCardList().size()>1){ 
//				return false;
//			}
//			//prüfen, ob zwei Karten der gleichen Art
//			int nSpade=0;
//			int nHeart=0;
//			int nDiamond=0;
//			int nClub=0;
//			for(int j=1;j<5;j++){ 
//				switch(stacks[j].getTopCard().getSuit()){
//					case SPADE : nSpade++;
//					case HEART : nHeart++;
//					case DIAMOND : nDiamond++;
//					case CLUB : nClub++;
//				}	
//			}
//			if(nSpade>1 || nHeart>1 || nDiamond>1 || nClub>1){return false;}
//		}
//		return true;
		
		if(stacks[Idiot.TRAY].getCardList().size() != 48){
			return false;
		}
		
		for(int i = Idiot.FIRST_STACK; i <= Idiot.FOURTH_STACK; i++){
			if(stacks[i].getCardList().size() != 1){
				return false;
			}
		}
		
		return true;
	}


	@Override
	public int cardsLeft() {
		BoardSituation situation = game.getCurrentBoardsituation();
		int sum = 0;
		
		for(int i = Idiot.TALON; i <= Idiot.FOURTH_STACK; i++){
			sum += situation.getStackArray()[i].getCardList().size();
		}
		
		//Wenn noch 4 Asse auf dem Spielfeld sind, dann hat man alle Karten weggelegt, die man weglegen kann.
		return sum - 4;
	}


	@Override
	public boolean isLegalMove(Move move) {
		return this.isLegalMove(game.getCurrentBoardsituation(), move);
	}


}