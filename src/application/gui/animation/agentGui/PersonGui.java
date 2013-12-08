package application.gui.animation.agentGui;

import person.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;

import application.Phonebook;

public class PersonGui extends CityGui {
	
	private final int WINDOWX = 600;
	private final int WINDOWY = 325;
	
	private Person agent = null;
	private boolean isHungry = false;
	public boolean walk = true;

	public boolean raveMode = false;

	Random rand = new Random();
	int red = rand.nextInt(255);
	int blue  = rand.nextInt(255);
	int green  = rand.nextInt(255);
	Color myColor = new Color(red, blue, green);
	Color transColor = new Color(0,0,0,1);
	Color currColor;

	private int xChineseLocation = WINDOWX/2 + 15;
	private int yChineseLocation = 20 + 10;
	//This is going to be used for future restaurants
	private int xItalianLocation = (WINDOWX-100) + 15;
	private int yItalianLocation = 20+10;
	private int xMexicanLocation = (WINDOWX/2-75) + 15;
	private int yMexicanLocation = (WINDOWY - 75) + 10;
	private int xAmericanLocation = (WINDOWX - 100) + 15;
	private int yAmericanLocation = (WINDOWY - 75) + 10;
	private int xSeafoodLocation = (WINDOWX-100) + 15;
	private int ySeafoodLocation = 150 + 10; //?
	
	private int xMarketLocation = 500 + 25;
	private int yMarketLocation = 100 + 25;
	private int xBankLocation = 300 + 30;
	private int yBankLocation = 230 + 30;
	

	private int xPos, yPos;//default person position
	private int xDestination, yDestination;//default start position
	private int xHome, yHome;
	private enum Command {noCommand, GoToRestaurant, GoToMarket, GoToBank, GoToBusStop, GoOnBus, GoHome};
	private Command command = Command.noCommand;

	private enum PersonState {nothing};
	PersonState state = PersonState.nothing;

	private String choice;
	public PersonGui() {
		setxPos(0);
		setyPos(125);
		setxHome(0);
		yHome = 125;
		setxDestination(25);
		setyDestination(125);
		setDefaultColor();
	}

	public PersonGui(Person p) {
		this.agent = p;
		if(p instanceof Worker) {
			setxPos(0);
			setyPos(25);
			setxHome(0);
			yHome = 25;
			setxDestination(25);
			setyDestination(25);
		}
		if(p instanceof Wealthy) {
			setxPos(0);
			setyPos(125);
			setxHome(0);
			yHome = 125;
			setxDestination(25);
			setyDestination(125);
		}
		if(p instanceof Crook) {
			setxPos(280);
			setyPos(250);
			setxHome(280);
			yHome = 250;
			setxDestination(300);
			setyDestination(250);
		}
		if(p instanceof Deadbeat) {
			setxPos(280);
			setyPos(250);
			setxHome(280);
			yHome = 250;
			setxDestination(300);
			setyDestination(250);
		}
		setDefaultColor();
		//this.gui = gui;
	}

	public void updatePosition() {
		//if (!inBusyIntersection()) {
		if (true){
			if (getxPos() < getxDestination())
				setxPos(getxPos() + 1);
			else if (getxPos() > getxDestination())
				setxPos(getxPos() - 1);

			if (getyPos() < getyDestination())
				setyPos(getyPos() + 1);
			else if (getyPos() > getyDestination())
				setyPos(getyPos() - 1);
		}

		if (getxPos() == getxDestination() && getyPos() == getyDestination()) {
			//System.out.println(command + "  " + agent.getName() + "has semaphore permits = " + agent.getAtDestination().availablePermits());

			if(agent != null) {
				if (command == Command.GoToRestaurant) {
					agent.msgAtDestination();
					currColor = transColor;
				}
				if (command == Command.GoToMarket && getxPos() == xMarketLocation && getyPos() == yMarketLocation) {
					agent.msgAtDestination();
					currColor = transColor;
				}
				if (command == Command.GoToBank && getxPos() == xBankLocation && getyPos() == yBankLocation) {
					agent.msgAtDestination();
					currColor = transColor;
				}
				if (command == Command.GoHome && getxPos() == getxHome() && getyPos() == yHome) {
					agent.msgAtDestination();
					currColor = transColor;
				}
				if (command == Command.GoToBusStop && getxPos() == xDestination && getyPos() == getyDestination()) {
					agent.msgAtDestination();
					System.out.println("Reached bus stop");
					currColor = transColor;
				}
				command = Command.noCommand;
			}

		}
	}

	public void draw(Graphics2D g) {

		if (raveMode){
			Random rand = new Random();
			int red = rand.nextInt(255);
			int blue  = rand.nextInt(255);
			int green  = rand.nextInt(255);
			Color myColor = new Color(red, blue, green);
			g.setColor(myColor);
		}
		else if (!raveMode)
			g.setColor(currColor);

		g.fillRect(getxPos(), getyPos(), 20, 20);
		if(currColor != transColor)
			g.setColor(Color.WHITE);
		if(agent != null) {
			g.drawString(agent.getName(), getxPos(), getyPos());
		}
		else
			g.drawString("testGui", getxPos(), getyPos());
	}

	public void setHungry() {
		isHungry = true;
		setPresent(true);
		setxDestination(getxHome());
		setyDestination(yHome);
	}

	public boolean isHungry() {
		return isHungry;
	}

