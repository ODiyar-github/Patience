package exceptions;

public class SaveException extends Exception {
	public SaveException(){
		super("Es ist ein Fehler beim Speichern aufgetreten.");
	}
}