package support;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;
import model.Card;
import model.Freecell;
import model.Stack;

public class Stacktangle extends Rectangle
{
	private Stack stack;
	private double stackWidth;
	private double stackHeight;
	private double stackXCoordinate;
	private double stackYCoordinate;
	private ArrayList<Cardtangle> cardtangles;
	
	public Stacktangle(Stack stack)
	{
		setCardtangles(new ArrayList<Cardtangle>());
		//, 100, 150, 50 + stack.getCoordinateX() * 150, 100 + stack.getCoordinateY() * 200
		
		this.setStack(stack);
		this.setStackWidth(49);
		this.setStackHeight(getHeightForStack() - 100);
		this.setStackXCoordinate(75 + stack.getCoordinateX() * 150);
		this.setStackYCoordinate(175 + stack.getCoordinateY() * 200);
		
		this.setX(50 + stack.getCoordinateX() * 150);
		this.setY(150 + stack.getCoordinateY() * 200);
		this.setWidth(100);
		this.setHeight(getHeightForStack());
	}

	private double getHeightForStack()
	{
		int tempHeight = 150;
		
		if(stack.getArrPosition() >= Freecell.FIRST_STACK && stack.getArrPosition() <= Freecell.EIGTH_STACK)
		{
			for(Card card : stack.getCardList())
				tempHeight = tempHeight + 30;
			
			if(stack.getCardList().size() > 0)
				tempHeight = tempHeight - 30;
		}
		return tempHeight;	
	}
	
	public Stack getStack()
	{
		return stack;
	}

	public void setStack(Stack stack)
	{
		this.stack = stack;
	}

	public double getStackWidth()
	{
		return stackWidth;
	}

	public void setStackWidth(double stackWidth)
	{
		this.stackWidth = stackWidth;
	}

	public double getStackHeight()
	{
		return stackHeight;
	}

	public void setStackHeight(double stackHeight)
	{
		this.stackHeight = stackHeight;
	}

	public double getStackXCoordinate()
	{
		return stackXCoordinate;
	}

	public void setStackXCoordinate(double stackXCoordinate)
	{
		this.stackXCoordinate = stackXCoordinate;
	}

	public double getStackYCoordinate()
	{
		return stackYCoordinate;
	}

	public void setStackYCoordinate(double stackYCoordinate)
	{
		this.stackYCoordinate = stackYCoordinate;
	}

	public ArrayList<Cardtangle> getCardtangles()
	{
		return cardtangles;
	}

	public void setCardtangles(ArrayList<Cardtangle> cardtangles)
	{
		this.cardtangles = cardtangles;
	}
}
