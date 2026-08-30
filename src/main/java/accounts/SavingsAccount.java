package accounts;

import models.Account;
import models.Depositor;

public class SavingsAccount extends Account {
	
	//no-arg constructor
	public SavingsAccount()
	{
		super();
	}
	
	//parameterized constructor
	public SavingsAccount(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance)
	{
		super(depositor, acctNum, acctType, acctStatus, balance);
	}
	
	//copy constructor
	public SavingsAccount(SavingsAccount account)
	{
		super(account);
	}


}
