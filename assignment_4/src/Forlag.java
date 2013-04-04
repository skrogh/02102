
public class Forlag {

	private String name;
	private String sted;
	
	public Forlag( String name, String sted ) {
		this.name = name;
		this.sted = sted;
	}

    public String getName() { return this.name; }
    public String getSted() { return this.sted; }

    public String toString() {
        String returnString = "Navn: " + name + "\n";
        returnString += "Sted: " + sted;
        
        return returnString;
    }
	
}
