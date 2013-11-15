package person;

import agent.StringUtil;

public abstract class Role {

	protected Person person;
	
	enum roleState {active, inActive, waitingToExecute}
	private roleState state = roleState.inActive;
	
	protected Role() {
	}
	
	protected Role(Person person) {
		this.person = person;
	}
	
	protected void stateChanged() {
        //calls Person scheduler
		//do we just call the scheduler, or do we release the semaphore on stateChanged?
		person.pickAndExecuteAnAction();
    }

    protected abstract boolean pickAndExecuteAnAction();
    
    protected void inactivateRole() {
    	setState(roleState.inActive);
    	stateChanged();
    }

	public roleState getState() {
		return state;
	}

	public void setState(roleState state) {
		this.state = state;
	}
	
	  /**
     * Print message
     */
    protected void print(String msg) {
        print(msg, null);
    }

    /**
     * Print message with exception stack trace
     */
    protected void print(String msg, Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append(getName());
        sb.append(": ");
        sb.append(msg);
        sb.append("\n");
        if (e != null) {
            sb.append(StringUtil.stackTraceString(e));
        }
        System.out.print(sb.toString());
    }

}
