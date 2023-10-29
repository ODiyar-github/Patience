package view.game;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import control.BoardController;
import control.PatienceController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BoardSituation;
import model.Card;
import model.Freecell;
import model.Game;
import model.Idiot;
import model.PlayerType;
import model.Rank;
import model.Stack;
import model.Suit;
import support.Cardtangle;
import support.Move;
import support.StackTangleIdiot;
import support.Stacktangle;
import support.ThreadAnimation;
import view.BoardAUI;
import view.Main;
import view.loginWindow.LoginWindowController;
import view.newGame.NewGameController;
import view.sequences.CardSequences;

public class FreecellController implements BoardAUI {

	private PatienceController patienceController;

	private String currentMusicName = "";
	private MediaPlayer currentMediaPlayer;
	private boolean musicStarted;

	Double xCoordinate = null;
	Double yCoordinate = null;
	Stacktangle[] stacktangles;
	Cardtangle[] cardtangles;
	Stage stage = null;

	@FXML
	private AnchorPane root;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu menu;

	@FXML
	private MenuItem saveGame;

	@FXML
	private MenuItem exitGame;

	@FXML
	private Menu music;

	@FXML
	private Menu options;

	@FXML
	private MenuItem startMusic;

	@FXML
	private MenuItem stopMusic;

	@FXML
	private MenuItem undo;

	@FXML
	private MenuItem redo;

	@FXML
	private MenuItem tipp;

	@FXML
	private MenuItem solution;
	
	private static final int ANIMATION_TIME = 1;
	private static final int CYCLE_COUNT = 500;

	public void startAI()
	{
		if(patienceController.currentPlayerIsAI())
		{
			this.root.setDisable(true);
			if(!patienceController.getCurrentPlayer().aiTurn()){
				JOptionPane.showMessageDialog(new Frame(), "Weiter komme ich leider nicht :(", "Abbruch" , JOptionPane.PLAIN_MESSAGE);
				this.root.setDisable(false);
			}
		} else {
			this.root.setDisable(false);
		}
	}
	
	@FXML
	void solutionHandler(ActionEvent event) {
		patienceController.setFirstPlayer(patienceController.getTipController());
		startAI();
	}

	@FXML
	void tippHandler(ActionEvent event) {
		Move tip = patienceController.getTipController().tip();
		if(tip == null){
			JOptionPane.showMessageDialog(new Frame(), "Ich habe leider keinen Tip f�r Sie.", "Tip", JOptionPane.PLAIN_MESSAGE);
		}
		else{
			Stack destinationStack = tip.getDestinationStack();
			Card transferCard = tip.getTransferStack().getTopCard();
			
			for(Stacktangle stackTangle : stacktangles){
				if(stackTangle.getStack() == destinationStack){
					stackTangle.setEffect(new Lighting());
					stackTangle.setStroke(Color.YELLOW);
					stackTangle.setStrokeWidth(10.00);
				}
			}
			for(Cardtangle cardtangle : cardtangles){
				if(cardtangle.getCard().equals(transferCard)){
					cardtangle.setEffect(new Lighting());
					cardtangle.setStroke(Color.YELLOW);
					cardtangle.setStrokeWidth(10.00);
				}
			}
		}
	}


