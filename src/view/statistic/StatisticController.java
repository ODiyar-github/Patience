package view.statistic;

import control.PatienceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Statistic;
import view.Main;

public class StatisticController {

	private PatienceController patienceController;
	private Statistic freecellStatistic;
	private Statistic zankStatistic;
	private Statistic idiotStatistic;
	
	private String freecell="";
	private String idiot="";
	private String zank="";
	
	
	@FXML
	private TextArea statisticTextArea;

	@FXML
	private Button backButton;

	@FXML
	void backHandler(ActionEvent event) {
		// Statistik-Fenster schließen
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		// LoginWindow öffnen
		try {
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
			System.out.println(patienceController);
		} catch (Exception e) {

		}
	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;
		initialize();
	}

	private void initialize() {
		statisticTextArea.setEditable(false);
		freecellStatistic=patienceController.getPatience().getFreecellStatistic();
		idiotStatistic=patienceController.getPatience().getIdiotStatistic();
		zankStatistic=patienceController.getPatience().getZankStatistic();
		
		freecell = "\t"+"Spiele insgesamt: " +this.freecellStatistic.getGamesPlayed()+ "\n"+ "\t"+"Spiele gewonnen: "+this.freecellStatistic.getGamesSuccessfull()+"\n";
		freecell = freecell + "\t"+"Durchschnittliche Züge: "+ this.freecellStatistic.getAverageMoves()+"\n" + "\t"+"Minimale Züge: " + this.freecellStatistic.getMinMoves()+"\n";
		freecell = freecell + "\t"+"Maximale Züge: " + this.freecellStatistic.getMaxMoves()+"\n" + "\t"+"Karten übrig: " + this.freecellStatistic.getAverageCardsLeft()+"\n";
		
		idiot = "\t"+"Spiele insgesamt: " +this.idiotStatistic.getGamesPlayed()+ "\n"+"\t"+"Spiele gewonnen: "+this.idiotStatistic.getGamesSuccessfull()+"\n";
		idiot = idiot + "\t"+"Durchschnittliche Züge: "+ this.idiotStatistic.getAverageMoves()+"\n" + "\t"+"Minimale Züge: " + this.idiotStatistic.getMinMoves()+"\n";
		idiot = idiot + "\t"+"Maximale Züge: " + this.idiotStatistic.getMaxMoves()+"\n" + "\t"+"Karten übrig: " + this.idiotStatistic.getAverageCardsLeft()+"\n";
		
		zank = "\t"+"Spiele insgesamt: " +this.zankStatistic.getGamesPlayed()+"\n"+"\t"+ "Spiele gewonnen: "+this.zankStatistic.getGamesSuccessfull()+"\n";
		zank = zank + "\t"+"Durchschnittliche Züge: "+ this.zankStatistic.getAverageMoves()+"\n" + "\t"+"Minimale Züge: " + this.zankStatistic.getMinMoves()+"\n";
		zank = zank + "\t"+"Maximale Züge: " + this.zankStatistic.getMaxMoves()+"\n" + "\t"+"Karten übrig: " + this.zankStatistic.getAverageCardsLeft()+"\n";
		
		statisticTextArea.appendText("FREECELL Statistik:\n"+freecell+"\n"+"ZANK Statistik:\n"+zank+"\n"+"IDIOT Statistik:\n"+idiot);
		
	}

}
