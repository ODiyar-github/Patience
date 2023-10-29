package control;

import static org.junit.Assert.*;

import org.junit.Test;

import model.BoardSituation;
import model.Stack;
import model.Zank;
import support.Move;

public class ZankControllerTest {
	
	PatienceController patienceController = new PatienceController();
	IOController iOController = patienceController.getiOController();
	Stack stack1 = iOController.loadCustomStack("Karten");
	Zank zank = new Zank(stack1, stack1, null, null);
	ZankController zankController = new ZankController(patienceController, zank);
	BoardSituation boardSituation = zank.getCurrentBoardsituation();
	Stack[] boardStacks = boardSituation.getStackArray();
	Move moveLegal = new Move(boardStacks[9],boardStacks[14],boardStacks[9]);
	Move moveIllegal = new Move(boardStacks[13],boardStacks[15],boardStacks[13]);
	
	@Test
	public void test(){
		for(int i=0; i<boardStacks.length; i++){
			System.out.print(i);
			boardStacks[i].printStack();
			System.out.println("");
		}
		
	}
	@Test
	public void testIsLegalMove(){
		assertTrue(zankController.isLegalMove(moveLegal));
		assertFalse(zankController.isLegalMove(moveIllegal));
	}
	
}
