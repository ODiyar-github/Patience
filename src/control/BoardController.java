package control;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import model.Card;
import model.Rank;
import model.Stack;
import model.Suit;

/**
 * Der BoardController übernimmt die einzelnen Funktion 
 * für die BoardSituation.
 * @author sopr091
 *
 */
public class BoardController {
	
	private PatienceController patienceController;
	
	
	/**
	 * Der Konstruktor für den BoardController.
	 * Der BoardController muss initialiesiert sein, sonst ist er Null.
	 * @param patienceController
	 */
	public BoardController(PatienceController patienceController) {
		this.patienceController = patienceController;
	}

	/**
	 * Löscht die gesamte Karten aus dem sourceStack die gleich dem tranferStack sind.
	 * @param sourceStack 
	 * @param transferStack 
	 */
	public static void removeFromStack(Stack sourceStack, Stack transferStack) {
		LinkedList<Card> sourceList = sourceStack.getCardList();
		for(Card transferCard : transferStack.getCardList()){
			Iterator<Card> iterator = sourceList.descendingIterator();
			
			while(iterator.hasNext()){
				Card sourceCard = iterator.next();
				if(sourceCard.equals(transferCard)){
					sourceList.remove(sourceCard);
					break;
				}
			}
		}
	}
	
	/**
	 * Die Methode fügt zum destination Stack die Karten aus dem transferStack hinzu. (Der tansferStack bleibt dabei nicht erhalten)
	 * 
	 * @param destinationStack = Der StackStapel in welche eingefügt wird.
	 * @param transferStack = Der StackStapel welche verschoben wird 
	 */
	public static void addToStack(Stack destinationStack, Stack transferStack) {
		LinkedList<Card> cardList = destinationStack.getCardList();
		for(Card card : transferStack.getCardList()){
			cardList.add(card);
			card.setCurrentStack(destinationStack);
		}
	}
	
	/**
	 * Gibt einen Stack mit allen 52 Karten in zufälliger Reihenfolge wieder
	 * @return Stack = Zufällige reihen folge von Karten.
	 */
	public static Stack generateRandomStack() {
		LinkedList<Card> completeList = new LinkedList<Card>(); //Liste mit allen 52 Karten
		Stack returnStack = new Stack("", 0, 0, 0);				//Stack, der zurückgegeben wird
		LinkedList<Card> returnList = returnStack.getCardList();
		
		//completeList befüllen
		for(int rank = 0; rank < 13; rank++){
			for(int suit = 0; suit < 4; suit++){
				completeList.add(new Card(Suit.values()[suit], Rank.values()[rank]));
			}
		}
		
		//returnList befüllen
		Random random = new Random();
		while(!completeList.isEmpty()){
			Card card = completeList.get(random.nextInt(completeList.size()));
			completeList.remove(card);
			returnList.add(card);
			card.setCurrentStack(returnStack);
		}
		
		return returnStack;
	}

	/**
	 * Gibt den PatienceController wieder
	 * @return PatienceController = Aktueller.
	 */
	public PatienceController getPatienceController() {
		if(this.patienceController!=null){
			return patienceController;
		}
		return null;
	}
	
	/**
	 * Ersetzt den Aktuellen PatienceController mit einem Neuen PatienceController.
	 * @param patienceController
	 */
	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
	}
	
	
	/**
	 * Gibt den Stack zurück, der bewegt werden soll, wenn diese Karte vom Nutzer ausgewählt wird (also alle Karten, die über dieser liegen, diese eingeschlossen)
	 * @param card
	 * @return
	 */
	
	public static Stack cardToTransferStack(Card card) {
		Stack transferStack = new Stack("", 0, 0, 0);
		transferStack.setArrPosition(card.getCurrentStack().getArrPosition());
		Stack sourceStack = card.getCurrentStack();
		LinkedList<Card> sourceList = sourceStack.getCardList();
		
		boolean cardFound = false;
		
		//Die gesuchte Karte und alle, die darüber liegen zum transferStack hinzufügen. Die Karten werden dafür geklont, da sie im sourceStack nicht verloren gehen sollen.
		for(Card sourceCard : sourceList){
			if(sourceCard == card){
				cardFound = true;
			}
			if(cardFound){
				Card transferCard = sourceCard.clone();
				transferStack.addCard(transferCard);
			}
		}
		
		return transferStack;
	}
	
	/**
	 * Dreht den kompletten Stack um
	 * @param stack
	 */
	public static void flipStack(Stack stack) {
		LinkedList<Card> oldCardList = stack.getCardList();
		LinkedList<Card> newCardList = new LinkedList<Card>();
		
		for(Card card : oldCardList) {
			newCardList.addFirst(card);
			card.setCurrentStack(stack);
			card.setVisible(!card.isVisible());
		}
		
		stack.setCardList(newCardList);
//		stack.getTopCard().setVisible(true);
	}
	
	public static void switchCoordinates(Stack firstStack, Stack secondStack) {
		int tempX = firstStack.getCoordinateX();
		int tempY = firstStack.getCoordinateY();
		
		firstStack.setCoordinateX(secondStack.getCoordinateX());
		firstStack.setCoordinateY(secondStack.getCoordinateY());
		secondStack.setCoordinateX(tempX);
		secondStack.setCoordinateY(tempY);
		
		
		
	}
}
