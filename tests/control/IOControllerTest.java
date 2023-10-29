package control;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Card;
import model.Rank;
import model.Stack;
import model.Suit;

public class IOControllerTest {
	
	
	
	PatienceController patienceController = new PatienceController();
	IOController iOController = new IOController(patienceController);
	Stack testStack = new Stack("", 0, 0, 0);
	//testStack.add();
	//Card card = new Card(getSuit(suitNrank[0]), getRank(suitNrank[1]));
	Card card1 = new Card(Suit.CLUB,Rank.NINE);
	Card card2 = new Card(Suit.HEART,Rank.QUEEN);
	Card card3 = new Card(Suit.DIAMOND,Rank.KING);
	Card card4 = new Card(Suit.HEART,Rank.TEN);
	Card card5 = new Card(Suit.DIAMOND,Rank.JACK);
	Card card6 = new Card(Suit.CLUB,Rank.FOUR);
	Card card7 = new Card(Suit.SPADE,Rank.ACE);

	
	Stack testStack2 = new Stack("", 0, 0, 0);
	@Test
	public void doBeforeTests() {
		testStack.addCard(card1);
		testStack.addCard(card2);
		testStack.addCard(card3);
		testStack.addCard(card4);
		testStack.addCard(card5);
		testStack.addCard(card6);
		testStack.addCard(card7);
	}
	@Test
	public void test()
	{
		testStack2 = IOController.loadCustomStack("/sopra/sopgr09/sopr095/Schreibtisch/text.txt");
		assertEquals(testStack, testStack2);
		
	}

}
