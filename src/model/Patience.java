package model;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author sopr099
 * Die Oberklasse von Patience welche die Spieldateien beeinhaltet
 */
public class Patience implements Serializable {

	/**
	 * Die Liste der vefügbaren Kartenrücken
	 */
	private ArrayList<String> cardBackList;

	/**
	 * Der aktuell ausgewählte Kartenrücken
	 */
	private String currentCardBack;

	/**
	 * Die Liste aller verfügbaren Musikstücke
	 */
	private ArrayList<String> musicList;

	/**
	 * Das aktuell ausgewählte Musik
	 */
	private String currentMusic;

	/**
	 * Die aktuell gespielte Partie
	 */
	private Game currentGame;

	/**
	 * Die Statistik der gespielten Idioten-Patience-Spiele
	 */
	private Statistic idiotStatistic;

	/**
	 * Die Statistik der gespielten Freecell-Patience-Spiele
	 */
	private Statistic freecellStatistic;

	/**
	 * Die Statistik der gespielten Zank-Patience-Spiele
	 */
	private Statistic zankStatistic;

	/**
	 * Die Liste der gespeicherten Partien
	 */
	private ArrayList<Game> gameList;

	/**
	 * Die Liste aller vorgefertigten Kartenreihenfolgen
	 */
	private ArrayList<Stack> customStacks;

	/**
	 * Erstellt eine Patience, dabei werden die Listen als neue leere Listen initialisiert, die im Falle von cardBackList und musicList über
	 * die über Setter gesetzt direkt gesetzt werden und im Falle von gameList und customStacks noch über add() befüllt werden müssen
	 */
	public Patience() {
		this.cardBackList = null;
		this.currentCardBack = "Standart-Blau";
		this.musicList = null;
		this.currentMusic = "";
		this.idiotStatistic = new Statistic();
		this.freecellStatistic  = new Statistic();
		this.zankStatistic = new Statistic();
		this.gameList = new ArrayList<Game>();
		this.customStacks = new ArrayList<Stack>();
		setMusicList();
		setCardBackList();
	}

	/**
	 * @return Die Liste der verfügbaren Kartenrücken
	 */
	public ArrayList<String> getCardBackList() {
		return cardBackList;
	}

	/**
	 * Setzt die CardBackList gleich einer Liste der im dafür vorgesehen Folder befindlichen Karetenrücken
	 */
	public void setCardBackList() {
		
		ArrayList<String> tempCardBackList = new ArrayList<String>();
		tempCardBackList.add("Standart-Blau");
		tempCardBackList.add("Standart-Rot");
		tempCardBackList.add("YU-GI-OH");
		tempCardBackList.add("HeartStone");
		tempCardBackList.add("Gwent(The Witcher)1");
		tempCardBackList.add("Gwent(The Witcher)2");
		tempCardBackList.add("Gwent(The Witcher)3");
		tempCardBackList.add("Gwent(The Witcher)4");
		
		
		
		/* 
		 * TODO: Alle Images aus dem Folder in die Liste packen
		 */
		cardBackList = tempCardBackList;
	}

	/**
	 * @return Den aktuellen Kartenrücken als Bild
	 */
	public String getCurrentCardBack() {
		return this.currentCardBack;
	}

	/**
	 * @param currentCardBack Den neuen Kartenrücken als Image
	 */
	public void setCurrentCardBack(String currentCardBack) {
		this.currentCardBack = currentCardBack;
	}

	/**
	 * @return Die Liste der verfügbaren Musik
	 */
	public ArrayList<String> getMusicList() {
		return musicList;
	}

	/**
	 *  Setzt die musicList gleich einer Liste der im dafür vorgesehen Folder befindlichen Audiodateien
	 */
	public void setMusicList() {	
		
		ArrayList<String> tempMusicList = new ArrayList<String>();
		tempMusicList.add("jinglebells");
		tempMusicList.add("x-factor1");
		tempMusicList.add("x-factor2");
		tempMusicList.add("hallohelmut");
		tempMusicList.add("ohbaby");
		tempMusicList.add("dermickiekrausesong");
		tempMusicList.add("zujungfürmich");
		tempMusicList.add("ichverkaufemeinenkörper");
		
		
		/* 
		 * TODO: Alle AudioFileFormats aus dem Folder in die Liste packen
		 */
		musicList =  tempMusicList;
	}

	/**
	 * @return Die aktuel ausgewählte Musik
	 */
	public String getCurrentMusic() {
		return currentMusic;
	}

	/**
	 * @param currentMusic Die gewünschte Musik
	 */
	public void setCurrentMusic(String musicName) {
		this.currentMusic=musicName;
	}

	/**
	 * @return Die aktuell gespielte Partie
	 */
	public Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * @param currentGame Das Spiel welches als das aktuelle gesetzt werden soll
	 */
	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	/**
	 * @return Die Statistik der gespielten Idioten-Patience-Spiele
	 */
	public Statistic getIdiotStatistic() {
		return idiotStatistic;
	}

	/**
	 * @return Die Statistik der gespielten Freecell-Patience-Spiele
	 */
	public Statistic getFreecellStatistic() {
		return freecellStatistic;
	}

	/**
	 * @return Die Statistik der gespielten Zank-Patience-Spiele
	 */
	/**
	 * @return
	 */
	public Statistic getZankStatistic() {
		return zankStatistic;
	}

	/**
	 * @return Die Liste aller sich im Speicher befindlichen Partien
	 */
	public ArrayList<Game> getGameList() {
		return gameList;
	}

	public void setCustomStacks(Stack stack){
		customStacks.add(stack);
	}
	
	/**
	 * @return Die Liste aller auswählbaren Kartenreihenfolgen
	 */
	public ArrayList<Stack> getCustomStacks() {
		return customStacks;
	}
	/**
	 * entfernt den übergebenen Stack aus der Liste der vorgefertigten Stacks
	 * @param stack
	 */
	public void removeCustomStack(Stack stack){
		for(Stack currentStack : customStacks){
			if(currentStack.equals(stack)){
				customStacks.remove(stack);
			}
		}
	}
	
}
