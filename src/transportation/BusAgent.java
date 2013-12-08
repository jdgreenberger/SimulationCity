package transportation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import person.Person;
import application.Phonebook;
import application.TimeManager;
import application.gui.animation.agentGui.BusGuiHorizontal;
import application.gui.animation.agentGui.BusGuiVertical;
import agent.Agent;

public abstract class BusAgent extends Agent{

	/*********************
	 ****** DATA *********
	 *********************/
	boolean needToDeposit;
	boolean ifHorizontal;
	BusGuiHorizontal guiH;
	BusGuiVertical guiV;
	String name;
	
	class busPassenger
	{
		Person passenger;
		int busStop;
		busPassenger(Person p, int bStop)
		{
			passenger = p;
			busStop = bStop;
		}
	}
	
	List<busPassenger> busPassengers = Collections.synchronizedList(new ArrayList<busPassenger>());
	List<Person> peopleAtBusStop = Collections.synchronizedList(new ArrayList<Person>());
	

	public BusAgent(String name) 
	{
		if(name.equals("Horizontal"))
		{
			ifHorizontal = true;
			guiH = new BusGuiHorizontal();
		}
		else if(name.equals("Vertical"))
		{
			ifHorizontal = false;
			guiV = new BusGuiVertical();
		}
	}
	
	/**** 
	 * Interaction to go -
	 * Person messaging bus stop and going to sleep with metaphor
	 * Bus arrives at bus stops and gets all waiting customers
	 * Messages all sleeping customers to wake up
	 * Customers responds by messaging bus I am getting on
	 * AmericanRestaurantCustomer goes to sleep, Gui disappears
	 * Bus arrives at next bus stop, messages all customers with that destination
	 * AmericanRestaurantCustomer wakes up, GUI reappears at that spot
	 * ***/

	/*********************
	 ***** MESSAGES ******
	 *********************/
	
	public void msgGettingOnBus(Person p, int bStop)
	{
		busPassengers.add(new busPassenger(p, bStop));
		stateChanged();
	}
	
	/*********************
	 ***** SCHEDULER *****
	 *********************/
	protected boolean pickAndExecuteAnAction() 
	{
		
		return false;
	}

	/*********************
	 ****** ACTIONS ******
	 *********************/
	
	private void tellPeopleWaiting()
	{
		
	}
	
	private void tellPeopleGetOff()
	{
		
	}
}