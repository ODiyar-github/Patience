package view.loadGame;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.PatienceController;
import exceptions.SaveException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Card;
import model.Freecell;
import model.Game;
import model.Idiot;
import model.PlayerType;
import model.Stack;
import model.Zank;
import support.NiceStrings;
import view.Main;
import view.game.FreecellController;
import view.game.IdiotController;
import view.game.ZankController;
import view.loginWindow.LoginWindowController;

public class LoadGameController {

	private PatienceController patienceController;
	private ArrayList<Game> currentGameList;

	@FXML
	private ListView<String> listView;

	@FXML
	private Button loadButton;

	@FXML
	private Button backButton;

	@FXML
	private ImageView gameImage;

    @FXML
    private Button deleteGame;
	
    @FXML
    void deleteHandler(ActionEvent event) {
    	if(listView.getSelectionModel().getSelectedIndex()!=-1){
        	int dialogResult = JOptionPane.showConfirmDialog (null, "Wollen Sie wirklich den Spielstand löschen?","Warning",JOptionPane.YES_NO_OPTION);
        	if(dialogResult == JOptionPane.YES_OPTION){
        		
        		patienceController.getPatience().getGameList().remove(listView.getSelectionModel().getSelectedIndex());
        		listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
        		listView.refresh();
        		gameImage.setImage(null);
        		try {
					patienceController.getiOController().save();
				} catch (SaveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		if(listView.getItems().size()!=0){
        			listView.getSelectionModel().select(0);
        			Game firstGame=currentGameList.get(0);
        			gameImage.setImage(new Image("file:images/gameImages/" + firstGame.getGameImagePath() + ".png"));
        		}
        	}
        	else{
        		System.out.println("NO");
        	}
    	}
    }
	
	
	
	@FXML
	void backHandler(ActionEvent event) {
		// LoadGame-Fenster schließen
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
	void loadHandler(ActionEvent event) {
		Game selectedGame = currentGameList.get(listView.getSelectionModel().getSelectedIndex());
		if (selectedGame instanceof model.Zank) {
			Zank selectedZankGame = (Zank) currentGameList.get(listView.getSelectionModel().getSelectedIndex());
			loadGame(selectedZankGame);
		} else if (selectedGame instanceof model.Idiot) {
			Idiot selectedIdiotGame = (Idiot) currentGameList.get(listView.getSelectionModel().getSelectedIndex());
			loadGame(selectedIdiotGame);
		} else if (selectedGame instanceof model.Freecell) {

			Freecell selectedFreecellGame = (Freecell) currentGameList
					.get(listView.getSelectionModel().getSelectedIndex());
			loadGame(selectedFreecellGame);
		}
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	public void loadGame(Game game) {
		if (game.getGameType().equals("Idiot")) {
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

				Idiot idiot = (Idiot) game;
				patienceController.loadGame(idiot);

				IdiotController controller = (IdiotController) loader.getController();
				controller.setStage(dialogStage);
				controller.setPatienceController(patienceController);
				controller.startAI();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (game.getGameType().equals("Zank")) {
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

				Zank zank = (Zank) game;
				patienceController.loadGame(zank);

				ZankController controller = (ZankController) loader.getController();
				controller.setPatienceController(patienceController);
				controller.setStage(dialogStage);
				controller.setLayout();

			} catch (Exception e) {

			}
		} else if (game.getGameType().equals("Freecell")) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("game/freecell.fxml"));
				AnchorPane loginLayout = (AnchorPane) loader.load();

				Scene scene = new Scene(loginLayout);
				Stage dialogStage = new Stage();
				dialogStage.setScene(scene);
				dialogStage.setTitle("Freecell-Patience");
				dialogStage.setResizable(false);
				dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
				dialogStage.show();

				Freecell freecell = (Freecell) game;
				patienceController.loadGame(freecell);

				FreecellController controller = (FreecellController) loader.getController();
				controller.setPatienceController(patienceController);
				controller.setStage(dialogStage);
				controller.setLayout();
			} catch (Exception e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(new Frame(), "Heftiger Fehler augetreten", "Fehler",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(new Frame(), "Bitte einen Spieltyp auswählen", "Fehler",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
		initialize();
		selectionListView();
	}

	private void selectionListView() {
		
		gameImage.setTranslateX(gameImage.getTranslateX()-50);
		
		if(listView.getItems().size()!=0){
			listView.getSelectionModel().select(0);
			Game firstGame=currentGameList.get(0);
			gameImage.setImage(new Image("file:images/gameImages/" + firstGame.getGameImagePath() + ".png"));
		}
		
		listView.setOnMousePressed((event)->{
			if (listView.getSelectionModel().getSelectedIndex() != -1) {
				Game game = currentGameList.get(listView.getSelectionModel().getSelectedIndex());
				System.out.println(listView.getSelectionModel().getSelectedIndex());
				gameImage.setImage(new Image("file:images/gameImages/" + game.getGameImagePath() + ".png"));
			}
		});


	}

	private void initialize() {
		currentGameList = patienceController.getPatience().getGameList();
		for (Game game : currentGameList) {
			String date = NiceStrings.dateToString(game.getDate());
			String gameType = game.getGameType();
			listView.getItems().add(date + " " + gameType);
		}
		
		ImageView settingImage = new ImageView(new Image("/images/icons/Delete_Icon.png"));
		deleteGame.setGraphic(settingImage);
		deleteGame.setTranslateX(deleteGame.getTranslateX()-7);
		deleteGame.setTranslateY(deleteGame.getTranslateY()-8);
		
	}

}