	//Actions
	public void DoGoToRestaurant(String restaurantType) {//later you will map building to map coordinates.
		switch(restaurantType.toLowerCase()) {
			case "chinese" : {
				setxDestination(xChineseLocation);
				setyDestination(yChineseLocation);
			}
			break;
			case "italian" : {
				setxDestination(xItalianLocation);
				setyDestination(yItalianLocation);
			}
			break;
			case "mexican" : {
				setxDestination(xMexicanLocation);
				setyDestination(xMexicanLocation);
			}
			break;
			case "american" : {
				setxDestination(xAmericanLocation);
				setyDestination(yAmericanLocation);
			}
			break;
			case "seafood" : {
				setxDestination(xSeafoodLocation);
				setyDestination(ySeafoodLocation);
			}
			break;
			default:
			break;
		}
		setDefaultColor();
		command = Command.GoToRestaurant;
	}

	public void DoGoToMarket() {//later you will map building to map coordinates.
		setxDestination(xMarketLocation);
		setyDestination(yMarketLocation);
		setDefaultColor();
		command = Command.GoToMarket;
	}

	public void DoGoToBank() {//later you will map building to map coordinates.		
		setxDestination(xBankLocation);
		setyDestination(yBankLocation);
		setDefaultColor();
		command = Command.GoToBank;
	}

	public void DoGoToBusStop(int stopNum) {//later you will map stop number to map coordinates.
		setxDestination(100);
		setyDestination(100);
	}

	public void DoGoHome() { //the person's assigned home number. Maybe use coordinates instead?
		setxDestination(getxHome());
		setyDestination(yHome);
		setDefaultColor();
		command = Command.GoHome;
	}

	public void doGoToBus() {
		System.out.println("Going to bus stop");
		if (xPos <= 170){
			if (yPos <= 30){
				xDestination = (int) Phonebook.getPhonebook().getBusStops().get(0).getX();
				setyDestination((int) Phonebook.getPhonebook().getBusStops().get(0).getY());
			}
			else {
				xDestination = (int) Phonebook.getPhonebook().getBusStops().get(1).getX();
				setyDestination((int) Phonebook.getPhonebook().getBusStops().get(1).getY());
			}	
		}
		else if (xPos > 170) {
			if (yPos <= 30){
				xDestination = (int) Phonebook.getPhonebook().getBusStops().get(2).getX();
				setyDestination((int) Phonebook.getPhonebook().getBusStops().get(2).getY());
			}
			else {
				xDestination = (int) Phonebook.getPhonebook().getBusStops().get(3).getX();
				setyDestination((int) Phonebook.getPhonebook().getBusStops().get(3).getY());
			}
		}	
		setDefaultColor();
		command = Command.GoToBusStop;
	}

	public boolean decideForBus(int xDest, int yDest) {
		if ((xDest - xPos >= 50) || (yDest - yPos >= 50)){
			return true;
		}
		else{
			return false;
		}
	}

	public void setHomeLocation(int x, int y) {
		setxHome(x);
		yHome = y;
	}

	public String toString() {
		return "Person Gui";
	}

	public int getxDestination() {
		return xDestination;
	}

	public void setxDestination(int xDestination) {
		this.xDestination = xDestination;
	}

	public int getxHome() {
		return xHome;
	}

	public void setxHome(int xHome) {
		this.xHome = xHome;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getyHome() {
		return yHome;
	}

	public void setRaveMode() {
		if (raveMode) {
			raveMode = false;
		}
		else
			raveMode = true;
	}

	public void setDefaultColor() {
		if (raveMode) {
			Random rand = new Random();
			int red = rand.nextInt(255);
			int blue  = rand.nextInt(255);
			int green  = rand.nextInt(255);
			currColor = new Color (red, blue, green);
		}
		else
			currColor = myColor;
	}

	public int getyDestination() {
		return yDestination;
	}

	public void setyDestination(int yDestination) {
		this.yDestination = yDestination;
	}

//	public boolean inBusyIntersection() {
//		if (Phonebook.getPhonebook().intersection1.getIntersection().contains(xPos, yPos)) {
//			if (Phonebook.getPhonebook().intersection1.isIntersectionBusy() == true &&
//					!this.equals(Phonebook.getPhonebook().intersection1.getInhabitant())) {
//				return  true;
//			}
//			return false;
//		}
//		else if (Phonebook.getPhonebook().intersection2.getIntersection().contains(xPos, yPos)) {
//			if (Phonebook.getPhonebook().intersection2.isIntersectionBusy() == true &&
//					!this.equals(Phonebook.getPhonebook().intersection2.getInhabitant())) {
//				return  true;
//			}
//			return false;
//		}
//		else if (Phonebook.getPhonebook().intersection3.getIntersection().contains(xPos, yPos)) {
//			if (Phonebook.getPhonebook().intersection3.isIntersectionBusy() == true &&
//					!this.equals(Phonebook.getPhonebook().intersection3.getInhabitant())) {
//				return  true;
//			}
//			return false;
//		}
//		else if (Phonebook.getPhonebook().intersection4.getIntersection().contains(xPos, yPos)) {
//			if (Phonebook.getPhonebook().intersection4.isIntersectionBusy() == true &&
//					!this.equals(Phonebook.getPhonebook().intersection4.getInhabitant())) {
//				return  true;
//			}
//			return false;
//		}
//		else {
//			return false;
//		}
//	}
}





