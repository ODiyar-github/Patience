package model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author sopr098
 *	Die Klasse Stack realisiert einen Kartenstapel.
 */
public class Stack implements Serializable {

	/**
	 * Name des Stapels
	 */
	private String name;

	/**
	 * x-Koordinate für die GUI zur Positionsbestimmung
	 */
	private int coordinateX;

	/**
	 * y-Koordinate für die GUI zur Positionsbestimmung
	 */
	private int coordinateY;

	/**
	 * Gibt die Position des Stacks im StackArray aus BoardSituation an
	 */
	private int arrPosition;

	/**
	 * Liste, die die Karten des Stacks enthält
	 */
	private LinkedList<Card> cardList;

	/**
	 * Direction gibt an, in welche Richtung der Stack aufgefächert ist
	 */
	private Direction direction;

	/**
	 * @param name Name des Stacks
	 * @param posX x-Koordinate zur Positionsbestimmung der GUI
	 * @param posY y-Koordinate zur Positionsbestimmung der GUI
	 * @param position Position des Stacks im StackArray aus BoardSituation
	 */
	public Stack(String name, int coordinateX, int coordinateY, int arrPosition) {
		this.name = name;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.arrPosition = arrPosition;
		this.cardList = new LinkedList<Card>();
	}
	
	/**
	 * @param name Name des Stacks
	 * @param posX x-Koordinate zur Positionsbestimmung der GUI
	 * @param posY y-Koordinate zur Positionsbestimmung der GUI
	 * @param position Position des Stacks im StackArray aus BoardSituation
	 * @param card Karte die direkte der Kartenliste des Kartenstapel zugefügt wird
	 */
	//wird dieser Konstruktor benötigt?
	public Stack(String name, int coordinateX, int coordinateY, int arrPosition, Card card) {
		this.name = name;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.arrPosition = arrPosition;
		this.cardList = new LinkedList<Card>();
		this.addCard(card);
	}
	
	public Stack( LinkedList<Card> cardList){
		this.cardList=cardList;
	}
	/**
	 * @return
	 */
	public boolean equals(Stack other) {
		LinkedList<Card> cardList = this.getCardList();
		LinkedList<Card>  otherCardList = other.getCardList();
		
		if(cardList.size()!=otherCardList.size()){return false;}
		for(int i = 0; i < cardList.size(); i++) {
			if(!(cardList.get(i).equals(otherCardList.get(i)))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return Returned einen neuen Stack mit selbem Inhalt
	 */
	public Stack clone() {
		Stack tempStack = new Stack(this.name, this.coordinateX, this.coordinateY, this.arrPosition);
		LinkedList<Card> tempCardList = tempStack.getCardList();
		
		for(int index = 0; index < cardList.size(); index++) {
			Card tempCard = cardList.get(index);
			tempCard = (Card) tempCard.clone();
			
			tempCardList.add(tempCard);
			tempCard.setCurrentStack(tempStack);
		}
	
		return tempStack;
	}
	
	/** 
	 * @return Gibt die oberste Karte des Stapels zurück
	 */
	public Card getTopCard() {
		if(cardList.size()==0){return null;}
		Card topCard = cardList.get(cardList.size()-1);
		return topCard;
	}
	
	/**
	 * legt Karte oben auf den Stapel
	 * @param card
	 */
	public void addCard(Card card){
		this.cardList.add(card);
		card.setCurrentStack(this);
	}
	
	/**
	 * entfernt oberste Karte des Stapels
	 */
	public void removeTopCard(){
		this.cardList.removeLast();
	}
	
	
	/**
	 * @return name vom Stack
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Name, auf den der Stack gesetzt werden soll
	 * Setzt den Namen des Stacks auf das übergebene Argument
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return x-Koordinate vom Stack
	 */
	public int getCoordinateX() {
		return coordinateX;
	}

	/**
	 * @param coordinateX x-Koordiante, auf die der Stack gesetzt werden soll
	 * Setzt die x-Koordinate des Stacks auf das übergebene Argument
	 */
	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	/**
	 * @return y-Koordinate vom Stack
	 */
	public int getCoordinateY() {
		return coordinateY;
	}

	/**
	 * @param coordinateY y-Koordiante, auf die der Stack gesetzt werden soll
	 * Setzt die y-Koordinate des Stacks auf das übergebene Argument
	 */
	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	/**
	 * @return Gibt die Position des Stacks im StackArray zurück
	 */
	public int getArrPosition() {
		return arrPosition;
	}

	/**
	 * @param arrPosition Position, auf die der Stack im StackArray gesetzt werden soll
	 */
	public void setArrPosition(int arrPosition) {
		this.arrPosition = arrPosition;
	}

	/**
	 * @return Gibt eine ArrayList von Karten von dem jeweiligen Stack zurück
	 */
	public LinkedList<Card> getCardList() {
		return cardList;
	}

	/**
	 * @param cardList ist eine ArrayList, die der Stack enthalten soll
	 * Fügt auf den Stack eine Liste von Karten (befüllt den Stack mit Karten)
	 */
	public void setCardList(LinkedList<Card> cardList) {
		this.cardList = cardList;
	}

	/**
	 * @return Gibt die Richtung an, wohin der Stack aufgefächert ist
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction Richtung, wohin der Stack aufgefächert werden soll
	 * Setzt die Richtung des Stapels in die übergebene Richtung
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void printStack()
	{
		if(this.cardList==null){System.out.println("stack ist null");}
		if(this.cardList.isEmpty()){System.out.println("stack cardlist ist leer");}
		for( Card card: this.cardList )
		{
			System.out.print(card.toString()+" ");
		}
	}
	
	public String stringCoordinates(){
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
		String xString = letters[coordinateX];
		String yString = "" + (coordinateY + 1);
		return xString + yString;
	}

}
