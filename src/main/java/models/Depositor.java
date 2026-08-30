package models;

import generators.genDepositor;

public class Depositor extends genDepositor {
	
	//no-arg constructor
	public Depositor()
	{
		super();
	}
	
	//constructor with parameters
	public Depositor(Name name, String SSN)
	{
		super(name, SSN);
	}
	
	//copy constructor
	public Depositor(Depositor depositor)
	{
		super(depositor);
	}
	
	//.equals() method 
	public boolean equals(Depositor depositor)
	{
        return name.equals(depositor.name) && SSN.equals(depositor.SSN);
    }
	
	//.toString() method
	public String toString()
	{
		return String.format("%8s  %10s", name, SSN);
	}

}
