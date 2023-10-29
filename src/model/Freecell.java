package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

import model.BoardSituation;

/**
 * @author sopr099
 * Eine Freecell-Partie
 */
public class Freecell extends Game implements Serializable {
	
	/*
	 * Konstanten um Zugriff auf Stacks zu erleichtern
	 */
	public static final int FIRST_STACK = 0;

	public static final int SECOND_STACK = 1;

	public static final int THIRD_STACK = 2;
 
	public static final int FOURTH_STACK = 3;

	public static final int FIFTH_STACK = 4;

	public static final int SIXTH_STACK = 5;

	public static final int SEVENTH_STACK = 6;

	public static final int EIGTH_STACK = 7;

	public static final int FIRST_FREECELL = 8;

	public static final int SECOND_FREECELL = 9;

	public static final int THIRD_FREECELL = 10;

	public static final int FOURTH_FREECELL = 11;

	public static final int FIRST_HOMECELL = 12;

	public static final int SECOND_HOMECELL = 13;

	public static final int THIRD_HOMECELL = 14;

	public static final int FOURTH_HOMECELL = 15;

	
	/**
	 * @param stack : Kartenstapel aus dessen Kartenreihenfolge die erste Boardsituation generiert wird
	 * @param firstPlayer : Beschreibt den Spielertyp(Mensch oder KI-Typ)
	 */
	public Freecell(Stack stack, PlayerType firstPlayer)
	{
		this.numberOfStacks = 16;
		this.gameType = "Freecell";
		this.date = LocalDateTime.now();
		this.countMoves = 0;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = null;
		this.firstPlayersTurn = true;
//		this.boardsituationList = new LinkedList<BoardSituation>();
		
		Stack tempStack = stack.clone();
		Stack[] startStacks = generateStartStacks(tempStack);
	
		BoardSituation startBoardSituation= new BoardSituation(startStacks);		
		 
		this.currentBoardsituation = startBoardSituation;
//		boardsituationList.add(currentBoardsituation);
	}
	
	private Stack[] generateStartStacks(Stack customStack)
	{
		Stack[] stacks = new Stack[numberOfStacks];
		Stack stack = null;
		
		int xCoordinate = 0;
		int yCoordinate = 0;
		
		for(int stackPos = 0; stackPos < numberOfStacks; stackPos++)
		{
			if(stackPos <= EIGTH_STACK) 
			{
				xCoordinate = stackPos; 
				yCoordinate = 1;
			}
			
			if(stackPos >= FIRST_FREECELL) 
			{
				xCoordinate = stackPos-8; 
				yCoordinate = 0;
			}
					
			stack = new Stack("", xCoordinate, yCoordinate, stackPos);
			stacks[stackPos] = stack;
		}
		
		Card card = null;
		LinkedList<Card> cardList = customStack.getCardList();
		
		for(int cardPos = 0; cardPos < 52; cardPos++)
		{
			card = cardList.get(cardPos);	
			card.setVisible(true);
			
			int stackPos = cardPos % 8;
			card.setCurrentStack(stacks[stackPos]);
			
			stacks[stackPos].getCardList().add(card);
		}
		
		return stacks;
	}

}
