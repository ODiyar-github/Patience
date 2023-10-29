package view.game;

import java.awt.Frame;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import control.PatienceController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
import model.BoardSituation;
import model.Card;
import model.Freecell;
import model.Game;
import model.Idiot;
import model.PlayerType;
import model.Stack;
import support.Cardtangle;
import support.Move;
import support.StackTangleIdiot;
import support.Stacktangle;
import support.ThreadAnimation;
import view.BoardAUI;
import view.Main;
import view.loginWindow.LoginWindowController;

public class IdiotController implements BoardAUI {

	private Boolean lock = false;

	private Stage stage = null;
	
	private static final int ANIMATION_TIME = 1;
	private static final int CYCLE_COUNT = 500;
	private static final int WAIT_TIME = 150;

	private double mainSceneX, mainSceneY;
	private int counter;
	private int cardsInStack;
	private double currentCardX = 0;
	private double currentCardY = 0;
	private Double xCoordinate = null;
	private Double yCoordinate = null;
	private StackTangleIdiot[] stackTangles;
	private boolean cardSet;

	private String currentMusicName = "";
	private MediaPlayer currentMediaPlayer;
	private boolean musicStarted;

	private MediaPlayer currentMusic;

	final double width = 150, height = 150;

	private Rectangle[] recTangleArray;
	private ArrayList<Cardtangle> cardtangles;

	private PatienceController patienceController;

	@FXML
	private AnchorPane root;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu game;

	@FXML
	private MenuItem save;

	@FXML
	private MenuItem exit;

	@FXML
	private Menu options;

	@FXML
	private MenuItem undo;

	@FXML
	private MenuItem redo;

	@FXML
	private Menu music;

	@FXML
	private MenuItem start;

	@FXML
	private MenuItem stop;

	@FXML
	private MenuItem solution;

	@FXML
	private MenuItem tipp;

	@FXML
	void solutionHandler(ActionEvent event) {
		patienceController.setFirstPlayer(patienceController.getTipController());

		patienceController.getFirstPlayer().aiTurn();
	}

