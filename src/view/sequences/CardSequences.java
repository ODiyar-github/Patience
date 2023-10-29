package view.sequences;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFileChooser;

import control.IOController;
import control.PatienceController;
import exceptions.SaveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Card;
import model.Rank;
import model.Stack;
import model.Suit;
import view.Main;
import view.newGame.NewGameController;

public class CardSequences {

	private PatienceController patienceController;
	private ObservableList<Card> cardList;
	private ArrayList<Card> stack; 
	private ArrayList<Card> newStack;
	@FXML
	private ListView<String> listView;

	@FXML
	private ListView<String> listView1;

	@FXML
	private Button addToCards;

	@FXML
	private Button removeFromCards;

	@FXML
	private Button save;

	@FXML
	private Button back;

	@FXML
	private Button loadCards;
	
    @FXML
    private Label cardCounter;

    @FXML
    private ImageView image;

	@FXML
	void addToCardsHandler(ActionEvent event) {
		String selectedCard = listView.getSelectionModel().getSelectedItem();
		if (listView.getSelectionModel().getSelectedItem() != null) {
			newStack.add(stack.get(listView.getSelectionModel().getSelectedIndex()));
			stack.remove(listView.getSelectionModel().getSelectedIndex());
			listView.getItems().remove(selectedCard);
			listView1.getItems().add(selectedCard);
			cardCounter.setText(listView.getItems().size()+"");
			showCards();
		}
	}

	public void showCards(){
		for(Card card:newStack){
			System.out.print(card.getSuit().toString() + "_" + card.getRank().toString()+",");
		}
		System.out.println();
	}
	
	
	@FXML
	void backHandler(ActionEvent event) {
		// Kartensequenz-Fenster schließen
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		// NewGame-Fenster öffnen
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("newGame/newGame.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();
			
			NewGameController controller = (NewGameController) loader.getController();
			controller.setPatienceController(patienceController);
			
			Scene scene = new Scene(loginLayout);
			Stage dialogStage = new Stage();
			dialogStage.setScene(scene);
			dialogStage.setTitle("Neues Spiel");
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("/images/icons/SceneIcon.jpg"));
			dialogStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	void loadCardsHandler(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fileChooser.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
        	file = fileChooser.getSelectedFile();
        }
		Stack stack = IOController.loadCustomStack(file.getPath());
		
		this.stack.removeAll(this.stack);
		newStack.removeAll(newStack);
		newStack.addAll(stack.getCardList());
		
		
		listView.getItems().removeAll(listView.getItems());
		for(Card card: newStack){
			listView1.getItems().add(card.toString());
		}
	}

	@FXML
	void removeFromCardsHandler(ActionEvent event) {
		String selectedCard = listView1.getSelectionModel().getSelectedItem();
		if (listView1.getSelectionModel().getSelectedItem() != null) {
			stack.add(newStack.get(listView1.getSelectionModel().getSelectedIndex()));
			newStack.remove(listView1.getSelectionModel().getSelectedIndex());
			listView1.getItems().remove(selectedCard);
			listView.getItems().add(selectedCard);
			cardCounter.setText(listView.getItems().size()+"");
			showCards();
		}
	}

	@FXML
	void saveHandler(ActionEvent event) {
		if(listView.getItems().size()==0){
			createStackToSave();
			final Node source = (Node) event.getSource();
			final Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			//SpeichernFenster öffnen
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("sequences/saveWindow.fxml"));
				AnchorPane saveLayout = (AnchorPane) loader.load();
				
				SaveWindowController controller = (SaveWindowController) loader.getController();
				controller.setPatienceController(patienceController);
				Scene scene = new Scene(saveLayout);
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
		else{
			//MESSAGEBOX EINFÜGEN
		}
	}
		
	public void createStackToSave(){
		LinkedList<Card> newStackLinkedList= new LinkedList<Card>();
		for(Card card:newStack){
			newStackLinkedList.add(card);
		}
		Stack toInsert= new Stack(newStackLinkedList);
		patienceController.getPatience().setCustomStacks(toInsert);
		try {
			patienceController.getiOController().save();
		} catch (SaveException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void initialize() {
		cardList = FXCollections.observableArrayList(createStack());
		for(Card card: cardList){
			listView.getItems().add(card.getSuit().toString() + "_" + card.getRank().toString());
		}
		
		listView.refresh();
		cardCounter.setText(listView.getItems().size()+"");
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			
			@Override
			public void handle(MouseEvent arg0) {

			}

		});

	}

	public ArrayList<Card> createStack() {
		newStack=new ArrayList<Card>();
		stack = new ArrayList<Card>();
		Rank[] ranks = Rank.values();
		Suit[] suits = Suit.values();
		for (Suit suit : suits) {
			for (Rank rank : ranks) {
				stack.add(new Card(suit, rank));
			}
		}
		return stack;
	}

	public void setPatienceController(PatienceController patienceController) {
		this.patienceController = patienceController;

	}
}
