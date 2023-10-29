package support;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Card;
import model.Freecell;
import model.Stack;
import model.Zank;

public class StacktangleZank extends Rectangle
{
	private Stack stack;
	private double stackWidth;
	private double stackHeight;
	private double stackXCoordinate;
	private double stackYCoordinate;
	private double otherX;
	
	
	public StacktangleZank(Stack stack)
	{
		this.setStack(stack);
		
		this.setX(360 + stack.getCoordinateX() * 150);
		this.setOtherX(360 + stack.getCoordinateX() * 150);
		
		if(stack.getArrPosition() >= Zank.FIRST_OUTER_STACK && stack.getArrPosition() <= Zank.FOURTH_OUTER_STACK){
			this.setX(this.getX() - 30 * stack.getCardList().size());
			
			if(!stack.getCardList().isEmpty())
				this.setX(this.getX() + 30);
		}
		
		this.setY(100 + stack.getCoordinateY() * 155);
		this.setWidth(getWidthForStack());
		this.setHeight(150);
		
		this.setStackWidth(getWidthForStack() - 50);
		this.setStackHeight(2);
		this.setStackXCoordinate(this.getX() + 20);
		this.setStackYCoordinate(this.getY() + 65);	
	}
	
	private double getWidthForStack()
	{
		int tempWidth = 100;
		
		if(stack.getArrPosition() >= Zank.FIRST_OUTER_STACK && stack.getArrPosition() <= Zank.EIGHTH_OUTER_STACK)
		{
			tempWidth += 30 * stack.getCardList().size();
			
			if(!stack.getCardList().isEmpty())
				tempWidth = tempWidth - 30;
		}
		return tempWidth;	
	}

	public Stack getStack() {
		return stack;
	}

	public void setStack(Stack stack) {
		this.stack = stack;
	}

	public double getStackWidth() {
		return stackWidth;
	}

	public void setStackWidth(double stackWidth) {
		this.stackWidth = stackWidth;
	}

	public double getStackHeight() {
		return stackHeight;
	}

	public void setStackHeight(double stackHeight) {
		this.stackHeight = stackHeight;
	}

	public double getStackXCoordinate() {
		return stackXCoordinate;
	}

	public void setStackXCoordinate(double stackXCoordinate) {
		this.stackXCoordinate = stackXCoordinate;
	}

	public double getStackYCoordinate() {
		return stackYCoordinate;
	}

	public void setStackYCoordinate(double stackYCoordinate) {
		this.stackYCoordinate = stackYCoordinate;
	}
	
	public void setOtherX(double otherX){
		this.otherX = otherX;
	}
	
	public double getOtherX(){
		return otherX;
	}
}
