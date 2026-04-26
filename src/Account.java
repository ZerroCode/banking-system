import java.util.ArrayList;
public class Account extends genAccount {
	
	//no-arg constructor
	public Account()
	{
		super();
	}
	
	//constructor with parameters
	public Account(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance)
	{
		super(depositor, acctNum, acctType, acctStatus, balance);
	}
		
	//copy constructor
	public Account(Account account)
	{
		super(account);
	}

	
	//,toString() method
	public String toString()
	{
        return String.format("%10s  %8d  %9s %7s  $%7.2f",
                depositor,
                acctNum,
                acctType,
                acctStatus,
                balance
                );
	}
	
	//.equals() method
	public boolean equals(Account account)
	{
        return depositor.equals(account.depositor) && acctNum == account.acctNum;
    }
	
	//definitely not a setter
	public void addTransaction(TransactionReceipt recepit)
	{
		acctHistory.add(recepit);
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
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance);
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
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is closed", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
		if (ticket.getTransactionAmount() <= 0)
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Invalid Deposit Amount", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
															//valid account
		
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance + ticket.getTransactionAmount());
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
		
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance - ticket.getTransactionAmount());
		balance -= ticket.getTransactionAmount();
		addTransaction(receipt);
		Bank.subToStaticAmount(ticket.getTransactionAmount(), acctType);
	    return receipt;
		
	}
	
	
	/*Method reopenAcct
	 * Input:
	 * ticket - references to the transaction ticket 
	 *Process:
	 * if account is already open, returns receipt with error message
	 * otherwise, returns receipt with new status
	 * output:
	 * if account is already open returns receipt with error message
	 * otherwise, returns receipt with new status
	 */
	public TransactionReceipt reopenAcct(TransactionTicket ticket)
	{
															//invalid account
		if(acctStatus.equals("open"))
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is already open", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
															//valid account
		acctStatus = "open";
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance);
		addTransaction(receipt);
	    return receipt;
		
	}
	
	/*Method closeAcct
	 * Input:
	 * ticket - references to the transaction ticket 
	 *Process:
	 * if account is already closed, returns receipt with error message
	 * otherwise, returns receipt with new status
	 * output:
	 * if account is already closed, returns receipt with error message
	 * otherwise, returns receipt with new status
	 */
	public TransactionReceipt closeAcct(TransactionTicket ticket)
	{
															//invalid account
		if(acctStatus.equals("closed"))
		{
			TransactionReceipt receipt = new TransactionReceipt(ticket, false, "Account is already closed", acctType, balance, balance);
			addTransaction(receipt);
		    return receipt;
		}
															//valid account
		acctStatus = "closed";
		TransactionReceipt receipt = new TransactionReceipt(ticket, true, "", acctType, balance, balance);
		addTransaction(receipt);
	    return receipt;
		
	}
	
	/**
	 * Method getTransactionHistory
	 * Input:
	 *  ticket - reference to the transaction ticket
	 * Process:
	 *  Retrieves the transaction history of the account.
	 *  Filters transactions based on the date provided in the transaction ticket.
	 *  Returns only transactions that occurred on or before the date specified in the ticket.
	 * Output:
	 *  Returns an ArrayList of TransactionReceipt objects containing the account's transaction history.
	 */
	public ArrayList<TransactionReceipt> getTransactionHistory(TransactionTicket ticket)
	{
		ArrayList<TransactionReceipt> history = new ArrayList<>();

	    for (TransactionReceipt receipt : acctHistory) {
	        // Ensure transactions before or on the date in the ticket are included
	        if (!receipt.getTransactionTicket().getDateOfTransaction().after(ticket.getDateOfTransaction())) 
	        {
	            history.add(receipt);
	        }
	    }
	    return history;
	}
	
	
}
