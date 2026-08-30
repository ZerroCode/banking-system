package accounts;

import java.util.Calendar;
import accounts.SavingsAccount;
import models.Bank;
import models.Depositor;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;

public class CDAccount extends SavingsAccount {
	
	private Calendar maturityDate;
	
	//no-arg constructor
	public CDAccount()
	{
		super();
		maturityDate = Calendar.getInstance();
	}
	
	//parameterized constructor
	public CDAccount(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance, String dateStr)
	{
		super(depositor, acctNum, acctType, acctStatus, balance);
		maturityDate = Calendar.getInstance();
		maturityDate.clear();
		String[] dateArray = dateStr.split("/");
		maturityDate.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1, Integer.parseInt(dateArray[1]));
	}
	
	//copy constructor
	public CDAccount(CDAccount account)
	{
		super(account);
		this.maturityDate = account.maturityDate;
	}

	//getters 
	public Calendar getMaturityDate()
	{
		return  maturityDate;
	}
	
	public String getMaturityDateStr() 
	{
		String str;
		str = String.format("%02d/%02d/%4d",
				maturityDate.get(Calendar.MONTH) + 1,
				maturityDate.get(Calendar.DAY_OF_MONTH),
				maturityDate.get(Calendar.YEAR));
		return str;
	}
	
	//.toString() method
	public String toString()
	{
		String str = super.toString();
		str += String.format("%16s",getMaturityDateStr());
		return str;
	}
	
	//methods:
	
	/*Method getBalance
	 * Input:
	 * ticket - reference to the transaction ticket
	 *Process:
	 * creates transaction receipt with account information and balance
	 * returns receipt with balance
	 * output:
	 * returns receipt with balance
	 */
	public TransactionReceipt getBalance(TransactionTicket ticket)
	{
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance, getMaturityDateStr());
		addTransaction(receipt);
	    return receipt;
	}
	
	/*Method makeDeposit
	 * Input:
	 * ticket - reference to the transaction ticket
	 *Process:
	 * creates transaction receipt with account information and new balance if deposit is valid and/or reached CD limit. returns receipt with new balance
	 * otherwise, returns receipt with error
	 * output:
	 * if deposit is valid and/or reached CD limit. returns receipt with new balance
	 * otherwise, returns receipt with error
	 */
	public TransactionReceipt makeDeposit(TransactionTicket ticket)
	{
		if(acctStatus.equals("closed"))
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is closed", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
		if (ticket.getTransactionAmount() <= 0)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Invalid Deposit Amount", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
		if(ticket.getDateOfTransaction().before(maturityDate))
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "CD maturity date " + getMaturityDateStr() + " not reached", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
															//valid account
		maturityDate = Calendar.getInstance();
		maturityDate.add(Calendar.MONTH, ticket.getTermOfCD());
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance + ticket.getTransactionAmount(), getMaturityDateStr());
		balance += ticket.getTransactionAmount();
		addTransaction(receipt);
		Bank.addToStaticAmount(ticket.getTransactionAmount(), acctType);
	    return receipt;
		
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
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is closed", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
		if(ticket.getTransactionAmount() <= 0)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Invalid Withdrawal Amount", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
		if(ticket.getTransactionAmount() > balance)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "account does not contain sufficient funds", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
		if(ticket.getDateOfTransaction().before(maturityDate))
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "CD maturity date " + getMaturityDateStr() + " not reached", acctType, balance, balance, getMaturityDateStr());
			addTransaction(receipt);
		    return receipt;
		}
														//valid account
		maturityDate = Calendar.getInstance();
		maturityDate.add(Calendar.MONTH, ticket.getTermOfCD());
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - ticket.getTransactionAmount(), getMaturityDateStr());
		balance -= ticket.getTransactionAmount();
		addTransaction(receipt);
		Bank.subToStaticAmount(ticket.getTransactionAmount(), acctType);
		return receipt;
		
	}
	
	
	

}
