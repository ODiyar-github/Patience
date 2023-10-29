package support;

import javafx.scene.shape.Rectangle;
import model.Card;

public class Cardtangle extends Rectangle
{
	private Card card;
	private Stacktangle stacktangle;
	
	public Cardtangle(Card card)
	{
		this.setCard(card);
	}
	
	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	public Stacktangle getStacktangle()
	{
		return stacktangle;
	}

	public void setStacktangle(Stacktangle stacktangle)
	{
		this.stacktangle = stacktangle;
	}
}
