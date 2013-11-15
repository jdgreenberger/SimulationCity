package restaurant.interfaces;

import restaurant.HostAgent;
import restaurant.Menu;
import restaurant.WaiterAgent;

/**
 * A sample Customer interface built to unit test a CashierAgent.
 *
 * @author Kristi Hupka
 *
 */
public interface Customer {

	public abstract void setHost(HostAgent host);

	public abstract String getCustomerName();
	
	public abstract String getName();



	/**
	 * Messages
	 */
	public abstract void gotHungry(int xHome, int yHome);

	public abstract void msgTablesAreFull();
	
	public abstract void msgPleaseFollowMe(int tableNumber, Menu menu, WaiterAgent waiter);

	public abstract void msgAnimationFinishedGoToSeat();

	public abstract void msgWhatWouldYouLike();

	public abstract void msgPleaseReorder(Menu newMenu);

	public abstract void msgHeresYourOrder(String choice);

	public abstract void msgHeresYourCheck(double check);
	
	public abstract void msgAnimationFinishedGoToCashier();

	public abstract void msgHeresYourChange(double change);
	
	public abstract void msgGoToJail();

	public abstract void msgAnimationFinishedLeaveRestaurant();
}