package generators;

import models.Name;

public abstract class genDepositor {
	
	protected Name name;
	protected String SSN;
	
	//no-arg constructor
	public genDepositor()
	{
		this.name = new Name();
		this.SSN = "";
	}
	
	//constructor with parameters
	public genDepositor(Name name, String SSN)
	{
		this.name = new Name(name);
		this.SSN = SSN;
	}
	
	//copy constructor
	public genDepositor(genDepositor depositor)
	{
		this.name = depositor.name;
		this.SSN = depositor.SSN;
	}
	
	//getters
	public String getSSN()
	{
		return SSN;
	}
	
	public Name getName()
	{
		return new Name(name);
	}
	
}
