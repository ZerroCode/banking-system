package generators;

import java.util.Calendar;
import generators.genTransactionTicket;
import transactions.TransactionTicket;
import models.Account;

public abstract class genTransactionReceipt {
	
	protected TransactionTicket transactionTicket;
	protected boolean successIndicatorFlag;
	protected String reasonForFailure;
	protected String accountType;
	protected double PreTransactionBalance;
	protected double PostTransactionBalance;
	protected Calendar postTransactionMaturityDate;
	protected Account[] accounts;
	
	
	//constructors with parameters
	public genTransactionReceipt(TransactionTicket transactionTicket, boolean successIndicatorFlag, String reasonForFailure, String accountType, double PreTransactionBalance, double PostTransactionBalance)
	{
		this.transactionTicket = new TransactionTicket(transactionTicket);
		this.successIndicatorFlag = successIndicatorFlag;
		this.reasonForFailure = reasonForFailure;
		this.accountType = accountType;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
	}
	
	public genTransactionReceipt(TransactionTicket transactionTicket, boolean successIndicatorFlag, String reasonForFailure, String accountType, double PreTransactionBalance, double PostTransactionBalance, String dateStr)
	{
		this.transactionTicket = new TransactionTicket(transactionTicket);
		this.successIndicatorFlag = successIndicatorFlag;
		this.reasonForFailure = reasonForFailure;
		this.accountType = accountType;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
		postTransactionMaturityDate = Calendar.getInstance();
		postTransactionMaturityDate.clear();
		String[] dateArray = dateStr.split("/");
		postTransactionMaturityDate.set(Integer.parseInt(dateArray[2]), 
				Integer.parseInt(dateArray[0]) - 1, 
				Integer.parseInt(dateArray[1]));
	}
	
	public genTransactionReceipt(TransactionTicket transactionTicket, boolean successIndicatorFlag, String reasonForFailure, Account[] accts) 
	{
		this.transactionTicket = transactionTicket;
		this.successIndicatorFlag = successIndicatorFlag;
		this.reasonForFailure = reasonForFailure;
		this.accounts = accts;
	}
	
	//copy constructor
	public genTransactionReceipt(genTransactionReceipt receipt)
	{
		this.transactionTicket = receipt.transactionTicket;
		this.successIndicatorFlag = receipt.successIndicatorFlag;
		this.reasonForFailure = receipt.reasonForFailure;
		this.accountType = receipt.accountType;
		this.PreTransactionBalance = receipt.PreTransactionBalance;
		this.PostTransactionBalance = receipt.PostTransactionBalance;
		this.postTransactionMaturityDate = receipt.postTransactionMaturityDate;
		this.accounts = receipt.accounts;
	}
		
	//getters
	public TransactionTicket getTransactionTicket()
	{
		return new TransactionTicket(transactionTicket);
	}
	
	public boolean getTransactionSuccessIndicatorFlag()
	{
		return successIndicatorFlag;
	}
	
	public String getTransactionFailureReason()
	{
		return reasonForFailure;
	}
	
	public String getAccountType()
	{
		return accountType;
	}
	
	public double getPreTransactionBalance()
	{
		return PreTransactionBalance;
	}
	
	public double getPostTransactionBalance()
	{
		
		return PostTransactionBalance;
	}
	
	public Calendar getPostTransactionMaturityDate()
	{
		return postTransactionMaturityDate;
	}
	
	public String getPostTransactionMaturityDateStr()
	{
		String str;
		str = String.format("%02d/%02d/%4d",
				postTransactionMaturityDate.get(Calendar.MONTH) + 1,
				postTransactionMaturityDate.get(Calendar.DAY_OF_MONTH),
				postTransactionMaturityDate.get(Calendar.YEAR));
		return str;
	}
	
	public Account[] getAccounts() 
	{
        return accounts;
    }

	
}
