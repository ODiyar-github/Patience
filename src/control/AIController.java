package control;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import model.BoardSituation;
import support.Move;

public abstract class AIController {

	protected PatienceController patienceController;
	
	protected Stack<Move> moveStack;
	
	protected boolean firstCall;
	
	public AIController(PatienceController patienceController){
		this.patienceController = patienceController;
		moveStack = new java.util.Stack<Move>();
		firstCall = true;
	}

	/**
	 *  Die AI macht seinen Tug, bzw. fängt an, ihre funktionen zu starten, die für sie implementiert worden ist. 
	 * @throws InterruptedException 
	 */	
	public abstract boolean aiTurn();
	
	/**
	 * Die Methode tip() gibt einen möglichen Zug wieder, den der Spieler oder die AI machen kann.
	 * @return move von einem Spieler/AI.
	 */
	public abstract Move tip();
	
	/**
	 * Berechnet rekursiv einen Stack von Zügen, die zu einem guten Ergebnis führen
	 * @param depth
	 * 		Tiefe der Rekursion in Zügen. -1 steht für unbegrenzt viele Züge.
	 * @param situation
	 * 		BoardSituation, von der aus die Züge starten sollen
	 * @return
	 * 		Tuple aus einem Stack mit den Zügen, die getan werden sollen und einer Bewertung dieser Züge
	 */
	public Tuple<Double, Stack<Move>> computeMoves(int depth, BoardSituation situation){
		List<Move> possibleMoves = getPossibleMoves(situation);
		Tuple<Double, Stack<Move>> currentTuple = new Tuple<Double, Stack<Move>>(evaluate(situation), new Stack<Move>());
		
		if(depth == 0 || abort(situation) || possibleMoves.isEmpty() || currentTuple.firstElement == -1){
			return new Tuple<Double, Stack<Move>>(evaluate(situation), new Stack<Move>());
		}
		else{
			List<Tuple<Double, Stack<Move>>> evaluatedMoves = new LinkedList<Tuple<Double, Stack<Move>>>();
			BoardSituation situationAfterMove;
			Tuple<Double, Stack<Move>> tuple;
			
			for(Move move : possibleMoves){
				situationAfterMove = simulateMove(situation, move);
				tuple = computeMoves(depth - 1, situationAfterMove);
				tuple.secondElement.push(move);
				evaluatedMoves.add(tuple);
			}
			
			Tuple<Double, Stack<Move>> maxTuple = max(evaluatedMoves);
			
			if(currentTuple.firstElement != maxTuple.firstElement){
				if(currentTuple.firstElement == -1){
					return currentTuple;
				}
				if(maxTuple.firstElement == -1){
					return maxTuple;
				}
				if(maxTuple.firstElement > currentTuple.firstElement){
					return maxTuple;
				}
			}
			return currentTuple;
		}
	}
	
	/**
	 * Gibt zurück, ob bei der übergebenen Situation die Rekursion abgebrochen werden soll
	 */
	public abstract boolean abort(BoardSituation situation);
	
	/**
	 * Bewertet die übergebene BoardSituation. Je höher die Zahl, desto besser. -1 zählt als unendlich.
	 */
	public abstract double evaluate(BoardSituation situation);
	
	/**
	 * Gibt alle Züge zurück, die in der übergebenen Situation getan werden können
	 */
	public abstract List<Move> getPossibleMoves(BoardSituation situation);
	
	/**
	 * Berechnet wie die BoardSituation aussieht, nachdem der Zug getan wird.
	 */
	public BoardSituation simulateMove(BoardSituation boardSituation, Move move){
		return patienceController.getGameController().simulateMove(boardSituation, move);
	}
	
	protected class Tuple<A, B>{
		protected A firstElement;
		protected B secondElement;
		protected Tuple(A firstElement, B secondElement){
			this.firstElement = firstElement;
			this.secondElement = secondElement;
		}
	}
	
	public Tuple<Double, Stack<Move>> max(List<Tuple<Double, Stack<Move>>> list){
		Tuple<Double, Stack<Move>> returnTuple = list.get(0);
		
		for(Tuple<Double, Stack<Move>> compareTuple : list){
			if(returnTuple.firstElement == compareTuple.firstElement && returnTuple.secondElement.size() > compareTuple.secondElement.size()){
				returnTuple = compareTuple;
			}
			if(returnTuple.firstElement < compareTuple.firstElement || compareTuple.firstElement == -1){
				returnTuple = compareTuple;
			}
		}
		return returnTuple;
	}
}
