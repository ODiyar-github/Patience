package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Zank extends Game implements Serializable {

	/**
	 * Konstanten zur Verwaltung der jeweiligen Stacks
	 */
	public static final int FIRST_TALON=0;

	public static final int SECOND_TALON=1;

	public static final int FIRST_TRAY=2;

	public static final int SECOND_TRAY=3;

	public static final int FIRST_SUBSTACK=4;

	public static final int SECOND_SUBSTACK=5;

	public static final int FIRST_OUTER_STACK=6;

	public static final int SECOND_OUTER_STACK=7;

	public static final int THIRD_OUTER_STACK=8;

	public static final int FOURTH_OUTER_STACK=9;

	public static final int FIFTH_OUTER_STACK=10;

	public static final int SIXTH_OUTER_STACK=11;

	public static final int SEVENTH_OUTER_STACK=12;

	public static final int EIGHTH_OUTER_STACK=13;

	public static final int FIRST_CLUB_STACK=14;

	public static final int SECOND_CLUB_STACK=15;

	public static final int FIRST_SPADE_STACK=16;

	public static final int SECOND_SPADE_STACK=17;

	public static final int FIRST_DIAMOND_STACK=18;

	public static final int SECOND_DIAMOND_STACK=19;

	public static final int FIRST_HEART_STACK=20;

	public static final int SECOND_HEART_STACK=21;

	/**
	 * @param firstPlayerStack Stack von Spieler 1
	 * @param secondPlayerStack Stack von Spieler 2
	 * @param firstPlayer Erster Spieler des Spiels
	 * @param secondPlayer Zewiter Spieler des Spiels
	 * Konstruktor erzeigt ein neues Zank-Game mit ersten und zweitem Spieler und ihren jeweiligen Stacks
	 */
	public Zank(Stack firstPlayerStack, Stack secondPlayerStack, PlayerType firstPlayer, PlayerType secondPlayer) {
		this.numberOfStacks = 22;
		this.gameType = "Zank";
		this.date = LocalDateTime.now();
		this.countMoves = 0;
		this.firstPlayersTurn = true;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
//		this.boardsituationList = new LinkedList<BoardSituation>();
		
		firstPlayerStack = firstPlayerStack.clone();
		secondPlayerStack = secondPlayerStack.clone();
		
		Stack[] startStacks = generateStartStacks(firstPlayerStack, secondPlayerStack);
		
		BoardSituation startBoardSituation= new BoardSituation(startStacks);
		
		this.currentBoardsituation = startBoardSituation;
//		boardsituationList.add(currentBoardsituation);
	}
	
	private Stack[] generateStartStacks(Stack firstPlayerStack, Stack secondPlayerStack){
		Stack[] stacks = new Stack[numberOfStacks];
//		Stack stack = null;
		
		//Ersten Nachziehstapel belegen
		stacks[FIRST_TALON] = firstPlayerStack;
		firstPlayerStack.setCoordinateX(2);
		firstPlayerStack.setCoordinateY(5);
		firstPlayerStack.setArrPosition(FIRST_TALON);
			
		//Zweiten Nachziehstapel belegen
		stacks[SECOND_TALON] = secondPlayerStack;
		secondPlayerStack.setCoordinateX(1);
		secondPlayerStack.setCoordinateY(0);
		secondPlayerStack.setArrPosition(SECOND_TALON);
	
		//Ersten Ablegestapel erzeugen
		stacks[FIRST_TRAY] = new Stack("", 1, 5, FIRST_TRAY);
		
		//Zweiten Ablegestapel erzeugen
		stacks[SECOND_TRAY] = new Stack("", 2, 0, SECOND_TRAY);
		
		//Ersten Ersatzstapel erzeugen und die obersten 13 Karten des ersten Talons darauf legen
		stacks[FIRST_SUBSTACK] = new Stack("", 3, 5, FIRST_SUBSTACK);
		List<Card> cardList = stacks[FIRST_TALON].getCardList();
		for(int i = 0; i < 13; i++){
			Card card = cardList.get(cardList.size() - 1);
			cardList.remove(card);
			stacks[FIRST_SUBSTACK].getCardList().add(card);
			card.setCurrentStack(stacks[FIRST_SUBSTACK]);
			card.setVisible(true);
		}
		
		//Zweiten Ersatzstapel erzeugen und die obersten 13 Karten des zweiten Talons darauf legen
		stacks[SECOND_SUBSTACK] = new Stack("", 0, 0, SECOND_SUBSTACK);
		cardList = stacks[SECOND_TALON].getCardList();
		for(int i = 0; i < 13; i++){
			Card card = cardList.get(cardList.size() - 1);
			cardList.remove(card);
			stacks[SECOND_SUBSTACK].getCardList().add(card);
			card.setCurrentStack(stacks[SECOND_SUBSTACK]);
			card.setVisible(true);
		}
		
		//Linke Seitenstapel erzeugen und jeweils eine Karte des zweiten Talons darauf legen
		int coordinateX = 0;
		int coordinateY = 4;
		cardList = stacks[SECOND_TALON].getCardList();
		for(int i = FOURTH_OUTER_STACK; i >= FIRST_OUTER_STACK; i--){
			stacks[i] = new Stack("", coordinateX, coordinateY, i);
			
			Card card = cardList.get(cardList.size() - 1);
			cardList.remove(card);
			stacks[i].getCardList().add(card);
			card.setCurrentStack(stacks[i]);
			card.setVisible(true);
			
			coordinateY--;
		}
		
		//Rechte Seitenstapel erzeugen und jeweils eine Karte des ersten Talons darauf legen
		coordinateX = 3;
		coordinateY = 1;
		cardList = stacks[FIRST_TALON].getCardList();
		for(int i = FIFTH_OUTER_STACK; i <= EIGHTH_OUTER_STACK ; i++){
			stacks[i] = new Stack("", coordinateX, coordinateY, i);
			
			Card card = cardList.get(cardList.size() - 1);
			cardList.remove(card);
			stacks[i].getCardList().add(card);
			card.setCurrentStack(stacks[i]);
			card.setVisible(true);
			
			coordinateY++;
		}
		
		//Innere Stapel erzeugen
		coordinateX = 1;
		coordinateY = 1;
		for(int i = FIRST_CLUB_STACK; i <= SECOND_HEART_STACK; i++){
			stacks[i] = new Stack("", coordinateX, coordinateY, i);
			coordinateX = coordinateX % 2 + 1;
			if(coordinateX == 1)
				coordinateY++;
			
			switch(i)
			{
				case FIRST_CLUB_STACK: stacks[i].setName("Club"); break;
				case SECOND_CLUB_STACK: stacks[i].setName("Club"); break;
				case FIRST_SPADE_STACK: stacks[i].setName("Spade"); break;
				case SECOND_SPADE_STACK: stacks[i].setName("Spade"); break;
				case FIRST_DIAMOND_STACK: stacks[i].setName("Diamond"); break;
				case SECOND_DIAMOND_STACK: stacks[i].setName("Diamond"); break;
				case FIRST_HEART_STACK: stacks[i].setName("Heart"); break;
				case SECOND_HEART_STACK: stacks[i].setName("Heart"); break;				
					
				default:break;
			}
			
		}
		
		return stacks;
	}

	public boolean isFirstPlayerTurn() {
		return firstPlayersTurn;
	}
}
