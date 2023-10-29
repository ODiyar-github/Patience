package control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Card;
import model.Freecell;
import model.Game;
import model.PlayerType;
import model.Rank;
import model.Stack;
import model.Suit;
import support.Move;

public class FreecellControllerTest {

	PatienceController patienceController;
	
	@Before
	public void setUp() throws Exception
	{
		patienceController = new PatienceController();
		
		Stack customStack = new Stack("testStack",0,0,0);
		Card card = null;
		
		for(Suit suit: Suit.values())
			for(Rank rank : Rank.values())
			{
				card = new Card(suit, rank);
				customStack.addCard(card);
			}
	
		Freecell freeCell = new Freecell(customStack, PlayerType.HUMAN);		
		patienceController.loadGame(freeCell);
		
	}

	@Test
	public void testMove() 
	{
		Card transferCard = new Card(Suit.DIAMOND, Rank.FOUR);
		Stack sourceStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.SECOND_FREECELL];
		sourceStack.addCard(transferCard);
		Stack destinationStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_FREECELL];
		destinationStack.addCard(new Card(Suit.HEART, Rank.FIVE));
		
		patienceController.getGameController().move(transferCard, destinationStack);
		
		assertTrue(destinationStack.getTopCard() == transferCard);
		assertTrue(transferCard.getCurrentStack() == destinationStack);
		assertFalse(sourceStack.getTopCard() == transferCard);
		
		fail("Not yet implemented");
	}

	@Test
	public void testIsLegalMove() 
	{	
		Boolean isLegalMove = null;
		Move move = null;
		Stack destinationStack = null;
		
		Stack sourceStack = null;//patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_STACK];
		Stack transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.CLUB, Rank.ACE));
		destinationStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_HOMECELL];
		move = new Move(sourceStack, destinationStack, transferStack);
		
		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertTrue(isLegalMove);
		
		transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.CLUB, Rank.SIX));
		destinationStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_HOMECELL];
		destinationStack.addCard(new Card(Suit.HEART, Rank.FIVE));
		move = new Move(sourceStack, destinationStack, transferStack);
		
		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertFalse(isLegalMove);
		
		transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.HEART, Rank.SIX));
		destinationStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_HOMECELL];
		destinationStack.addCard(new Card(Suit.HEART, Rank.FIVE));
		move = new Move(sourceStack, destinationStack, transferStack);
		
		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertTrue(isLegalMove);
		
		transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.HEART, Rank.SIX));
		destinationStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_FREECELL];
		move = new Move(sourceStack, destinationStack, transferStack);
		
		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertTrue(isLegalMove);

		destinationStack.addCard(new Card(Suit.HEART, Rank.FIVE));
		move = new Move(sourceStack, destinationStack, transferStack);

		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertFalse(isLegalMove);
		
		transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.HEART, Rank.FOUR));
		destinationStack = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_HOMECELL];
		destinationStack.addCard(new Card(Suit.HEART, Rank.FIVE));
		move = new Move(sourceStack, destinationStack, transferStack);
		
		transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.HEART, Rank.SIX));
		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertFalse(isLegalMove);
		
		transferStack = new Stack("", 0,0,0);
		transferStack.addCard(new Card(Suit.DIAMOND, Rank.SIX));
		isLegalMove = patienceController.getGameController().isLegalMove(move);
		assertFalse(isLegalMove);
	}

	@Test
	public void testCardFromTalon() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsGameWon() 
	{
		Stack[] stacks = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray();
		
		for(int stackPos = Freecell.FIRST_HOMECELL; stackPos <= Freecell.FOURTH_HOMECELL; stackPos++)
			for(int cardPos = 0; cardPos < 12; cardPos++)
				stacks[stackPos].addCard(new Card(Suit.HEART, Rank.ACE));
		
		Boolean isWon = patienceController.getGameController().isGameWon();	
		assertFalse(isWon);
		
		for(int stackPos = Freecell.FIRST_HOMECELL; stackPos <= Freecell.FOURTH_HOMECELL; stackPos++)
				stacks[stackPos].addCard(new Card(Suit.HEART, Rank.ACE));
		
		isWon = patienceController.getGameController().isGameWon();	
		assertTrue(isWon);
	}

	@Test
	public void testIsMovable() 
	{
		Card card = null;
		Boolean isMovable = null;
		
		Stack sourceStackHome = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_HOMECELL];
		card = new Card(Suit.DIAMOND, Rank.ACE);
		sourceStackHome.addCard(card);
		
		isMovable = patienceController.getGameController().isMovable(card);		
		assertFalse(isMovable);
		
		Stack sourceStackFreecell = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_FREECELL];
		card = new Card(Suit.DIAMOND, Rank.ACE);
		sourceStackFreecell.addCard(card);

		isMovable = patienceController.getGameController().isMovable(card);	
		assertTrue(isMovable);
		
		Stack sourceStackGame = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation().getStackArray()[Freecell.FIRST_STACK];
		card = new Card(Suit.DIAMOND, Rank.ACE);
		sourceStackGame.addCard(card);
		
		isMovable = patienceController.getGameController().isMovable(card);	
		assertTrue(isMovable);		
	}

	@Test
	public void testFreecellController() 
	{
		assertNotNull(patienceController.getGameController());
	}

	@Test
	public void testCardToTransferStack() 
	{
		fail("Not yet implemented");
	}

}
