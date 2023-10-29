package control;
import java.util.List;

import model.BoardSituation;
import support.Move;

public class ZankAIController extends AIController {
	

	public ZankAIController(PatienceController patienceController){
		super(patienceController);
	}
	
	/**
	 * @see AIController#aiTurn()
	 * 
	 *  
	 */
	public boolean aiTurn() {
		return false;
	}


	/**
	 * @see AIController#tip()
	 */
	public Move tip() {
		return null;
	}

	@Override
	public boolean abort(BoardSituation situation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double evaluate(BoardSituation situation) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Move> getPossibleMoves(BoardSituation situation) {
		// TODO Auto-generated method stub
		return null;
	}

}
