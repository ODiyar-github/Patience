package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {

	@Test
	public void testEquals() {
		Stack stack1= new Stack("stack1",0,0,0);
		Stack stack2= new Stack("stack1",0,0,0);
		Card card1 = new Card(Suit.CLUB,Rank.ACE);
		Card card2 = new Card(Suit.CLUB,Rank.ACE);
		stack1.addCard(card1);
		stack2.addCard(card2);
		assertTrue(stack1.equals(stack2));
		stack1.addCard(card1);
		assertFalse(stack1.equals(stack2));
	}
	
	@Test
	public void testClone(){
		Stack stack1= new Stack("stack1",0,0,0);
		Card card1 = new Card(Suit.CLUB,Rank.ACE);
		stack1.addCard(card1);
		Stack clonedStack = stack1.clone();
		assertTrue(stack1.equals(clonedStack));
	}
	
	
	
	

}
