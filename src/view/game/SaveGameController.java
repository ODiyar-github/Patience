package view.game;

import control.PatienceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaveGameController {
	
	private PatienceController patienceController;
    @FXML
    private TextField textField;

    @FXML
    private Button back;

    @FXML
    private Button save;

    @FXML
    void backHandler(ActionEvent event) {
    	final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    }

    @FXML
    void saveHandler(ActionEvent event) {

    }
    
    public void setPatienceController(PatienceController patienceController) {
		this.patienceController=patienceController;
    }
	
}
