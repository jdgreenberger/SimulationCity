package italianRestaurant;
import java.awt.Point;
import java.util.Vector;

import person.Person;
import person.Role;
import person.Worker;
import application.WatchTime;
import application.gui.animation.BuildingPanel;

public class ItalianRestaurant {

	//Data
	String name;
	public boolean userClosed = false;
	private Point closestPoint;


	//List of Waiters
	private Vector<ItalianWaiterRole> waiters = new Vector<ItalianWaiterRole>();

	//Open and closing times
	public WatchTime openTime = new WatchTime(11);
	public WatchTime closeTime = new WatchTime(21);

	//Roles
	public ItalianHostRole italianRestaurantHostRole = new ItalianHostRole("Host");
	public ItalianCookRole italianRestaurantCookRole = new ItalianCookRole("Cook", this);
	//public RestaurantCookGui cookGui = new RestaurantCookGui(italianRestaurantCookRole);

	public ItalianCashierRole italianRestaurantCashierRole = new ItalianCashierRole("Cashier", this);
	public ItalianRevolvingStand theRevolvingStand = new ItalianRevolvingStand();
	private BuildingPanel restPanel;

	//Mocks
	//public ItalianMockCook italianRestaurantMockCook = new ItalianMockCook("MockCook");
	//public ItalianMockCashier italianRestaurantMockCashier = new ItalianMockCashier("MockCashier");

	public ItalianRestaurant(String name) {
		this.name = name;
		//italianRestaurantCookRole.setGui(cookGui);
	}

