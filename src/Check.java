import java.util.Calendar;

public class Check {

	private int acctNum;
	private double checkAmount;
	private Calendar dateOfCheck;
	
	//constructor
	public Check(int acctNum, double checkAmount, String dateStr) 
	{
		this.acctNum = acctNum;
		this.checkAmount = checkAmount;
		dateOfCheck = Calendar.getInstance();
		dateOfCheck.clear();
		String[] dateArray = dateStr.split("/");
		dateOfCheck.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1, Integer.parseInt(dateArray[1]));
	}
	
	//copy constructor 
	public Check(Check check)
	{
		this.acctNum = check.acctNum;
		this.checkAmount = check.checkAmount;
		this.dateOfCheck = check.dateOfCheck;
	}
	
	//.toString() method
	public String toString()
	{
		String str = "Account Number: " + acctNum;
		str += "\nCheck Amount: " + checkAmount;
		str += "\nCheck Date: " + dateOfCheck;
		return str;
	}
	//getters
	public int getAcctNum()
	{
		return acctNum;
	}
	
	public double getCheckAmount() 
	{
		return checkAmount;
	}
	
	public Calendar getDateOfCheck()
	{
		return dateOfCheck;
	}
	
	public String getDateOfCheckStr()
	{
		String str;
		str = String.format("%02d/%02d/%4d",
				dateOfCheck.get(Calendar.MONTH) + 1,
				dateOfCheck.get(Calendar.DAY_OF_MONTH),
				dateOfCheck.get(Calendar.YEAR));
		return str;
	}

}
