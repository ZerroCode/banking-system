package models;

import java.util.ArrayList;
import java.util.Calendar;
import models.Account;
import accounts.CDAccount;
import accounts.SavingsAccount;
import accounts.CheckingAccount;
import transactions.TransactionReceipt;
import transactions.TransactionTicket;
import models.Check;

public class Bank {
	
	private final ArrayList <Account> bank;
	private static double totalAmountInSavingsAccts = 0;
	private static double totalAmountInCheckingAccts = 0;
	private static double totalAmountInCDAccts = 0;
	private static double totalAmountInAllAccts = 0;
	
	//no-arg constructors
	public Bank()
	{
		this.bank = new ArrayList <Account>();
	}
	
	
	//getters
	public ArrayList <Account> getAccounts()
	{
		return bank;
	}
	
	public Account getAcct(int index)
	{
		Account acct = new Account();
		if(bank.get(index).getAcctType().equals("CD"))
    	{
    		acct = new CDAccount((CDAccount) bank.get(index));
    	}
    	if(bank.get(index).getAcctType().equals("Savings"))
    	{
    		acct = new SavingsAccount((SavingsAccount) bank.get(index));
    	}
    	if(bank.get(index).getAcctType().equals("Checking"))
    	{
    		acct = new CheckingAccount((CheckingAccount) bank.get(index));
    	}
		return acct;
	}
	
	public int getNumAccts()
	{
		return bank.size();
	}
	
	public static double getTotalAmountInSavingsAccts()
	{
		return totalAmountInSavingsAccts;
	}
	
	public static double getTotalAmountInCheckingAccts()
	{
		return totalAmountInCheckingAccts;
	}
	
	public static double getTotalAmountInCDAccts()
	{
		return totalAmountInCDAccts;
	}
	
	public static double getTotalAmountInAllAccts()
	{
		return totalAmountInAllAccts;
	}
		
	//addition for static methods
	public static void addToStaticAmount(double amount, String acctType)
	{
		switch (acctType) 
		{
        case "Savings":
            totalAmountInSavingsAccts += amount;
            break;
        case "Checking":
            totalAmountInCheckingAccts += amount;
            break;
        case "CD":
            totalAmountInCDAccts += amount;
            break;
        }
		totalAmountInAllAccts += amount;
	}
	
	//subtraction for static methods
	public static void subToStaticAmount(double amount, String acctType)
	{
		switch (acctType) 
		{
        case "Savings":
            totalAmountInSavingsAccts -= amount;
            break;
        case "Checking":
            totalAmountInCheckingAccts -= amount;
            break;
        case "CD":
            totalAmountInCDAccts -= amount;
            break;
        }
		totalAmountInAllAccts -= amount;
	}
	
	//methods:
	
	
	/* Method findAcct:
	 * Input:
	 *  bank - reference to array of accounts
	 *  numAccts - number of active accounts
	 *  requestedAccount - requested account number
	 * Process:
	 *  Performs a linear search on the acctNunArray for the requested account
	 * Output:
	 *  If found, the index of the requested account is returned
	 *  Otherwise, returns -1
	 */
	private int findAcct(int requestedAccount)
	{
	    for (int index = 0; index < bank.size(); index++) 
	    {
	        if (bank.get(index).getAcctNum() == requestedAccount)
	        {
	            return index;
	        }
	    }
	    return -1;
	}
	
