package model;

import java.io.Serializable;

/**
 * @author sopr098
 *	Statistik-Klasse verwaltet für die jeweiligen Spieltypen ihre Statistik und fasst deren Daten zusammen
 */
public class Statistic implements Serializable {

	/**
	 * Anzahl der Spiele, die insgesamt von dem jeweiligen Spieltyp gespielt wurden
	 */
	private int gamesPlayed;

	/**
	 * Anzahl der Spiele, die insgesamt vom jeweiligen Spieltyp erfolgreich beendet wurden
	 */
	private int gamesSuccessful;

	/**
	 * Durchschnittliche Anzahl an Zügen, die benötigt wurden, um das Spiel erfolgreich zu beenden
	 */
	private int averageMoves;

	/**
	 * Minimale Anzahl an Zügen, womit ein Spiel des jeweiligen Spieltyps erfolgreich beendet wurde
	 */
	private int minMoves;

	/**
	 * Maximale Anzahl an Zügen, womit ein Spiel des jeweiligen Spieltyps erfolgreich beendet wurde
	 */
	private int maxMoves;

	/**
	 * Durchschnittliche Anzahl an übrigen Karten von Spielen, die nicht erfolgreich beendet wurden
	 */
	private int averageCardsLeft;


	/**
	 * Erstellt ein neues Statistik-Objekt und setzt alle Attribute auf 0
	 */
	public Statistic() {
		this.gamesPlayed=0;
		this.gamesSuccessful=0;
		this.averageMoves=0;
		this.minMoves=0;
		this.maxMoves=0;
		this.averageCardsLeft=0;
	}

	/**
	 * @return Gibt die Anzahl der insgesamt gespielten Spiele eines jeweiligen Spieltyps zurück
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 * @param gamesPlayed Anzahl der insgesamt gespielten Spiele, auf das die Statistik gesetzt werden soll
	 * Setzt gamesPlayed auf den übergebenen Parameter
	 */
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * @return Gibt die Anzahl der erfolgreich beendeten Spiele eines jeweiligen Spieltyps zurück
	 */
	public int getGamesSuccessfull() {
		return gamesSuccessful;
	}

	/**
	 * @param gamesSuccessfull Anzahl der insgesamt erfolgreich beendeten Spiele, auf das die Statistik gesetzt werden soll
	 * Setzt gamesSuccessfull auf den übergebenen Parameter
	 */
	public void setGamesSuccessfull(int gamesSuccessfull) {
		this.gamesSuccessful = gamesSuccessfull;
	}

	/**
	 * @return Gibt die Anzahl der durchscnittlichen Züge zurück, die benötigt werden, um ein Spiel erfolgreich zu beenden
	 */
	public int getAverageMoves() {
		return averageMoves;
	}

	/**
	 * @param averageMoves Statistik wird auf die Anzahl der durchscnittlichen Züge, die benötigt werden, um ein Spiel erfolgreich zu beenden
	 * Setzt averageMoves auf die Anzahl der durchscnittlichen Züge, die benötigt werden, um ein Spiel erfolgreich zu beenden
	 */
	public void setAverageMoves(int averageMoves) {
		this.averageMoves = averageMoves;
	}

	/**
	 * @return Gibt die minimale Anzahl an Zügen zurück, womit ein Spiel des jeweiligen Spieltyps erfolgreich beendet wurde
	 */
	public int getMinMoves() {
		return minMoves;
	}

	/**
	 * @param minMoves Minimale Anzahl an Zügen, worauf die Statistik gesetzt werden soll
	 * Setzt minMoves auf den Wert des Parameters
	 */
	public void setMinMoves(int minMoves) {
		this.minMoves = minMoves;
	}

	/**
	 * @return Gibt die maximale Anzahl an Zügen zurück, womit ein Spiel des jeweiligen Spieltyps erfolgreich beendet wurde
	 */
	public int getMaxMoves() {
		return maxMoves;
	}

	/**
	 * @param maxMoves Maximale Anzahl an Zügen, worauf die Statistik gesetzt werden soll
	 * Setzt maxMoves auf den Wert des Parameters
	 */
	public void setMaxMoves(int maxMoves) {
		this.maxMoves = maxMoves;
	}

	/**
	 * @return Gibt die durchschnittliche Anzahl an übrigen Karten von Spielen zurück, die nicht erfolgreich beendet wurden
	 */
	public int getAverageCardsLeft() {
		return averageCardsLeft;
	}

	/**
	 * @param averageCardsLeft Durchschnittliche Anzahl an übrigen Karten von Spielen, die nicht erfolgreich beendet wurden
	 * Setzt averageCards auf den Wert des Paramters
	 */
	public void setAverageCardsLeft(int averageCardsLeft) {
		this.averageCardsLeft = averageCardsLeft;
	}

	public void incrementGamesPlayed(){
		gamesPlayed++;
	}
	
	public void incrementGamesSuccessful(){
		gamesSuccessful++;
	}
}
