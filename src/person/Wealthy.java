package person;

import person.Role.roleState;

class Wealthy extends Person 
{
    int eatTime1 = 10;
    int eatTime2 = 16;
    int bankTime = 10;
    int sleepTime = 22;
    boolean needToDeposit;

    //Messages
    void msgRentDue () 
    {
	    //roles.landlord.waitingToExecute;
	}
    
    public void updateTime(int newTime) 
    {
		if (newTime == eatTime1)
		{		
			//Need to turn Restaurant into a "Role"
			//RestaurantCustomer.state = roleState.waitingToExecute;
		}
		if (newTime == eatTime2)
		{
			//RestaurantCustomer.state = roleState.waitingToExecute;
		}
		if (newTime == sleepTime)
		{
		    //GoToSleep();
		}
    }
}