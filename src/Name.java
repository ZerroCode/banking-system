
public class Name extends genName {
	
	//no-arg constructor
	public Name()
	{
		super();
	}

	//constructor with parameters
	public Name(String last, String first)
	{
		super(last, first);
	}

	//copy constructor
	public Name(Name name)
	{
		super(name);
	}

	//.equals() method
	public boolean equals(Name name) 
	{
        return last.equals(name.last) && first.equals(name.first);
    }
	
	//.toString method
	public String toString()
	{
		return String.format("%8s  %10s", last, first);
		
	}
	
	}