	/* Method getBalance:
	 * Input:
	 * ticket - reference to the transaction ticket
	 * Process:
	 * Calls .findAcct() to see if the account exists
	 * If the account exists, returns a transaction receipt through the getBalance method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 * Output:
	 * If the account exists, returns a transaction receipt through the getBalance method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 */
	public TransactionReceipt getBalance(TransactionTicket ticket)
	{
		int index;
		index = findAcct(ticket.getAcctNum());
	    														//invalid account
	    if (index == -1)                                   
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" does not exist", "", 0, 0);
	    }
	    														//valid account
	    return bank.get(index).getBalance(ticket);
	    
	}
	
	/* Method makeDeposit:
	 * Input:
	 * ticket - reference to the transaction ticket
	 * Process:
	 * Calls .findAcct() to see if the account exists
	 * If the account exists, returns a transaction receipt through the makeDeposit method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 * Output:
	 * If the account exists, returns a transaction receipt through the makeDeposit method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 */
	public TransactionReceipt makeDeposit(TransactionTicket ticket)
	{
		int index;
		index = findAcct(ticket.getAcctNum());
	    														//invalid account
	    if (index == -1)                                   
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" does not exist", "", 0, 0);
	    }
	    														//valid account
	    return bank.get(index).makeDeposit(ticket);
	    
	}
	
	/* Method makeWithdrawal:
	 * Input:
	 * ticket - reference to the transaction ticket
	 * Process:
	 * Calls .findAcct() to see if the account exists
	 * If the account exists, returns a transaction receipt through the makeWithdrawal method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 * Output:
	 * If the account exists, returns a transaction receipt through the makeDeposit method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 */
	public TransactionReceipt makeWithdrawal(TransactionTicket ticket)
	{
		int index;
		index = findAcct(ticket.getAcctNum());
	    														//invalid account
	    if (index == -1)                                       
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" does not exist", "", 0, 0);
	    }
	    														//valid account
	    return bank.get(index).makeWithdrawal(ticket);
	}
	
	/* Method clearCheck:
	 * Input:
	 * ticket - reference to the transaction ticket
	 * Process:
	 * Calls .findAcct() to see if the account exists
	 * If the account exists, returns a transaction receipt through the clearCheck method in the Account class
	   otherwise, creates a transaction receipt with a error and returns it
	 * Output:
	 * If the account exists, returns a transaction receipt through the clearCheck method in the Account class
	 * otherwise, creates a transaction receipt with a error and returns it
	 */
	public TransactionReceipt clearCheck(Check check)
	{
		int index;
		index = findAcct(check.getAcctNum());
	    														//invalid account
	    if (index == -1)                                       
	    {
	    	return new TransactionReceipt(null, false, "Account number "+ check.getAcctNum() +" does not exist", "", 0, 0);
	    }
	    if(!bank.get(index).getAcctType().equals("Checking"))
	    {
		    TransactionTicket ticket = new TransactionTicket(check.getAcctNum(), check.getDateOfCheckStr(), "Clear Check", check.getCheckAmount(), 0);
	    	TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Cannot clear check from non-CD account", bank.get(index).getAcctType(), bank.get(index).getBalance(), bank.get(index).getBalance());
	    	bank.get(index).addTransaction(receipt);
		    return receipt;
	    }
	    		                								//valid account
		CheckingAccount CDAcct = (CheckingAccount) bank.get(index);
	    return  CDAcct.clearCheck(check);
	    
	}
	    
	/*Method openNewAcct
	 * Input:
	 * account - reference to the requested new account
	 * Process:
	 * Calls .finAacct() to see if the account exists
	 * If the account exists, creates a transaction receipt with a error and returns it
	 * If the account does not exists, then method will create a new account and return a transaction receipt
	 * output:
	 * If the account exists, creates a transaction receipt with a error and returns it
	 * If the account does not exists, then method will create a new account and return a transaction receipt
	 */
	public TransactionReceipt openNewAcct(Account account)
	{
		Calendar today = Calendar.getInstance();
		String todayStr = String.format("%02d/%02d/%4d",
				today.get(Calendar.MONTH) + 1,
				today.get(Calendar.DAY_OF_MONTH),
				today.get(Calendar.YEAR));
	    TransactionTicket ticket = new TransactionTicket(account.getAcctNum(), todayStr, "Open New Account", account.getBalance(), 0);
	    
																//invalid account
        for (Account value : bank) {
            if (value.equals(account)) {
                return new TransactionReceipt(ticket, false, "Account number " + account.getAcctNum() + " already exists", "", 0, 0);
            }
        }
	                                                        //valid account
	    	bank.add(account);
	    	if(account.getAcctType().equals("CD"))
	    	{
	    		CDAccount CDAcct = (CDAccount) account;
		    	TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", CDAcct.getAcctType(), 0, CDAcct.getBalance(), CDAcct.getMaturityDateStr());
		    	bank.get(bank.size() - 1).addTransaction(receipt);
		    	addToStaticAmount(CDAcct.getBalance(), CDAcct.getAcctType());
		    	return receipt;
	    	}
	    	TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", account.getAcctType(), 0, account.getBalance());
	    	bank.get(bank.size() - 1).addTransaction(receipt);
	    	addToStaticAmount(account.getBalance(), account.getAcctType());
	    	return receipt;
	    
	}

	/*Method acctInfo
	 * Input:
	 * ticket - reference to the transaction ticket
	 * Process:
	 * Calls .findAcct() to see if the account exists
	 * If the account does not exists or has a non-zero balance, method return transaction receipt with error 
	 * otherwise the account is deleted and returns transaction receipt
	 * output:
	 * method returns the new number of accounts
	 * output:
	 * If the account does not exists or has a non-zero balance, method return transaction receipt with error 
	 * otherwise the account is deleted and returns transaction receipt
	 */
	public TransactionReceipt deleteAcct(TransactionTicket ticket)
	{
		int index;
		index = findAcct(ticket.getAcctNum());
		
	    if (index == -1)                                        //invalid account
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" does not exist", "", 0, 0);
	    }
	    if(bank.get(index).getBalance() != 0)
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" has a non-zero balance", "", 0, 0);
	    }  
	    														//valid account
	    bank.remove(index);
	    return new TransactionReceipt(ticket, true, "", "", 0, 0);
	
	}
	
	/*Method acctInfo
	 * Input:
	 * ticket - reference to the transaction ticket
	 * Process:
	 * counts how many accounts belong to the SSN
	 * if any found, creates transaction receipt and returns with array of accounts
	 * otherwise, creates a transaction receipt with a error and returns it
	 * output:
	 * if any found, creates transaction receipt and returns with array of accounts
	 * otherwise, creates a transaction receipt with a error and returns it
	 */
	public TransactionReceipt acctInfo(TransactionTicket ticket)
	{
		int count = 0;

	    // First, count how many accounts belong to the SSN
        for (Account value : bank) {
            if (value.getDepositor().getSSN().equals(ticket.getAcctSSN())) {
                count++;
            }
        }
	    if (count == 0) 
	    {
            return new TransactionReceipt(ticket, false, "No accounts found for SSN " + ticket.getAcctSSN(), null);
	    }
	    
	    // Store matching accounts
	    Account[] matchingAccounts = new Account[count];
	    int index = 0;
        for (Account account : bank) {
            if (account.getDepositor().getSSN().equals(ticket.getAcctSSN())) {
                if (account.getAcctType().equals("CD")) {
                    CDAccount CDAcct = (CDAccount) account;
                    matchingAccounts[index++] = new CDAccount(CDAcct);
                }
                if (account.getAcctType().equals("Savings")) {
                    SavingsAccount SAcct = (SavingsAccount) account;
                    matchingAccounts[index++] = new SavingsAccount(SAcct);
                }
                if (account.getAcctType().equals("Checking")) {
                    CheckingAccount CAcct = (CheckingAccount) account;
                    matchingAccounts[index++] = new CheckingAccount(CAcct);
                }
            }
        }
	    return new TransactionReceipt(ticket, true, "", matchingAccounts);
	}
	
	/*Method closeAcct
	 * Input:
	 * ticket - references to the transaction ticket 
	 * Process:
	 * if account does not exist, returns receipt with error message
	 * otherwise, calls closeAcct in Account class
	 * output:
	 * if account does not exist, returns receipt with error message
	 * otherwise, calls closeAcct in Account class
	 */
	public TransactionReceipt reopenAcct(TransactionTicket ticket)
	{
		int index;
		index = findAcct(ticket.getAcctNum());
	    														//invalid account
	    if (index == -1)                                       
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" does not exist", "", 0, 0);
	    }
																//valid account
	    return bank.get(index).reopenAcct(ticket);				
	}

	/*Method closeAcct
	 * Input:
	 * ticket - references to the transaction ticket 
	 * Process:
	 * if account does not exist, returns receipt with error message
	 * otherwise, calls closeAcct in Account class
	 * output:
	 * if account does not exist, returns receipt with error message
	 * otherwise, calls closeAcct in Account class
	 */
	public TransactionReceipt closeAcct(TransactionTicket ticket)
	{
		int index;
		index = findAcct(ticket.getAcctNum());
																//invalid account
	    if (index == -1)                                
	    {
	    	return new TransactionReceipt(ticket, false, "Account number "+ ticket.getAcctNum() +" does not exist", "", 0, 0);
	    }
	    														//valid account
	    return bank.get(index).closeAcct(ticket);				
	}
	
	

}
