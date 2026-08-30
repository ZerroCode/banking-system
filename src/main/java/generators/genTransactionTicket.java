package generators;

import java.util.Calendar;

public abstract class genTransactionTicket {
	
	protected int acctNum;
	protected Calendar dateOfTransaction;
	protected String typeOfTransaction;
	protected double amountOfTransaction;
	protected int termOfCD;
	protected String acctSSN = "";
	
	//constructors with parameters
	public genTransactionTicket(int acctNum, String dateStr, String typeOfTransaction, double amountOfTransaction, int termOfCD)
	{
		this.acctNum = acctNum;
		dateOfTransaction = Calendar.getInstance();
		dateOfTransaction.clear();
		String[] dateArray = dateStr.split("/");
		dateOfTransaction.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1, Integer.parseInt(dateArray[1]));
		this.typeOfTransaction = typeOfTransaction;
		this.amountOfTransaction = amountOfTransaction;
		this.termOfCD = termOfCD;
	}
	
	public genTransactionTicket(String acctSSN, String typeOfTransaction)
	{
		this.acctSSN = acctSSN;
		this.typeOfTransaction = typeOfTransaction;
	}
	
	//copy constructor
	public genTransactionTicket(genTransactionTicket ticket)
	{
		this.acctNum = ticket.acctNum;
		this.dateOfTransaction = ticket.dateOfTransaction;
		this.typeOfTransaction = ticket.typeOfTransaction;
		this.amountOfTransaction = ticket.amountOfTransaction;
		this.termOfCD = ticket.termOfCD;
		this.acctSSN = ticket.acctSSN;
	}
	
	//getters
	public int getAcctNum()
	{
		return acctNum;
	}
	
	public String getDateOfTransactionStr()
	{
		String str;
		str = String.format("%02d/%02d/%4d",
				dateOfTransaction.get(Calendar.MONTH) + 1,
				dateOfTransaction.get(Calendar.DAY_OF_MONTH),
				dateOfTransaction.get(Calendar.YEAR)
							);
		return str;
	}
	
	public Calendar getDateOfTransaction()
	{
		return dateOfTransaction;
	}
	
	public String getTransactionType()
	{
		return typeOfTransaction;
	}
	
	public double getTransactionAmount() 
	{
		return amountOfTransaction;
	}
	
	public int getTermOfCD()
	{
		return termOfCD;
	}
	
	public String getAcctSSN()
	{
		return acctSSN;
	}

}
