package view.loginWindow;

import java.io.IOException;

import control.PatienceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import view.Main;
import view.loadGame.LoadGameController;
import view.newGame.NewGameController;
import view.option.OptionController;
import view.statistic.StatisticController;

public class LoginWindowController {

	private PatienceController patienceController;

	@FXML
	private Button newGame;

	@FXML
	private Button loadGame;

	@FXML
	private Button infoButton;

	@FXML
	private Button showStatisticButton;

	@FXML
	private Button settingsButton;

	@FXML
	void loadGameHandler(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("loadGame/loadGame.fxml"));
			AnchorPane loadLayout = (AnchorPane) loader.load();

			LoadGameController controller = (LoadGameController) loader.getController();
			controller.setPatienceController(patienceController);
			
			final Scene SCENE = new Scene(loadLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(SCENE);
			dialogStage.setTitle("Lade Spiel");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void newGameHandler(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("newGame/newGame.fxml"));
			AnchorPane optionLayout = (AnchorPane) loader.load();

			NewGameController controller = (NewGameController) loader.getController();
			controller.setPatienceController(patienceController);
			
			final Scene SCENE = new Scene(optionLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(SCENE);
			dialogStage.setTitle("Neues Spiel");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void infoHandler(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		//stage.close();
		WebView webView = new WebView();
		WebEngine engine = webView.getEngine();

		engine.load("https://sopra.cs.tu-dortmund.de/wiki/sopra/17b/projekt2/start");
		Scene manual = new Scene(webView);
		Stage manualStage = new Stage();
		manualStage.setScene(manual);
		manualStage.setTitle("Anleitung");
		manualStage.setHeight(700);
		manualStage.setWidth(1000);
		manualStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
		manualStage.show();
	}

	@FXML
	void settingHandler(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("option/option.fxml"));
			AnchorPane optionLayout = (AnchorPane) loader.load();
			
			OptionController controller = (OptionController) loader.getController();
			controller.setPatienceController(patienceController);
			
			final Scene SCENE = new Scene(optionLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(SCENE);
			dialogStage.setTitle("Optionen");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void showStatisticHandler(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("statistic/Statistic.fxml"));
			AnchorPane statisticLayout = (AnchorPane) loader.load();

			StatisticController controller = (StatisticController) loader.getController();
			controller.setPatienceController(patienceController);

			final Scene SCENE = new Scene(statisticLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(SCENE);
			dialogStage.setTitle("Statistik");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController=patienceController;
		
	}
	
	
	
	@FXML
	void initialize() {
		infoButton.setText("");
		showStatisticButton.setText("");
		settingsButton.setText("");
		showStatisticButton.setPrefHeight(48);
		showStatisticButton.setPrefWidth(58);
		showStatisticButton.setTranslateX(showStatisticButton.getTranslateX() + 58);
		infoButton.setTranslateX(infoButton.getTranslateX() - 1.5);
		settingsButton.setPrefHeight(48);
		settingsButton.setPrefWidth(58);

		ImageView infoImage = new ImageView(new Image("/images/icons/info-icon.png"));
		infoImage.setFitWidth(58);
		infoImage.setFitHeight(48);
		infoButton.setGraphic(infoImage);

		ImageView statisticImage = new ImageView(new Image("/images/icons/statistic-icon.png"));
		statisticImage.setFitWidth(58);
		statisticImage.setFitHeight(48);
		showStatisticButton.setGraphic(statisticImage);

		ImageView settingImage = new ImageView(new Image("/images/icons/Setting-icon.png"));
		settingImage.setFitWidth(58);
		settingImage.setFitHeight(48);
		settingsButton.setGraphic(settingImage);

		try {
			patienceController = new PatienceController();
			patienceController.getiOController().load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
