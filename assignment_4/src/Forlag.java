
public class Forlag {

	private String name;
	private String sted;
	
	public Forlag( String name, String sted ) {
		this.name = name;
		this.sted = sted;
	}

    public String getName() { return this.name; }
    public String getSted() { return this.sted; }

    public void printInfo() {
        System.out.println( "Navn: " + name );
        System.out.println( "Sted: " + sted );
    }
	
}