	@FXML
	void tippHandler(ActionEvent event) {
		Move tip = patienceController.getTipController().tip();
		if(tip == null){
			JOptionPane.showMessageDialog(new Frame(), "Ich habe leider keinen Tip für Sie.", "Tip", JOptionPane.PLAIN_MESSAGE);
		}
		else{
			Stack destinationStack = tip.getDestinationStack();
			Card transferCard = tip.getTransferStack().getTopCard();
			
			for(StackTangleIdiot stackTangle : stackTangles){
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
	void stageClicked(MouseEvent event) {
		// System.out.println(event.getX() + "," + event.getY());
		// System.out.println(patienceController.getSettingsController().getCurrentCardBack());

	}

	@FXML
	void exitHandler(ActionEvent event) {
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
	void redoHandler(ActionEvent event) {
		// System.out.println("redo");
		patienceController.getGameController().redo();
	}

	@FXML
	void saveHandler(ActionEvent event) {
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
			} catch (Exception e) {
				// TODO: handle exception here
				e.printStackTrace();
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
	void startHandler(ActionEvent event) {
		currentMusicName = patienceController.getPatience().getCurrentMusic();
		if (currentMusicName.equals("")) {
			JOptionPane.showMessageDialog(new Frame(), "Musik in Optionen auswÃ¤hlen", "Fehler",
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
	void stopHandler(ActionEvent event) {
		if (musicStarted) {
			currentMediaPlayer.stop();
			musicStarted = false;
		}
	}

	@FXML
	void undoHandler(ActionEvent event) {
		// System.out.println("undo");
		patienceController.getGameController().undo();
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
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		exit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		solution.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		tipp.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));

	}

	public void createRectangle() {

		BoardSituation currentBoard = patienceController.getPatience().getCurrentGame().getCurrentBoardsituation();

		Stack[] stackArray = currentBoard.getStackArray();

		stackTangles = new StackTangleIdiot[Idiot.numberOfStacks];
		cardtangles = new ArrayList<Cardtangle>();

		for (Stack stack : stackArray) {
			StackTangleIdiot stackTangle = new StackTangleIdiot(stack);
			stackTangles[stack.getArrPosition()] = stackTangle;
			root.getChildren().add(stackTangle);

			int yCoordinate = (int) stackTangle.getY();
			for (Card card : stack.getCardList()) {
				Cardtangle cardTangle = createRectangleForCard(stackTangle, card, yCoordinate);
				cardtangles.add(cardTangle);

				if (stack.getArrPosition() >= Idiot.FIRST_STACK && stack.getArrPosition() <= Idiot.FOURTH_STACK)
					yCoordinate = yCoordinate + 30;
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

	private Cardtangle createRectangleForCard(StackTangleIdiot rectangleStack, Card card, int yCoordinateCard) {
		Cardtangle rectangleCard = null;
		rectangleCard = new Cardtangle(card);
		rectangleCard.setX(rectangleStack.getX());
		rectangleCard.setY(yCoordinateCard);
		rectangleCard.setWidth(100);
		rectangleCard.setHeight(150);
		// showCardBack(rectangleCard);
		if (card.isVisible()) {
			showCard(rectangleCard, card);
		} else {
			showCardBack(rectangleCard); // hier Pixel der PNG auf 79x123 setzen
		}

		rectangleCard.setOnMousePressed((t) -> {
			Cardtangle rectangleClickedCard = (Cardtangle) (t.getSource());
			Card clickedCard = rectangleClickedCard.getCard();

			if (!this.patienceController.getGameController().isMovable(card)) {
				rectangleClickedCard.setDisable(true);
				return;
			}

			if (this.patienceController.getGameController().cardFromTalon(card)) {
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

			double offsetX = t.getSceneX() - xCoordinate;
			double offsetY = t.getSceneY() - yCoordinate;

			rectangleClickedCard.setX(rectangleClickedCard.getX() + offsetX);
			rectangleClickedCard.setY(rectangleClickedCard.getY() + offsetY);

			rectangleClickedCard.toFront();

			xCoordinate = t.getSceneX();
			yCoordinate = t.getSceneY();

			// animateTransferstackOnDragged(rectangleClickedCard, t);

			for (StackTangleIdiot stacktangle : stackTangles) {
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
				refreshBoard(null);
			else
				this.patienceController.getGameController().move(transferCard, destinationStack);
		});

		root.getChildren().add(rectangleCard);

		return rectangleCard;
	}

	private Stack getDestinationStack(Rectangle rectangleReleasedCard, MouseEvent mouseEvent) {

		for (StackTangleIdiot stacktangle : stackTangles) {

			if (rectangleReleasedCard.intersects(stacktangle.getStackXCoordinate(), stacktangle.getStackYCoordinate(),
					stacktangle.getStackWidth(), stacktangle.getStackHeight())) {
				return stacktangle.getStack();
			}
		}

		return null;
	}

	private void animateFromTalonMove(Cardtangle rectangleCard, Card card) {

	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
		this.patienceController.getGameController().addBoardAUI(this);

		createRectangle();

		// playMusic();
	}

	@FXML
	void initialize() {

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
					createRectangle();
					startAI();
				}}));
			timeline.play();
		}
	}

	@Override
	public void refreshGameFinished() {
		JOptionPane.showMessageDialog(new Frame(), "Glueckwunsch! Sie habe gewonnen: " + "Zuege: " + patienceController.getPatience().getCurrentGame().getCountMoves(), "Gewonnen", JOptionPane.PLAIN_MESSAGE);

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
//			e.printStackTrace();
//		}
	}


	public void startAI()
	{
		if(patienceController.currentPlayerIsAI())
		{
			this.root.setDisable(true);
			if(!patienceController.getCurrentPlayer().aiTurn()){
				this.root.setDisable(false);
			}
		}else {
			this.root.setDisable(false);
		}
	}

	public void lock(Boolean lock) {
		this.lock = lock;
	}

	private class animationHandler implements EventHandler<ActionEvent>{
		List<Cardtangle> cardtangleTransferList;
		double changeX;
		double changeY;
		int cycles;
		Move move;
		public animationHandler(Move move){
			super();
			this.move = move;
			List<Card> transferList = move.getTransferStack().getCardList();
			Card transferCard = move.getTransferStack().getCardList().get(0);
			Stack destinationStack = move.getDestinationStack();
			StackTangleIdiot destinationTangle = null;
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
			for (StackTangleIdiot stacktangle : stackTangles) {
				if (stacktangle.getStack().getArrPosition() == destinationStack.getArrPosition()) {
					destinationTangle = stacktangle;
				}
			}
			
			double sourceX = transferTangle.getX();
			double sourceY = transferTangle.getY();
			double destinationX = destinationTangle.getX();
			double destinationY = destinationTangle.getY();
			if(destinationStack.getArrPosition() >= Idiot.FIRST_STACK && destinationStack.getArrPosition() <= Idiot.FOURTH_STACK){
				destinationY += destinationStack.getCardList().size() * 30;
			}
			
			changeX = (destinationX - sourceX) / ((double)CYCLE_COUNT - 1);
			changeY = (destinationY - sourceY) / ((double)CYCLE_COUNT - 1);
			
			for(Cardtangle cardtangle : cardtangleTransferList){
				showCard(cardtangle, cardtangle.getCard());
			}
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
				if(move.getSourceStack().getArrPosition() != Idiot.TALON || move.getDestinationStack().getArrPosition() == Idiot.FOURTH_STACK){
					root.getChildren().removeAll(root.getChildren());
					root.getChildren().add(menuBar);
					createRectangle();
//					try {
//						Thread.sleep(WAIT_TIME);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					startAI();
				}
			}
		}
	}

	private void animateMove(Move move) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_TIME), new animationHandler(move)));

		timeline.setCycleCount(CYCLE_COUNT);
		timeline.play();
	}
}
