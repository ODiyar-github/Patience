package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {
	@Test
	public void cloneTest()
	{
		Card card1 = new Card(Suit.CLUB, Rank.ACE);
		Card card2 = card1.clone();
		assertTrue(card2.equals(card1));
	}

}
