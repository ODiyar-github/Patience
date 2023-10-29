package model;

import static org.junit.Assert.*;

import org.junit.Test;

import control.IOController;
import control.PatienceController;

public class ZankTest {
	
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
	
	
	
	//Pik-Koenig Pik-Dame Pik-Bube Pik-10 Pik-8 Pik-7 Pik-6 Pik-5 Pik-4 Pik-3 Pik-Ass Pik-2
	//Pik-Koenig Pik-Dame Pik-Bube Pik-10 Pik-8 Pik-7 Pik-6 Pik-5 Pik-4 Pik-3 Pik-2 Pik-Ass
	//Kreuz-Ass Kreuz-2 Kreuz-3 Kreuz-4
	PatienceController patienceController = new PatienceController();
	String firstStackString="Pik-Ass Pik-2 Pik-3 Pik-4 Pik-5 Pik-6 Pik-7 Pik-8 Pik-9 Pik-10 Pik-Bube Pik-Dame Pik-Koenig Kreuz-Ass Kreuz-2 Kreuz-3 Kreuz-4 Kreuz-5 Kreuz-6 Kreuz-7 Kreuz-8 Kreuz-9 Kreuz-10 Kreuz-Bube Kreuz-Dame Kreuz-Koenig Karo-Ass Karo-2 Karo-3 Karo-4 Karo-5 Karo-6 Karo-7 Karo-8 Karo-9 Karo-10 Karo-Bube Karo-Dame Karo-Koenig Herz-Ass Herz-2 Herz-3 Herz-4 Herz-5 Herz-6 Herz-7 Herz-8 Herz-9 Herz-10 Herz-Bube Herz-Dame Herz-Koenig";
	String secondStackString="Pik-2 Pik-Ass Pik-3 Pik-4 Pik-5 Pik-6 Pik-7 Pik-8 Pik-9 Pik-10 Pik-Bube Pik-Dame Pik-Koenig Kreuz-Ass Kreuz-2 Kreuz-3 Kreuz-4 Kreuz-5 Kreuz-6 Kreuz-7 Kreuz-8 Kreuz-9 Kreuz-10 Kreuz-Bube Kreuz-Dame Kreuz-Koenig Karo-Ass Karo-2 Karo-3 Karo-4 Karo-5 Karo-6 Karo-7 Karo-8 Karo-9 Karo-10 Karo-Bube Karo-Dame Karo-Koenig Herz-Ass Herz-2 Herz-3 Herz-4 Herz-5 Herz-6 Herz-7 Herz-8 Herz-9 Herz-10 Herz-Bube Herz-Dame Herz-Koenig";
	String firstSubStackString="Pik-Koenig Pik-Dame Pik-Bube Pik-10 Pik-9 Pik-8 Pik-7 Pik-6 Pik-5 Pik-4 Pik-3 Pik-2 Pik-Ass";
	String secondSubStackString="Pik-Koenig Pik-Dame Pik-Bube Pik-10 Pik-9 Pik-8 Pik-7 Pik-6 Pik-5 Pik-4 Pik-3 Pik-Ass Pik-2";
	String otherFirstStackString="Kreuz-5 Kreuz-6 Kreuz-7 Kreuz-8 Kreuz-9 Kreuz-10 Kreuz-Bube Kreuz-Dame Kreuz-Koenig Karo-Ass Karo-2 Karo-3 Karo-4 Karo-5 Karo-6 Karo-7 Karo-8 Karo-9 Karo-10 Karo-Bube Karo-Dame Karo-Koenig Herz-Ass Herz-2 Herz-3 Herz-4 Herz-5 Herz-6 Herz-7 Herz-8 Herz-9 Herz-10 Herz-Bube Herz-Dame Herz-Koenig";
	String otherSecondStackString="Kreuz-5 Kreuz-6 Kreuz-7 Kreuz-8 Kreuz-9 Kreuz-10 Kreuz-Bube Kreuz-Dame Kreuz-Koenig Karo-Ass Karo-2 Karo-3 Karo-4 Karo-5 Karo-6 Karo-7 Karo-8 Karo-9 Karo-10 Karo-Bube Karo-Dame Karo-Koenig Herz-Ass Herz-2 Herz-3 Herz-4 Herz-5 Herz-6 Herz-7 Herz-8 Herz-9 Herz-10 Herz-Bube Herz-Dame Herz-Koenig";
	@Test
	public void testZank(){
		
		Stack firstStack =  IOController.stringToStack(firstStackString);
		Stack secondStack =  IOController.stringToStack(secondStackString);
		Stack firstSubStack =  IOController.stringToStack(firstSubStackString);
		Stack secondSubStack =  IOController.stringToStack(secondSubStackString);
		Stack otherFirstStack =  IOController.stringToStack(otherFirstStackString);
		Stack otherSecondStack =  IOController.stringToStack(otherSecondStackString);
		Zank zank = new Zank(firstStack,secondStack,PlayerType.HUMAN,PlayerType.HUMAN);
		patienceController.loadGame(zank);
		BoardSituation situation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		Stack[] otherStacks = new Stack[22];
		otherStacks[FIRST_TALON]=otherFirstStack;
		otherStacks[SECOND_TALON]=otherSecondStack;
		otherStacks[FIRST_TRAY]=new Stack("stack",0,0,0);
		otherStacks[SECOND_TRAY]=new Stack("stack",0,0,0);
		otherStacks[FIRST_SUBSTACK]=firstSubStack;
		otherStacks[SECOND_SUBSTACK]=secondSubStack;
		
		otherStacks[FIRST_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[FIRST_OUTER_STACK].addCard(new Card (Suit.CLUB,Rank.FOUR));
		otherStacks[SECOND_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[SECOND_OUTER_STACK].addCard(new Card (Suit.CLUB,Rank.THREE));
		otherStacks[THIRD_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[THIRD_OUTER_STACK].addCard(new Card(Suit.CLUB,Rank.TWO));
		otherStacks[FOURTH_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[FOURTH_OUTER_STACK].addCard(new Card(Suit.CLUB,Rank.ACE));
		otherStacks[FIFTH_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[FIFTH_OUTER_STACK].addCard(new Card(Suit.CLUB,Rank.ACE));
		otherStacks[SIXTH_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[SIXTH_OUTER_STACK].addCard(new Card(Suit.CLUB,Rank.TWO));
		otherStacks[SEVENTH_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[SEVENTH_OUTER_STACK].addCard(new Card (Suit.CLUB,Rank.THREE));
		otherStacks[EIGHTH_OUTER_STACK]=new Stack("",0,0,0);
		otherStacks[EIGHTH_OUTER_STACK].addCard(new Card (Suit.CLUB,Rank.FOUR));
		
		
		for(int i=FIRST_CLUB_STACK;i<=SECOND_HEART_STACK;i++){
			otherStacks[i] = new Stack("stack",0,0,0);
		}
		BoardSituation otherSituation = new BoardSituation(otherStacks);
		for(int j=FIRST_TALON;j<=SECOND_HEART_STACK;j++){
			System.out.println();
			situation.getStackArray()[j].printStack();
		}
		System.out.println("Vergleich");
		for(int j=FIRST_TALON;j<=SECOND_HEART_STACK;j++){
			System.out.println();
			otherSituation.getStackArray()[j].printStack();
		}
		assertTrue(situation.equals(otherSituation));
	}
	
	
	
}
