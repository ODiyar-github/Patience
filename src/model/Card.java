package model;

//import java.io.FileInputStream;
//import java.io.ObjectInputStream;
import java.io.Serializable;

//import javafx.scene.image.Image;

/**
 * @author sopr099
 *  Eine Karte innerhalb eines beliebigen Spiels
 */
public class Card implements Serializable {

	/**
	 * Gibt an ob Karte aufgedeckt ist
	 */
	private boolean visible;

//	/**
//	 * Das Bild der Karte (entsprechend ihres Symbols und ihrer Wertigkeit)
//	 */
//	private Image image;

	/**
	 * Die Wertigkeit der Karte (ACE,TWO,...,KING)
	 */
	private Rank rank;

	/**
	 * Das Symbol der Karte(DIAMOND, HEART, CLUB, SPADE)
	 */
	private Suit suit;

	/**
	 * Der Stapel auf dem die Karte sich aktuell befindet
	 */
	private Stack currentStack;

	/**
	 *  Erzeugt eine Karte mit passendem Bild gemäß des übergebenen Symbols und der Wertigkeit
	 */
	public Card(Suit suit, Rank rank) {
		visible = false;
//		image = new Image("images/cardImages/" + suit + "_" + rank + ".png");
		this.rank = rank;
		this.suit = suit;
		currentStack = null;
	}

	/**
	 * @return Zeigt an ob der Wert der Karte auf dem Spielfeld sichtbar ist
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible 
	 * @return Setzt ob der Wert der Karte auf dem Spielfeld sichtbar ist
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

//	/**
//	 * @return Das Bild welches den Wert der Karte (suit und rank) anzeigt
//	 */
//	public Image getImage() {
//		return image;
//	}
	
	/**
	 * @return Gibt den rank der Karte zurück
	 */
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * @return Gibt den suit der Karte zurück
	 */
	public Suit getSuit() {
		return suit;
	}
	
	public String toString()
	{
		return this.getSuit().toString()+"-"+this.getRank().toString();
	}
	

	/**
	 * @return Gibt den Stack zurück auf dem die Karte aktuell liegt
	 */
	public Stack getCurrentStack() {
		if(currentStack==null){System.out.println("kein currentStack");}
		return currentStack;
	}

	/**
	 * @param Setzt den Stack auf dem die Karte aktuell liegt
	 */
	public void setCurrentStack(Stack currentStack) {
		this.currentStack = currentStack;
	}

	/**
	 * @return Gibt zurück ob es sich um eine rote Karte handelt
	 */
	public boolean isRed() {
		return suit.ordinal() < 2;
	}
	
	/**
	 * @return Gibt zurück ob es sich um eine schwarze Karte handelt
	 */
	public boolean isBlack() {
		return suit.ordinal() > 1;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Card clone(){
		Card card = new Card(this.suit, this.rank);
		card.setVisible(this.visible);
		card.setCurrentStack(this.currentStack);
		return card;
	}
	
	
	public boolean equals(Card card){
		//System.out.println("Die equals-Methode für Karten wurde benutzt.");
		return card.getSuit() == this.suit && card.getRank() == this.rank;
	}
}
