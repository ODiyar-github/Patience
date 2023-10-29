package view.game;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
import javafx.scene.control.Label;
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
import model.Card;
import model.Freecell;
import model.PlayerType;
import model.Stack;
import model.Zank;
import support.Cardtangle;
import support.Move;
import support.StackTangleIdiot;
import support.StacktangleZank;
import view.BoardAUI;
import view.Main;
import view.loginWindow.LoginWindowController;
import view.newGame.NewGameController;
import view.sequences.CardSequences;

public class ZankController implements BoardAUI {

	private PatienceController patienceController;
	// private PlayerType firstPlayerType, secondPlayerType;
	// private Stack customStackPlayer1, customStackPlayer2;
	private Double xCoordinate = null;
	private Double yCoordinate = null;
	private StacktangleZank[] stacktangles;
	private Stage stage = null;

	private String currentMusicName = "";
	private MediaPlayer currentMediaPlayer;
	private boolean musicStarted;


	 	@FXML
	    private AnchorPane root;

	    @FXML
	    private Label playerTurn;

	    @FXML
	    private MenuBar menuBar;

	    @FXML
	    private Menu game;

	    @FXML
	    private MenuItem saveGame;

	    @FXML
	    private MenuItem exitGame;

	    @FXML
	    private MenuItem undo;

	    @FXML
	    private MenuItem redo;

	    @FXML
	    private MenuItem pass;

	    @FXML
	    private MenuItem tipp;

	    @FXML
	    private MenuItem solution;

	    @FXML
	    private Menu music;

	    @FXML
	    private MenuItem startMusic;

	    @FXML
	    private MenuItem stopMusic;


	@FXML
	void solutionHandler(ActionEvent event) {

	}

