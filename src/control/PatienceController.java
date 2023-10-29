package control;
import java.time.LocalDateTime;

import exceptions.SaveException;
import model.Freecell;
import model.Game;
import model.Idiot;
import model.Patience;
import model.PlayerType;
import model.Zank;

public class PatienceController {

	private Patience patience;

	private GameController gameController;

	private IOController iOController;

	private AIController firstPlayer;

	private AIController secondPlayer;
	
	private AIController tipController;

	private SettingsController settingsController;

	private BoardController boardController;

	private StatisticController statisticController;
	/**
	 *  Alle Verf�gbaren Controller werden Initialiesiert.
	 */
	public  PatienceController() {
		patience=new Patience();
		iOController=new IOController(this);
		settingsController=new SettingsController(this);
		boardController=new BoardController(this);
		statisticController=new StatisticController(this);
	}

	
	/**
	 * lädt ein Spiel vom Typ IdiotPatience und setzt es als das aktuelle Spiel
	 * @param idiot
	 */
	
	public void loadGame(Idiot idiot) {
		if(idiot==null) {
			throw new NullPointerException("Es wurde kein Spiel übergeben.");
		}
		
		patience.setCurrentGame(idiot);
		
		patience.getGameList().add(idiot);
		
		gameController = new IdiotController(this, idiot);
		
		tipController = new IdiotAIController(this);
		
		if(idiot.getFirstPlayer() == PlayerType.IDIOT){
			firstPlayer = new IdiotAIController(this);
		}
	}

	
	/**
	 * lädt ein Spiel vom Typ Freecell und setzt es als das aktuelle Spiel
	 * @param freecell
	 */
	
	public void loadGame(Freecell freecell) {
		if(freecell==null) {
			throw new NullPointerException("Es wurde kein Spiel übergeben.");
		}
		
		patience.setCurrentGame(freecell);
		
		patience.getGameList().add(freecell);

		gameController = new FreecellController(this, freecell);

		tipController = new FreecellAIController(this);

		if(freecell.getFirstPlayer() == PlayerType.FREECELL){
			firstPlayer = new FreecellAIController(this);
		}
		else{
			firstPlayer = null;
		}

		secondPlayer = null;
	}
	
	
    /**
     * lädt ein Spiel vom Typ Zank und setzt es als das aktuelle Spiel
     * @param zank
     */
	
	public void loadGame(Zank zank) {
		if(zank==null) {
			throw new NullPointerException("Es wurde kein Spiel übergeben.");
		}
		
		patience.setCurrentGame(zank);
		
		patience.getGameList().add(zank);
		
		gameController = new ZankController(this, zank);
		
		tipController = new ZankAIHardController(this);
		
		if(zank.getFirstPlayer() == PlayerType.ZANK_EASY){
			firstPlayer = new ZankAIEasyController(this);
		}
		else if(zank.getFirstPlayer() == PlayerType.ZANK_HARD){
			firstPlayer = new ZankAIHardController(this);
		}
		else{
			firstPlayer = null;
		}
		
		if(zank.getSecondPlayer() == PlayerType.ZANK_EASY){
			secondPlayer = new ZankAIEasyController(this);
		}
		else if(zank.getSecondPlayer() == PlayerType.ZANK_HARD){
			secondPlayer = new ZankAIHardController(this);
		}
		else{
			secondPlayer = null;
		}
		
		((ZankController)gameController).automaticalMovesAtStart();
		((ZankController)gameController).initializeTurn();
	}
	/**
	 * Beendet das Spiel und verändert die Statistik.
	 * @param won
	 * 		War das Spiel erfolgreich für den Spieler, der gerade an der Reihe ist?
	 * @return
	 * 		true, wenn das beenden erfolgreich war. false sonst.
	 */
	public boolean finishGame(boolean won){
		Game currentGame = patience.getCurrentGame();
		
		boolean currentPlayerHuman = getCurrentPlayer() == null;
		
		boolean otherPlayerHuman;
		
		if(currentGame.isFirstPlayerTurn()){
			otherPlayerHuman = firstPlayer == null;
		}
		else {
			otherPlayerHuman = secondPlayer == null;
		}
		
		//Das Spiel zählt nur als succesfull, wenn ein Mensch gewonnen hat.
		if(currentPlayerHuman || otherPlayerHuman){
			boolean successful = isGameSuccessful(won);
			if(currentGame.getClass() == Zank.class){
				statisticController.modifyZankStatistic(successful, currentGame.getCountMoves(), gameController.cardsLeft());
			}
			else if(currentGame.getClass() == Idiot.class){
				statisticController.modifyIdiotStatistic(successful, currentGame.getCountMoves(), gameController.cardsLeft());
			}
			else if(currentGame.getClass() == Freecell.class){
				statisticController.modifyFreeCellStatistic(successful, currentGame.getCountMoves(), gameController.cardsLeft());
			}
		}
		
		patience.getGameList().remove(getPatience().getCurrentGame());
		patience.setCurrentGame(null);
		
		try {
			iOController.save();
		} catch (SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean isGameSuccessful(boolean won){
		
		Game currentGame = patience.getCurrentGame();
		
		boolean currentPlayerHuman = getCurrentPlayer() == null;
		
		boolean otherPlayerHuman;
		
		if(currentGame.getClass() == Zank.class){
			if(won){
				return currentPlayerHuman;
			}
			else if(currentPlayerHuman){
				return ((ZankController)gameController).currentPlayerBetter();
			}
			else{
				return !((ZankController)gameController).currentPlayerBetter();
			}
		}
		else{
			return won;
		}
	}
	
	/**
	 * Speichert das aktuelle Game und beendet es.
	 * @return
	 * 		true, wenn das speichern erfolgreich war, false sonst.
	 */
	public boolean saveGame(){
		patience.getCurrentGame().setDate(LocalDateTime.now());
		
		try {
			iOController.save();
		} catch (SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	/**
	 * gibt den aktuellen Spieler zurück
	 * @return AIController
	 */
	
	public AIController getCurrentPlayer() {
		if(patience.getCurrentGame().isFirstPlayerTurn()){
			return firstPlayer;
		}
		else{
			return secondPlayer;
		}
	}


	public Patience getPatience() {
		return patience;
	}


	public void setPatience(Patience patience) {
		this.patience = patience;
	}


	public GameController getGameController() {
		return gameController;
	}


	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}


	public IOController getiOController() {
		return iOController;
	}


	public void setiOController(IOController iOController) {
		this.iOController = iOController;
	}


	public AIController getFirstPlayer() {
		return firstPlayer;
	}


	public void setFirstPlayer(AIController firstPlayer) {
		this.firstPlayer = firstPlayer;
	}


	public AIController getSecondPlayer() {
		return secondPlayer;
	}


	public void setSecondPlayer(AIController secondPlayer) {
		this.secondPlayer = secondPlayer;
	}


	public SettingsController getSettingsController() {
		return settingsController;
	}


	public void setSettingsController(SettingsController settingsController) {
		this.settingsController = settingsController;
	}


	public BoardController getBoardController() {
		return boardController;
	}


	public void setBoardController(BoardController boardController) {
		this.boardController = boardController;
	}


	public StatisticController getStatisticController() {
		return statisticController;
	}


	public void setStatisticController(StatisticController statisticController) {
		this.statisticController = statisticController;
	}
	
	public AIController getTipController(){
		return tipController;
	}
	
	public boolean currentPlayerIsAI(){
		return getCurrentPlayer() != null;
	}
}
