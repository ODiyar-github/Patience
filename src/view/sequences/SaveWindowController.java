package view.sequences;



import java.awt.Frame;
import java.io.IOException;

import javax.swing.JOptionPane;

import control.PatienceController;
import exceptions.SaveException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Stack;
import view.Main;
import view.newGame.NewGameController;

public class SaveWindowController {

	private PatienceController patienceController;

	@FXML
    private TextField textField;

    @FXML
    private Button save;

    @FXML
    private Button exit;
    
    @FXML
    void exitHandler(ActionEvent event) {
    	final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("sequences/cardSequences.fxml"));
			AnchorPane cardLayout = (AnchorPane) loader.load();
			
			CardSequences controller = (CardSequences) loader.getController();
			controller.setPatienceController(patienceController);
			Scene scene = new Scene(cardLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.setTitle("Kartensequenz speichern");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void saveHandler(ActionEvent event) {
    	if(textField.getText().equals("")){
    		JOptionPane.showMessageDialog(new Frame(),
					"Bitte Name eingeben",
					"Fehler",
					JOptionPane.WARNING_MESSAGE);
    	}
    	else{
    		String name =textField.getText();
    		for(Stack stack: patienceController.getPatience().getCustomStacks()){
    			if(name.equals(stack.getName())){
    				JOptionPane.showMessageDialog(new Frame(),
    						"Name bereits vorhanden",
    						"Fehler",
    						JOptionPane.WARNING_MESSAGE);
    				break;
    			}
    		}
    		patienceController.getPatience().getCustomStacks().get(patienceController.getPatience().getCustomStacks().size()-1).setName(name);
    		try {
				patienceController.getiOController().save();
			} catch (SaveException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		final Node source = (Node) event.getSource();
    		final Stage stage = (Stage) source.getScene().getWindow();
    		stage.close();
        	try {
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(Main.class.getResource("newGame/newGame.fxml"));
    			AnchorPane cardLayout = (AnchorPane) loader.load();
    			
    			NewGameController controller = (NewGameController) loader.getController();
    			controller.setPatienceController(patienceController);
    			Scene scene = new Scene(cardLayout);
    			Stage dialogStage = new Stage();
    			dialogStage.setScene(scene);
    			dialogStage.setTitle("Neues Spiel erstellen");
    			dialogStage.setResizable(false);
    			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
    			dialogStage.show();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;

	}
	
	@FXML
	void initialize() {
		
	}

	
}
