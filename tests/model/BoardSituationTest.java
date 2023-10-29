package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import control.IOController;
import control.PatienceController;

public class BoardSituationTest {
	PatienceController patienceController = new PatienceController();
	String stackFile="Pik-Ass Kreuz-4 Karo-Bube Herz-10 Karo-Koenig Herz-Dame Kreuz-9 Kreuz-8 Karo-7 Karo-6 Herz-9 Herz-8";
	Stack talon;
	Stack first;
	Stack second;
	Stack third;
	Stack fourth;
	Stack tray;
	private BoardSituation boardSituation;
	private BoardSituation boardSituationClone;
	
	@Before
	public void doBeforeTest()
	{
		Stack talon = new Stack("talon",0,0,0);
		Stack first = new Stack("first",0,0,1);
		Stack second = new Stack("second",0,0,2);
		Stack third = new Stack("third",0,0,3);
		Stack fourth = new Stack("fourth",0,0,4);
		Stack tray = new Stack("tray",0,0,5);
		talon = IOController.stringToStack(stackFile);
		Stack[] stacks = new Stack[]{talon,first,second,third,fourth,tray};
		boardSituation = new BoardSituation(stacks);
		boardSituationClone = new BoardSituation(null);
	}
	
	@Test
	public void testClone()
	{
		boardSituationClone = boardSituation.clone();
		assertTrue(boardSituationClone.equals(boardSituation));
	}
}