	//Methods
	public Role arrivedAtWork(Person person, String title)  {

		if (title == "host") {
			//Setting previous bank guard role to inactive
			if (italianRestaurantHostRole.getPerson() != null) {
				Worker worker = (Worker) italianRestaurantHostRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting bank guard role to new role
			italianRestaurantHostRole.setPerson(person);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			return italianRestaurantHostRole;
		}
		else if (title == "cook") {
			//Setting previous bank guard role to inactive
			if (italianRestaurantCookRole.getPerson() != null) {
				Worker worker = (Worker) italianRestaurantCookRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting cook role to new role
			italianRestaurantCookRole.setPerson(person);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			//restPanel.addGui(cookGui);
			return italianRestaurantCookRole;
		}
		else if (title.contains("cashier")) {
			//Setting previous bank guard role to inactive
			if (italianRestaurantCashierRole.getPerson() != null) {
				Worker worker = (Worker) italianRestaurantCashierRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting cashier role to new role
			italianRestaurantCashierRole.setPerson(person);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			return italianRestaurantCashierRole;
		}
		else if (title == "waiter") {	
			ItalianWaiterRole waiter = new ItalianWaiterRole(person, person.getName(), title);
//			if (waiters.size() <= 12) {
//				//RestaurantWaiterGui g = new RestaurantWaiterGui(waiter);
//				//restPanel.addGui(g);
//				//waiter.setGui(g);
//				//g.setHomePosition(5, (55 + (22 * waiters.size())));
//			}
//			else if (waiters.size() <= 24) {
//				RestaurantWaiterGui g = new RestaurantWaiterGui(waiter);
//				restPanel.addGui(g);
//				waiter.setGui(g);
//				g.setHomePosition(27, (55 + (22 * (waiters.size()-12))));
//			}
//
//			waiters.add(waiter);
			italianRestaurantHostRole.addWaiter(waiter);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			return waiter;
		}
		else if (title == "altWaiter") {
/////////////ItalianAltWaiterRole altWaiter = new ItalianAltWaiterRole(person, person.getName(), title);
//			if (waiters.size() <= 12) {
//				RestaurantWaiterGui g = new RestaurantWaiterGui(altWaiter);
//				restPanel.addGui(g);
//				altWaiter.setGui(g);
//				g.setHomePosition(5, (55 + (22 * waiters.size())));
//			}
//			else if (waiters.size() <= 24) {
//				RestaurantWaiterGui g = new RestaurantWaiterGui(altWaiter);
//				restPanel.addGui(g);
//				altWaiter.setGui(g);
//				g.setHomePosition(27, (55 + (22 * (waiters.size()-12))));
//			}

/////////////waiters.add(altWaiter);
/////////////italianRestaurantHostRole.addWaiter(waiter);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
/////////////return altWaiter;
		}
		//for waiter and alternative waiters, you message the host
		return null;
	}

//	public boolean arrived(ItalianCustomerRole rCR) {
//		if (customers.size() <= 12) {
//			RestaurantCustomerGui rCG = (RestaurantCustomerGui) rCR.gui;
//			rCG.setHomePosition((22 * customers.size()), 10);
//			restPanel.addGui(rCG);
//			customers.add(rCR);
//			rCR.gotHungry((22 * customers.size()), 10);
//			return true;
//		}
//		else if (customers.size() <= 24) {
//			RestaurantCustomerGui rCG = (RestaurantCustomerGui) rCR.gui;
//			rCG.setHomePosition((22 * (customers.size() - 12)), 32);
//			restPanel.addGui(rCG);
//			customers.add(rCR);
//			rCR.gotHungry((22 * (customers.size() - 12)), 32);
//			return true;
//		}
//		return false;
//	}

	public void goingOffWork(Person person) {
		Worker worker = (Worker) person;

		if (worker.getWorkerRole().equals(italianRestaurantHostRole)) {
			italianRestaurantHostRole = null;
			//restPanel.removeGui(worker.getWorkerRole().gui);
		}
		if (worker.getWorkerRole().equals(italianRestaurantCashierRole)) {
			italianRestaurantCashierRole = null;
		}
		if (worker.getWorkerRole().equals(italianRestaurantCookRole)) {
			italianRestaurantCookRole = null;
			//restPanel.removeGui(cookGui);
		}
		//WAITERS AND ALT WAITERS
		//finish the "leave work" in Role.java 
		//make function in host to delete waiter
		//waiters have to finish duties before finishing waiter & no assignments
		//look at onBreak code to follow
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItalianRevolvingStand getRevolvingStand() {
		return theRevolvingStand;
	}

	public void setPanel(BuildingPanel panel) {
		restPanel = panel;
	}

	public ItalianCookRole getCook(boolean test) {
//		if (test) {
//			return italianRestaurantMockCook;
//		}
		return italianRestaurantCookRole;
	}

	public ItalianCashierRole getCashier(boolean test) {
//		if (test) {
//			return italianRestaurantMockCashier;
//		}
		return italianRestaurantCashierRole;
	}

	public boolean isOpen() {
		if (italianRestaurantHostRole.getPerson() != null && italianRestaurantHostRole.waiters.size() != 0 && italianRestaurantCookRole.getPerson() != null && italianRestaurantCashierRole != null && !userClosed)
			return true;
		else 
			return false;
	}

	public void setBuildingPanel (BuildingPanel buildingPanel) {
		restPanel = buildingPanel;
	}

	public void removeWaiter(ItalianWaiterRole italianRestaurantWaiterRole) {
		//waiters.remove(italianRestaurantWaiterRole);
		//restPanel.removeGui(italianRestaurantWaiterRole.gui);
	}

	public void removeCustomer(ItalianCustomerRole customerRole) {
		//customers.remove(customerRole);
		//restPanel.removeGui(customerRole.gui);
	}

	public void closeBuilding(){
		userClosed = true;
		italianRestaurantHostRole.msgLeaveRole();
		for (ItalianWaiterRole w1: waiters) {
			w1.msgLeaveRole();
			restPanel.removeGui(w1.gui);
		}
		italianRestaurantCookRole.msgLeaveRole();
		//restPanel.removeGui(cookGui);

		italianRestaurantCashierRole.msgLeaveRole();
	}

	public void setClosestStop(Point point) {
		closestStop = point;
	}
}

