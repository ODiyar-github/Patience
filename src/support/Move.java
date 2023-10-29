package support;

import model.Stack;

/**
 * @author sopr099
 * Ein Objekt was dazu dient die in einem Spielzug verschobenen Karten zusammenzufassen
 */
public class Move {

	/**
	 * Stapel von dem in dem Move Karten genommen werden
	 */
	private Stack sourceStack;

	/**
	 * Stapel auf den im Move Karten gelegt werden
	 */
	private Stack destinationStack;

	/**
	 * Stapel der Karte/n die bewegt werden
	 */
	private Stack transferStack;


	/**
	 * @param sourceStack : Der Stack von dem Karten genommen werden
	 * @param destinationStack : Der Stack auf den die Karten gelegt werden
	 * @param transferStack : Der Stack der Karten die bewegt werden
	 */
	public Move(Stack sourceStack, Stack destinationStack, Stack transferStack) {
		this.sourceStack = sourceStack;
		this.destinationStack = destinationStack;
		this.transferStack = transferStack;
	}

	/**
	 * @return Returned den Stack von dem Karten genommen werden
	 */
	public Stack getSourceStack() {
		return sourceStack;
	}

	/**
	 * @param Setzt den Stack von dem Karten genommen werden
	 */
	public void setSourceStack(Stack sourceStack) {
		this.sourceStack = sourceStack;
	}

	/**
	 * @return Returned den Stack auf den die Karten gelegt werden 
	 */
	public Stack getDestinationStack() {
		return destinationStack;
	}

	/**
	 * @param Setzt den Stack auf den die Karten gelegt werden
	 */
	public void setDestinationStack(Stack destinationStack) {
		this.destinationStack = destinationStack;
	}

	/**
	 * @return Returned den Stack der Karten die bewegt werden
	 */
	public Stack getTransferStack() {
		return transferStack;
	}

	/**
	 * @param Setzt den Stack der Karten die bewegt werden
	 */
	public void setTransferStack(Stack transferStack) {
		this.transferStack = transferStack;
	}

	@Override
	public String toString(){
		return sourceStack.stringCoordinates() + " -> " + destinationStack.stringCoordinates();
	}
}
