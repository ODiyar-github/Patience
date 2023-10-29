package control;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Idiot;
import model.PlayerType;
import model.Rank;
import model.Stack;
import model.Suit;
import support.Move;

public class IdiotControllerTest {

	PatienceController patienceController = new PatienceController();
	String stackFile="Pik-Ass Kreuz-4 Karo-Bube Herz-10 Karo-Koenig Herz-Dame Kreuz-9 Kreuz-8 Karo-7 Karo-6 Herz-9 Herz-8";
	String stackFileLegal="Pik-Ass Herz-4 Karo-Bube Herz-10";
	String stackFileNotLegal="Pik-Ass Kreuz-4 Karo-Bube Herz-10";
	String stackFileWon = "Pik-Ass Kreuz-4 Karo-Bube Herz-10";
	String stackFileNotWon1="Pik-Ass Herz-4 Karo-Bube Herz-10";
	String stackFileNotWon2="Pik-Ass Kreuz-4 Karo-Bube Herz-10 Karo-Koenig Herz-Dame Kreuz-9 Kreuz-8";

	
	/**
	 * testet isLegalMove(move Move)
	 */
	@Test
	public void testMove() {
		
		Stack talon = IOController.stringToStack(stackFile);	
		Idiot idiot = new Idiot(talon,PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		Stack[] stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		
		assertTrue(stackArray[0]!=null);
		assertTrue(stackArray[0].getTopCard()!=null);
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		assertTrue(stackArray[1].getTopCard().getSuit()==Suit.SPADE);
		assertTrue(stackArray[1].getTopCard().getRank()==Rank.ACE);
		assertTrue(stackArray[2].getTopCard().getSuit()==Suit.CLUB);
		assertTrue(stackArray[2].getTopCard().getRank()==Rank.FOUR);
		assertTrue(stackArray[3].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[3].getTopCard().getRank()==Rank.JACK);
		assertTrue(stackArray[4].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[4].getTopCard().getRank()==Rank.TEN);
		assertTrue(stackArray[0].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[0].getTopCard().getRank()==Rank.KING);
		
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		patienceController.getGameController().move(stackArray[4].getTopCard(),stackArray[5]);
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		assertTrue(stackArray[1].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[1].getTopCard().getRank()==Rank.KING);
		assertTrue(stackArray[2].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[2].getTopCard().getRank()==Rank.QUEEN);
		assertTrue(stackArray[3].getTopCard().getSuit()==Suit.CLUB);
		assertTrue(stackArray[3].getTopCard().getRank()==Rank.NINE);
		assertTrue(stackArray[4].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[4].getTopCard().getRank()==Rank.TEN);
		assertTrue(stackArray[0].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[0].getTopCard().getRank()==Rank.SEVEN);
		
		patienceController.getGameController().move(stackArray[1].getTopCard(),stackArray[3]);
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		//es sollte sich nichts ändern
		assertTrue(stackArray[1].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[1].getTopCard().getRank()==Rank.KING);
		assertTrue(stackArray[2].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[2].getTopCard().getRank()==Rank.QUEEN);
		assertTrue(stackArray[3].getTopCard().getSuit()==Suit.CLUB);
		assertTrue(stackArray[3].getTopCard().getRank()==Rank.NINE);
		assertTrue(stackArray[4].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[4].getTopCard().getRank()==Rank.TEN);
		assertTrue(stackArray[0].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[0].getTopCard().getRank()==Rank.SEVEN);
		
		patienceController.getGameController().move(stackArray[1].getTopCard(),stackArray[5]);
		stackArray = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		//es sollte sich nichts ändern
		assertTrue(stackArray[1].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[1].getTopCard().getRank()==Rank.KING);
		assertTrue(stackArray[2].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[2].getTopCard().getRank()==Rank.QUEEN);
		assertTrue(stackArray[3].getTopCard().getSuit()==Suit.CLUB);
		assertTrue(stackArray[3].getTopCard().getRank()==Rank.NINE);
		assertTrue(stackArray[4].getTopCard().getSuit()==Suit.HEART);
		assertTrue(stackArray[4].getTopCard().getRank()==Rank.TEN);
		assertTrue(stackArray[0].getTopCard().getSuit()==Suit.DIAMOND);
		assertTrue(stackArray[0].getTopCard().getRank()==Rank.SEVEN);

	}
	
	
	/**
	 * testet move(Card transferCard, Stack destinationStack) 
	 */
	@Test
	public void testIsLegalMove() {
		
		Stack talon = IOController.stringToStack(stackFileLegal);
		Idiot idiot = new Idiot(talon,PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		Stack[] stackArray = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		Move move3 = new Move(stackArray[2],stackArray[5],stackArray[2]);
		assertTrue(patienceController.getGameController().isLegalMove(move3));
		
		talon = IOController.stringToStack(stackFileNotLegal);
		idiot = new Idiot(talon,PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		stackArray = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		patienceController.getGameController().cardFromTalon(stackArray[0].getTopCard());
		stackArray = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		Move move2 = new Move(stackArray[1],stackArray[0],null);
		assertFalse(patienceController.getGameController().isLegalMove(move2));
	}
	
	
	/**
	 * testet gameWon
	 */
	@Test
	public void testGameWon() {
		
		Stack talon = new Stack("talon",0,0,0);
		Stack first = new Stack("first",0,0,1);
		Stack second = new Stack("second",0,0,2);
		Stack third = new Stack("third",0,0,3);
		Stack fourth = new Stack("fourth",0,0,4);
		Stack tray = new Stack("tray",0,0,5);
		
		Stack[] stacks={talon,first,second,third,fourth,tray};		
		
		stacks[0]=IOController.stringToStack(stackFileWon);
		
		Idiot idiot = new Idiot(IOController.stringToStack(stackFileWon),PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		Stack[] stackArray = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		

		idiot = new Idiot(IOController.stringToStack(stackFileNotWon1),PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		assertFalse(patienceController.getGameController().isGameWon());
		
		
		idiot = new Idiot(IOController.stringToStack(stackFileNotWon2),PlayerType.HUMAN);
		this.patienceController.loadGame(idiot);
		stackArray = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();

	}

}
