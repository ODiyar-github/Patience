package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

import model.BoardSituation;

/**
 * @author sopr099
 * 
 * Eine Idioten-Partie
 */
public class Idiot extends Game implements Serializable {
	
	/*
	 * Konstanten um Zugriff auf Stacks zu erleichtern
	 */
	public static final int TALON = 0;

	public static final int FIRST_STACK = 1;

	public static final int SECOND_STACK = 2;

	public static final int THIRD_STACK = 3;

	public static final int FOURTH_STACK = 4;

	public static final int TRAY = 5;

	/**
	 * @param stack : Kartenstapel aus dessen Kartenreihenfolge die erste Boardsituation generiert wird
	 * @param firstPlayer: Beschreibt den Spielertyp(Mensch oder KI-Typ)
	 */
	public Idiot(Stack stack, PlayerType firstPlayer) 
	{
		this.numberOfStacks = 6;
		this.gameType = "Idiot";
		this.date = LocalDateTime.now();
		this.countMoves = 0;
		this.firstPlayersTurn = true;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = null;
		
		Stack tempStack = stack.clone();
		System.out.println("idiot konstruktor "+stack.equals(tempStack));
		tempStack.printStack();
		Stack[] startStacks = generateStartStacks(tempStack);
		
		for(int j=0;j<6;j++){
			System.out.println("");
			System.out.println("j:"+j);
			startStacks[j].printStack();
		}
	
		BoardSituation startBoardSituation= new BoardSituation(startStacks);
		 
		this.currentBoardsituation = startBoardSituation;	
	}
	
	/**
	 * Generiert das Array der Stacks für die Anfangsspielsituation
	 * @param customStack
	 * 		Der Stack, von dem die Karten verteilt werden und, der der Talon wird
	 * @return
	 * 		Array der Stacks, die auf das Spielfeld gelegt werden
	 */
	private Stack[] generateStartStacks(Stack customStack)
	{	
		Stack[] stacks = new Stack[numberOfStacks];
		Stack stack; 
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		
		stacks[TALON] = customStack;
		customStack.setCoordinateX(xCoordinate);
		customStack.setCoordinateY(yCoordinate);
		customStack.setArrPosition(TALON);
		
		for(int stackPos = FIRST_STACK ; stackPos < numberOfStacks; stackPos++)
		{		
			xCoordinate = stackPos;
			
			stack = new Stack("", xCoordinate, yCoordinate, stackPos);
			stack.setArrPosition(stackPos);
			stacks[stackPos] = stack;
		}
		
//		Card card = null;
//		LinkedList<Card> cardList = customStack.getCardList();
		
//		for(int stackPos = FIRST_STACK; stackPos <= FOURTH_STACK; stackPos++)
//		{
//			card = customStack.getTopCard();
//			if(card!=null){
//				card.setVisible(true);
//				card.setCurrentStack(stacks[stackPos]);
//			
//				stacks[stackPos].getCardList().add(card);		
//			}
//			else{System.out.println("keine Karte");}
//		}
		
		return stacks;
	}

}
