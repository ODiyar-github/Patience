package exceptions;

public class LoadException extends Exception {
	public LoadException(){
		super("Es ist ein Fehler beim Laden aufgetreten.");
	}
}