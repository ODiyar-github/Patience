package view.newGame;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.PatienceController;
import exceptions.SaveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Card;
import model.Freecell;
import model.Idiot;
import model.PlayerType;
import model.Stack;
import model.Zank;
import view.Main;
import view.game.FreecellController;
import view.game.IdiotController;
import view.game.ZankController;
import view.loginWindow.LoginWindowController;
import view.sequences.CardSequences;

public class NewGameController {

	private PatienceController patienceController;
	
	@FXML
	private RadioButton idiotRadio;

	@FXML
	private Button backButton;

	@FXML
	private Button gameStartButton;

	@FXML
	private RadioButton zankRadio;

	@FXML
	private RadioButton freeCellRadio;
	
    @FXML
    private ToggleGroup gameType;

	@FXML
	private ComboBox<String> player2ComboBox;

	@FXML
	private ComboBox<String> player1ComboBox;

	@FXML
	private ComboBox<String> sequenceCard1;

	@FXML
	private ComboBox<String> sequenceCard2;

	@FXML
	private Button creatSequencesButton;


    @FXML
    private Button deleteStack;
	
    @FXML
    void deleteHandler(ActionEvent event) {
    	if(!sequenceCard1.getItems().get(sequenceCard1.getSelectionModel().getSelectedIndex()).equals("Random")){
    		try {
        		int selected=sequenceCard1.getSelectionModel().getSelectedIndex();
        		this.patienceController.getPatience().getCustomStacks().remove(selected-1);
        		sequenceCard1.getItems().remove(selected);
        		sequenceCard2.getItems().remove(selected);
        		sequenceCard1.getSelectionModel().select(0);
        		sequenceCard2.getSelectionModel().select(0);
				this.patienceController.getiOController().save();
			} catch (SaveException e) {
				e.printStackTrace();
			}
    	}
    }

	
	@FXML
	void backHandler(ActionEvent event) {
		// NewGame-Fenster schließen
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		// LoginWindow öffnen
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("loginWindow/loginWindow.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();
			
			LoginWindowController controller = (LoginWindowController) loader.getController();
			controller.setPatienceController(patienceController);
			
			Scene scene = new Scene(loginLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.setTitle("Patience");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	void creatSequenceHandler(ActionEvent event) {
		//NewGame-Fenster schließen
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		//Kartensequenz-Fenster öffnen
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("sequences/cardSequences.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();
			
			CardSequences controller = (CardSequences) loader.getController();
			controller.setPatienceController(patienceController);
			
			Scene scene = new Scene(loginLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.setTitle("Kartensequenzen");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} 
		catch (Exception e) {

		}
	}

	@FXML
	void gameStartHandler(ActionEvent event) {
		//NewGame-Fenster schließen
		if(gameType.getSelectedToggle()==idiotRadio){
			final Node source = (Node) event.getSource();
			final Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("game/idiot.fxml"));
				AnchorPane loginLayout = (AnchorPane) loader.load();
				
				Scene scene = new Scene(loginLayout);
				Stage dialogStage = new Stage();
				dialogStage.setScene(scene);
				dialogStage.setTitle("Idiot-Patience");
				dialogStage.setResizable(false);
				dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
				dialogStage.show();
				
				Stack stack = this.getCustomStack(this.sequenceCard1.getSelectionModel().getSelectedItem());
				PlayerType playerType = this.getPlayerType(this.player1ComboBox.getSelectionModel().getSelectedItem());
				Idiot idiot = new Idiot(stack, playerType);
				patienceController.loadGame(idiot);
				
				IdiotController controller = (IdiotController) loader.getController();
				controller.setStage(dialogStage);
				controller.setPatienceController(patienceController);
				controller.startAI();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(gameType.getSelectedToggle()==zankRadio){
			final Node source = (Node) event.getSource();
			final Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("game/zank.fxml"));
				AnchorPane loginLayout = (AnchorPane) loader.load();
				
				Scene scene = new Scene(loginLayout);
				Stage dialogStage = new Stage();
				dialogStage.setScene(scene);
				dialogStage.setTitle("Zank-Patience");
				dialogStage.setResizable(false);
				dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
				dialogStage.show();
				
				PlayerType firstPlayerType = this.getPlayerType(this.player1ComboBox.getSelectionModel().getSelectedItem());
				PlayerType secondPlayerType = this.getPlayerType(this.player2ComboBox.getSelectionModel().getSelectedItem());
				Stack firstPlayerStack = this.getCustomStack(this.sequenceCard1.getSelectionModel().getSelectedItem());
				Stack secondPlayerStack = this.getCustomStack(this.sequenceCard2.getSelectionModel().getSelectedItem());

				Zank zank = new Zank(firstPlayerStack, secondPlayerStack, firstPlayerType, secondPlayerType);
				patienceController.loadGame(zank);
			
				ZankController controller = (ZankController) loader.getController();
				
				controller.setStage(dialogStage);
				controller.setPatienceController(patienceController);
				controller.setLayout();
				
			} 
			catch (Exception e) {
				
			}
		}
		else if(gameType.getSelectedToggle()==freeCellRadio){
			final Node source = (Node) event.getSource();
			final Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("game/freecell.fxml"));
				AnchorPane loginLayout = (AnchorPane) loader.load();
						
				Scene scene = new Scene(loginLayout);
				Stage dialogStage = new Stage();
				dialogStage.setScene(scene);
				dialogStage.setTitle("FreeCell-Patience");
				dialogStage.setResizable(false);
				dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
				dialogStage.show();
				
				Stack stack = this.getCustomStack(this.sequenceCard1.getSelectionModel().getSelectedItem());
				PlayerType playerType = this.getPlayerType(this.player1ComboBox.getSelectionModel().getSelectedItem());
				Freecell freecell = new Freecell(stack, playerType);
				patienceController.loadGame(freecell);
				
				FreecellController controller = (FreecellController) loader.getController();
				controller.setPatienceController(patienceController);
				controller.setStage(dialogStage);
				controller.setLayout();	
				
				controller.startAI();
			} 
			catch (Exception e) {
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(new Frame(),
						"Heftiger Fehler augetreten",
						"Fehler",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		else{			
			JOptionPane.showMessageDialog(new Frame(),
					"Bitte einen Spieltyp auswählen",
					"Fehler",
					JOptionPane.WARNING_MESSAGE);
		}		
	}

	@FXML
	void freecellHandler(ActionEvent event) {
		gameType.selectToggle(freeCellRadio);
		player1ComboBox.setDisable(false);
		player2ComboBox.setDisable(true);
		player1ComboBox.getSelectionModel().select(0);
		sequenceCard1.setDisable(false);
		sequenceCard2.setDisable(true);
		
		player1ComboBox.getItems().remove("KI Leicht");
		player1ComboBox.getItems().remove("KI Schwer");
		player1ComboBox.getItems().remove("KI");

		player1ComboBox.getItems().add("KI");
		
		player1ComboBox.getSelectionModel().select(0);
		sequenceCard1.getSelectionModel().select(0);
		player2ComboBox.getSelectionModel().select(0);
		sequenceCard2.getSelectionModel().select(0);
	}

	@FXML
	void idiotHandler(ActionEvent event) {
		gameType.selectToggle(idiotRadio);
		player1ComboBox.setDisable(false);
		player2ComboBox.setDisable(true);
		player1ComboBox.getSelectionModel().select(0);
		sequenceCard1.setDisable(false);
		sequenceCard2.setDisable(true);
		sequenceCard1.getSelectionModel().select(0);
		sequenceCard2.getSelectionModel().select(0);
		
		player1ComboBox.getItems().remove("KI Leicht");
		player1ComboBox.getItems().remove("KI Schwer");
		player1ComboBox.getItems().remove("KI");
		
		player1ComboBox.getItems().add("KI");
	}

	@FXML
	void zankHandler(ActionEvent event) {
		gameType.selectToggle(zankRadio);
		player1ComboBox.setDisable(false);
		player2ComboBox.setDisable(false);
		player1ComboBox.getSelectionModel().select(0);
		player2ComboBox.getSelectionModel().select(1);
		sequenceCard1.setDisable(false);
		sequenceCard2.setDisable(false);
		sequenceCard1.getSelectionModel().select(0);
		sequenceCard2.getSelectionModel().select(0);
		
		player1ComboBox.getItems().remove("KI Leicht");
		player1ComboBox.getItems().remove("KI Schwer");
		player1ComboBox.getItems().remove("KI");

		player1ComboBox.getItems().add("KI Leicht");
		player1ComboBox.getItems().add("KI Schwer");
	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController=patienceController;
		ObservableList<Stack> stackList= FXCollections.observableArrayList(this.patienceController.getPatience().getCustomStacks());
		
		sequenceCard1.getItems().add("Random");
		sequenceCard2.getItems().add("Random");
		
		for(Stack stack: stackList){
			sequenceCard1.getItems().add(stack.getName());
			sequenceCard2.getItems().add(stack.getName());
		}
		
		sequenceCard1.getSelectionModel().select(0);
		sequenceCard2.getSelectionModel().select(0);
	}
	
	@FXML
	void initialize() {
		//Möglichkeiten für Spieler
		player1ComboBox.getItems().add("Human");
		player1ComboBox.getItems().add("KI");
		player2ComboBox.getItems().add("Human");
		player2ComboBox.getItems().add("KI Leicht");
		player2ComboBox.getItems().add("KI Schwer");
		
		gameType.selectToggle(idiotRadio);
		
		//ComboBoxen disablen
		player1ComboBox.setDisable(false);
		player2ComboBox.setDisable(true);
		sequenceCard1.setDisable(false);
		sequenceCard2.setDisable(true);
		
		player1ComboBox.getSelectionModel().select(0);
		sequenceCard1.getSelectionModel().select(0);
		player2ComboBox.getSelectionModel().select(0);
		sequenceCard2.getSelectionModel().select(0);
		
		ImageView settingImage = new ImageView(new Image("/images/icons/Delete_Icon.png"));
		deleteStack.setGraphic(settingImage);
		deleteStack.setTranslateX(deleteStack.getTranslateX()-7.5);
		deleteStack.setTranslateY(deleteStack.getTranslateY()-8);
		
	}
	
	private Stack getCustomStack(String name)
	{
		if(name.equals("Random"))
			return this.patienceController.getBoardController().generateRandomStack();
		
		for(Stack customStack : this.patienceController.getPatience().getCustomStacks())
		{
			if(customStack.getName().equals(name))
				return customStack;
		}
		
		return null;
	}

	private PlayerType getPlayerType(String string){
		if(string.equals("Human")){
			return PlayerType.HUMAN;
		}
		
		if(string.equals("KI")){
			if(this.freeCellRadio.isSelected()){
				return PlayerType.FREECELL;
			}
			else{
				return PlayerType.IDIOT;
			}
		}
		
		if(string.equals("KI Leicht")){
			return PlayerType.ZANK_EASY;
		}
		
		if(string.equals("KI Schwer")){
			return PlayerType.ZANK_HARD;
		}
		
		return null;
	}
}
