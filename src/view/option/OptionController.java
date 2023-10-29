package view.option;

import java.awt.Frame;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.PatienceController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.Main;
import view.loginWindow.LoginWindowController;


public class OptionController {

	private PatienceController patienceController;
	private boolean musicStarted;
	private Image currentImageSelected;
	private String currentImageName;
	private MediaPlayer currentMediaPlayer;
	private String currentMusicName="jinglebells";
	
    @FXML
    private ListView<String> cardBack;

    @FXML
    private ImageView image;

    @FXML
    private ListView<String> music;

    @FXML
    private Button startButton;

    @FXML
    private Button settings;

    @FXML
    private Button backButton;

    @FXML
    private Button stopButton;

    @FXML
    void backHandler(ActionEvent event){
    	if(musicStarted) {
    		currentMediaPlayer.stop();
    	}
    	//Option-Fenster schließen
    	final Node source = (Node) event.getSource();
    	final Stage stage = (Stage) source.getScene().getWindow();
    	stage.close(); 
    	//LoginWindow öffnen
    	try{
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("loginWindow/loginWindow.fxml"));
            AnchorPane loginLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(loginLayout);
            Stage dialogStage = new Stage();
    		dialogStage.setScene(scene);
    		dialogStage.setTitle("Patience");
    		dialogStage.setResizable(false);
    		dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
    		dialogStage.show();
    	}
    	catch(Exception e){
    	}
    }

    @FXML
    void settingsHandler(ActionEvent event) {
    	//EinstellungsButton
    	if(musicStarted) {
    		currentMediaPlayer.stop();
    	}
    	currentMusicName=music.getSelectionModel().getSelectedItem().toString();
    	System.out.println("OPTIONS: "+currentMusicName);
    	patienceController.getSettingsController().setCurrentMusic(currentMusicName);
    	
    	System.out.println(patienceController.getSettingsController().getCurrentMusic());
    	
    	patienceController.getSettingsController().setCurrentCardBack(currentImageName);
    	try {
			patienceController.getiOController().save();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	//Option-Fenster schließen
    	final Node source = (Node) event.getSource();
    	final Stage stage = (Stage) source.getScene().getWindow();
    	stage.close(); 
    	//LoginWindow öffnen
    	try{
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
    	}
    	catch(Exception e){
    	}
    }

    @FXML
    void startHandler(ActionEvent event) {
    	//Musik
    	if(!musicStarted && !currentMediaPlayer.equals(null)){
			currentMediaPlayer.play();
			musicStarted=true;
    	}
    	
    	
    }

    @FXML
    void stopHandler(ActionEvent event) {
    	//Musik
    	if(musicStarted) {
    		currentMediaPlayer.stop();
    		musicStarted=false;
    	}
    }
    
    public void setPatienceController(PatienceController patienceController) {
    	this.patienceController=patienceController;
    	initialize();
    }
   
	private void initialize() {		
		buttonSettings();
		listViewSettings();
	}
	
	private void listViewSettings() {
		//Karten R�cken anzeigen
		ArrayList<String> tempList=patienceController.getSettingsController().getCardBackList();
		cardBack.getItems().addAll(tempList);
		
		cardBack.getSelectionModel().select(0);
		currentImageSelected=new Image("file:images/cardBackImages/Standart-Blau.png");
		currentImageName="Standart-Blau";
		image.setImage(currentImageSelected);
		
		cardBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(cardBack.getSelectionModel().getSelectedItem().equals("Standart-Blau")) {
					currentImageSelected=new Image("file:images/cardBackImages/Standart-Blau.png");
					currentImageName="Standart-Blau";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("Standart-Rot")) {
					currentImageSelected=new Image("file:images/cardBackImages/Standart-Rot.png");
					currentImageName="Standart-Rot";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("YU-GI-OH")) {
					currentImageSelected=new Image("file:images/cardBackImages/YU-GI-OH.png");
					currentImageName="YU-GI-OH";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("HeartStone")) {
					currentImageSelected=new Image("file:images/cardBackImages/HeartStone.png");
					currentImageName="HeartStone";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("Gwent(The Witcher)1")) {
					currentImageSelected=new Image("file:images/cardBackImages/Gwent(TheWitcher)1.png");
					currentImageName="Gwent(TheWitcher)1";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("Gwent(The Witcher)2")) {
					currentImageSelected=new Image("file:images/cardBackImages/Gwent(TheWitcher)2.png");
					currentImageName="Gwent(TheWitcher)2";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("Gwent(The Witcher)3")) {
					currentImageSelected=new Image("file:images/cardBackImages/Gwent(TheWitcher)3.png");
					currentImageName="Gwent(TheWitcher)3";
					image.setImage(currentImageSelected);
				}
				else if(cardBack.getSelectionModel().getSelectedItem().equals("Gwent(The Witcher)4")) {
					currentImageSelected=new Image("file:images/cardBackImages/Gwent(TheWitcher)4.png");
					currentImageName="Gwent(TheWitcher)4";
					image.setImage(currentImageSelected);
				}
			}
		});
		
		ArrayList<String> musicList=patienceController.getSettingsController().getMusicList();
		music.getItems().addAll(musicList);
		music.getSelectionModel().select(0);
		
		music.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				currentMusicName=music.getSelectionModel().getSelectedItem().toString().toLowerCase();
				System.out.println(currentMusicName);
				if(currentMusicName.equals("")){
					JOptionPane.showMessageDialog(new Frame(),
							"Musik in Optionen auswählen",
							"Fehler",
							JOptionPane.WARNING_MESSAGE);
				}
				else{
					if(musicStarted){
						currentMediaPlayer.stop();
						musicStarted=false;
					}
					Media media = new Media(Paths.get("audioFiles/"+currentMusicName +".mp3").toUri().toString());
					currentMediaPlayer=new MediaPlayer(media);
				}
			}
		});
		
		
	}
	
	private void buttonSettings() {
		stopButton.setText("");
		startButton.setText("");
		ImageView infoImage = new ImageView(new Image("/images/icons/stopMusicIcon.png"));
		infoImage.setFitWidth(58);
		infoImage.setFitHeight(48);
		stopButton.setGraphic(infoImage);
		ImageView statisticImage = new ImageView(new Image("/images/icons/playMusicIcon.png"));
		statisticImage.setFitWidth(58);
		statisticImage.setFitHeight(48);
		startButton.setGraphic(statisticImage);
		
	}
	
}