	@FXML
	void exitGameHandler(ActionEvent event) {

		patienceController.finishGame(patienceController.getGameController().isGameWon());
		if (musicStarted) {
			currentMediaPlayer.stop();
		}
		stage.close();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("loginWindow/loginWindow.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();

			LoginWindowController controller = (LoginWindowController) loader.getController();
			controller.setPatienceController(patienceController);

			Scene scene = new Scene(loginLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.setTitle("Hauptfenster");
			dialogStage.setResizable(false);
			dialogStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	void redoHandler(ActionEvent event) {
		System.out.println("redo");
		patienceController.getGameController().redo();
	}

	@FXML
	void undoHandler(ActionEvent event) {
		System.out.println("undo");
		patienceController.getGameController().undo();
	}

	@FXML
	void saveGameHandler(ActionEvent event) {
		boolean saved = patienceController.saveGame();
		if (saved) {
			JOptionPane.showMessageDialog(new Frame(), "Speichern erfolgreich", "Speichern",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(new Frame(), "Speichern fehlgeschlagen", "Speichern",
					JOptionPane.WARNING_MESSAGE);
		}
		stage.close();
		try {
			if (musicStarted) {
				currentMediaPlayer.stop();
			}
			WritableImage image = this.root.snapshot(new SnapshotParameters(), null);
			// TODO: probably use a file chooser here
			File file = new File("images/gameImages/"
					+ this.patienceController.getPatience().getCurrentGame().getGameImagePath() + ".png");
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (IOException e) {
				// TODO: handle exception here
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("loginWindow/loginWindow.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();

			LoginWindowController controller = (LoginWindowController) loader.getController();
			controller.setPatienceController(patienceController);

			Scene scene = new Scene(loginLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.setTitle("Hauptfenster");
			dialogStage.setResizable(false);
			dialogStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	void startMusicHandler(ActionEvent event) {
		currentMusicName = patienceController.getPatience().getCurrentMusic().toLowerCase();
		System.out.println(currentMusicName);
		if (currentMusicName.equals(null)) {
			JOptionPane.showMessageDialog(new Frame(), "Musik in Optionen auswählen", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		} else if (currentMusicName.equals(null)) {
			JOptionPane.showMessageDialog(new Frame(), "Musik in Optionen auswählen", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (musicStarted) {
				musicStarted = false;
				currentMediaPlayer.stop();
			}
			Media media = new Media(Paths.get("audioFiles/" + currentMusicName + ".mp3").toUri().toString());
			currentMediaPlayer = new MediaPlayer(media);
			currentMediaPlayer.play();
			musicStarted = true;
		}
	}

	@FXML
	void stopMusicHandler(ActionEvent event) {
		if (musicStarted) {
			currentMediaPlayer.stop();
			musicStarted = false;
		}
	}

	@FXML
	void stageOnClick(MouseEvent event) {

	}

	void initialize() {

	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
		this.patienceController.getGameController().addBoardAUI(this);
	}

	public void setPatienceController(PatienceController patienceController, Game game) {

		this.patienceController = patienceController;

		Freecell freecell = (Freecell) game;
		this.patienceController.loadGame(freecell);
		this.patienceController.getGameController().addBoardAUI(this);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		Scene scene = stage.getScene();
		root = (AnchorPane) scene.getRoot();
		// root.getStylesheets().add(getClass().getResource("background.css").toExternalForm());

		stage.setX(300);
		stage.setY(50);
		stage.setMaxWidth(1250);
		stage.setMinWidth(1250);
		stage.setMaxHeight(1000);
		stage.setMinHeight(1000);
		stage.setResizable(true);

		undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
		redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
		saveGame.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		exitGame.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		solution.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		tipp.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
	}

	public void setLayout() {
		Stack[] stacks = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation()
				.getStackArray();
		Stacktangle rectangleStack = null;
		cardtangles = new Cardtangle[52];
		stacktangles = new Stacktangle[stacks.length];

		int cardtanglePos = 0;
		for (Stack stack : stacks) {
			rectangleStack = new Stacktangle(stack);
			rectangleStack.setFill(Color.TRANSPARENT);
			rectangleStack.setStroke(Color.BLACK);

			stacktangles[stack.getArrPosition()] = rectangleStack;
			root.getChildren().add(rectangleStack);

			int yCoordinate = (int) rectangleStack.getY();

			for (Card card : stack.getCardList()) {
				cardtangles[cardtanglePos] = createRectangleForCard(rectangleStack, card, yCoordinate);

				rectangleStack.getCardtangles().add(cardtangles[cardtanglePos]);
				cardtangles[cardtanglePos].setStacktangle(rectangleStack);

				if (stack.getArrPosition() >= Freecell.FIRST_STACK && stack.getArrPosition() <= Freecell.EIGTH_STACK)
					yCoordinate = yCoordinate + 30;

				cardtanglePos++;
			}
		}
	}

	private Cardtangle createRectangleForCard(Stacktangle rectangleStack, Card card, int yCoordinateCard) {
		Cardtangle rectangleCard = null;
		rectangleCard = new Cardtangle(card);
		rectangleCard.setX(rectangleStack.getX());
		rectangleCard.setY(yCoordinateCard);
		rectangleCard.setWidth(100);
		rectangleCard.setHeight(150);
		rectangleCard.setFill(new ImagePattern(new Image(
				"file:images/cardImages/" + card.getSuit().toString() + "_" + card.getRank().toString() + ".png")));

		rectangleCard.setOnMousePressed((t) -> {
			Cardtangle rectangleClickedCard = (Cardtangle) (t.getSource());

			if (!this.patienceController.getGameController().isMovable(card)) {
				rectangleClickedCard.setDisable(true);
				return;
			}

			xCoordinate = t.getSceneX();
			yCoordinate = t.getSceneY();

			// rectangleClickedCard.toFront();
		});

		rectangleCard.setOnMouseDragged((t) -> {
			Cardtangle rectangleClickedCard = (Cardtangle) (t.getSource());

			if (!this.patienceController.getGameController().isMovable(card)) {
				rectangleClickedCard.setDisable(true);
				return;
			}

			animateTransferstackOnDragged(rectangleClickedCard, t);

			for (Stacktangle stacktangle : stacktangles) {
				if (rectangleClickedCard.intersects(stacktangle.getStackXCoordinate(),
						stacktangle.getStackYCoordinate(), stacktangle.getStackWidth(), stacktangle.getStackHeight())) {
					//System.out.println(stacktangle);

					stacktangle.setEffect(new Lighting());
					stacktangle.setStroke(Color.WHITE);
					stacktangle.setStrokeWidth(5.00);
				} else {
					stacktangle.setStroke(Color.BLACK);
					stacktangle.setStrokeWidth(1.0);
				}
			}
		});

		rectangleCard.setOnMouseReleased((t) -> {
			Cardtangle rectangleReleasedCard = (Cardtangle) (t.getSource());
			rectangleReleasedCard.setDisable(false);

			Card transferCard = rectangleReleasedCard.getCard();
			Stack destinationStack = getDestinationStack(rectangleReleasedCard, t);

			if (destinationStack == null)
				refreshBoard(null);
			else
				this.patienceController.getGameController().move(transferCard, destinationStack);
		});

		root.getChildren().add(rectangleCard);

		return rectangleCard;
	}

	private void animateTransferstackOnDragged(Cardtangle rectangleClickedCard, MouseEvent t) {
		double offsetX = t.getSceneX() - xCoordinate;
		double offsetY = t.getSceneY() - yCoordinate;

		Stacktangle stackStangleOfClickedCard = rectangleClickedCard.getStacktangle();

		Card clickedCard = rectangleClickedCard.getCard();
		Stack stackFromClickedCard = clickedCard.getCurrentStack();
		int posFromCardInStack = stackFromClickedCard.getCardList().indexOf(clickedCard);

		Card currentCard = null;
		for (int cardPos = posFromCardInStack; cardPos < stackFromClickedCard.getCardList().size(); cardPos++) {
			currentCard = stackFromClickedCard.getCardList().get(cardPos);

			for (Cardtangle cardtangle : stackStangleOfClickedCard.getCardtangles()) {
				if (cardtangle.getCard() == currentCard) {
					cardtangle.setX(cardtangle.getX() + offsetX);
					cardtangle.setY(cardtangle.getY() + offsetY);

					cardtangle.toFront();
				}
			}
		}

		// Card clickedCard = rectangleClickedCard.getCard();
		// Stack stackFromClickedCard = clickedCard.getCurrentStack();
		// int posFromCardInStack =
		// stackFromClickedCard.getCardList().indexOf(clickedCard);
		//
		// Card currentCard = null;
		// for(int cardPos = posFromCardInStack; cardPos <
		// stackFromClickedCard.getCardList().size(); cardPos++)
		// {
		// currentCard = stackFromClickedCard.getCardList().get(cardPos);
		//
		// for(Cardtangle cardtangle : cardtangles)
		// {
		// if(cardtangle.getCard() == currentCard)
		// {
		// cardtangle.setX(cardtangle.getX() + offsetX);
		// cardtangle.setY(cardtangle.getY() + offsetY);
		//
		// cardtangle.toFront();
		// }
		// }
		// }

		xCoordinate = t.getSceneX();
		yCoordinate = t.getSceneY();
	}

	private Stack getDestinationStack(Rectangle rectangleReleasedCard, MouseEvent mouseEvent) {
		double releasedXCoordinate = mouseEvent.getSceneX();
		double releasedYCoordinate = mouseEvent.getSceneY();

		for (Stacktangle stacktangle : stacktangles) {
			// Bounds bounds = new Bounds(2.00, 2.00, 2.00, 2.00, 2.00, 2.00);

			if (rectangleReleasedCard.intersects(stacktangle.getStackXCoordinate(), stacktangle.getStackYCoordinate(),
					stacktangle.getStackWidth(), stacktangle.getStackHeight())) {
				return stacktangle.getStack();
			}
		}

		return null;
	}

	@Override
	public void refreshBoard(Move move) {
		if (move != null)
			animateMove(move);
		else{
			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_TIME),
					new EventHandler<ActionEvent>(){			
	
				@Override
				public void handle(ActionEvent t) {
					root.getChildren().removeAll(root.getChildren());
					root.getChildren().add(menuBar);
					setLayout();
					startAI();
				}}));
			timeline.play();
		}
	}

	@Override
	public void refreshGameFinished() {
		JOptionPane.showMessageDialog(new Frame(), "Glueckwunsch! Sie haben gewonnen: " + "Zuege: " + patienceController.getPatience().getCurrentGame().getCountMoves(), "Gewonnen", JOptionPane.PLAIN_MESSAGE);

		this.root.setDisable(false);
		
//		stage.close();
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Main.class.getResource("loginWindow/loginWindow.fxml"));
//			AnchorPane loginLayout = (AnchorPane) loader.load();
//
//			LoginWindowController controller = (LoginWindowController) loader.getController();
//			controller.setPatienceController(patienceController);
//
//			Scene scene = new Scene(loginLayout);
//			Stage dialogStage = new Stage();
//			dialogStage.setScene(scene);
//			dialogStage.setTitle("Hauptfenster");
//			dialogStage.setResizable(false);
//			dialogStage.show();
//		} catch (Exception e) {
//
//		}
	}
	
	private class animationHandler implements EventHandler<ActionEvent>{
		List<Cardtangle> cardtangleTransferList;
		double changeX;
		double changeY;
		int cycles;
		public animationHandler(Move move){
			super();
			List<Card> transferList = move.getTransferStack().getCardList();
			Card transferCard = move.getTransferStack().getCardList().get(0);
			Stack destinationStack = move.getDestinationStack();
			Stacktangle destinationTangle = null;
			Cardtangle transferTangle = null;
			cardtangleTransferList = new LinkedList<Cardtangle>();
			cycles = 0;
			
			for(Card card : transferList){
				for(Cardtangle cardtangle : cardtangles){
					if(cardtangle.getCard().equals(card)){
						cardtangleTransferList.add(cardtangle);
					}
					if(cardtangle.getCard().equals(transferCard)){
						transferTangle = cardtangle;
					}
				}
			}
			for (Stacktangle stacktangle : stacktangles) {
				if (stacktangle.getStack().getArrPosition() == destinationStack.getArrPosition()) {
					destinationTangle = stacktangle;
				}
			}
			
			double sourceX = transferTangle.getX();
			double sourceY = transferTangle.getY();
			double destinationX = destinationTangle.getX();
			double destinationY = destinationTangle.getY();
			if(destinationStack.getArrPosition() <= Freecell.EIGTH_STACK){
				destinationY += + destinationStack.getCardList().size() * 30;
			}
			
			changeX = (destinationX - sourceX) / ((double)CYCLE_COUNT - 1);
			changeY = (destinationY - sourceY) / ((double)CYCLE_COUNT - 1);
		}

		@Override
		public void handle(ActionEvent arg0) {
			for(Cardtangle cardtangle : cardtangleTransferList){
				root.getChildren().remove(cardtangle);
				cardtangle.setX(cardtangle.getX() + changeX);
				cardtangle.setY(cardtangle.getY() + changeY);
				root.getChildren().add(cardtangle);
			}
			cycles++;
			if(cycles == CYCLE_COUNT){
				root.getChildren().removeAll(root.getChildren());
				root.getChildren().add(menuBar);
				setLayout();
				startAI();
			}
		}
	}

	private void animateMove(Move move) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_TIME), new animationHandler(move)));

		timeline.setCycleCount(CYCLE_COUNT);
		timeline.play();
	}
}
