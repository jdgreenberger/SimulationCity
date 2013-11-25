package market.test;

import application.Phonebook;
import junit.framework.TestCase;
import market.Market;
import market.MarketOrder.orderState;
import market.SalesPersonRole;
import market.MarketCustomerRole.MarketCustomerState;
import market.test.mock.MockMarketCustomer;
import market.test.mock.MockMarketRunner;
import market.test.mock.MockSalesPerson;
import person.Worker;
import restaurant.Restaurant;
import restaurant.test.mock.MockCook;

public class SalesPersonTest extends TestCase {

	Market market;
	Restaurant restaurant;
	Worker worker;
	SalesPersonRole salesPerson;
	MockMarketCustomer marketCustomer;
	MockMarketRunner marketRunner;
	MockCook cook;
	
	public void setUp() throws Exception {
		super.setUp();
		market = Phonebook.getPhonebook().getMarket();
		worker = new Worker("Worker", 50, "SalesPerson", "Market", 8, 12, 24);
		salesPerson = new SalesPersonRole(worker, "SalesPerson", "MarketSalesPerson", market);
		marketCustomer = new MockMarketCustomer("Mock Customer");
		marketRunner = (MockMarketRunner) Phonebook.getPhonebook().getMarket().getMarketRunner(true);
		salesPerson.test = true;
		
		restaurant = Phonebook.getPhonebook().getRestaurant();
//		cook = Phonebook.getPhonebook().getRestaurant().getCookRole();
	}


	public void testOneMarketCustomerPaymentNormativeScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Checking pre-conditions
		assertEquals("Sales Person shouldn't have any orders in it", salesPerson.orders.size(), 0);
		
		assertEquals("Sales Person should have an empty event log before the SalesPerson's msgIWantProducts is called. Instead, the SalesPerson's event log reads: "
				+ salesPerson.log.toString(), 0, salesPerson.log.size());
		
		//Step 1
		salesPerson.msgIWantProducts(marketCustomer, "Car", 1);
		
		//Checking post conditions for step 1
		assertEquals("Sales Person should have 1 order in it", salesPerson.orders.size(), 1);
		
		
		
		assertEquals("Sales Person should have 1 event in the log before the SalesPerson's msgIWantProducts is called. Instead, the SalesPerson's event log reads: "
				+ salesPerson.log.toString(), 1, salesPerson.log.size());
		
		//Step 2
		assertTrue("Sales Person's scheduler should have returned true (has an action to do on an Order from a Customer), but didn't.", salesPerson.pickAndExecuteAnAction());
		
		//Checking post condition for step 2
		assertEquals("Mock Market Runner should have 1 event in the log after Sales Person scheduler is called. Instead, the SalesPerson's event log reads: "
				+ marketRunner.log.toString(), 1, marketRunner.log.size());
		
		assertFalse("Sales Person's scheduler should have returned false because it has no action to do, but didn't.", salesPerson.pickAndExecuteAnAction());
		
		//Step 3
		salesPerson.msgOrderFulfilled(salesPerson.orders.get(0));
		
		//Checking post conditions for step 3
		assertEquals("Sales Person's orders list should still only have 1 order ", salesPerson.orders.size(), 1);
		
		assertEquals("Sales Person's order state should be ", salesPerson.orders.get(0).state, orderState.itemsFound);
		
		//Step 4
		assertTrue("Sales Person's scheduler should have returned true (has an action to do on an Order from a Market Runner) , but didn't.", salesPerson.pickAndExecuteAnAction());
		
		//Checking post conditions step 4
		assertEquals("MockMarketCustomer should have 1 event in the log after the scheduler is called. Instead, the MockCustomer's event log reads: "
				+ salesPerson.log.toString(), 1, salesPerson.log.size());
		
		assertEquals("Sales Person's orders list should still only have 1 order ", salesPerson.orders.size(), 1);
		
		assertEquals("Sales Person's order state should be ", salesPerson.orders.get(0).state, orderState.gaveToCustomer);
		
		//Step 5
		salesPerson.msgPayment(marketCustomer, Phonebook.getPhonebook().getMarket().marketItemsForSale.get("Car").price);
		
		//Checking post conditions of step 5
		assertEquals("Sales Person's orders list should have 0 orders", salesPerson.orders.size(), 0);
		
		assertFalse("Sales Person's scheduler should have returned flase (has no actions to do) , but didn't.", salesPerson.pickAndExecuteAnAction());
	} //testOneMarketCustomerPaymentNormativeScenerio
	
	public void testTwoBusinessPaymentNormativeScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void testThreeMarketCustomerAndBusinessPaymentScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testFourBusinessAndMarketCustomerPaymentScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
