package person;

import java.util.concurrent.Semaphore;

import agent.StringUtil;
import application.gui.trace.AlertLog;
import application.gui.trace.AlertTag;
import application.gui.animation.agentGui.*;

public abstract class Role {

        public Person person = null;
        
        protected String roleName = null;
        protected String personName = null;
        
        public enum RoleState {active, inActive, waitingToExecute};

        protected Semaphore atDestination = new Semaphore(0,true);

        protected RoleState state = RoleState.inActive;
        protected boolean leaveRole = false;
        
        //Testing
        public boolean test;
        
        //For customer roles
        protected Role(Person person, String pName, String rName) {
                this.person = person;
                personName = pName;
                roleName = rName;
                state = RoleState.inActive;
        }
        
        //For business roles
        protected  Role(String rName) 
        {
                roleName = rName;
                state = RoleState.inActive;
        }
        
        public Person getPerson() {
        		return person;
        }
        
        public void setPerson(Person person) {
                this.person = person;
                if (person != null) {
                	personName = person.getName();
                }
        }
        
        protected void stateChanged() {
        //calls Person scheduler
                //do we just call the scheduler, or do we release the semaphore on stateChanged?
                person.stateChanged();
        }

    protected abstract boolean pickAndExecuteAnAction();
 
        public String getName() {
        	return person.getName();
        }
        
        public void setState(RoleState state) 
        {
                this.state = state;
        }
    
        public RoleState getState() 
        {
                return state;
        }

        public void setRoleInactive() 
        {
                this.state = RoleState.inActive;
        }
        
        public void setRoleActive()
        {
                this.state = RoleState.active;
        }
        
          /**
     * Print message
     */
    protected void print(String msg) 
    {
            //System.out.println(roleName + " " + getName() + " : " + msg);
           if(person == null)
           {
        	   AlertLog.getInstance().logError(AlertTag.GENERAL_CITY, roleName + " null", msg);
           }
    		else if (roleName.equals("Bank Customer") || roleName.equals("Bank Guard") 
                            || roleName.equals("Bank Teller") || roleName.equals("Loan Officer")
                            || roleName.contains("bankTeller"))
            {
            AlertLog.getInstance().logInfo(AlertTag.BANK, roleName + " " + getName(), msg);
            }
            else if (roleName.equals("Maintenance Worker"))
            {
            AlertLog.getInstance().logInfo(AlertTag.HOUSING, roleName + " " + getName(), msg);
            }
            else if (roleName.equals("Market Customer") || roleName.contains("MarketCustomer") || roleName.equals("Market Runner") 
                            || roleName.contains("MarketRunner") || roleName.equals("Sales Person") || roleName.contains("SalesPerson") || roleName.contains("Market") || roleName.equals("UPS Man"))
            {
            AlertLog.getInstance().logInfo(AlertTag.MARKET, roleName + " " + getName(), msg);
            }
            //Different Restaurants IMPLEMENT
            else if (roleName.equals("Alternative Waiter") || roleName.equals("Cashier")
                            || roleName.equals("Cook") || roleName.equals("Host") 
                            || roleName.equals("Restaurant Customer") || roleName.equals("waiter") )
            {
            AlertLog.getInstance().logInfo(AlertTag.RESTAURANT, roleName + " " + getName(), msg);
            }
            else
            {
            AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, roleName + " " + getName(), msg);       
            }
                    
    }

    /**
     * Print message with exception stack trace
     */
    protected void print(String msg, Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append("role ");
        sb.append(roleName);
        sb.append(": ");
        sb.append(msg);
        sb.append("\n");
        if (e != null) {
            sb.append(StringUtil.stackTraceString(e));
        }
        System.out.print(sb.toString());
    }
    
    public String getRoleName () {
            return roleName;
    }

    public void msgLeaveRole() 
    {
        leaveRole = true;
    }
    
    public RoleState getRoleState()
    {
    	return state;
    }

}