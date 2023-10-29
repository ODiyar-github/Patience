package control;
import java.util.ArrayList;
import javafx.scene.image.Image;

import java.util.Collection;

import model.Stack;
import view.SettingsAUI;

public class SettingsController {

	private PatienceController patienceController;
	
	private Collection<SettingsAUI> settingsAUI;

	
	public SettingsController(PatienceController patienceController) {
		this.patienceController=patienceController;
	}
	
	/**
	 * gibt die Liste der auswählbaren Musik-Dateien zurück
	 * @return musicList
	 */

	public ArrayList<String> getMusicList() {
		return patienceController.getPatience().getMusicList();
	}

	
	/**
	 * gibt die Liste der auswählbaren vorgefertigten Zieh-Stapel zurück
	 * @return stackList
	 */
	
	public ArrayList<Stack> getCustomStacks() {
		return patienceController.getPatience().getCustomStacks();
	}

	
	/**
	 * setzt die Musik auf die übergebene Musik-Datei 
	 */
	
	public void setCurrentMusic(String musicName) {
		patienceController.getPatience().setCurrentMusic(musicName);
	}

	
	/**
	 * gibt die aktuelle Musik-Datei zurück
	 * @return music
	 */
	
	public String getCurrentMusic() {
		return patienceController.getPatience().getCurrentMusic();
	}

	
	/**
	 * gibt die Liste der auswählbaren Kartenrückseiten zurück
	 * @return imageList
	 */
	
	public ArrayList<String> getCardBackList() {
		return patienceController.getPatience().getCardBackList();
	}

	
	/**
	 * setzt die aktuelle Kartenrückseite auf das übergebene Bild
	 */
	public void setCurrentCardBack(String cardBack) {
		patienceController.getPatience().setCurrentCardBack(cardBack);
	}
	
	/**
	 * gibt die aktuelle Kartenrückseite zurück
	 * @return currentCardBackName
	 */
	
	public String getCurrentCardBack() {
		return patienceController.getPatience().getCurrentCardBack();
	}

	
	/**
	 * aktualisiert die Einstellungen 
	 */
	
	void callRefreshSettings() {

	}

	
	/**
	 * fügt eine SettingsAUI hinzu
	 * @param settingsAUI
	 */
	
	public void addSettingsAUI(SettingsAUI settingsAUI) {

	}

	
	/**
	 * fügt eine vorgefertigten Ziehstapekl hinzu
	 * @param customStack
	 */
	public void addCustomStack(Stack customStack) {
		this.patienceController.getPatience().setCustomStacks(customStack);
	}

	
	/**
	 * löscht den übergebenen Stapel aus der Liste der vorgefertigten Ziehstapel
	 * @param customStack
	 */	
	public void removeCustomStack(Stack customStack) {
		patienceController.getPatience().removeCustomStack(customStack);
	}

	
	//wird diese Methode benötigt?
	public Collection<SettingsAUI> getSettingsAUI() {
		return settingsAUI;
	}

	public void setSettingsAUI(Collection<SettingsAUI> settingsAUI) {
		this.settingsAUI = settingsAUI;
	}

}
