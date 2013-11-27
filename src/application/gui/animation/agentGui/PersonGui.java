package application.gui.animation.agentGui;

import person.*;

import java.awt.*;
import java.util.Random;

import javax.swing.JLabel;

public class PersonGui extends CityGui {

	private Person agent = null;
	private boolean isHungry = false;

	public boolean raveMode = false;
	
	Random rand = new Random();
	int red = rand.nextInt(255);
	int blue  = rand.nextInt(255);
	int green  = rand.nextInt(255);
	Color myColor = new Color(red, blue, green);
	Color transColor = new Color(0,0,0,1);
	Color currColor;

	private int xRestaurant1Location = 300 + 15;
	private int yRestaurant1Location = 20 + 10;
	//This is going to be used for future restaurants
	//	private int xRestaurant2Location;
	//	private int yRestaurant2Location;
	//	private int xRestaurant3Location;
	//	private int yRestaurant3Location;
	//	private int xRestaurant4Location;
	//	private int yRestaurant4Location;
	//	private int xRestaurant5Location;
	//	private int yRestaurant5Location;
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
		yDestination = 125;
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
			yDestination = 25;
		}
		if(p instanceof Wealthy) {
			setxPos(0);
			setyPos(125);
			setxHome(0);
			yHome = 125;
			setxDestination(25);
			yDestination = 125;
		}
		if(p instanceof Crook) {
			setxPos(280);
			setyPos(250);
			setxHome(280);
			yHome = 250;
			setxDestination(300);
			yDestination = 250;
		}
		if(p instanceof Deadbeat) {
			setxPos(280);
			setyPos(250);
			setxHome(280);
			yHome = 250;
			setxDestination(300);
			yDestination = 250;
		}
		setDefaultColor();
		//this.gui = gui;
	}

	public void updatePosition() {
		if (getxPos() < getxDestination())
			setxPos(getxPos() + 1);
		else if (getxPos() > getxDestination())
			setxPos(getxPos() - 1);

		if (getyPos() < yDestination)
			setyPos(getyPos() + 1);
		else if (getyPos() > yDestination)
			setyPos(getyPos() - 1);

		if (getxPos() == getxDestination() && getyPos() == yDestination) {
			//System.out.println(command + "  " + agent.getName() + "has semaphore permits = " + agent.getAtDestination().availablePermits());

			if(agent != null) {
				if (command == Command.GoToRestaurant && getxPos() == xRestaurant1Location && getyPos() == yRestaurant1Location) {
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
		yDestination = yHome;
	}

	public boolean isHungry() {
		return isHungry;
	}

	//Actions
	public void DoGoToRestaurant() {//later you will map building to map coordinates.
		setxDestination(xRestaurant1Location);
		yDestination = yRestaurant1Location;
		setDefaultColor();
		command = Command.GoToRestaurant;
	}

	public void DoGoToMarket() {//later you will map building to map coordinates.
		setxDestination(xMarketLocation);
		yDestination = yMarketLocation;
		setDefaultColor();
		command = Command.GoToMarket;
	}

	public void DoGoToBank() {//later you will map building to map coordinates.		
		setxDestination(xBankLocation);
		yDestination = yBankLocation;
		setDefaultColor();
		command = Command.GoToBank;
	}

	public void DoGoToBusStop(int stopNum) {//later you will map stop number to map coordinates.
		setxDestination(100);
		yDestination = 100;
	}

	public void DoGoHome() { //the person's assigned home number. Maybe use coordinates instead?
		setxDestination(getxHome());
		yDestination = yHome;
		setDefaultColor();
		command = Command.GoHome;
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
}
