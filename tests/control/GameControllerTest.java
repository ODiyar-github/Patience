package control;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.BoardSituation;
import model.Card;
import model.Idiot;
import model.PlayerType;
import model.Rank;
import model.Stack;
import model.Suit;
import support.Move;

public class GameControllerTest {
	
	PatienceController patienceController = new PatienceController();
	String stackFile="Karo-Koenig Kreuz-8 Herz-Dame Kreuz-9 Herz-10 Karo-Bube Herz-4 Pik-Ass";
	String stackFileMove="Pik-Ass Herz-4 Karo-Bube Herz-10 Karo-Koenig Kreuz-8 Herz-Dame Kreuz-9";
	String newBoardString="";
	BoardSituation before;
	BoardSituation after;
	BoardSituation oldBoard;
	BoardSituation newBoard;
	BoardSituation currentBoard;
	Stack[] stackArray;
	Stack[] newBoardStacks = new Stack[6];

	
	
	@Before
	public void doBeforeTests(){
		Stack talon = IOController.stringToStack(stackFile);	
		Idiot idiot = new Idiot(talon,PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
	}

	@Test
	public void testDoMove() {
		Stack talon = IOController.stringToStack(stackFileMove);	
		Idiot idiot = new Idiot(talon,PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		Stack newStack0 = new Stack("",0,0,0);
		newStack0.addCard(new Card(Suit.CLUB,Rank.NINE));
		newStack0.addCard(new Card(Suit.HEART,Rank.QUEEN));
		newStack0.addCard(new Card(Suit.CLUB,Rank.EIGHT));
		newStack0.addCard(new Card(Suit.DIAMOND,Rank.KING));
		
		newBoardStacks[0]=newStack0;
		Stack newStack1 = new Stack("",0,0,0);
		newStack1.addCard(new Card(Suit.SPADE,Rank.ACE));
		newBoardStacks[1]=newStack1;
		Stack newStack2 = new Stack("",0,0,0);
		newBoardStacks[2]=newStack2;
		Stack newStack3 = new Stack("",0,0,0);
		newStack3.addCard(new Card(Suit.DIAMOND,Rank.JACK));
		newBoardStacks[3]=newStack3;
		Stack newStack4 = new Stack("",0,0,0);
		newStack4.addCard(new Card(Suit.HEART,Rank.TEN));
		newBoardStacks[4]=newStack4;
		Stack newStack5 = new Stack("",0,0,0);
		newStack5.addCard(new Card(Suit.HEART,Rank.FOUR));
		newBoardStacks[5]=newStack5;
		newBoard = new BoardSituation(newBoardStacks);
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		Stack transferStack =BoardController.cardToTransferStack(stackArray[2].getTopCard());
		Move move1 = new Move(stackArray[2],stackArray[5],transferStack);
		patienceController.getGameController().doMove(move1);
		currentBoard = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertTrue(currentBoard.equals(newBoard));	
	}
	
	@Test 
	public void testDoMoveBoardSituation(){
		
		Stack talon = IOController.stringToStack(stackFileMove);	
		Idiot idiot = new Idiot(talon,PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		Stack newStack0 = new Stack("",0,0,0);
		newStack0.addCard(new Card(Suit.CLUB,Rank.NINE));
		newStack0.addCard(new Card(Suit.HEART,Rank.QUEEN));
		newStack0.addCard(new Card(Suit.CLUB,Rank.EIGHT));
		newStack0.addCard(new Card(Suit.DIAMOND,Rank.KING));
		
		newBoardStacks[0]=newStack0;
		Stack newStack1 = new Stack("",0,0,0);
		newStack1.addCard(new Card(Suit.SPADE,Rank.ACE));
		newBoardStacks[1]=newStack1;
		Stack newStack2 = new Stack("",0,0,0);
		newBoardStacks[2]=newStack2;
		Stack newStack3 = new Stack("",0,0,0);
		newStack3.addCard(new Card(Suit.DIAMOND,Rank.JACK));
		newBoardStacks[3]=newStack3;
		Stack newStack4 = new Stack("",0,0,0);
		newStack4.addCard(new Card(Suit.HEART,Rank.TEN));
		newBoardStacks[4]=newStack4;
		Stack newStack5 = new Stack("",0,0,0);
		newStack5.addCard(new Card(Suit.HEART,Rank.FOUR));
		newBoardStacks[5]=newStack5;
		newBoard = new BoardSituation(newBoardStacks);
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		Stack transferStack =BoardController.cardToTransferStack(stackArray[2].getTopCard());
		Move move1 = new Move(stackArray[2],stackArray[5],transferStack);
		currentBoard = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		currentBoard = patienceController.getGameController().doMove(currentBoard,move1);
		assertTrue(currentBoard.equals(newBoard));	
	}
	
	@Test 
	public void testUndo(){
		//undo wird vor dem ersten Zug aufgerufen, ist also nicht möglich
		before = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		this.patienceController.getGameController().undo();
		after = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertEquals(before,after);
		
		before = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		this.patienceController.getGameController().undo();
		after = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertTrue(before.equals(after));
		
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		before = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		patienceController.getGameController().move(stackArray[2].getTopCard(),stackArray[5]);
		this.patienceController.getGameController().undo();
		after = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertTrue(before.equals(after));
		
	}
	
	@Test 
	public void testRedo(){
		//redo wird vor dem ersten Zug aufgerufen, ist also nicht möglich
		before = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		this.patienceController.getGameController().redo();		
		after = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertEquals(before,after);
		
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		before = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		this.patienceController.getGameController().undo();
		this.patienceController.getGameController().redo();
		after = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertTrue(before.equals(after));
		
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		patienceController.getGameController().move(stackArray[2].getTopCard(),stackArray[5]);
		before = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		this.patienceController.getGameController().undo();
		this.patienceController.getGameController().redo();
		after = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		assertTrue(before.equals(after));
		
		
		
	}

}
