package bank.interfaces;

public interface BankCustomer {

	void msgGoToTeller(BankTeller tell1);

	void msgCaughtYou();

	void msgGotAway();

	void msgLoanClosed();

	void msgYourLoanWasApproved();

	void msgYourLoanWasDenied(double processingMoney);

	void msgDepositReceived();

	void msgInsufficentFunds();

	void msgBankrupt();

	void msgHereIsYourMoney(double processingMoney);

	void msgHereIsNewAccount(int accountNum);

	void msgNoTellerAvailable();

}
