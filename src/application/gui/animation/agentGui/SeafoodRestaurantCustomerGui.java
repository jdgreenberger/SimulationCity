package application.gui.animation.agentGui;

//import restaurant.CustomerAgent;
//import restaurant.WaiterAgent;
import seafoodRestaurant.SeafoodRestaurantCustomerRole;

import java.awt.*;

public class SeafoodRestaurantCustomerGui implements Gui{

	private SeafoodRestaurantCustomerRole role = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private AmericanRestaurantHostRole host;
	RestaurantGui gui;

	private int xCordCurrent, yCordCurrent;
	private final int height = 20, width = 20;
	private static final int xCordOffScreen = -40;
	private static final int yCordOffScreen = -40;
	private static int xCordLobby = 10;
	private static int yCordLobby;
	private static final int xCordCashier = 400;
	private static final int yCordCashier = 100;
	private int xDestination, yDestination;
	private int xCordPutOnTable = 20;
	private int yCordPutOnTable = 20;
	private enum Command {noCommand, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;
	private String myOrder = "";

    public static final int xTable[] = {0, 200, 300, 400, 200, 300, 400, 200, 300, 400};
    public static final int yTable[] = {0, 250, 250, 250, 350, 350, 350, 450, 450, 450};

	public SeafoodRestaurantCustomerGui(SeafoodRestaurantCustomerRole c, RestaurantGui gui, int n)
	{ //AmericanRestaurantWaiterRole m) {
		role = c;
		//xCordOffScreen, yCordOffScreen
		xCordCurrent = xCordOffScreen;
		yCordCurrent = yCordOffScreen;
		//height = 20;
		//width = 20;
		xDestination = xCordOffScreen;
		yDestination = yCordOffScreen;
		//maitreD = m;
		this.gui = gui;
        yCordLobby = width + (width * n);
	}

	public void updatePosition() 
	{
		if (xCordCurrent < xDestination)
			xCordCurrent++;
		else if (xCordCurrent > xDestination)
			xCordCurrent--;

		if (yCordCurrent < yDestination)
			yCordCurrent++;
		else if (yCordCurrent > yDestination)
			yCordCurrent--;

		
		
		if (xCordCurrent == xDestination && yCordCurrent == yDestination) 
		{
			if (command==Command.GoToSeat)
			{
				role.msgAnimationFinishedGoToSeat();
			}
			else if (command == Command.GoToCashier)
			{
				role.msgAnimationFinishedGoToCashier();
			}
			else if (command==Command.LeaveRestaurant) 
			{
				role.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				//?
				//gui.setCustomerEnabled(role);
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics2D g) 
	{
			g.setColor(Color.GREEN);
		//g.fillRect(xCordCurrent, yCordCurrent, height, width);
		g.fillOval(xCordCurrent, yCordCurrent, height, width);
		g.setColor(Color.RED);
		g.drawString(myOrder, xCordCurrent + xCordPutOnTable, yCordCurrent + yCordPutOnTable);
	}

	public boolean isPresent() 
	{
		return isPresent;
	}
	public void setHungry() 
	{
		isHungry = true;
		role.gotHungry();
		setPresent(true);
	}
	public boolean isHungry() 
	{
		return isHungry;
	}

	public void setPresent(boolean p) 
	{
		isPresent = p;
	}

	public void DoGoToSeat(int seatnumber) 
	{//later you will map seatnumber to table coordinates.
		xDestination = xTable[seatnumber];
		yDestination = yTable[seatnumber];
		command = Command.GoToSeat;
		//System.out.println("Moving to seat: " + seatnumber);
	}

	public void DoOrder(String order)
	{
		myOrder = order;
	}
    
    public void GoToCashier()
    {
    	xDestination = xCordCashier;
    	yDestination = yCordCashier;
		command = Command.GoToCashier;
    }
	
    public void DoEnterRestaurant()
    {
    	xDestination = xCordLobby;
    	yDestination = yCordLobby;
    }
    
	public void DoExit() 
	{
		//xCordOffscreen = -40;
		//yCordOffscreen = -40;
		xDestination = xCordOffScreen;
		yDestination = yCordOffScreen;
		//System.out.println("XCord is " + xCordOffscreen + " YCord is " + yCordOffscreen);
		//xDestination = xCordOffscreen;
		//yDestination = yCordOffscreen;
		command = Command.LeaveRestaurant;
	}
	
    public int getXPos() {
    	return xCordCurrent;
    }
    public int getYPos() {
    	return yCordCurrent;
    }
}
