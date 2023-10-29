package model;

import java.io.Serializable;

/**
 * @author sopr098
 *	Enumeration, um die Symbole der Karten zu unterscheiden
 * Mögliche Symbole sind Karo, Herz, Pik und Kreuz
 * Die Unterscheidung der Farben erfolgt in den Karten 
 */
public enum Suit implements Serializable {

	DIAMOND, HEART, CLUB, SPADE;

}
