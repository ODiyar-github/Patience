package support;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Card;
import model.Stack;

public class StackTangleIdiot extends Rectangle {
	private Stack stack;
	private double stackWidth;
	private double stackHeight;
	private double stackXCoordinate;
	private double stackYCoordinate;
	
	public StackTangleIdiot(Stack stack)
	{
		this.stack=stack;
		this.setHeight(150);
		this.setWidth(100);
		this.setX(100 + stack.getCoordinateX()*150 );
		this.setY(100 + stack.getCoordinateY() );
		this.stackWidth=20;
		this.stackHeight=30;
		this.stackXCoordinate=35+this.getX();
		this.stackYCoordinate=35+this.getY();
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(1);
	}

	public Stack getStack()
	{
		return this.stack;
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
}
