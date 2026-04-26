import java.util.ArrayList;
public abstract class genAccount {
	
	protected int acctNum;
	protected double balance;
	protected String acctType;
	protected String acctStatus;
	protected Depositor depositor;
	protected ArrayList <TransactionReceipt> acctHistory = new ArrayList <>();
	
	//no-arg constructor
	public genAccount()
	{
		this.depositor = new Depositor();
		this.acctNum = 0;
		this.acctType = "";
		this.acctStatus = "";
		this.balance = 0.0;
	}
	
	//constructor with parameters
	public genAccount(Depositor depositor, int acctNum, String acctType, String acctStatus, double balance)
	{
		this.depositor = new Depositor(depositor);
		this.acctNum = acctNum;
		this.acctType = acctType;
		this.acctStatus = acctStatus;
		this.balance = balance;
	}
		
	//copy constructor
	public genAccount(genAccount account)
	{
		this.depositor = account.depositor;
		this.acctNum = account.acctNum;
		this.acctType = account.acctType;
		this.acctStatus = account.acctStatus;
		this.balance = account.balance;
		this.acctHistory = account.acctHistory;
	}
	
	//getters
	public int getAcctNum()
	{
		return acctNum;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public String getAcctType()
	{
		return acctType;
	}
	
	public String getAcctStatus()
	{
		return acctStatus;
	}
	
	public Depositor getDepositor()
	{
		return new Depositor(depositor);
	}
	
	
	
}
