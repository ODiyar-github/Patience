package control;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

import exceptions.LoadException;
import exceptions.SaveException;
import model.Card;
import model.Patience;
import model.Rank;
import model.Stack;
import model.Suit;

public class IOController {
	
	public static final File SAVE_FILE = new File("patience.ser");

	private PatienceController patienceController;

	/**
	 *  
	 */
	
	public IOController(PatienceController patienceController) {
				this.patienceController=patienceController;
	}

	
	/**
	 *lädt ein Spiel  
	 * @throws LoadException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	
	public void load() throws LoadException{
		try
		{
			if(!SAVE_FILE.exists())
				return;	
			
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(SAVE_FILE));
			Patience patience= (Patience) stream.readObject();
			patienceController.setPatience(patience); 
	        stream.close();
	        
		}
		catch(Exception e)
		{			
			throw new LoadException();
		}

	}

	
	/**
	 * speichert das aktuelle Model
	 */
	
	public void save() throws SaveException {
		try
		{		
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE));
			stream.writeObject(patienceController.getPatience());
			stream.close();
		}
		catch(Exception e)
		{
			throw new SaveException();
		}

	}

	
	/**
	 * lädt einen Stapel aus einer übergebenen Datei, dieser wird als Talon der Patience gesetzt
	 */
	
	public static Stack loadCustomStack(String stackFilePath) {
		String everything = null;
		File stackFile = new File(stackFilePath);
		try(BufferedReader reader = new BufferedReader(new FileReader(stackFile))) {
		    StringBuilder stringBuilder = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        stringBuilder.append(line);
		        stringBuilder.append(System.lineSeparator());
		        line = reader.readLine();
		    }
		    everything = stringBuilder.toString();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		
		everything=everything.replaceAll("\r", "");
		everything=everything.replaceAll("\n", "");
		Stack stack = stringToStack(everything);
		return stack;
	}
	
	/**
	 * @author sopr095
	 * Wandelt den eingegebenen String in ein Suit enum.
	 * @param s	Der übergebene String.
	 * @return	Gibt das enum zurück
	 */
	public static Suit getSuit(String input)
	{
		HashMap<String, Suit> suitMap = new HashMap<String, Suit>();
		suitMap.put("Herz", Suit.HEART);
		suitMap.put("Kreuz", Suit.CLUB);
		suitMap.put("Pik", Suit.SPADE);
		suitMap.put("Karo", Suit.DIAMOND);
		return suitMap.get(input);
	}
	
	
	
	/**
	 * @author sopr095
	 * Wandelt den eigegebenen String in ein Rank enum.
	 * @param s Der übergebene String.
	 * @return	Gibt das enum zurück.
	 */
	public static Rank getRank(String input)
	{
		HashMap<String, Rank> rankMap = new HashMap<String, Rank>();
		rankMap.put("Ass", Rank.ACE);
		rankMap.put("2", Rank.TWO);
		rankMap.put("3", Rank.THREE);
		rankMap.put("4", Rank.FOUR);
		rankMap.put("5", Rank.FIVE);
		rankMap.put("6", Rank.SIX);
		rankMap.put("7", Rank.SEVEN);
		rankMap.put("8", Rank.EIGHT);
		rankMap.put("9", Rank.NINE);
		rankMap.put("10", Rank.TEN);
		rankMap.put("Bube", Rank.JACK);
		rankMap.put("Dame", Rank.QUEEN);
		rankMap.put("Koenig", Rank.KING);
		return rankMap.get(input);
	}
	
	/**
	 * @author sopr095
	 * erzeugt einen Stapel aus einem übergebenen String, der die Reihenfolge der Karten enthält
	 * @param stackString
	 * @return Stack
	 */	
	public static Stack stringToStack(String stackString) {
		String[] values;
		values = stackString.split(" ");
		Stack stack = new Stack("", 0, 0, 0);
		LinkedList<Card> stringCardList = new LinkedList<Card>();
		for(int i=values.length-1; i>=0; i--)
		{
			String[] suitNrank;
			suitNrank = values[i].split("-");
			
			Card card = new Card(getSuit(suitNrank[0]), getRank(suitNrank[1]));
			stringCardList.add(card);
		}
		stack.setCardList(stringCardList);
		return stack;
	}
}
