package model;

import org.junit.Test;

import control.IOController;

public class FreecellTest {
	IOController iocontroller = new IOController(null);
	Stack stack = iocontroller.loadCustomStack("/sopra/sopgr09/sopr095/Schreibtisch/share/Projekt2/Karten");
	Freecell freecell = new Freecell(stack, null);
	Stack[] generateStack = freecell.getCurrentBoardsituation().getStackArray();
	
	@Test
	public void testGenerateStack()
	{
		for(Stack current : generateStack)
		{
			current.printStack();
			System.out.println("");
		}
	}

}
