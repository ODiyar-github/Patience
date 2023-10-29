package model;

import java.io.Serializable;

/**
 * @author sopr099
 * Realisiert eine Situtation im Spiel und beeinhaltet die Stapel mit ihrem jeweiligen Inhalten zu diesem Zeitpunkt
 */
public class BoardSituation implements Serializable {

	/**
	 * Die Stapel mit ihren Inhalten in dieser Situation
	 */
	private Stack[] stackArray;
	
	/**
	 * Referenz auf vorherige BoardSituation in der BoardSituationList
	 */
	private BoardSituation next;
	
	/**
	 * Referenz auf nachfolgende BoardSituation in der BoardSituationList
	 */
	private BoardSituation previous;

	/**
	 *  Eine BoardSituation die die Verteilung der Stapel zu einem festen Zeitpunkt darstellt
	 */
	public BoardSituation(Stack[] stackArray) {
		this.setStackArray(stackArray);
	}

	/**
	 * @return Die nächste BoardSituation der Liste
	 */
	public BoardSituation getNext() {
		return next;
	}

	/**
	 * @param next Setzt die nächste Boardsituation auf next 
	 */
	public void setNext(BoardSituation next) {
		if(this.getNext() != null)
			this.getNext().setPrevious(null);
		
		this.next = next;
		this.next.setPrevious(this);
	}

	/**
	 * @return the previous
	 */
	public BoardSituation getPrevious() {
		return previous;
	}

	/**
	 * @param previous the previous to set
	 */
	private void setPrevious(BoardSituation previous) {
		this.previous = previous;
	}

	/**
	 * @return Returned den Array mit allen Stapeln in der Boardsituation
	 */
	public Stack[] getStackArray() {
		return stackArray;
	}

	/**
	 * @param stackArray Setzt den Array mit allen Stapeln in der Boardsituation
	 */
	public void setStackArray(Stack[] stackArray) {
		this.stackArray = stackArray;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public BoardSituation clone(){
		Stack[] newArray = new Stack[stackArray.length];
		for(int i = 0; i < stackArray.length; i++){
			newArray[i] = stackArray[i].clone();
			newArray[i].setArrPosition(i);
		}
		
		return new BoardSituation(newArray);
	}
	
	public boolean equals(BoardSituation board)
	{
		Stack[] boardStack = board.getStackArray();
		Stack[] currentStack = this.getStackArray();
		if(boardStack.length != currentStack.length)
		{
			return false;
		}
		for(int i=0; i < boardStack.length; i++)
		{
			if(!boardStack[i].equals(currentStack[i]))
			{
				return false;
			}
		}
		return true;
	}

}
