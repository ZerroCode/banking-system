
public class TransactionTicket extends genTransactionTicket {
	
	//constructors with parameters
	public TransactionTicket(int acctNum, String dateStr, String typeOfTransaction, double amountOfTransaction, int termOfCD)
	{
		super(acctNum, dateStr, typeOfTransaction, amountOfTransaction, termOfCD);
	}
	
	public TransactionTicket(String acctSSN, String typeOfTransaction)
	{
		super(acctSSN, typeOfTransaction);
	}
	
	//copy constructor
	public TransactionTicket(TransactionTicket ticket)
	{
		super(ticket);
	}
	
	//.toString() method
	public String toString()
	{
		String str = "Transaction Requested: " + typeOfTransaction;
		if(acctSSN.isEmpty())
		{
			str += "\nAccount Number: " + acctNum;
		}
		else
		{
			str += "\nSocial Security Number: " + acctSSN;
		}
		
		return str;
	}
	

}
