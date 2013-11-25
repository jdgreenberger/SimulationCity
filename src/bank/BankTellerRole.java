package bank;

import java.util.ArrayList;
import java.util.List;

import person.Person;
import person.Role;
import person.Worker;
import application.Phonebook;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankTeller;

public class BankTellerRole extends Role implements BankTeller {

	//DATA
	protected String RoleName = "Bank Teller";
	int balanceMinimum = 5;
	String name;
	List<Account> myAccounts;		//A list of the accounts that only this teller will handle

	public enum AccountState {neutral, newAccount, waiting, depositing, withdrawing, requestingLoan, 
		closingLoan, loanApproved, loanDenied, bankEmpty, leavingBank}

	static public class Account {	
		private BankCustomer customer;
		public int accountNum; 		//the hash key
		public double loan = 0;
		public double balance = 0;
		public double creditScore = 150;
		public double processingMoney = 0;
		AccountState state;

		public Account (BankCustomer c1) {
			setCustomer(c1);
		}

		public void setAccountNum (int n){
			accountNum = n;
		}

		public BankCustomer getCustomer() {
			return customer;
		}

		public void setCustomer(BankCustomer customer) {
			this.customer = customer;
		}

		public void setCredit (double cred){
			creditScore = cred;
		}

		public void setProcessingMoney (double m) {
			processingMoney = m;
		}

		public void setState(AccountState s1) {
			state = s1;
		}

		public AccountState getState() {
			return state;
		}

		public int getAccountNum() {
			return accountNum;
		}
	}

	public BankTellerRole (String name, Person p1, String roleName) {
		super(p1, name, roleName);
		myAccounts = new ArrayList<>();
	}

	public BankTellerRole(String roleName) {
		super(roleName);
		myAccounts = new ArrayList<>();
	}

	//MESSAGES

	public void msgWantNewAccount (BankCustomer cust1) {	
		print("Customer wants new account");
		Account a1 = new Account(cust1);
		Phonebook.getPhonebook().getBank().accounts.add(a1);
		a1.setState(AccountState.newAccount);
		myAccounts.add(a1);
		stateChanged();
	}

	public void msgINeedMoney(double desiredAmount, int accountNum) {
		print("Customer needs money");
		Account correct = findMyAccount (accountNum);
		correct.processingMoney = desiredAmount;
		correct.state = AccountState.withdrawing;
		stateChanged();
	}

	public void msgHereIsMyDeposit(double amount, int accountNum) {
		print("Customer wants to deposit");
		Account correct = findMyAccount (accountNum);
		correct.processingMoney = amount;
		correct.state = AccountState.depositing;
		stateChanged();
	}

	public void msgINeedALoan(double desiredLoan, int accountNum) {
		Account correct = findMyAccount (accountNum);
		correct.processingMoney = desiredLoan;
		correct.state = AccountState.requestingLoan;
		print("Customer requesting loan of $" + correct.processingMoney);
		stateChanged();
	}

	public void msgPayingOffLoan(double loan, int accountNum) {
		Account correct = findMyAccount (accountNum);
		correct.processingMoney = loan;
		correct.state = AccountState.closingLoan;
		stateChanged();
	}

	public void msgThisLoanApproved(Account account1) {
		print("Loan approved.");
		account1.state = AccountState.loanApproved;
		stateChanged();
	}

	public void msgThisLoanDenied (Account account1, double possibleLoan) {
		print("Loan denied.");
		account1.state = AccountState.loanDenied;
		account1.processingMoney = possibleLoan;
		stateChanged();
	}

	public void msgLeavingBank(int accountNum) {
		Account correct = findMyAccount (accountNum);
		myAccounts.remove(correct);
	}

	//Scheduler

	public boolean pickAndExecuteAnAction() {

		for (Account account1: myAccounts) {

			if (account1.state == AccountState.newAccount)	{
				openAccount(account1);
				return false;
			}

			if (account1.state == AccountState.withdrawing)	{
				withdrawMoney(account1);
				return false;
			}

			if (account1.state == AccountState.depositing)	{
				depositMoney(account1);
				return false;
			}

			if (account1.state == AccountState.requestingLoan)	{
				requestLoan(account1);
				return false;
			}

			if (account1.state == AccountState.closingLoan)	{
				closeLoan(account1);
				return false;
			}

			if (account1.state == AccountState.loanApproved) {	
				approveLoan(account1);
				return false;
			}

			if (account1.state == AccountState.loanDenied)	{
				denyLoan(account1);
				return false;
			}

			if (account1.state == AccountState.closingLoan)	{
				closeLoan(account1);
				return false;
			}

		}

		if (leaveRole){
			Worker myself = (Worker) person;
			myself.roleFinishedWork();
			leaveRole = false;
			return true;
		}

		return false;
	}


	//Actions
	Account findMyAccount (int accNum) {
		for (Account a: myAccounts) {
			if (a.getAccountNum() == accNum) {
				return a;		
			}
		}
		//If can't find the account in my current set of accounts, look in the global list
		return findPhonebookAccount(accNum);
	}

	Account findPhonebookAccount (int accNum) {
		synchronized(Phonebook.getPhonebook().getBank().accounts) {
			for (Account a: Phonebook.getPhonebook().getBank().accounts) {
				if (a.getAccountNum() == accNum) {
					myAccounts.add(a);		//Anytime we must search through the global list, 		
					return a;						//we need add account to the local one for easy future access
				}
			}
		}
		return null;
	}



	void openAccount (Account account1) {
		int hashKey = ++Phonebook.getPhonebook().getBank().accountNumKeyList;
		account1.setAccountNum(hashKey);
		account1.getCustomer().msgHereIsNewAccount(account1.getAccountNum());
		account1.state = AccountState.neutral;
	}

	void withdrawMoney(Account account1) {
		if (account1.balance > (account1.processingMoney + balanceMinimum))  {
			Phonebook.getPhonebook().getBank().vault -= account1.processingMoney;
			account1.getCustomer().msgHereIsYourMoney(account1.processingMoney);
		}
		else
			account1.getCustomer().msgInsufficientFunds();

		account1.processingMoney = 0;
		account1.state = AccountState.neutral;
	}

	void depositMoney(Account account1) {
		Phonebook.getPhonebook().getBank().vault += account1.processingMoney;
		print("$" + account1.processingMoney + " deposited into bank vault");
		account1.balance +=  account1.processingMoney;
		account1.creditScore += account1.processingMoney/10;	//every time you deposit money, your credit goes up the bank can trust that you have money
		account1.getCustomer().msgDepositReceived();
		account1.state = AccountState.neutral;	
	}

	void requestLoan (Account account1) {
		Phonebook.getPhonebook().getBank().getLoanOfficer(test).msgIsLoanApproved(account1, this);
		account1.state = AccountState.waiting;
	}

	void closeLoan (Account account1) {	
		account1.creditScore += account1.loan/10;
		account1.loan = 0;
		account1.state = AccountState.neutral;
		account1.getCustomer().msgLoanClosed();
	}

	void approveLoan (Account account1) {
		account1.state = AccountState.neutral;
		account1.loan = account1.processingMoney;
		account1.balance += account1.loan;
		account1.getCustomer().msgYourLoanWasApproved();
	}

	void denyLoan (Account account1) {
		account1.state = AccountState.neutral;	
		account1.getCustomer().msgYourLoanWasDenied(account1.processingMoney);	//loan denied, but given your credit score you can have a loan of size (processingMoney)
	}

	public double getVault() {
		return Phonebook.getPhonebook().getBank().vault;
	}

	public List<Account> getAccounts() {
		return myAccounts;
	}
}
