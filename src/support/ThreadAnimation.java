package support;

import control.PatienceController;
import javafx.application.Platform;
import view.BoardAUI;
import view.game.IdiotController;

public class ThreadAnimation implements Runnable 
{
	private int time;
	private IdiotController idiotController;
	
	public ThreadAnimation(int time, IdiotController idiotController){
		this.time=time;
		this.idiotController=idiotController;
	}
	
	@Override
	synchronized public void run() {
	    Platform.runLater(() -> {
	    	System.out.println("TESTTTTT");
	        try {
	        	//wait(time);
	        	Thread.sleep(time);
	        	this.idiotController.lock(true);
	        	
	        } catch (Exception e) {
	        	System.out.println("Fehler beim Thread");
	        }
	    });

		
	}
}
