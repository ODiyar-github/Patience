package model;

import static org.junit.Assert.*;

import org.junit.Test;

import control.IOController;
import control.PatienceController;

public class IdiotTest {

	public static final int TALON = 0;
	public static final int FIRST_STACK = 1;
	public static final int SECOND_STACK = 2;
	public static final int THIRD_STACK = 3;
	public static final int FOURTH_STACK = 4;
	public static final int TRAY = 5;
	
	PatienceController patienceController = new PatienceController();
	String stackString="Pik-Ass Pik-2 Pik-3 Pik-4 Pik-5 Pik-6 Pik-7 Pik-8 Pik-9 Pik-10 Pik-Bube Pik-Dame Pik-Koenig Kreuz-Ass Kreuz-2 Kreuz-3 Kreuz-4 Kreuz-5 Kreuz-6 Kreuz-7 Kreuz-8 Kreuz-9 Kreuz-10 Kreuz-Bube Kreuz-Dame Kreuz-Koenig Karo-Ass Karo-2 Karo-3 Karo-4 Karo-5 Karo-6 Karo-7 Karo-8 Karo-9 Karo-10 Karo-Bube Karo-Dame Karo-Koenig Herz-Ass Herz-2 Herz-3 Herz-4 Herz-5 Herz-6 Herz-7 Herz-8 Herz-9 Herz-10 Herz-Bube Herz-Dame Herz-Koenig";
	
	@Test
	public void testIdiot() {
		Stack talon =  IOController.stringToStack(stackString);
		Idiot idiot = new Idiot(talon,PlayerType.HUMAN);
		patienceController.loadGame(idiot);
		BoardSituation situation = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();
		Stack[] otherStacks = new Stack[6];
		Stack otherTalon = IOController.stringToStack(stackString);
		otherStacks[0]= otherTalon;
		otherStacks[1]= new Stack("otherFirst",0,0,0);
		otherStacks[2]= new Stack("otherSecond",0,0,0);
		otherStacks[3]= new Stack("otherThird",0,0,0);
		otherStacks[4]= new Stack("otherFourth",0,0,0);
		otherStacks[5]= new Stack("otherTray",0,0,0);
		BoardSituation otherSituation = new BoardSituation(otherStacks);
		assertTrue(situation.equals(otherSituation));
	}

}
