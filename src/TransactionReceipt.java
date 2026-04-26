import java.util.ArrayList;

public class TransactionReceipt extends genTransactionReceipt {
	
	//constructors with parameters
	public TransactionReceipt(TransactionTicket transactionTicket, boolean successIndicatorFlag, String reasonForFailure, String accountType, double PreTransactionBalance, double PostTransactionBalance)
	{
		super(transactionTicket, successIndicatorFlag, reasonForFailure, accountType, PreTransactionBalance, PostTransactionBalance);
	}
	
	public TransactionReceipt(TransactionTicket transactionTicket, boolean successIndicatorFlag, String reasonForFailure, String accountType, double PreTransactionBalance, double PostTransactionBalance, String dateStr)
	{
		super(transactionTicket, successIndicatorFlag, reasonForFailure, accountType, PreTransactionBalance, PostTransactionBalance, dateStr);
	}
	
	public TransactionReceipt(TransactionTicket transactionTicket, boolean successIndicatorFlag, String reasonForFailure, Account[] accts) 
	{
		super(transactionTicket, successIndicatorFlag, reasonForFailure, accts);
	}
	
	//copy constructor
	public TransactionReceipt(TransactionReceipt receipt)
	{
		super(receipt);
	}
	
	//.toString() method                                  
		public String toString()
		{
			int accountCount = 0;
			StringBuilder str = new StringBuilder(transactionTicket + "\n");
			switch (transactionTicket.getTransactionType()) 
			{
	        case "Balance Inquiry":
	        	if(!successIndicatorFlag)
	        	{
	        		str.append("Error: ").append(reasonForFailure);
	        	}
	        	else
	        	{
	        		str.append("Account Type: ").append(accountType);
	        		str.append("\nCurrent Balance: $").append(PostTransactionBalance);
	        	}
	            break;
	            
	        case "Deposit":
        		str.append("Account Type: ").append(accountType).append("\n");
	        	str.append(String.format("Old Balance: $%.2f\n", PreTransactionBalance));
	            str.append(String.format("Amount to Deposit: $%.2f\n", transactionTicket.getTransactionAmount()));
	            if (successIndicatorFlag) 
	            {
	                str.append(String.format("New Balance: $%.2f", PostTransactionBalance));
	                if (accountType.equals("CD")) 
	                {
	                    str.append("\nCD New Maturity Date: ").append(getPostTransactionMaturityDateStr());
	                }
	            } 
	            else 
	            {
	                str.append("Error: ").append(reasonForFailure);
	            }
	            break;
	            
	        case "Withdrawal":
        		str.append("Account Type: ").append(accountType).append("\n");
	        	str.append(String.format("Old Balance: $%.2f\n", PreTransactionBalance));
	            str.append(String.format("Amount to Withdrawal: $%.2f\n", transactionTicket.getTransactionAmount()));
	            if (successIndicatorFlag) 
	            {
	                str.append(String.format("New Balance: $%.2f", PostTransactionBalance));
	                if (accountType.equals("CD")) 
	                {
	                    str.append("\nCD New Maturity Date: ").append(getPostTransactionMaturityDateStr());
	                }
	            } 
	            else 
	            {
	                str.append("Error: ").append(reasonForFailure);
	            }
	            break;
	            
	        case "Clear Check":
	        	str.append("Account Type: ").append(accountType).append("\n");
	        	str.append(String.format("Old Balance: $%.2f\n", PreTransactionBalance));
	            str.append(String.format("Amount to Check: $%.2f\n", transactionTicket.getTransactionAmount()));
	            str.append("Check Date: ").append(transactionTicket.getDateOfTransactionStr()).append("\n");
	            if (successIndicatorFlag) 
	            {
	                str.append(String.format("New Balance: $%.2f", PostTransactionBalance));
	            } 
	            else 
	            {
	                str.append("Error: ").append(reasonForFailure);
	            }
	        	break;
	            
	        case "Open New Account":
	        	if(!successIndicatorFlag)
	        	{
	        		str.append("Error: ").append(reasonForFailure);
	        	}
	        	else
	        	{
	        		str.append("Account Type: ").append(accountType);
	        		str.append("\nNew Balance: $").append(PostTransactionBalance);
	        		if (accountType.equals("CD")) 
	                {
	                    str.append("\nCD New Maturity Date: ").append(getPostTransactionMaturityDateStr());
	                }
	        	}	        	
	        	break;
	        	
	        case "Delete Account":
	        	if(!successIndicatorFlag)
	        	{
	        		str.append("Error: ").append(reasonForFailure);
	        	}
	        	else
	        	{
	        		str.append("Account number ").append(transactionTicket.getAcctNum()).append(" has successfully been deleted");
	        	}
	        	break;
	        	
	        case "Close Account":
	        	if(!successIndicatorFlag)
	        	{
	        		str.append("Error: ").append(reasonForFailure);
	        	}
	        	else
	        	{
	        		str.append("Account number ").append(transactionTicket.getAcctNum()).append(" has successfully been closed");
	        	}
	        		break;
	        		
	        case "Reopen Account":
	        	if(!successIndicatorFlag)
	        	{
	        		str.append("Error: ").append(reasonForFailure);
	        	}
	        	else
	        	{
	        		str.append("Account number ").append(transactionTicket.getAcctNum()).append(" has successfully been reopened");
	        	}	        	
	        	break;
	        	
	        case "Account Info":
	        	if (!successIndicatorFlag) 
	    	    {
	    	        str.append("Error: ").append(reasonForFailure);
	    	    } 
	    	    else 
	    	    {
	    	        str.append("LastName   FirstName         SSN   AcctNum   AcctType  Status   Balance   Maturity Date");
	    	        Account[] accs = accounts;

	    	        for (Account acc : accs) 
	    	        {
	    	        	accountCount++;
	    	            str.append(String.format("\n%8s  %10s  %10s  %8d  %9s %7s  $%7.2f",
                                acc.getDepositor().getName().getLastName(),
                                acc.getDepositor().getName().getFirstName(),
                                transactionTicket.getAcctSSN(),
                                acc.getAcctNum(),
                                acc.getAcctType(),
                                acc.getAcctStatus(),
                                acc.getBalance()));
	    	            if (acc.getAcctType().equals("CD")) 
	    	            {
	    	            	CDAccount CDAcct = (CDAccount) acc;
	    	                str.append("   ").append(CDAcct.getMaturityDateStr());
	    	            }
	    	        }
    	            str.append("\n");
	    	        str.append(accountCount).append(" accounts were found");
	    	    }
	        	break;
	        	
	        case "Account Info With Transaction History":
	        	if (!successIndicatorFlag) 
	    	    {
	    	        str.append("Error: ").append(reasonForFailure);
	    	    } 
	    	    else 
	    	    {
	    	        str.append("LastName   FirstName         SSN   AcctNum   AcctType  Status   Balance   Maturity Date");
	    	        Account[] accs = accounts;

	    	        for (Account acc : accs) 
	    	        {
	    	        	accountCount++;
	    	            str.append(String.format("\n%8s  %10s  %10s  %8d  %9s %7s  $%7.2f",
                                acc.getDepositor().getName().getLastName(),
                                acc.getDepositor().getName().getFirstName(),
                                transactionTicket.getAcctSSN(),
                                acc.getAcctNum(),
                                acc.getAcctType(),
                                acc.getAcctStatus(),
                                acc.getBalance()));

	    	            if (acc.getAcctType().equals("CD")) 
	    	            {
	    	            	CDAccount CDAcct = (CDAccount) acc;
	    	                str.append("   ").append(CDAcct.getMaturityDateStr());
	    	            }
	    	            str.append("\n ***** Account Transactions ***** ");
	    	            str.append("\n      Date       Transaction     Amount      Status    Balance      Reason For Failure\n");
	    	            ArrayList<TransactionReceipt> history = acc.getTransactionHistory(transactionTicket);
	    	            for (TransactionReceipt transaction : history) {
	    	                str.append(String.format("%10s  %16s  $%8.2f  %10s  $%8.2f  %s\n",
                                    transaction.getTransactionTicket().getDateOfTransactionStr(),
                                    transaction.getTransactionTicket().getTransactionType(),
                                    transaction.getTransactionTicket().getTransactionAmount(),
                                    transaction.getTransactionSuccessIndicatorFlag() ? "Done" : "Failed",
                                    transaction.getPostTransactionBalance(),
                                    transaction.getTransactionFailureReason()
                            ));
	    	        }
	    	        }
    	            str.append("\n");
	    	        str.append(accountCount).append(" accounts were found");
	    	    }
	        	break;
	        }
			return str.toString();
		}

	
}
