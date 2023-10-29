package model;

import java.io.Serializable;

/**
 * @author sopr098
 *	Enumeration, um die Wertigkeit der einzelnen Spielkarten zu unterscheiden
 *	Mögliche Wertigkeiten sind Ass, Zwei, Drei, Vier, Fünf, Sechs, Sieben, Acht, Neun, Zehn, Bube, Dame und König
 */
public enum Rank implements Serializable {

	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

}