	@FXML
	void tippHandler(ActionEvent event) {

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
			e.printStackTrace();
		}
	}

	@FXML
	void passHandler(ActionEvent event) {
		((control.ZankController)patienceController.getGameController()).pass();
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
		}
	}

	@FXML
	void initialize() {

	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
		this.patienceController.getGameController().addBoardAUI(this);
	}

	@Override
	public void refreshBoard(Move move) {
		// if(move != null)
		// animateMove(move);
		//		
		root.getChildren().removeAll(root.getChildren());
		root.getChildren().add(menuBar);
		root.getChildren().add(playerTurn);
		
		if(this.patienceController.getPatience().getCurrentGame().isFirstPlayerTurn()) {
			playerTurn.setText("Firstplayer");
		}
		else {
			playerTurn.setText("Secondplayer");
		}
		setLayout();

	}

	@Override
	public void refreshGameFinished() {
		boolean firstPlayerTurn = patienceController.getPatience().getCurrentGame().isFirstPlayerTurn();
		if(firstPlayerTurn){
			JOptionPane.showMessageDialog(new Frame(), "Glueckwunsch! Spieler1 hat gewonnen: " + "Zuege: " + patienceController.getPatience().getCurrentGame().getCountMoves(), "Gewonnen", JOptionPane.PLAIN_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(new Frame(), "Glueckwunsch! Spieler2 hat gewonnen: " + "Zuege: " + patienceController.getPatience().getCurrentGame().getCountMoves(), "Gewonnen", JOptionPane.PLAIN_MESSAGE);
			this.root.setDisable(false);
		}
	}

	private void animateMove(Move move) {
		// Stacktangle stacktangleToMove = null;
		//
		// double cardToMoveXCoordinate = 0;
		// double cardToMoveYCoordinate = 0;
		//
		// Timeline loop = new Timeline(new KeyFrame(Duration.millis(10),
		// new EventHandler<ActionEvent>()
		// {
		// @Override
		// public void handle(final ActionEvent t)
		// {
		//
		// }
		// }));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		Scene scene = stage.getScene();
		root = (AnchorPane) scene.getRoot();
		// mainStage.getStylesheets().add(getClass().getResource("background.css").toExternalForm());

		stage.setX(150);
		stage.setY(100);
		stage.setMaxWidth(1250);
		stage.setMinWidth(1250);
		stage.setMaxHeight(1100);
		stage.setMinHeight(1100);
		stage.setResizable(false);

		undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
		redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
		saveGame.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		exitGame.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		solution.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		tipp.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
		pass.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
	}

	public void setLayout() {
		Stack[] stacks = this.patienceController.getPatience().getCurrentGame().getCurrentBoardsituation()
				.getStackArray();
		StacktangleZank rectangleStack = null;
		stacktangles = new StacktangleZank[stacks.length];

		for (Stack stack : stacks) {
			rectangleStack = new StacktangleZank(stack);
			rectangleStack.setFill(Color.TRANSPARENT);
			rectangleStack.setStroke(Color.BLACK);

			Label label = new Label();

			if (stack.getArrPosition() >= Zank.FIRST_CLUB_STACK && stack.getArrPosition() <= Zank.SECOND_HEART_STACK) {
				label.setLayoutX(rectangleStack.getX());
				label.setLayoutY(rectangleStack.getY());
				label.setText(stack.getName());
				label.setTextFill(Color.WHITE);
			}

			stacktangles[stack.getArrPosition()] = rectangleStack;
			root.getChildren().addAll(rectangleStack, label);

			int xCoordinate = (int) rectangleStack.getOtherX();
			for (Card card : stack.getCardList()) {
				createRectangleForCard(rectangleStack, card, xCoordinate);

				if (stack.getArrPosition() >= Zank.FIRST_OUTER_STACK
						&& stack.getArrPosition() <= Zank.FOURTH_OUTER_STACK)
					xCoordinate = xCoordinate - 30;

				if (stack.getArrPosition() >= Zank.FIFTH_OUTER_STACK
						&& stack.getArrPosition() <= Zank.EIGHTH_OUTER_STACK)
					xCoordinate = xCoordinate + 30;
			}
		}
	}

	private void showCardBack(Cardtangle rectangelCard) {
		rectangelCard.setFill(new ImagePattern(new Image("file:images/cardBackImages/"
				+ patienceController.getSettingsController().getCurrentCardBack() + ".png")));
	}

	private void showCard(Cardtangle rectangleCard, Card card) {
		rectangleCard.setFill(new ImagePattern(new Image(
				"file:images/cardImages/" + card.getSuit().toString() + "_" + card.getRank().toString() + ".png")));

	}

	private void createRectangleForCard(StacktangleZank rectangleStack, Card card, int xCoordinateCard) {
		Cardtangle rectangleCard = null;
		rectangleCard = new Cardtangle(card);
		rectangleCard.setX(xCoordinateCard);
		rectangleCard.setY(rectangleStack.getY());
		rectangleCard.setWidth(100);
		rectangleCard.setHeight(150);
		// rectangleCard.setFill(new ImagePattern(new
		// Image("file:images/cardImages/" + card.getSuit().toString() + "_" +
		// card.getRank().toString() + ".png")));

		if (card.isVisible()) {
			showCard(rectangleCard, card);
		} else {
			showCardBack(rectangleCard); // hier Pixel der PNG auf 79x123 setzen
		}

		rectangleCard.setOnMousePressed((t) -> {
			Cardtangle rectangleClickedCard = (Cardtangle) (t.getSource());
			
			if(patienceController.getGameController().isMovable(rectangleClickedCard.getCard())){
				showCard(rectangleClickedCard, rectangleClickedCard.getCard());
			}

			if (!this.patienceController.getGameController().isMovable(card)) {
				rectangleClickedCard.setDisable(true);
				return;
			}

			xCoordinate = t.getSceneX();
			yCoordinate = t.getSceneY();

			rectangleClickedCard.toFront();
		});

		rectangleCard.setOnMouseDragged((t) -> {
			Cardtangle rectangleClickedCard = (Cardtangle) (t.getSource());

			if (!this.patienceController.getGameController().isMovable(card)) {
				rectangleClickedCard.setDisable(true);
				return;
			}

			double offsetX = t.getSceneX() - xCoordinate;
			double offsetY = t.getSceneY() - yCoordinate;

			rectangleClickedCard.setX(rectangleClickedCard.getX() + offsetX);
			rectangleClickedCard.setY(rectangleClickedCard.getY() + offsetY);

			xCoordinate = t.getSceneX();
			yCoordinate = t.getSceneY();

			// animateTransferstackOnDragged(rectangleClickedCard, t);

			for (StacktangleZank stacktangle : stacktangles) {
				if (rectangleClickedCard.intersects(stacktangle.getStackXCoordinate(),
						stacktangle.getStackYCoordinate(), stacktangle.getStackWidth(), stacktangle.getStackHeight())) {
					//
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
				patienceController.getGameController().move(transferCard, transferCard.getCurrentStack());
			else
				this.patienceController.getGameController().move(transferCard, destinationStack);
		});

		root.getChildren().add(rectangleCard);
	}

	private Stack getDestinationStack(Rectangle rectangleReleasedCard, MouseEvent mouseEvent) {
		for (StacktangleZank stacktangle : stacktangles) {
			if (rectangleReleasedCard.intersects(stacktangle.getStackXCoordinate(), stacktangle.getStackYCoordinate(),
					stacktangle.getStackWidth(), stacktangle.getStackHeight())) {
				return stacktangle.getStack();
			}
		}

		return null;
	}

}