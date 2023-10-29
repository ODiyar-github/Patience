package control;
import model.Statistic;

/**
 * @author sopr098
 *	StatistikController dient zum bearbeiten der Statistiken vom jeweiligen Spieltyp
 */
public class StatisticController {

	private PatienceController patienceController;

	/**
	 * @param patienceController Übergebene PatienceController zur Verwaltung
	 * Konstruktor, der einen StatisticController erstellt
	 */
	public StatisticController(PatienceController patienceController) {
		this.patienceController=patienceController;
	}

	/**
	 * @param gameSuccessful Boolean, der angibt ob das Spiel erfolgreich beendet wurde
	 * @param moves Anzahl der Spielzüge im Spiel
	 * @param cardsLeft Anzahl der übriggebliebenen Karten (bei erfolgreich beendetem Spiel 0)
	 * Methode zum modifizieren der Freecell-Statistik
	 */
	public void modifyFreeCellStatistic(boolean gameSuccessful, int moves, int cardsLeft) {
		Statistic statistic = patienceController.getPatience().getFreecellStatistic();
		modifyStatistic(statistic, gameSuccessful, moves, cardsLeft);
	}
	
	public void modifyStatistic(Statistic statistic, boolean gameSuccessful, int moves, int cardsLeft){
		statistic.setAverageMoves((statistic.getAverageMoves() * statistic.getGamesPlayed() + moves) / (statistic.getGamesPlayed() + 1));
		statistic.setAverageCardsLeft((statistic.getAverageCardsLeft() * statistic.getGamesPlayed() + cardsLeft) / (statistic.getGamesPlayed() + 1));
		statistic.incrementGamesPlayed();
		
		if(gameSuccessful){
			statistic.incrementGamesSuccessful();
		}
		
		if(moves < statistic.getMinMoves()){
			statistic.setMinMoves(moves);
		}
		
		if(moves > statistic.getMaxMoves()){
			statistic.setMaxMoves(moves);
		}
	}

	/**
	 * @param gameSuccessful Boolean, der angibt ob das Spiel erfolgreich beendet wurde
	 * @param moves Anzahl der Spielzüge im Spiel
	 * @param cardsLeft Anzahl der übriggebliebenen Karten (bei erfolgreich beendetem Spiel 0)
	 * Methode zum modifizieren der Idiot-Statistik
	 */
	public void modifyIdiotStatistic(boolean gameSuccessful, int moves, int cardsLeft) {
		Statistic statistic = patienceController.getPatience().getIdiotStatistic();
		modifyStatistic(statistic, gameSuccessful, moves, cardsLeft);
	}

	/**
	 * @param gameSuccessful Boolean, der angibt ob das Spiel erfolgreich beendet wurde
	 * @param moves Anzahl der Spielzüge im Spiel
	 * @param cardsLeft Anzahl der übriggebliebenen Karten (bei erfolgreich beendetem Spiel 0)
	 * Methode zum modifizieren der Zank-Statistik
	 */
	public void modifyZankStatistic(boolean gameSuccessful, int moves, int cardsLeft) {
		Statistic statistic = patienceController.getPatience().getZankStatistic();
		modifyStatistic(statistic, gameSuccessful, moves, cardsLeft);
	}

	/**
	 * @return Gibt die FreeCell-Statistik zurück
	 */
	public Statistic getFreeCellStatistic() {
		return this.patienceController.getPatience().getFreecellStatistic();
	}

	/**
	 * @return Gibt die Idiot-Statistik zurück
	 */
	public Statistic getIdiotStatistic() {
		return this.patienceController.getPatience().getIdiotStatistic();
	}

	/**
	 * @return Gibt die Zank-Statistik zurück
	 */
	public Statistic getZankStatistic() {
		return this.patienceController.getPatience().getZankStatistic();
	}

	/**
	 * @return Gibt den PatienceController zurück
	 */
	public PatienceController getPatienceController() {
		return patienceController;
	}

	/**
	 * @param patienceController Der zu setzende PatienceController
	 * Setzt den PatienceController auf den übergebenen Parameter
	 */
	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
	}

}
