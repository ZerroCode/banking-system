package generators;

public abstract class genName {
	
	protected String last;
	protected String first;
	
	//no-arg constructor
	public genName(){
		this.last = "";
		this.first = "";
	}

	//constructor with parameters
	public genName(String last, String first){
		this.last = last;
		this.first = first;
	}

	//copy constructor
	public genName(genName name){
		this.last = name.last;
		this.first = name.first;
	}
	
	//getters
	public String getLastName(){
		return last;
	}

	public String getFirstName(){
		return first;
	}
	
	}
