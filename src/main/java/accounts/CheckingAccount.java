package accounts;

import java.util.Calendar;
import models.Account;
import models.Bank;
import models.Depositor;
import models.Check;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;

public class CheckingAccount extends Account {
	
	public CheckingAccount()
	{
		super();
	}
	
	//parameterized constructor
	public CheckingAccount(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance)
	{
		super(depositor, acctNum, acctType, acctStatus, balance);
	}

	//copy constructor
	public CheckingAccount(CheckingAccount account)
	{
		super(account);
	}
	
	/*Method makeWithdrawal
	 * Input:
	 * ticket - reference to the transaction ticket
	 *Process:
	 * creates transaction receipt with account information and new balance if withdrawal is valid and/or reached CD limit. returns receipt with new balance
	 * otherwise, returns receipt with error
	 * output:
	 * returns receipt with new balance
	 * output:
	 * if withdrawal is valid and/or reached CD limit. returns receipt with new balance
	 * otherwise, returns receipt with error
	 */
	public TransactionReceipt makeWithdrawal(TransactionTicket ticket)
	{
														//invalid account
		if(acctStatus.equals("closed"))
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is closed", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
		if(ticket.getTransactionAmount() <= 0)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Invalid Withdrawal Amount", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
		if(ticket.getTransactionAmount() > balance)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "account does not contain sufficient funds", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
														//valid account
		if(balance < 2500)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - (ticket.getTransactionAmount() + 1.50));
			balance -= ticket.getTransactionAmount() + 1.50;
			addTransaction(receipt);
			Bank.subToStaticAmount(ticket.getTransactionAmount() + 1.50, acctType);
		    return receipt;
		}
		
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - ticket.getTransactionAmount());
		balance -= ticket.getTransactionAmount();
		addTransaction(receipt);
		Bank.subToStaticAmount(ticket.getTransactionAmount(), acctType);
	    return receipt;
		
	}
	
	/*Method clearCheck
	 * Input:
	 * check - references to the Check 
	 *Process:
	 * creates transaction receipt with account information and new balance if clearCheck is valid. returns receipt with new balance
	 * otherwise, returns receipt with error
	 * output:
	 * creates transaction receipt with account information and new balance if clearCheck is valid. returns receipt with new balance
	 * otherwise, returns receipt with error
	 */
	public TransactionReceipt clearCheck(Check check)
	{
		Calendar today = Calendar.getInstance();
		Calendar sixMonthsAgo = (Calendar) today.clone();
	    sixMonthsAgo.add(Calendar.MONTH, -6);
	    TransactionTicket ticket = new TransactionTicket(check.getAcctNum(), check.getDateOfCheckStr(), "Clear Check", check.getCheckAmount(), 0);
	    													//invalid account
	    if(acctStatus.equals("closed"))
		{
	    	TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is closed", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
	    if (check.getDateOfCheck().after(today)) {
	    	TransactionReceipt receipt = new TransactionReceipt(ticket, false, " Check not cleared - Post-dated check: " + check.getDateOfCheckStr(), acctType, balance, balance);
	        addTransaction(receipt);
		    return receipt;
	    }
	    if (check.getDateOfCheck().before(sixMonthsAgo)) {
	    	TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Check is older than six months", acctType, balance, balance);
	        addTransaction(receipt);
		    return receipt;
	    }
	    if (check.getCheckAmount() > balance) {
	        balance -= 2.50; // Bounced check fee
	        TransactionReceipt receipt = new TransactionReceipt(ticket, false, " Insufficient Funds Available - Bounce Fee ($2.50) Charged", acctType, balance + 2.50, balance);
	        addTransaction(receipt);
			Bank.subToStaticAmount(2.50, acctType);
		    return receipt;
	    }
	    													//valid account
	    if (balance < 2500)
	    {
	    	balance -= check.getCheckAmount() + 1.50;
		    TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance + check.getCheckAmount() + 1.50, balance);
		    addTransaction(receipt);
			Bank.subToStaticAmount(check.getCheckAmount() + 1.50, acctType);
		    return receipt;
	    }
	    
	    balance -= check.getCheckAmount();
	    TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance + check.getCheckAmount(), balance);
	    addTransaction(receipt);
		Bank.subToStaticAmount(check.getCheckAmount(), acctType);
	    return receipt;
		
	}
}
