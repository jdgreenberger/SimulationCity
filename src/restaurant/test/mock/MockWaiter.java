package restaurant.test.mock;

import restaurant.interfaces.Customer;


import restaurant.interfaces.Cashier;
import restaurant.interfaces.Cook;
import restaurant.interfaces.Host;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Waiter;
import restaurant.WaiterRole;

/**
 * A sample MockWaiter built to unit test a CashierAgent.
 *
 * @author Kristi Hupka
 *
 */
public class MockWaiter extends Mock {

	public MockWaiter(String name) {
		super(name);
	}

	public void msgAtDestination() {
		// TODO Auto-generated method stub
		
	}

	
	public void msgIsInLobby() {
		// TODO Auto-generated method stub
		
	}

	
	public void msgReadyToOrder(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	
	public void msgHeresMyOrder(Customer customer, String choice) {
		// TODO Auto-generated method stub
		
	}

	
	public void msgOrderIsNotAvailable(String choice, int tableNumber) {
		// TODO Auto-generated method stub
		
	}


	public void msgOrderIsReady(int table, String choice) {
		// TODO Auto-generated method stub
		
	}


	public void msgIWantMyCheck(Customer cust) {
		// TODO Auto-generated method stub
		
	}

	
	public void msgLeavingTable(Customer cust) {
		// TODO Auto-generated method stub
		
	}


	public void msgWantToGoOnBreak() {
		// TODO Auto-generated method stub
		
	}


	public void msgPermissionToBreak(boolean reply) {
		// TODO Auto-generated method stub
		
	}


	public void msgGoOffBreak() {
		// TODO Auto-generated method stub
		
	}

	
	public void msgHereIsCheck(int tableNumber, double checkAmount) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received HereIsCheck from cashier. Total = " + checkAmount));
	}


	public void setHost(Host host) {
		// TODO Auto-generated method stub
		
	}

	public void setCook(Cook cook) {
		// TODO Auto-generated method stub
		
	}

	public void setCashier(Cashier cashier) {
		// TODO Auto-generated method stub
		
	}

	public boolean isOnBreak() {
		// TODO Auto-generated method stub
		return false;
	}


	public void msgPleaseSeatCustomer(int tableNumber, Customer customer, int xHome, int yHome) {
		// TODO Auto-generated method stub
		
	}

}
